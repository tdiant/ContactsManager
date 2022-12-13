package nyan.best.contactsmanager.eventsys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleList {

    private final Map<EventPriority, List<RegisteredEventHandle>> listeners = new HashMap<>();

    public List<RegisteredEventHandle> getAllHandles() {
        return new ArrayList<>() {{
            listeners.forEach((x, y) -> addAll(y));
        }};
    }

    public List<RegisteredEventHandle> getAllPriorityHandles(EventPriority priority) {
        return new ArrayList<>(listeners.getOrDefault(priority, new ArrayList<>()));
    }

    public void addEventHandle(RegisteredEventHandle handle) {
        if (handle == null)
            throw new RuntimeException();
        EventPriority priority = handle.getPriority();
        List<RegisteredEventHandle> ls = listeners.getOrDefault(priority, new ArrayList<>());
        ls.add(handle);
        listeners.put(priority, ls);
    }

    public void fireEvent(Event event) {
        for (EventPriority priority : EventPriority.priorities) {
            List<RegisteredEventHandle> ls = listeners.get(priority);
            if (ls == null)
                continue;
            for (RegisteredEventHandle handle : ls) {
                if (handle.getEventTypeClz().equals(event.getClass()))
                    handle.fireEvent(event);
            }
        }
    }

}
