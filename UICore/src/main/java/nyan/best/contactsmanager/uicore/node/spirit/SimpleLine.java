package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;

import java.util.HashMap;

public class SimpleLine extends Node {

    private Pos pointA;
    private Pos pointB;

    public SimpleLine(Pos pointA, Pos pointB) {
        setPointA(pointA);
        setPointB(pointB);

        getStyle().putAll(new HashMap<>() {{
            put("Color", "255,0,0,0");
            put("StrokeWidth", "1");
        }});
    }

    public Pos getPointA() {
        return pointA;
    }

    public void setPointA(Pos pointA) {
        this.pointA = pointA;
        resetPosAndSize();
    }

    public Pos getPointB() {
        return pointB;
    }

    public void setPointB(Pos pointB) {
        this.pointB = pointB;
        resetPosAndSize();
    }

    @Override
    public void setSize(AliveSize size) {
        super.setSize(size); //todo
    }

    @Override
    public void setPosition(AlivePosition position) {
        super.setPosition(position); //todo
    }

    private void resetPosAndSize() {
        if (pointA == null || pointB == null)
            return;
        setPosition(new AlivePosition(AliveType.SEPARATED,
                Math.min(pointA.x(), pointB.x()),
                Math.min(pointA.y(), pointB.y())
        ));
        setSize(new AliveSize(AliveType.SEPARATED,
                Math.max(pointA.x(), pointB.x()) - Math.min(pointA.x(), pointB.x()),
                Math.max(pointA.y(), pointB.y()) - Math.min(pointA.y(), pointB.y())
        ));
    }

}
