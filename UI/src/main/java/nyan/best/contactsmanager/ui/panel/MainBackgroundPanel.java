package nyan.best.contactsmanager.ui.panel;

import nyan.best.contactsmanager.ui.node.MainBackgroundNode;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;

public class MainBackgroundPanel extends Pane {

    public MainBackgroundPanel(AlivePosition parentPos, AliveSize parentSize) {
        setSize(new AliveSize(AliveType.RELATED_RATIO, parentSize, 1, 1));
        setPosition(new AlivePosition(AliveType.SEPARATED, parentPos, 0, 0));
        MainBackgroundNode node = new MainBackgroundNode();
        node.setPosition(position);
        node.setSize(size);
        this.addChild(node);
    }

}
