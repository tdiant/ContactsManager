package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.event.UIEvent;
import nyan.best.contactsmanager.uicore.WindowInstance;

public abstract class UIMouseEvent extends UIEvent {

    public static final int MOUSE_BTN_CODE_LEFT = 0;
    public static final int MOUSE_BTN_CODE_RIGHT = 1;
    public static final int MOUSE_BTN_CODE_SCROLL_CLICK = 2;
    public static final int MOUSE_BTN_CODE_BACK = 3;

    private final Pos currentCursorPos;

    public UIMouseEvent(WindowInstance window, Pos currentCursorPos) {
        super(window);
        this.currentCursorPos = currentCursorPos;
    }

    public Pos getCurrentCursorPos() {
        return currentCursorPos;
    }

}
