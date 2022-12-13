package nyan.best.contactsmanager.ui.stage;

import nyan.best.contactsmanager.common.entity.FakeContact;
import nyan.best.contactsmanager.common.i18n.I18N;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.ui.event.ContactListener;
import nyan.best.contactsmanager.ui.event.ListViewClickListener;
import nyan.best.contactsmanager.ui.panel.MainDetailsPanel;
import nyan.best.contactsmanager.ui.panel.MainListPanel;
import nyan.best.contactsmanager.ui.transfer.ContactTransfer;
import nyan.best.contactsmanager.ui.transfer.ListTransfer;
import nyan.best.contactsmanager.uicore.Stage;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;

import java.util.UUID;

public class MainStage extends Stage {

    private Pane root = new Pane();
    public MainListPanel listPanel;
    public MainDetailsPanel detailsPanel = null;

    private final ListTransfer<FakeContact> contactList;
    private final ContactTransfer contactTransfer;

    public MainStage(AliveSize size, AlivePosition position, ListTransfer<FakeContact> contactList, ContactTransfer contactTransfer) {
        super(I18N.lang("stage.main.title"), size, position);
        this.contactList = contactList;
        this.contactTransfer = contactTransfer;

        init();

        this.setRootNode(root);
//        NODE_RENDER_CLZ.put(MainBackgroundPanel.MainBackgroundNode.class, MainBackgroundPanel.MainBackgroundNodeRender.class);
    }

    private void init() {
        root.getStyle().put("Background", "255,255,255,255");
//        root.getStyle().put("Background", "255,255,0,123");
//        root.addChild(new MainBackgroundPanel(root.getPosition(), root.getSize()));
        root.setSize(new AliveSize(AliveType.RELATED_RATIO, this.size, 1, 1));

        listPanel = new MainListPanel();
        listPanel.getSize().setWidth(290);
        listPanel.init(contactList);
        root.addChild(listPanel);

        UUID uuid = UUID.randomUUID();
        Runnable detailLoadRunnable = () -> {
            if (detailsPanel != null)
                root.removeChild(detailsPanel);

            if (contactTransfer.getContact() == null)
                return;

            detailsPanel = new MainDetailsPanel(contactTransfer.getContact());
//            detailsPanel = new MainDetailsPanel(new Contact(
//                    uuid,
//                    "张三",
//                    Arrays.asList(
//                            new PhoneItem(PhoneItem.PhoneType.MOBILE, "13000001234", uuid),
//                            new PhoneItem(PhoneItem.PhoneType.MOBILE, "18520001289", uuid)
//                    ),
//                    Arrays.asList(
//                            "i@tdiant.net",
//                            "tdiant@126.com"
//                    ),
//                    Arrays.asList(
//                            new PersonDateItem("生日", new Date(2002, 6, 6), uuid),
//                            new PersonDateItem("入学日", new Date(2020, 9, 23), uuid)
//                    ),
//                    Arrays.asList(
//                            new InstanceMessageItem("QQ", "149385534", uuid)
//                    ),
//                    ""
//            ));
            detailsPanel.getPosition().setLeft(290);
            detailsPanel.init();

            root.addChild(detailsPanel);
        };
        detailLoadRunnable.run();
        contactTransfer.addSetRunnable(detailLoadRunnable);

//        detailsPanel.getStyle().put("Background", "255,100,100,100");

        EventManager.registerEventHandles(new ListViewClickListener(this));
        EventManager.registerEventHandles(new ContactListener(this));
    }

    public ListTransfer<FakeContact> getContactList() {
        return contactList;
    }

    public ContactTransfer getContactTransfer() {
        return contactTransfer;
    }

    @Override
    public void onDraw() {
        root.getSize().setWidth(size.getWidth());
        root.getSize().setHeight(size.getHeight());

        listPanel.getSize().setHeight(size.getHeight());

        if (detailsPanel != null) {
            detailsPanel.getSize().setHeight(size.getHeight());
            detailsPanel.getSize().setWidth(size.toSeparatedAttribute().getWidth() - 290);
        }

    }
}
