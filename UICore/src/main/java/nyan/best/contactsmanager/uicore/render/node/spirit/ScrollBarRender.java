package nyan.best.contactsmanager.uicore.render.node.spirit;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.spirit.ScrollBar;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

public class ScrollBarRender extends NodeRender<ScrollBar> {
    public ScrollBarRender(WindowInstance window, ScrollBar nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        nodeObj.proRec.getSize().setHeight(
                nodeObj.singleHeight * nodeObj.getSize().getHeight() / nodeObj.totalHeight
        );
        nodeObj.proRec.getPosition().setTop(
                (nodeObj.getSize().getHeight() - nodeObj.proRec.getSize().getHeight()) * Math.min(1, nodeObj.offsetRatio)
        );
    }
}
