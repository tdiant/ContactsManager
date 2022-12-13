package nyan.best.contactsmanager.ui.node.details;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.node.spirit.TextInput;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class DetailModifyItem extends Pane {

    static {
        NODE_RENDER_CLZ.put(DetailModifyItem.class, DetailModifyItemRender.class);
    }

    private SimpleLabel typeLabel;
    private TextInput valueTxt;

    public DetailModifyItem(String title) {
        getSize().setHeight(30);

        this.typeLabel = new SimpleLabel(title);
        this.typeLabel.setSize(new AliveSize(AliveType.SEPARATED, 50, 30));
        this.typeLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 0, 0));
        this.typeLabel.getStyle().put("FontType", "BOLD");
        addChild(typeLabel);

        this.valueTxt = new TextInput();
        this.valueTxt.setSize(new AliveSize(AliveType.SEPARATED, 150, 30));
        this.valueTxt.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 55, 1));
        addChild(valueTxt);
    }

    public TextInput getValueTxt() {
        return valueTxt;
    }

    public static class DetailModifyItemRender extends PaneRender {
        public DetailModifyItemRender(WindowInstance window, DetailModifyItem nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
            nodeObj.getSize().setType(AliveType.SEPARATED);
            nodeObj.getSize().setWidth(nodeObj.getParent().getSize().toSeparatedAttribute().getWidth());

            ((DetailModifyItem) nodeObj).valueTxt.getSize().setHeight(30);
        }
    }
}
