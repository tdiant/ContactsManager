package nyan.best.contactsmanager.uicore.event.text;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.event.UIEvent;

public class UITextInputEvent extends UIEvent {

    private static final HandleList handleList = new HandleList();

    private final long charUnicode;

    public UITextInputEvent(WindowInstance window, long charUnicode) {
        super(window);
        this.charUnicode = charUnicode;
    }

    public long getCharUnicode() {
        return charUnicode;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

}
