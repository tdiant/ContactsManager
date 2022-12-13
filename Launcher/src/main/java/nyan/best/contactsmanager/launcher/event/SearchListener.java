package nyan.best.contactsmanager.launcher.event;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.launcher.AppLauncher;
import nyan.best.contactsmanager.launcher.data.LauncherDatabaseManager;
import nyan.best.contactsmanager.uicore.event.text.UITextInputValueChangeEvent;

import static nyan.best.contactsmanager.launcher.AppLauncher.contactList;

public class SearchListener implements EventListener {

    @EventHandle
    public void onSearchInput(UITextInputValueChangeEvent e) {
        if (e.getTextInput().getName() == null || !e.getTextInput().getName().equalsIgnoreCase("TextInputSearch"))
            return;
        var txt = e.getTextInput();

        System.out.println(txt.getValue());

        if (txt.getValue().isEmpty()) {
            contactList.clear();
            AppLauncher.contactList.addAll(LauncherDatabaseManager.contactService.list(1, 200));
        } else {
            var ls = LauncherDatabaseManager.contactService.search(txt.getValue());
            System.out.println(ls);
            contactList.clear();
            contactList.addAll(ls);
        }
    }

}
