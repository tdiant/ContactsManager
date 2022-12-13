package nyan.best.contactsmanager.launcher.event;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.launcher.AppMain;
import nyan.best.contactsmanager.uicore.event.window.UIWindowClosedEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class WindowListener implements EventListener {

    @EventHandle
    public void onWindowClose(UIWindowClosedEvent e) {
        if (AppMain.launcher.nowStage == null)
            return;
        if (e.getWindow() == AppMain.launcher.nowStage.getWindow()) {
            System.exit(1);
            AppMain.launcher.gcThread.stop();
            GLFW.glfwTerminate();
            Objects.requireNonNull(GLFW.glfwSetErrorCallback(null)).free();
        }
    }

}
