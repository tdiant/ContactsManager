package nyan.best.contactsmanager.ui.node.details;

import nyan.best.contactsmanager.common.entity.InstanceMessageItem;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class DetailInstanceMsgItem extends Pane {

    static {
        NODE_RENDER_CLZ.put(DetailInstanceMsgItem.class, DetailInstanceMsgItemRender.class);
    }

    public InstanceMessageItem instanceMsgItem;

    private SimpleLabel typeLabel;
    private SimpleLabel valueLabel;

    public DetailInstanceMsgItem(InstanceMessageItem instanceMsgItem) {
        this.instanceMsgItem = instanceMsgItem;

//        this.getStyle().put("Background", "128,0,0,0");
        getSize().setHeight(30);

        this.typeLabel = new SimpleLabel(instanceMsgItem.getType());
        this.typeLabel.setSize(new AliveSize(AliveType.SEPARATED, 50, 30));
        this.typeLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 0, 0));
        this.typeLabel.getStyle().put("FontType", "BOLD");
        this.typeLabel.setInnerTag(true);
        addChild(typeLabel);

        this.valueLabel = new SimpleLabel(instanceMsgItem.getValue());
//        this.valueLabel.setSize(new AliveSize(AliveType.SEPARATED, 40, 30));
        this.valueLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 55, 1));
        this.valueLabel.setInnerTag(true);
        addChild(valueLabel);
    }

    public static class DetailInstanceMsgItemRender extends PaneRender {
        public DetailInstanceMsgItemRender(WindowInstance window, DetailInstanceMsgItem nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
            nodeObj.getSize().setType(AliveType.SEPARATED);
            nodeObj.getSize().setWidth(nodeObj.getParent().getSize().toSeparatedAttribute().getWidth());

            ((DetailInstanceMsgItem) nodeObj).valueLabel.getSize().setHeight(30);
        }
    }
}
