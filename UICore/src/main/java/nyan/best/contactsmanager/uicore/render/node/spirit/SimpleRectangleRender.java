package nyan.best.contactsmanager.uicore.render.node.spirit;

import io.github.humbleui.skija.Matrix33;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleRectangle;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

import java.util.Objects;

public class SimpleRectangleRender extends NodeRender<SimpleRectangle> {
    public SimpleRectangleRender(WindowInstance window, SimpleRectangle nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        double cirRadius = Double.parseDouble(nodeObj.getStyle().get("CirRadius"));
        int[] color = GLFWUtils.getARGB(nodeObj.getStyle().get("Color"));

        float realWidth = Float.parseFloat(Objects.requireNonNullElse(
                nodeObj.getStyle().get("RectangleWidth"),
                nodeObj.getSize().toSeparatedAttribute().getWidth() + ""
        ));
        float realHeight = Float.parseFloat(Objects.requireNonNullElse(
                nodeObj.getStyle().get("RectangleHeight"),
                nodeObj.getSize().toSeparatedAttribute().getHeight() + ""
        ));

        RRect rect = RRect.makeXYWH(
                (float) nodeObj.getPosition().toSeparatedAttribute().getLeft(),
                (float) nodeObj.getPosition().toSeparatedAttribute().getTop(),
                realWidth, realHeight,
                (float) cirRadius
        );

        Path path = new Path();
        path.addRRect(rect);

        float rotateDegree = Float.parseFloat(nodeObj.getStyle().get("Rotate"));
        if (rotateDegree > 0) {
            path.transform(Matrix33.makeRotate(rotateDegree,
                    (float) nodeObj.getPosition().toSeparatedAttribute().getLeft() +
                            (float) nodeObj.getSize().toSeparatedAttribute().getWidth() / 2,
                    (float) nodeObj.getPosition().toSeparatedAttribute().getTop() +
                            (float) nodeObj.getSize().toSeparatedAttribute().getHeight() / 2
            ));
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setARGB(color[0], color[1], color[2], color[3]);
        window.getCanvas().drawPath(path, paint);

        paint.close();
        path.close();
    }
}
