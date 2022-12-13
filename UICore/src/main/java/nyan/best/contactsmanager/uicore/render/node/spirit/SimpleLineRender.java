package nyan.best.contactsmanager.uicore.render.node.spirit;

import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLine;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

public class SimpleLineRender extends NodeRender<SimpleLine> {
    public SimpleLineRender(WindowInstance window, SimpleLine nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        Paint paint = new Paint();

        int[] colors = GLFWUtils.getARGB(nodeObj.getStyle().get("Color"));
        paint.setARGB(colors[0], colors[1], colors[2], colors[3]);
        paint.setMode(PaintMode.STROKE);
        paint.setStrokeWidth(Float.parseFloat(nodeObj.getStyle().get("StrokeWidth")));

        window.getCanvas().drawLine(
                nodeObj.getPointA().x(),
                nodeObj.getPointA().y(),
                nodeObj.getPointB().x(),
                nodeObj.getPointB().y(),
                paint
        );

        paint.close();
    }
}
