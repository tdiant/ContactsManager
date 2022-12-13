package nyan.best.contactsmanager.uicore.event.text;

import nyan.best.contactsmanager.eventsys.HandleList;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.event.UIEvent;
import nyan.best.contactsmanager.uicore.node.spirit.TextInput;

public class UITextInputValueChangeEvent extends UIEvent {

    private static final HandleList handleList = new HandleList();

    private final TextInput textInput;

    public UITextInputValueChangeEvent(WindowInstance window, TextInput textInput) {
        super(window);
        this.textInput = textInput;
    }

    public TextInput getTextInput() {
        return textInput;
    }

    public static HandleList getHandleList() {
        return handleList;
    }

    @Override
    public HandleList getHandles() {
        return handleList;
    }
}
