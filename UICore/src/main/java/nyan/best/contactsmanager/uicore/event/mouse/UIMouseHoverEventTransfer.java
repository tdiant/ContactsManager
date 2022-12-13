package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.uicore.node.Node;

public class UIMouseHoverEventTransfer implements EventListener {

    private Node lastHoverNode = null;

    @EventHandle
    public void onMouseMove(UIMouseMoveEvent e) {
        Node newNode = e.getWindow().getStage().rayToNodeByPos(e.getToPos());
        if (lastHoverNode != newNode) {
            EventManager.callEvent(new UIMouseHoverNodeEvent(e.getWindow(), newNode));
            EventManager.callEvent(new UIMouseLeaveNodeEvent(e.getWindow(), lastHoverNode));
            lastHoverNode = newNode;
        }
    }

}
