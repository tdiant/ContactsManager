package nyan.best.contactsmanager.uicore.render.node.spirit;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.shaper.Shaper;
import io.github.humbleui.types.Rect;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

public class SimpleLabelRender extends NodeRender<SimpleLabel> {

    public SimpleLabelRender(WindowInstance window, SimpleLabel nodeObj) {
        super(window, nodeObj);
    }

    private String lastValue = "";
    private AliveSize lastSz = new AliveSize(AliveType.SEPARATED, 0, 0);
    private AlivePosition lastPos = new AlivePosition(AliveType.SEPARATED, 0, 0);
    private TextBlob lastTextBlob = null;
    private float[] lastVs = null;

    @Override
    public void onDraw() {
        if (nodeObj.getText() == null || nodeObj.getText().isEmpty())
            return;

        tmpHeight = -1;
        tmpWidth = -1;

        int[] argb = GLFWUtils.getARGB(nodeObj.getStyle().get("TextColor"));
        Paint paint = new Paint()
                .setARGB(argb[0], argb[1], argb[2], argb[3]);
        if (nodeObj.getStyle().get("TextStroke").equalsIgnoreCase("true"))
            paint.setMode(PaintMode.STROKE).setStrokeWidth(1);

        if (lastValue.equals(nodeObj.getText()) && lastPos.equals(nodeObj.getPosition().toSeparatedAttribute()) && lastSz.equals(nodeObj.getSize().toSeparatedAttribute())) {
            window.getCanvas().drawTextBlob(lastTextBlob, lastVs[0], lastVs[1], paint);
            paint.close();
            return;
        }

        if (lastTextBlob != null && !lastTextBlob.isClosed())
            lastTextBlob.close();

        Shaper shaper = Shaper.make();
        Font font = genFont();

        TextBlob blob = shaper.shape(
                nodeObj.getText(),
                font//,
//                (float) nodeObj.getSize().getWidth()
        );

        Rect rect = font.measureText(nodeObj.getText());

        float[] vs = ensureSize(cntHeight(blob), cntWidth(rect));

        if (nodeObj.getSize().toSeparatedAttribute().getWidth() == 0 ||
                nodeObj.getSize().toSeparatedAttribute().getHeight() == 0) {
            nodeObj.setSize(new AliveSize(AliveType.SEPARATED, cntWidth(rect) + 5, cntHeight(blob) + 5));
        }

        window.getCanvas().drawTextBlob(blob, vs[0], vs[1], paint);
//        window.getCanvas().drawTextBlob(blob, (float) (pos.getLeft()), (float) (pos.getTop()), paint);

        lastTextBlob = blob;
        lastVs = vs;
        lastValue = nodeObj.getText();
        lastSz = nodeObj.getSize().toSeparatedAttribute();
        lastPos = nodeObj.getPosition().toSeparatedAttribute();

        font.close();
        paint.close();
        shaper.close();
    }

    private Font font;

    public Font genFont() {
        if (font != null && !font.isClosed())
            return font;
        font = new Font()
                .setTypeface(
                        Typeface.makeFromName(
                                nodeObj.getStyle().get("FontFamily"),
                                GLFWUtils.getFontStyle(nodeObj.getStyle().get("FontType"))
                        ))
                .setSize(Float.parseFloat(nodeObj.getStyle().get("FontSize")));
        return font;
    }

    private float[] ensureSize(double height, double width) {
        float v = (float) nodeObj.getPosition().toSeparatedAttribute().getLeft(), v1 = (float) nodeObj.getPosition().toSeparatedAttribute().getTop();
        if (nodeObj.getStyle().get("TextCenterX").equalsIgnoreCase("true"))
            v = (float) (nodeObj.getPosition().toSeparatedAttribute().getLeft() + nodeObj.getSize().toSeparatedAttribute().getWidth() / 2 - width / 2);
        if (nodeObj.getStyle().get("TextCenterY").equalsIgnoreCase("true"))
            v1 = (float) (nodeObj.getPosition().toSeparatedAttribute().getTop() + nodeObj.getSize().toSeparatedAttribute().getHeight() / 2 - height / 2);
        return new float[]{v, v1};
    }

    private double tmpHeight = -1;
    private double tmpWidth = -1;

    private double cntHeight(TextBlob blob) {
        if (tmpHeight >= 0) return tmpHeight;
        tmpHeight = blob.getBounds().getHeight();
        return tmpHeight;
    }

    private double cntWidth(Rect rect) {
        if (tmpWidth >= 0) return tmpWidth;
        tmpWidth = rect.getWidth();
        return tmpWidth;
    }

}
