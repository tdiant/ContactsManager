package nyan.best.contactsmanager.uicore.render.node.spirit;

import io.github.humbleui.skija.Paint;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleCircle;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

public class SimpleCircleRender extends NodeRender<SimpleCircle> {
    public SimpleCircleRender(WindowInstance window, SimpleCircle nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        Paint paint = new Paint();

        int[] colors = GLFWUtils.getARGB(nodeObj.getStyle().get("Color"));
        paint.setARGB(colors[0], colors[1], colors[2], colors[3]);

        window.getCanvas().drawCircle(
                (float) nodeObj.getPosition().toSeparatedAttribute().getLeft(),
                (float) nodeObj.getPosition().toSeparatedAttribute().getTop(),
                (float) (nodeObj.getSize().toSeparatedAttribute().getWidth() / 2),
                paint
        );

        paint.close();
    }
}
