package nyan.best.contactsmanager.uicore.event.transfer;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardClickEvent;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardPressedEvent;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardReleasedEvent;
import nyan.best.contactsmanager.uicore.util.StepProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UIKeyboardClickEventTransfer implements EventTransferListener {

    private final HashMap<Integer, List<WindowInstance>> clickRecordMap = new HashMap<>();
    private final StepProgress sp = new StepProgress(30) {
        private long lastMeowTimestamp = System.currentTimeMillis();

        @Override
        public void meow() {
            for (var keyCode : clickRecordMap.keySet()) {
                for (var window : clickRecordMap.get(keyCode)) {
                    EventManager.callEvent(new UIKeyboardClickEvent(window, keyCode));
                }
            }
        }

        @Override
        public boolean checkCanEnd() {
            return false;
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onEnd() {
        }
    };

    @EventHandle
    public void onKeyPress(UIKeyboardPressedEvent e) {
        var ls = clickRecordMap.getOrDefault(e.getKeyCode(), new ArrayList<>());
        if (ls.contains(e.getWindow())) {
            return;
        }
        ls.add(e.getWindow());
        clickRecordMap.put(e.getKeyCode(), ls);
        sp.start();
    }

    @EventHandle
    public void onKeyReleased(UIKeyboardReleasedEvent e) {
        var ls = clickRecordMap.getOrDefault(e.getKeyCode(), new ArrayList<>());
        if (ls.contains(e.getWindow())) {
            ls.remove(e.getWindow());
            clickRecordMap.put(e.getKeyCode(), ls);
        }
    }

}
