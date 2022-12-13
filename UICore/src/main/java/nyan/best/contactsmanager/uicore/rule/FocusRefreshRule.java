package nyan.best.contactsmanager.uicore.rule;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseClickEvent;

public class FocusRefreshRule implements EventListener {

    @EventHandle
    public void onMouseClick(UIMouseClickEvent e) {
        var node = e.getClickedNode();
        var stage = e.getWindow().getStage();
        if (stage.getFocusNode() != null)
            stage.getFocusNode().setFocus(false);
        node.setFocus(true);
        stage.setFocusNode(node);
    }

}
