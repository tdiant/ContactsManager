package nyan.best.contactsmanager.uicore.util;

import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.types.Rect;
import org.lwjgl.glfw.GLFWErrorCallback;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class GLFWUtils {
    public static void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalArgumentException("Unable to init GLFW");
    }

    public static FontStyle getFontStyle(String styleName) {
        switch (styleName.toUpperCase()) {
            case "NORMAL":
            default:
                return FontStyle.NORMAL;
            case "BOLD":
                return FontStyle.BOLD;
            case "ITALIC":
                return FontStyle.ITALIC;
            case "BOLD_ITALIC":
                return FontStyle.BOLD_ITALIC;
        }
    }

    public static int[] getARGB(String str) {
        String[] args = str.replace(" ", "").split(",");
        int a = 255, r = 233, g = 233, b = 233;
        if (args.length == 4) {
            a = Integer.parseInt(args[0]);
            r = Integer.parseInt(args[1]);
            g = Integer.parseInt(args[2]);
            b = Integer.parseInt(args[3]);
        } else {
            r = Integer.parseInt(args[0]);
            g = Integer.parseInt(args[1]);
            b = Integer.parseInt(args[2]);
        }
        return new int[]{a, r, g, b};
    }

    public static Rect getRectFromSeparatedSizeAndPos(AliveSize size, AlivePosition pos) {
        size = size.toSeparatedAttribute();
        pos = pos.toSeparatedAttribute();
        return new Rect(
                (float) pos.getLeft(),
                (float) pos.getTop(),
                (float) (pos.getLeft() + size.getWidth()),
                (float) (pos.getTop() + size.getHeight())
        );
    }

    public static boolean checkInRect(Rect rect, double x, double y) {
        return rect.getLeft() <= x && rect.getRight() >= x
                && rect.getTop() <= y && rect.getBottom() >= y;
    }

    public static double dis(double x0, double y0, double x1, double y1) {
        return Math.sqrt(
                Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2)
        );
    }

    public static AlivePosition getPositionCenter(AliveSize size, AliveSize parentSize, AlivePosition parentPos) {
        parentSize = parentSize.toSeparatedAttribute();
        parentPos = parentPos.toSeparatedAttribute();
        size = size.toSeparatedAttribute();
        return new AlivePosition(
                AliveType.RELATED_SEPARATED,
                parentPos,
                (float) (parentSize.getWidth() / 2 - size.getWidth() / 2),
                (float) (parentSize.getHeight() / 2 - size.getHeight() / 2)
        );
    }

}
