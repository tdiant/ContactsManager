package nyan.best.contactsmanager.uicore.event.keyboard;

import nyan.best.contactsmanager.uicore.event.UIEvent;
import nyan.best.contactsmanager.uicore.WindowInstance;

public abstract class UIKeyboardEvent extends UIEvent {
    public UIKeyboardEvent(WindowInstance window) {
        super(window);
    }
}
