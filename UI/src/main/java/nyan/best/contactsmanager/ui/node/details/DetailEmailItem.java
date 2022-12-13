package nyan.best.contactsmanager.ui.node.details;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class DetailEmailItem extends Pane {

    static {
        NODE_RENDER_CLZ.put(DetailEmailItem.class, DetailEmailItemRender.class);
    }

    public int index;
    public String email;
    private SimpleLabel valueLabel;

    public DetailEmailItem(int index, String email) {
        this.index = index;
        this.email = email;

//        this.getStyle().put("Background", "128,0,0,0");
        getSize().setHeight(30);

        this.valueLabel = new SimpleLabel(email);
//        this.valueLabel.setSize(new AliveSize(AliveType.SEPARATED, 40, 30));
        this.valueLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 5, 1));
        this.valueLabel.setInnerTag(true);
        addChild(valueLabel);
    }

    public static class DetailEmailItemRender extends PaneRender {
        public DetailEmailItemRender(WindowInstance window, DetailEmailItem nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
            nodeObj.getSize().setType(AliveType.SEPARATED);
            nodeObj.getSize().setWidth(nodeObj.getParent().getSize().toSeparatedAttribute().getWidth());

            ((DetailEmailItem) nodeObj).valueLabel.getSize().setHeight(30);
        }
    }

}
