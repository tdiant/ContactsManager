package nyan.best.contactsmanager.ui.node;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.Button;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class DetailsTitle extends Pane {

    static {
        NODE_RENDER_CLZ.put(DetailsTitle.class, DetailsTitleRender.class);
    }

    protected SimpleLabel titleLabel;
    protected Button btn;

    public DetailsTitle(String title, String btnLabel, String btnName) {
        this.size.setHeight(39);

        this.name = "details_type#" + title;
        this.zOrder = 3;

        this.getStyle().remove("Background");

        titleLabel = new SimpleLabel(title);
//        titleLabel.setSize(new AliveSize(AliveType.SEPARATED, 200, 50));
//        titleLabel.getStyle().put("Background", "255,0,200,200");
        titleLabel.getStyle().put("TextCenterX", "false");
        titleLabel.getStyle().put("FontSize", "16");
        titleLabel.getStyle().put("FontType", "BOLD");
        titleLabel.getStyle().put("TextColor", "255,26,115,232");
        addChild(titleLabel);

        if (btnLabel != null) {
            btn = new Button(btnLabel);
            btn.setZOrder(10);
            btn.setSize(new AliveSize(AliveType.SEPARATED, 40, 25));
            btn.setName(btnName);
//        btn.getStyle().put("Background", "100,0,0,0");
            addChild(btn);
        }

    }

    public static class DetailsTitleRender extends PaneRender {
        public DetailsTitleRender(WindowInstance window, DetailsTitle nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
            ((DetailsTitle) nodeObj).titleLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getNodeObj().getPosition(), 0, 0));
            ((DetailsTitle) nodeObj).titleLabel.getSize().setHeight(35);
            if (((DetailsTitle) nodeObj).btn != null)
                ((DetailsTitle) nodeObj).btn.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getNodeObj().getPosition(), ((DetailsTitle) nodeObj).titleLabel.getSize().getWidth() + 10, 6));
        }
    }

}
