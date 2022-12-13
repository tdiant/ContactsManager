package nyan.best.contactsmanager.uicore.render.node;

import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.render.NodeRenderFactory;
import nyan.best.contactsmanager.uicore.render.StyleSheet;
import nyan.best.contactsmanager.uicore.render.node.spirit.TextInputRender;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

import java.util.ArrayList;
import java.util.Collections;

public abstract class NodeRender<T extends Node> {

    protected final WindowInstance window;
    protected final T nodeObj;

    public NodeRender(WindowInstance window, T nodeObj) {
        this.window = window;
        this.nodeObj = nodeObj;
    }

    public T getNodeObj() {
        return nodeObj;
    }

    public WindowInstance getWindow() {
        return window;
    }

    protected AlivePosition basePos() {
        return nodeObj.getPosition().toSeparatedAttribute();
    }

    public final void draw() {
        //Global Style Render
        StyleSheet style = nodeObj.getStyle();
for (String key : style.keySet()) {
    if ("background".equalsIgnoreCase(key)) {
        int[] argb = GLFWUtils.getARGB(style.get("Background"));
        Paint paint = new Paint().setARGB(argb[0], argb[1], argb[2], argb[3]);
        var rect = getSizeRect();
        if (rect instanceof RRect) {
            window.getCanvas().drawRRect(
//                            GLFWUtils.getRectFromSeparatedSizeAndPos(nodeObj.getSize(), nodeObj.getPosition()),
                    (RRect) rect,
                    paint
            );
        } else {
            window.getCanvas().drawRect(
                    rect,
                    paint
            );
        }
        paint.close();
    }
}

        int layer = window.getCanvas().save();

        if (nodeObj.getStyle().getOrDefault("OverflowShow", "false").equalsIgnoreCase("false")) {
            Rect rect = getSizeRect();
            if (nodeObj.getParent() != null && nodeObj.getParent().getNodeRender() != null && nodeObj.getStyle().getOrDefault("IgnoreParentIntersect", "false").equalsIgnoreCase("false")) {
                Rect parentRect = nodeObj.getParent().getNodeRender().getSizeRect();
                rect = rect.intersect(parentRect);
                if (rect == null)
                    rect = new Rect(0, 0, 1, 1);
            }

            if (rect instanceof RRect)
                window.getCanvas().clipRRect((RRect) rect, true);
            else
                window.getCanvas().clipRect(rect, true);

//        window.getCanvas().saveLayer(new SaveLayerRec(new SaveLayerRec(null, paint, SaveLayerRecFlag.INIT_WITH_PREVIOUS)))
        }

        onDraw();

        window.getCanvas().restoreToCount(layer);

        for (var x : nodeObj.getChildren(true)) {
            if (x.getNodeRender() == null)
                x.setNodeRender(NodeRenderFactory.gottaNodeRender(window, x));
            x.getNodeRender().draw();
        }
    }

    public abstract void onDraw();

    public Node checkRay(Pos pos) {
        var ls = new ArrayList<>(this.nodeObj.getChildren());
        Collections.reverse(ls);
        ls.remove(this.nodeObj);

        for (var p : ls) {
            var pr = p.getNodeRender();
            if (pr == null)
                continue;
            var q = pr.checkRay(pos);
            if (q != null)
                return q;
        }
        if (GLFWUtils.checkInRect(
                GLFWUtils.getRectFromSeparatedSizeAndPos(nodeObj.getSize(), nodeObj.getPosition()),
                pos.x(),
                pos.y()
        )) {
            return nodeObj;
        }
        return null;
    }

    public Rect getSizeRect() {
        var rect = GLFWUtils.getRectFromSeparatedSizeAndPos(nodeObj.getSize().toSeparatedAttribute(), nodeObj.getPosition().toSeparatedAttribute());
        if (nodeObj.getParent() != null && nodeObj.getParent().getNodeRender() != null)
            rect = rect.intersect(nodeObj.getParent().getNodeRender().getSizeRect());
        if (rect == null)
            rect = new Rect(0, 0, 0, 0);
        return rect;
    }

}
