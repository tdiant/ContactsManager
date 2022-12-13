package nyan.best.contactsmanager.uicore.event.window;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.event.UIEvent;

public abstract class UIWindowUIEvent extends UIEvent {
    public UIWindowUIEvent(WindowInstance window) {
        super(window);
    }
}
