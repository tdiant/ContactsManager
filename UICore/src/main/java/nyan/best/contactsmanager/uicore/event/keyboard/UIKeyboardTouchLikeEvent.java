package nyan.best.contactsmanager.uicore.event.keyboard;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;

public abstract class UIKeyboardTouchLikeEvent extends UIKeyboardEvent {

    private final int keyCode;

    public UIKeyboardTouchLikeEvent(WindowInstance window, int keyCode) {
        super(window);
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }


}
