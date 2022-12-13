package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIMouseMoveEvent extends UIMouseEvent {
    private static final HandleList handleList = new HandleList();

    private final Pos fromPos;
    private final Pos toPos;

    public UIMouseMoveEvent(WindowInstance window, Pos fromPos, Pos toPos) {
        super(window, toPos);
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public Pos getToPos() {
        return toPos;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

}
