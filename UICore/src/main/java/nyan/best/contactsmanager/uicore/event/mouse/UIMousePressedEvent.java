package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIMousePressedEvent extends UIMouseTouchLikeEvent {
    public UIMousePressedEvent(WindowInstance window, Pos cursorPos, int btnCode) {
        super(window, cursorPos, btnCode);
    }
}
