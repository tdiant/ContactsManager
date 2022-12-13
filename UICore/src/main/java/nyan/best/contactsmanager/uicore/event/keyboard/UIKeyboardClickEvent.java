package nyan.best.contactsmanager.uicore.event.keyboard;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIKeyboardClickEvent extends UIKeyboardTouchLikeEvent {
    private static final HandleList handleList = new HandleList();

    public UIKeyboardClickEvent(WindowInstance window, int keyCode) {
        super(window, keyCode);
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }
}