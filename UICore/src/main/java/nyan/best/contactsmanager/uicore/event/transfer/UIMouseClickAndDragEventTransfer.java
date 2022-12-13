package nyan.best.contactsmanager.uicore.event.transfer;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseClickEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseDragEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMousePressedEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseReleasedEvent;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

public class UIMouseClickAndDragEventTransfer implements EventTransferListener {

    public static final double CLICK_RADIUS = 0.25;

    private Pos lastPressPos = null;

    @EventHandle
    public void onMousePress(UIMousePressedEvent e) {
        lastPressPos = e.getCurrentCursorPos();
    }

    @EventHandle
    public void onMouseRelease(UIMouseReleasedEvent e) {
        if (lastPressPos == null)
            return;
        if (GLFWUtils.dis(lastPressPos.x(), lastPressPos.y(), e.getCurrentCursorPos().x(), e.getCurrentCursorPos().y()) < CLICK_RADIUS) {
            EventManager.callEvent(new UIMouseClickEvent(
                    e.getWindow(),
                    e.getCurrentCursorPos(),
                    e.getWindow().getStage().rayToNodeByPos(e.getCurrentCursorPos()),
                    e.getBtnCode()
            ));
        } else {
            EventManager.callEvent(new UIMouseDragEvent(
                    e.getWindow(),
                    lastPressPos,
                    e.getCurrentCursorPos(),
                    e.getWindow().getStage().rayToNodeByPos(e.getCurrentCursorPos()),
                    e.getBtnCode()
            ));
        }
    }

}
