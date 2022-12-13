package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.eventsys.EventCancellable;
import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;

public class UIMouseDragEvent extends UIMouseEvent implements EventCancellable {

    private static final HandleList handleList = new HandleList();
    private boolean isCancelled = false;

    private final Pos fromPos;
    private final Pos toPos;
    private final Node draggedNode;
    private final int btnCode;

    public UIMouseDragEvent(WindowInstance window, Pos fromPos, Pos toPos, Node draggedNode, int btnCode) {
        super(window, toPos);
        this.fromPos = fromPos;
        this.toPos = toPos;
        this.draggedNode = draggedNode;
        this.btnCode = btnCode;
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public Pos getToPos() {
        return toPos;
    }

    public Node getDraggedNode() {
        return draggedNode;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

    @Override
    public void setCancelled(boolean status) {
        this.isCancelled = status;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
