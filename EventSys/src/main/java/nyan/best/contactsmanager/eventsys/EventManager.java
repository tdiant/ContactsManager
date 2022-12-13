package nyan.best.contactsmanager.eventsys;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventManager {

    public static void callEvent(Event event) {
        try {
            Class<? extends Event> clz = event.getClass();
            Method handleGetter = clz.getMethod("getHandleList");
            HandleList hs = (HandleList) handleGetter.invoke(null);
            hs.fireEvent(event);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void registerEventHandles(EventListener listener) {
        Class<?> cls = listener.getClass();
        for (Method m : cls.getMethods()) {
            if (!m.isAnnotationPresent(EventHandle.class))
                continue;
            try {
                RegisteredEventHandle reh = new RegisteredEventHandle(listener, m);
                HandleList hl = (HandleList) reh.getEventTypeClz()
                        .getMethod("getHandleList")
                        .invoke(null);
                hl.addEventHandle(reh);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
