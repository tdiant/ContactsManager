package nyan.best.contactsmanager.ui.event;

import nyan.best.contactsmanager.eventsys.Event;
import nyan.best.contactsmanager.eventsys.HandleList;

public class LoadingFinishEvent implements Event {

    private final static HandleList handleList = new HandleList();

    @Override
    public HandleList getHandles() {
        return handleList;
    }

    public static HandleList getHandleList() {
        return handleList;
    }
}
