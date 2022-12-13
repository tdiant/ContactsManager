package nyan.best.contactsmanager.uicore.event.window;

import nyan.best.contactsmanager.uicore.common.Size;
import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;

public class UIWindowResizeUIEvent extends UIWindowUIEvent {

    private static final HandleList handleList = new HandleList();

    private final Size newSize;

    public UIWindowResizeUIEvent(WindowInstance window, Size newSize) {
        super(window);
        this.newSize = newSize;
    }

    public Size getNewSize() {
        return newSize;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

}
