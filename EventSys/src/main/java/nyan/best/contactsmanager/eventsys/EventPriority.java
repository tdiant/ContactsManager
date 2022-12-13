package nyan.best.contactsmanager.eventsys;

import java.util.ArrayList;
import java.util.List;

public enum EventPriority {

    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST;

    public static final List<EventPriority> priorities = new ArrayList<>() {{
        add(HIGHEST);
        add(HIGH);
        add(NORMAL);
        add(LOW);
        add(LOWEST);
    }};

}
