package nyan.best.contactsmanager.ui.node;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.node.pane.Card;
import nyan.best.contactsmanager.uicore.render.node.pane.CardRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class DetailsCard extends Card {

    static {
        NODE_RENDER_CLZ.put(DetailsCard.class, DetailsCardRender.class);
    }

    public DetailsCard() {
        getStyle().put("OverflowShow", "false");
    }

    public void addDetailsSubPane(Node pane) {
        var maxHeight = cntMaxHeight();
        pane.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 15, maxHeight));
        addChild(pane);
    }

    public double cntMaxHeight() {
        var maxHeight = 0.0;
        for (var p : getChildren(true)) {
            maxHeight = Math.max(maxHeight, p.getPosition().getTop() + p.getSize().toSeparatedAttribute().getHeight());
        }
        this.size.setHeight(Math.max(this.size.getHeight(), maxHeight));
        return maxHeight;
    }

    public static class DetailsCardRender extends CardRender {
        public DetailsCardRender(WindowInstance window, DetailsCard nodeObj) {
            super(window, nodeObj);
        }
    }

}
