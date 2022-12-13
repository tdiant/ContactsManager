package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIMouseScrollEvent extends UIMouseEvent {

    private static final HandleList handleList = new HandleList();

    private final double offsetX;
    private final double offsetY;

    public UIMouseScrollEvent(WindowInstance window, Pos currentCursorPos, double offsetX, double offsetY) {
        super(window, currentCursorPos);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

}
