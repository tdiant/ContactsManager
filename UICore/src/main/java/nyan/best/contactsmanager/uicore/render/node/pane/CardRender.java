package nyan.best.contactsmanager.uicore.render.node.pane;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.pane.Card;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleRectangle;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.render.node.spirit.SimpleRectangleRender;

public class CardRender extends SimpleRectangleRender {
    public CardRender(WindowInstance window, Card nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        super.onDraw();
        var maxHeight = 0.0;
        for (var p : nodeObj.getChildren(true)) {
            maxHeight = Math.max(maxHeight, p.getPosition().getTop() + p.getSize().toSeparatedAttribute().getHeight());

            p.getSize().setWidth(nodeObj.getSize().toSeparatedAttribute().getWidth());
        }
        nodeObj.getSize().setHeight(
                Math.max(nodeObj.getSize().getHeight(), maxHeight)
        );
    }
}
