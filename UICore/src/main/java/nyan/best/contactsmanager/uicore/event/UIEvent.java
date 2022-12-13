package nyan.best.contactsmanager.uicore.event;

import nyan.best.contactsmanager.eventsys.Event;
import nyan.best.contactsmanager.uicore.WindowInstance;

public abstract class UIEvent implements Event {

    private final WindowInstance window;

    public UIEvent(WindowInstance window) {
        this.window = window;
    }

    public WindowInstance getWindow() {
        return window;
    }

}
