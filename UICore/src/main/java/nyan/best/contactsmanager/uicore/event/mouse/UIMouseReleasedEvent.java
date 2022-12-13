package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIMouseReleasedEvent extends UIMouseTouchLikeEvent {
    public UIMouseReleasedEvent(WindowInstance window, Pos cursorPos, int btnCode) {
        super(window, cursorPos, btnCode);
    }
}
