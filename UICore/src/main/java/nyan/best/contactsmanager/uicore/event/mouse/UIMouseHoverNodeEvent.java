package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.event.UIEvent;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;

public class UIMouseHoverNodeEvent extends UIEvent {

    private static final HandleList handleList = new HandleList();

    private final Node hoverNode;

    public UIMouseHoverNodeEvent(WindowInstance window, Node hoverNode) {
        super(window);
        this.hoverNode = hoverNode;
    }

    public Node getHoverNode() {
        return hoverNode;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

}
