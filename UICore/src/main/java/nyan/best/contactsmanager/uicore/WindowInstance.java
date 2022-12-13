package nyan.best.contactsmanager.uicore;

import io.github.humbleui.skija.*;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.skija.impl.Stats;
import io.github.humbleui.types.IRect;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.common.Size;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardPressedEvent;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardReleasedEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseMoveEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMousePressedEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseReleasedEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseScrollEvent;
import nyan.best.contactsmanager.uicore.event.text.UITextInputEvent;
import nyan.best.contactsmanager.uicore.event.window.UIWindowClosedEvent;
import nyan.best.contactsmanager.uicore.event.window.UIWindowCreatedEvent;
import nyan.best.contactsmanager.uicore.event.window.UIWindowResizeUIEvent;
import nyan.best.contactsmanager.uicore.util.SystemUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowInstance {

    private String title;
    private final Stage stage;

    private float dpi = 1f;
    private boolean vsync = true;
    private long window;
    private Pos cursorPosition;
    private Size size;

    private DirectContext context;
    private BackendRenderTarget renderTarget;
    private Surface surface;
    private Canvas canvas;

    private boolean closeTag = false;

    public WindowInstance(String title, Stage stage) {
        this.title = title;
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void run(int left, int top, int right, int bottom) {
        IRect bounds = new IRect(left, top, right, bottom);
        createWindow(bounds);
        resize(size.width(), size.height());
        loop();
        windowDestroyHandle();
    }

    private static long lastWindow = NULL;

    private void createWindow(IRect bounds) {
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW_TRUE);

        window = GLFW.glfwCreateWindow(
                bounds.getWidth(),
                bounds.getHeight(),
                title,
                NULL,
                lastWindow
        );
        lastWindow = window;

        if (window == NULL)
            throw new RuntimeException("Failed to create the window titled " + title);

        GLFW.glfwSetWindowPos(window, bounds.getLeft(), bounds.getTop());
        this.dimensionsUpdateHandle();
        cursorPosition = new Pos(size.width() / 2, size.height() / 2);

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(vsync ? 1 : 0);

        GLFW.glfwShowWindow(window);

        GLFW.glfwSetWindowSize(window, (int) (size.width() / dpi), (int) (size.height() / dpi));

        EventManager.callEvent(new UIWindowCreatedEvent(this));
    }

    private void loop() {
        GL.createCapabilities();
        if ("false".equals(System.getProperty("skija.staticLoad")))
            Library.load();
        context = DirectContext.makeGL();

        //改变窗口大小
        var eventWinSize = GLFW.glfwSetWindowSizeCallback(window, (window, width, height) -> {
            dimensionsUpdateHandle();
            EventManager.callEvent(new UIWindowResizeUIEvent(this, size));
            stage.size.setType(AliveType.SEPARATED);
            stage.size.setWidth(width / dpi);
            stage.size.setHeight(height / dpi);
//            stage.size = new AliveSize(AliveType.SEPARATED, width / dpi, height / dpi);//surface.getWidth(), surface.getHeight());
            initSkija();
            draw();
        });

        var eventWinPos = GLFW.glfwSetWindowPosCallback(window, (window, posX, posY) -> {
            stage.position = new AlivePosition(AliveType.SEPARATED, posX, posY);
        });

        //鼠标移动
        var eventCursorPos = GLFW.glfwSetCursorPosCallback(window, (window, posX, posY) -> {
            Pos oldPos = cursorPosition;
            if (SystemUtils.isMac()) {
                cursorPosition = new Pos((int) posX, (int) posY);
            } else {
                cursorPosition = new Pos((int) (posX / dpi), (int) (posY / dpi));
            }
            EventManager.callEvent(new UIMouseMoveEvent(this, oldPos, cursorPosition));
        });

        //鼠标按键触发
        var eventBtnCalled = GLFW.glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (action == GLFW_RELEASE) {
                EventManager.callEvent(new UIMouseReleasedEvent(this, cursorPosition, button));
            } else if (action == GLFW_PRESS) {
                EventManager.callEvent(new UIMousePressedEvent(this, cursorPosition, button));
            }
        });

        //鼠标滚轮触发
        var eventScroll = GLFW.glfwSetScrollCallback(window, (window, offsetX, offsetY) -> {
            EventManager.callEvent(new UIMouseScrollEvent(this, cursorPosition, offsetX, offsetY));
        });

        //按键触发
        var eventKeyCalled = GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW_RELEASE) {
                EventManager.callEvent(new UIKeyboardReleasedEvent(this, key));
            } else if (action == GLFW_PRESS) {
                EventManager.callEvent(new UIKeyboardPressedEvent(this, key));
            }
        });

        //输入触发
        var eventCharCalled = GLFW.glfwSetCharCallback(window, (window, unicode) -> {
            EventManager.callEvent(new UITextInputEvent(this, unicode));
        });

        initSkija();

        while (!GLFW.glfwWindowShouldClose(window) && !closeTag) {
            draw();
            GLFW.glfwPollEvents();

            if (eventWinSize != null) eventWinSize.close();
            if (eventWinPos != null) eventWinPos.close();
            if (eventBtnCalled != null) eventBtnCalled.close();
            if (eventCursorPos != null) eventCursorPos.close();
            if (eventScroll != null) eventScroll.close();
            if (eventKeyCalled != null) eventKeyCalled.close();
            if (eventCharCalled != null) eventCharCalled.close();
        }
    }

    private void draw() {
        canvas.clear(0);
        int layer = canvas.save();
        if (stage.isScale())
            canvas.scale(dpi, dpi);
        stage.draw();
        canvas.restoreToCount(layer);
        context.flush();
        glfwSwapBuffers(window);
    }

    private void initSkija() {
        Stats.enabled = true;

        if (surface != null)
            surface.close();
        if (renderTarget != null)
            renderTarget.close();

        renderTarget = BackendRenderTarget.makeGL(
                (int) (size.width() * dpi),
                (int) (size.height() * dpi),
                0, 8, 0,
                FramebufferFormat.GR_GL_RGBA8);

        surface = Surface.makeFromBackendRenderTarget(
                context,
                renderTarget,
                SurfaceOrigin.BOTTOM_LEFT,
                SurfaceColorFormat.RGBA_8888,
                ColorSpace.getDisplayP3(),
                new SurfaceProps(PixelGeometry.RGB_H));

        canvas = surface.getCanvas();
    }

    private void dimensionsUpdateHandle() {
        int[] width = new int[1];
        int[] height = new int[1];
        float[] scaleX = new float[1];
        float[] scaleY = new float[1];

        glfwGetFramebufferSize(window, width, height);
        glfwGetWindowContentScale(window, scaleX, scaleY);
        assert scaleX[0] == scaleY[0];

        size = new Size(
                (int) (width[0] / scaleX[0]),
                (int) (height[0] / scaleY[0])
        );
        stage.size.setType(AliveType.SEPARATED);
        stage.size.setWidth(size.width() / dpi);
        stage.size.setHeight(size.width() / dpi);

        this.dpi = scaleX[0];
    }

    private void windowDestroyHandle() {
        closeTag = true;
        try {
            Thread.sleep(0);
            Callbacks.glfwFreeCallbacks(window);
            GLFW.glfwDestroyWindow(window);
        } catch (Exception e) {
            //Throw away and ignore
            e.printStackTrace();
        }
        EventManager.callEvent(new UIWindowClosedEvent(this));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        GLFW.glfwSetWindowTitle(window, title);
    }

    public float getDpi() {
        return dpi;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        GLFW.glfwSwapInterval(vsync ? 1 : 0);
    }

    public Pos getCursorPosition() {
        return cursorPosition;
    }

    public Size getSize() {
        return size;
    }

    public DirectContext getContext() {
        return context;
    }

    public BackendRenderTarget getRenderTarget() {
        return renderTarget;
    }

    public Surface getSurface() {
        return surface;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void resize(double width, double height) {
        GLFW.glfwSetWindowSize(window, (int) (width / dpi), (int) (height / dpi));
    }

    public void movePos(double posX, double posY) {
        GLFW.glfwSetWindowPos(window, (int) posX, (int) posY);
    }

    public void setCursor(CursorType cursorType) {
        GLFW.glfwSetCursor(window, GLFW.glfwCreateStandardCursor(cursorType.cursorIdx));
//        GLFW.glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public void close() {
        closeTag = true;
        glfwSetWindowShouldClose(window, true);
    }

    public enum CursorType {
        ARROW(GLFW_ARROW_CURSOR),
        IBEAM(GLFW_IBEAM_CURSOR),
        CROSS_HARE(GLFW_CROSSHAIR_CURSOR),
        POINTING_HAND(GLFW_POINTING_HAND_CURSOR);
        private int cursorIdx = GLFW_CURSOR_NORMAL;

        CursorType(int cursorIdx) {
            this.cursorIdx = cursorIdx;
        }

        public int getCursorIdx() {
            return cursorIdx;
        }
    }
}
