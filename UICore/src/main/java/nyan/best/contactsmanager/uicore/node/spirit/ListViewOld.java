package nyan.best.contactsmanager.uicore.node.spirit;

import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.TextBlob;
import io.github.humbleui.skija.shaper.Shaper;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.render.node.spirit.SimpleLabelRender;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

import java.util.ArrayList;
import java.util.List;

public class ListViewOld extends Node {

    public static final int eachItemHeight = 46;

    private List<String> data = new ArrayList<>();
    private float scrollOffsetY = 0;

    public ListViewOld() {
    }

    public ListViewOld(List<String> data) {
        this.data = data;
        refreshDataInnerNode();
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
        refreshDataInnerNode();
    }

    public void refreshDataInnerNode() {
        this.getChildren().clear();
        for (int i = 0; i < data.size(); i++) {
            var x = data.get(i);
            var item = new ListViewItem("    " + x);
            item.setParent(this);
            item.setSize(new AliveSize(AliveType.SEPARATED, this.size, this.size.toSeparatedAttribute().getWidth(), eachItemHeight));
            item.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 0, i * (eachItemHeight + 4)));
            addChild(item);
        }
    }

    public float getScrollOffsetY() {
        return scrollOffsetY;
    }

    public void setScrollOffsetY(float scrollOffsetY) {
        this.scrollOffsetY = scrollOffsetY;
        var children = this.getChildren(true);
        for (Node x : children) {
            if (!(x instanceof ListViewItem))
                continue;
            var pos = x.getPosition();
            pos.setTop(pos.getTop() - scrollOffsetY);
        }
    }

    public static class ListViewItem extends SimpleLabel {

        public ListViewItem(String text) {
            super(text);
            getStyle().put("TextCenterX", "false");
            getStyle().put("TextCenterY", "true");
            getStyle().put("FontSize", "16");
//            getStyle().put("Background", "255,255,255,255");
            getStyle().put("Background", "255,232,232,232");
            getStyle().put("OverflowShow", "false");
        }

        public static class ListViewItemRender extends SimpleLabelRender {
            public ListViewItemRender(WindowInstance window, ListViewItem nodeObj) {
                super(window, nodeObj);
            }

            @Override
            public void onDraw() {
                Shaper shaper = Shaper.make();
                Font font = genFont();
                int[] argb = GLFWUtils.getARGB(nodeObj.getStyle().get("TextColor"));
                Paint paint = new Paint().setARGB(argb[0], argb[1], argb[2], argb[3]);

                TextBlob blob = shaper.shape(
                        nodeObj.getText(),
                        font
                );

                double height = blob.getBounds().getHeight();

                float v = (float) nodeObj.getPosition().toSeparatedAttribute().getLeft(), v1 = (float) nodeObj.getPosition().toSeparatedAttribute().getTop();

                if (nodeObj.getStyle().get("TextCenterY").equalsIgnoreCase("true"))
                    v1 = (float) (nodeObj.getPosition().toSeparatedAttribute().getTop() + nodeObj.getSize().toSeparatedAttribute().getHeight() / 2 - height / 2);

                window.getCanvas().drawTextBlob(blob, v + 10, v1, paint);

                font.close();
                paint.close();
                shaper.close();

            }

            @Override
            public Rect getSizeRect() {
                return RRect.makeXYWH(
                        (float) nodeObj.getPosition().toSeparatedAttribute().getLeft(),
                        (float) nodeObj.getPosition().toSeparatedAttribute().getTop(),
                        (float) nodeObj.getSize().getWidth(),
                        (float) nodeObj.getSize().getHeight(),
                        6f
                );
            }
        }
    }

}
