package nyan.best.contactsmanager.uicore.render.node.spirit;

import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.shaper.Shaper;
import io.github.humbleui.types.Rect;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.node.spirit.TextInput;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

public class TextInputRender extends NodeRender<TextInput> {

    private long lastBulingTimestamp = System.currentTimeMillis();
    private boolean lastBulingStatus = true;

    public TextInputRender(WindowInstance window, TextInput nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        nodeObj._initInnerChildren();

        var size = nodeObj.getSize().toSeparatedAttribute();
        nodeObj.inputValLabel.setSize(new AliveSize(AliveType.SEPARATED, size.getWidth() - 20, size.getHeight()));
//        nodeObj.inputValLabel.setSize(new AliveSize(AliveType.SEPARATED, size.getWidth() - 20, size.getHeight()));
//        inputValLabel.setSize(new AliveSize(AliveType.RELATED_RATIO, this.size, 0.85, 0.85));

        if (nodeObj.isFocus()) {
            if (!nodeObj.getChildren(true).contains(nodeObj.ibeamPosRec))
                nodeObj.addChild(nodeObj.ibeamPosRec);
            if (System.currentTimeMillis() - lastBulingTimestamp > 600) {
                lastBulingTimestamp = System.currentTimeMillis();
                lastBulingStatus = !lastBulingStatus;
                if (lastBulingStatus) {
                    nodeObj.ibeamPosRec.getStyle().put("Color", "0,0,0,0");
                } else {
                    nodeObj.ibeamPosRec.getStyle().put("Color", "255,0,0,0");
                }
            }
        } else {
            nodeObj.ibeamPosRec.getStyle().put("Color", "0,0,0,0");
            nodeObj.getChildren().remove(nodeObj.ibeamPosRec);
        }

        if (nodeObj.inputValLabel.getNodeRender() == null || !(nodeObj.inputValLabel.getNodeRender() instanceof TextInputValLabelRender)) {
            nodeObj.inputValLabel.setNodeRender(new TextInputValLabelRender(this.window, nodeObj.inputValLabel));
        }

    }

    public void moveIbeamPos(int idx) {
        Shaper shaper = Shaper.make();
        if (nodeObj.inputValLabel.getNodeRender() == null)
            return;
        Font font = ((SimpleLabelRender) nodeObj.inputValLabel.getNodeRender()).genFont();

        short[] glyphs = font.getStringGlyphs(nodeObj.getValue());
        float[] widths = font.getWidths(glyphs);
        float totWidths = 0;
        for (int i = 1; i <= idx; i++)
            totWidths += widths[i - 1];

        var sizeWidth = nodeObj.getSize().toSeparatedAttribute().getWidth();
        var slideWindowWidth = nodeObj.inputValLabel.getSize().toSeparatedAttribute().getWidth();
        var slideWindowLeft = nodeObj.inputValLabel.getPosition().getLeft();

        var slideWindowLeftLabelWidth = Math.abs(slideWindowLeft - 10);
        var slideWindowRightLabelWidth = slideWindowLeftLabelWidth + slideWindowWidth;

        if (totWidths >= slideWindowLeftLabelWidth && totWidths <= slideWindowRightLabelWidth) {
            var realLeft = totWidths - slideWindowLeft + nodeObj.inputValLabel.getPosition().getLeft();
            nodeObj.ibeamPosRec.getPosition().setLeft(realLeft + nodeObj.inputValLabel.getPosition().getLeft());
        } else if (totWidths < slideWindowLeftLabelWidth) {
            var realLeft = nodeObj.inputValLabel.getPosition().getLeft();
            nodeObj.ibeamPosRec.getPosition().setLeft(realLeft);
            nodeObj.inputValLabel.getPosition().setLeft(-totWidths + 10);
        } else {
            var realLeft = nodeObj.inputValLabel.getPosition().getLeft() + nodeObj.inputValLabel.getSize().toSeparatedAttribute().getWidth();
            nodeObj.ibeamPosRec.getPosition().setLeft(realLeft);
            nodeObj.inputValLabel.getPosition().setLeft(
                    -(slideWindowLeftLabelWidth + totWidths - slideWindowLeftLabelWidth - (sizeWidth - 20))
            );
        }

        shaper.close();
    }

    public static class TextInputValLabelRender extends SimpleLabelRender {

        public TextInputValLabelRender(WindowInstance window, SimpleLabel nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
        }

        @Override
        public Rect getSizeRect() {
            return this.nodeObj.getParent().getNodeRender().getSizeRect();
        }
    }

}
