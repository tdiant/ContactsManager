package nyan.best.contactsmanager.eventsys;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegisteredEventHandle {

    private final EventListener listener;
    private final Method eventHandleMethod;
    private final Class<? extends Event> eventTypeClz;
    private final EventPriority priority;
    private final boolean ignoreCancelled;

    public RegisteredEventHandle(EventListener listener, Method eventHandleMethod) {
        try {
            this.listener = listener;
            this.eventHandleMethod = eventHandleMethod;

            if (eventHandleMethod.getParameterCount() != 1)
                throw new RuntimeException("Cannot register event caused by wrong parameter");
            if (!eventHandleMethod.isAnnotationPresent(EventHandle.class))
                throw new RuntimeException("Cannot register event caused by wrong annotation");

            Class<?> eventClz = eventHandleMethod.getParameterTypes()[0];
            eventTypeClz = (Class<? extends Event>) eventClz;

            EventHandle anno = eventHandleMethod.getAnnotation(EventHandle.class);
            priority = anno.priority();
            ignoreCancelled = anno.ignoreCancelled();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void fireEvent(Event event) {
        try {
            eventHandleMethod.setAccessible(true);
            eventHandleMethod.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public EventListener getListener() {
        return listener;
    }

    public Method getEventHandleMethod() {
        return eventHandleMethod;
    }

    public Class<? extends Event> getEventTypeClz() {
        return eventTypeClz;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

}
