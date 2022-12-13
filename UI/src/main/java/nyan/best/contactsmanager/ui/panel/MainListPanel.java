package nyan.best.contactsmanager.ui.panel;

import nyan.best.contactsmanager.common.entity.FakeContact;
import nyan.best.contactsmanager.ui.transfer.ListTransfer;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.Button;
import nyan.best.contactsmanager.uicore.node.spirit.ListView;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.node.spirit.TextInput;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import java.util.stream.Collectors;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class MainListPanel extends Pane {

    static {
        NODE_RENDER_CLZ.put(MainListPanel.class, MainListPanelRender.class);
    }

    public SimpleLabel title;
    public TextInput searchText;
    public ListView listView;

    public MainListPanel() {
    }

    public void init(ListTransfer<FakeContact> contactList) {
        this.getStyle().put("Background", "228,242,242,242");
        title = new SimpleLabel("联系人");
        title.setParent(this);
        title.setSize(new AliveSize(AliveType.RELATED_SEPARATED, 100, 100));
        title.setPosition(new AlivePosition(AliveType.SEPARATED, 30, 10));
//        title.getStyle().put("Background", "255,0,0,0");
        title.getStyle().put("FontType", "BOLD");
        title.getStyle().put("FontSize", "32");
        title.getStyle().put("TextCenterX", "false");
        title.getStyle().put("TextCenterY", "false");
        addChild(title);

        Button addContactBtn = new Button("+");
        addContactBtn.setSize(new AliveSize(AliveType.SEPARATED, 35, 35));
        addContactBtn.setPosition(new AlivePosition(AliveType.SEPARATED, size.toSeparatedAttribute().getWidth() - 65, 20));
        addContactBtn.setName("BtnAddContact");
        addChild(addContactBtn);

        searchText = new TextInput("搜索");
        searchText.setName("TextInputSearch");
        searchText.setParent(this);
        searchText.setSize(new AliveSize(AliveType.SEPARATED, size.toSeparatedAttribute().getWidth() - 60, 38));
        searchText.setPosition(new AlivePosition(AliveType.SEPARATED, 30, 60));
        addChild(searchText);

        listView = new ListView();
        listView.setParent(this);
        listView.setSize(new AliveSize(AliveType.SEPARATED, size.toSeparatedAttribute().getWidth() - 60, 100));
        listView.setPosition(new AlivePosition(AliveType.SEPARATED, 30, 110));
//        listView.setData(Arrays.asList("test1", "test2", "test3", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456", "test456"));
        listView.setData(
                contactList.stream()
                        .map(FakeContact::getName)
                        .collect(Collectors.toList())
        );
        listView.setScrollOffsetY(30);
        listView.getStyle().put("OverflowShow", "false");
        addChild(listView);

        contactList.addRunnable(() -> {
            System.out.println(contactList);
            listView.setData(
                    contactList.stream()
                            .map(FakeContact::getName)
                            .collect(Collectors.toList())
            );
        });
    }

    public static class MainListPanelRender extends PaneRender {
        private final MainListPanel instance;

        public MainListPanelRender(WindowInstance window, MainListPanel nodeObj) {
            super(window, nodeObj);
            this.instance = nodeObj;
        }

        @Override
        public void onDraw() {
            super.onDraw();
            instance.listView.getSize().setHeight(
                    nodeObj.getSize().getHeight() - 30
            );
        }
    }

}
