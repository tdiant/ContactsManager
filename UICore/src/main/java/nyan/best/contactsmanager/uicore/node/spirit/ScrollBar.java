package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;

public class ScrollBar extends Node {

    public double totalHeight;
    public double singleHeight;
    public double offsetRatio = 0;

    public SimpleRectangle proRec;
    public SimpleRectangle bgRec;


    public ScrollBar(double totalHeight, double singleHeight) {
        this.totalHeight = totalHeight;
        this.singleHeight = singleHeight;

        this.bgRec = new SimpleRectangle();
        this.bgRec.setSize(new AliveSize(AliveType.RELATED_RATIO, this.getSize(), 1, 1));
        this.bgRec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 0, 0));
        this.bgRec.getStyle().put("Color", "200,240,240,240");
        this.bgRec.setInnerTag(true);
        addChild(bgRec);

        this.proRec = new SimpleRectangle();
        this.proRec.setSize(new AliveSize(AliveType.SEPARATED, this.getSize(), this.getSize().toSeparatedAttribute().getWidth(), 20));
        this.proRec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 0, 0));
        this.proRec.getStyle().put("Color", "255,120,120,120");
        this.proRec.setInnerTag(true);
        addChild(proRec);
    }

}
