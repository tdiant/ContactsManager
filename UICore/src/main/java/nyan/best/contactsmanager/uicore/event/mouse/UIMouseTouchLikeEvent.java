package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIMouseTouchLikeEvent extends UIMouseEvent {

    private static final HandleList handleList = new HandleList();

    private final Pos cursorPos;
    private final int btnCode;

    public UIMouseTouchLikeEvent(WindowInstance window, Pos cursorPos, int btnCode) {
        super(window, cursorPos);
        this.cursorPos = cursorPos;
        this.btnCode = btnCode;
    }

    public Pos getCursorPos() {
        return cursorPos;
    }

    public int getBtnCode() {
        return btnCode;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

}
