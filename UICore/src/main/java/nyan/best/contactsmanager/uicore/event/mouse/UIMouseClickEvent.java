package nyan.best.contactsmanager.uicore.event.mouse;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.node.Node;

public class UIMouseClickEvent extends UIMouseTouchLikeEvent {

    private final Node clickedNode;

    public UIMouseClickEvent(WindowInstance window, Pos cursorPos, Node clickedNode, int btnCode) {
        super(window, cursorPos, btnCode);
        this.clickedNode = clickedNode;
    }

    public Node getClickedNode() {
        return clickedNode;
    }

}
