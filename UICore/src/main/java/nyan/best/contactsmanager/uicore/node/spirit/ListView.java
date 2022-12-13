package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

import java.util.ArrayList;
import java.util.List;

public class ListView extends Node {

    public static final int eachItemHeight = 46;

    private List<String> data = new ArrayList<>();

    public ScrollBar scrollBar;

    public ListView() {
        this.getStyle().put("OverflowShow", "false");
    }

    public ListView(List<String> data) {
        this();
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

    public double lastCntMaxHeight = 0;

    public void refreshDataInnerNode() {
        this.getChildren().forEach(this::removeChild);
        for (int i = 0; i < data.size(); i++) {
            var x = data.get(i);
            var item = new ListViewItem(i, x);
            item.setSize(new AliveSize(AliveType.SEPARATED, this.size, this.size.toSeparatedAttribute().getWidth() - 13, eachItemHeight));
            item.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 0, i * (eachItemHeight + 4)));
            item.setParent(this);
//            item.setInnerTag(true);
            item.getStyle().put("OverflowShow", "false");
            addChild(item);
        }

        lastCntMaxHeight = cntMaxHeight();

        //Scroll bar
        var selfSize = this.size.toSeparatedAttribute();
        scrollBar = new ScrollBar(lastCntMaxHeight, selfSize.getHeight());
        scrollBar.setSize(new AliveSize(AliveType.SEPARATED, this.size, 8, selfSize.getHeight()));
        scrollBar.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, selfSize.getWidth() - 8, 0));
//        scrollBar.getStyle().put("Background", "255,0,0,0");
        addChild(scrollBar);
    }

    public double cntMaxHeight() {
        var maxHeight = 0.0;
        for (var p : getChildren(true)) {
            if (!(p instanceof ListViewItem))
                continue;
            maxHeight = Math.max(maxHeight, p.getPosition().getTop() + p.getSize().toSeparatedAttribute().getHeight());
        }
        this.size.setHeight(Math.max(this.size.getHeight(), maxHeight));
        return maxHeight;
    }

    public void setScrollOffsetY(float scrollOffsetY) {
        var children = this.getChildren(true).stream()
                .filter(x -> x instanceof ListViewItem)
                .toList();
        if (children.size() != 0) {
            if (children.get(0).getPosition().getTop() + scrollOffsetY > 0)
                return;

            if (children.get(children.size() - 1).getPosition().getTop() + scrollOffsetY < 0)
                return;
        }
        for (Node x : children) {
            var pos = x.getPosition();
            pos.setTop(pos.getTop() + scrollOffsetY);
        }
    }

    public static class ListViewItem extends Node {
        private String title;

        private SimpleLabel label;
        private SimpleRectangle rec;

        private int index;

        public ListViewItem(int index, String title) {
            this.title = title;
            this.index = index;
            label = new SimpleLabel(title);
            rec = new SimpleRectangle();
            this.getStyle().put("OverflowShow", "false");
            label.getStyle().put("OverflowShow", "false");
            rec.getStyle().put("OverflowShow", "false");

            label.setInnerTag(true);
            rec.setInnerTag(true);

            addChild(label);
            addChild(rec);
        }

        public int getIndex() {
            return index;
        }

        public static class ListViewItemRender extends NodeRender<ListViewItem> {
            public ListViewItemRender(WindowInstance window, ListViewItem nodeObj) {
                super(window, nodeObj);
            }

            @Override
            public void onDraw() {
                nodeObj.label.getSize().setType(AliveType.SEPARATED);
                nodeObj.label.getSize().setParent(nodeObj.size);
                nodeObj.label.getSize().setWidth(nodeObj.size.toSeparatedAttribute().getWidth() - 10);
                nodeObj.label.getSize().setHeight(nodeObj.size.toSeparatedAttribute().getHeight());
                nodeObj.label.setZOrder(2);
                nodeObj.label.getStyle().put("TextCenterX", "false");
                nodeObj.label.getStyle().put("TextCenterY", "true");
//                nodeObj.label.getStyle().put("Background", "128,120,120,120");

                nodeObj.rec.getSize().setType(AliveType.RELATED_RATIO);
                nodeObj.rec.getSize().setParent(nodeObj.size);
                nodeObj.rec.getSize().setWidth(1);
                nodeObj.rec.getSize().setHeight(1);
                nodeObj.rec.getStyle().put("Color", "255,230,230,230");
                nodeObj.rec.getStyle().put("CirRadius", "9");
                nodeObj.rec.setZOrder(1);

                nodeObj.label.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, nodeObj.position, 10, 0));
                nodeObj.rec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, nodeObj.position, 0, 0));
            }
        }
    }

}
