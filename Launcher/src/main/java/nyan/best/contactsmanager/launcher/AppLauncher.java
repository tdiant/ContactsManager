package nyan.best.contactsmanager.launcher;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.common.entity.FakeContact;
import nyan.best.contactsmanager.common.i18n.I18N;
import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.launcher.data.LauncherDatabaseManager;
import nyan.best.contactsmanager.launcher.event.SearchListener;
import nyan.best.contactsmanager.launcher.event.WindowListener;
import nyan.best.contactsmanager.ui.event.LoadingFinishEvent;
import nyan.best.contactsmanager.ui.event.TestListener;
import nyan.best.contactsmanager.ui.stage.LoadingStage;
import nyan.best.contactsmanager.ui.stage.MainStage;
import nyan.best.contactsmanager.ui.transfer.ContactTransfer;
import nyan.best.contactsmanager.ui.transfer.ListTransfer;
import nyan.best.contactsmanager.uicore.Stage;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.event.transfer.UIKeyboardClickEventTransfer;
import nyan.best.contactsmanager.uicore.event.transfer.UIListViewMouseScrollEventTransfer;
import nyan.best.contactsmanager.uicore.event.transfer.UIMouseClickAndDragEventTransfer;
import nyan.best.contactsmanager.uicore.event.transfer.UIMouseHoverEventTransfer;
import nyan.best.contactsmanager.uicore.event.window.UIWindowClosedEvent;
import nyan.best.contactsmanager.uicore.rule.AnimationButtonClickRule;
import nyan.best.contactsmanager.uicore.rule.AnimationButtonHoverRule;
import nyan.best.contactsmanager.uicore.rule.FocusRefreshRule;
import nyan.best.contactsmanager.uicore.rule.TextInputIbeamPosRule;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;

import java.util.ArrayList;

public class AppLauncher {

    public final GCThread gcThread = new GCThread();
    public Stage nowStage = null;

    public static ListTransfer<FakeContact> contactList = new ListTransfer<>();
    public static ContactTransfer contactTransfer = new ContactTransfer();

    public void runApp() {
        init();
        nowStage.start();
    }

    protected void init() {
        initLib();
        initThreads();
        initDatabase();
        initLang();
        initStages();
        initEvents();
    }

    protected void initDatabase() {
        LauncherDatabaseManager.init();
        LauncherDatabaseManager.loadContacts();
    }

    protected void initThreads() {
        gcThread.start();
    }

    protected void initLib() {
        GLFWUtils.init();
    }

    protected void initLang() {
        I18N.put("stage.main.title", "通信录软件");
        I18N.put("stage.loading.window.title", "加载中");
        I18N.put("details.phone.type.MOBILE", "手机");
        I18N.put("details.phone.type.HOME", "家庭");
        I18N.put("details.phone.type.WORK", "工作");
        I18N.put("details.phone.type.SCHOOL", "学校");
        I18N.put("details.phone.type.FAX", "传真");
    }

    protected void initStages() {
        nowStage = new LoadingStage(
                new AliveSize(AliveType.SEPARATED, 1380, 940),
                new AlivePosition(AliveType.SEPARATED, 400, 300)
        );
        EventManager.registerEventHandles(new EventListener() {
            final Stage loadingStage = nowStage;

            @EventHandle
            public void onLoadFinish(LoadingFinishEvent e) {
                nowStage = new MainStage(
                        new AliveSize(AliveType.SEPARATED, 1380, 940),
                        loadingStage.getPosition(),
                        contactList,
                        contactTransfer
                );
                loadingStage.stop();
            }

            @EventHandle
            public void onWindowClosed(UIWindowClosedEvent e) {
                if (e.getWindow().getStage() == loadingStage)
                    nowStage.start();
            }
        });

        contactTransfer.addFakeRunnable(() -> {
            var fake = contactTransfer.getFakeContact();
            if (fake == null)
                return;
            var contact = LauncherDatabaseManager.contactService.get(fake.getUuid());
            contactTransfer.setContact(contact);
        });
        contactTransfer.addRemoveRunnable(() -> {
            if (contactTransfer.getContact() != null) {
                LauncherDatabaseManager.contactService.remove(contactTransfer.getContact());
                contactList.removeIf(fakeContact -> fakeContact.getUuid().equals(contactTransfer.getContact().getUuid()));
            }
        });
        contactTransfer.addUpdateRunnable(() -> {
            if (contactTransfer.getContact() != null)
                LauncherDatabaseManager.contactService.update(contactTransfer.getContact());
        });
        contactTransfer.addCreateRunnable(() -> {
            var conatct = new Contact(
                    contactTransfer.contactName,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), ""
            );
            LauncherDatabaseManager.contactService.insert(conatct);
            contactTransfer.setContact(conatct);
            contactList.add(new FakeContact(conatct.getUuid(), conatct.getName()));
        });
    }

    protected void initEvents() {
        //UI Core needs
        EventManager.registerEventHandles(new UIMouseClickAndDragEventTransfer());
        EventManager.registerEventHandles(new UIMouseHoverEventTransfer());
        EventManager.registerEventHandles(new UIListViewMouseScrollEventTransfer());
        EventManager.registerEventHandles(new UIKeyboardClickEventTransfer());
        EventManager.registerEventHandles(new AnimationButtonHoverRule());
        EventManager.registerEventHandles(new AnimationButtonClickRule());
        EventManager.registerEventHandles(new FocusRefreshRule());
        EventManager.registerEventHandles(new TextInputIbeamPosRule());

        //Launcher running needs
        EventManager.registerEventHandles(new WindowListener());
        EventManager.registerEventHandles(new SearchListener());

        EventManager.registerEventHandles(new TestListener());
    }

}
