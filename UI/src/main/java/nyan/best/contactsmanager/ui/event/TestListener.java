package nyan.best.contactsmanager.ui.event;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardPressedEvent;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardReleasedEvent;

public class TestListener implements EventListener {

    @EventHandle
    public void onInput(UIKeyboardPressedEvent e) {
        System.out.println("keyboard presses::" + e.getKeyCode());
    }

    @EventHandle
    public void onInput2(UIKeyboardReleasedEvent e) {
        System.out.println("Keyboard releases::" + e.getKeyCode());
    }

}
