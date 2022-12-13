package nyan.best.contactsmanager.uicore.event.window;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.event.UIEvent;

public class UIWindowClosedEvent extends UIEvent {

    private static final HandleList handleList = new HandleList();

    public UIWindowClosedEvent(WindowInstance window) {
        super(window);
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

}
