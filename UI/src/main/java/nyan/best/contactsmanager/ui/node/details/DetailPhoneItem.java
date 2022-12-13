package nyan.best.contactsmanager.ui.node.details;

import nyan.best.contactsmanager.common.entity.PhoneItem;
import nyan.best.contactsmanager.common.i18n.I18N;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class DetailPhoneItem extends Pane {

    static {
        NODE_RENDER_CLZ.put(DetailPhoneItem.class, DetailsPhoneItemRender.class);
    }

    public PhoneItem phoneItem;

    private SimpleLabel typeLabel;
    private SimpleLabel valueLabel;

    public DetailPhoneItem(PhoneItem phoneItem) {
        this.phoneItem = phoneItem;

//        this.getStyle().put("Background", "128,0,0,0");
        getSize().setHeight(30);

        this.typeLabel = new SimpleLabel(I18N.lang("details.phone.type." + phoneItem.getType().name()));
        this.typeLabel.setSize(new AliveSize(AliveType.SEPARATED, 50, 30));
        this.typeLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 0, 0));
        this.typeLabel.getStyle().put("FontType", "BOLD");
        this.typeLabel.setInnerTag(true);
        addChild(typeLabel);

        this.valueLabel = new SimpleLabel(phoneItem.getValue());
//        this.valueLabel.setSize(new AliveSize(AliveType.SEPARATED, 40, 30));
        this.valueLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 55, 1));
        this.valueLabel.setInnerTag(true);
        addChild(valueLabel);
    }

    public static class DetailsPhoneItemRender extends PaneRender {
        public DetailsPhoneItemRender(WindowInstance window, DetailPhoneItem nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
            nodeObj.getSize().setType(AliveType.SEPARATED);
            nodeObj.getSize().setWidth(nodeObj.getParent().getSize().toSeparatedAttribute().getWidth());

            ((DetailPhoneItem) nodeObj).valueLabel.getSize().setHeight(30);
        }
    }

}
