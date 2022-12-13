package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.event.UIEvent;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;

public class UIMouseLeaveNodeEvent extends UIEvent {

    private static final HandleList handleList = new HandleList();

    private final Node leaveNode;

    public UIMouseLeaveNodeEvent(WindowInstance window, Node leaveNode) {
        super(window);
        this.leaveNode = leaveNode;
    }

    public Node getLeaveNode() {
        return leaveNode;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

}