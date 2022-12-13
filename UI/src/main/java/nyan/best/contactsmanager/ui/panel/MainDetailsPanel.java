package nyan.best.contactsmanager.ui.panel;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.ui.node.DetailsCard;
import nyan.best.contactsmanager.ui.node.DetailsTitle;
import nyan.best.contactsmanager.ui.node.details.DetailEmailItem;
import nyan.best.contactsmanager.ui.node.details.DetailInstanceMsgItem;
import nyan.best.contactsmanager.ui.node.details.DetailModifyItem;
import nyan.best.contactsmanager.ui.node.details.DetailPhoneItem;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.Button;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleRectangle;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class MainDetailsPanel extends Pane {

    static {
        NODE_RENDER_CLZ.put(MainDetailsPanel.class, MainDetailsPanelRender.class);
    }

    private final Contact contact;

    private SimpleRectangle bg;
    private SimpleLabel contactNameLabel;

    public MainDetailsPanel(Contact contact) {
        this.contact = contact;
    }

    public void init() {
//        this.getStyle().put("Background", "255,27,27,27");
        bg = new SimpleRectangle();
        bg.setName("bg");
        bg.setSize(new AliveSize(AliveType.SEPARATED,
                Math.min(size.toSeparatedAttribute().getWidth() - 20, 600),
                100
        ));
        bg.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 10, 30));
        bg.getStyle().put("CirRadius", "10");
        bg.getStyle().put("Color", "255,245,245,245");
        addChild(bg);

        contactNameLabel = new SimpleLabel(contact.getName());
        contactNameLabel.setParent(bg);
        contactNameLabel.setSize(new AliveSize(AliveType.SEPARATED, 100, 40));
        contactNameLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, bg.getPosition(), 25, 20));
        contactNameLabel.getStyle().put("FontSize", "22");
        contactNameLabel.getStyle().put("FontType", "BOLD");
        contactNameLabel.getStyle().put("TextCenterX", "false");
        contactNameLabel.getStyle().put("TextCenterY", "false");
        bg.addChild(contactNameLabel);

        DetailsCard phoneCard = new DetailsCard();
        phoneCard.addDetailsSubPane(new DetailsTitle("联系号码", "+", "addPhoneBtn"));
        for (var x : contact.getPhones()) {
            phoneCard.addDetailsSubPane(new DetailPhoneItem(x));
            System.out.println(x);
        }
        phoneCard.cntMaxHeight();
        addDetailsSubPane(phoneCard);

        DetailsCard emailCard = new DetailsCard();
        emailCard.addDetailsSubPane(new DetailsTitle("邮箱地址", "+", "addEmailBtn"));
        int idx = 0;
        for (var x : contact.getEmails()) {
            emailCard.addDetailsSubPane(new DetailEmailItem(idx, x));
            System.out.println(x);
            idx++;
        }
        emailCard.cntMaxHeight();
        addDetailsSubPane(emailCard);

        DetailsCard instanceMsgCard = new DetailsCard();
        instanceMsgCard.addDetailsSubPane(new DetailsTitle("聊天工具", "+", "addInstantMsgBtn"));
        for (var x : contact.getInstanceMessages()) {
            instanceMsgCard.addDetailsSubPane(new DetailInstanceMsgItem(x));
            System.out.println(x);
        }
        instanceMsgCard.cntMaxHeight();
        addDetailsSubPane(instanceMsgCard);

        Button btnRemove = new Button("删除");
        btnRemove.getStyle().put("BtnBackgroundColor", "255,255,77,79");
        btnRemove.getStyle().put("BtnTextColor", "255,255,255,255");
        btnRemove.setSize(new AliveSize(AliveType.SEPARATED, 40, 30));
        btnRemove.setName("BtnRemoveContact");
        addDetailsSubPane(btnRemove);
    }

    private void addDetailsSubPane(Node pane) {
        var maxHeight = 0.0;
        for (var p : bg.getChildren(true)) {
            maxHeight = Math.max(maxHeight, p.getPosition().getTop() + p.getSize().toSeparatedAttribute().getHeight());
        }
        pane.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 15, maxHeight + 10));
        bg.addChild(pane);
    }

    public List<DetailModifyItem> modifyItems = new ArrayList<>();
    public Runnable modifyCallback = () -> {
    };

    public void modifyModeShow(String title, List<String> items, ModifyCallback callback) {
        bg.getChildren().forEach(x -> {
            if (x instanceof DetailsCard || x instanceof Button) {
                bg.removeChild(x);
            }
        });

        DetailsCard modifyCard = new DetailsCard();
        modifyCard.addDetailsSubPane(new DetailsTitle(title, null, null));
        items.forEach(x -> {
            var modifyItem = new DetailModifyItem(x);
            modifyCard.addDetailsSubPane(modifyItem);
            modifyItems.add(modifyItem);
        });

        Button btnSubmit = new Button("提交");
        btnSubmit.getStyle().put("BtnBackgroundColor", "255,22,119,255");
        btnSubmit.getStyle().put("BtnTextColor", "255,255,255,255");
        btnSubmit.setSize(new AliveSize(AliveType.SEPARATED, 40, 30));
        btnSubmit.setName("BtnSubmitModify");
        modifyCard.addDetailsSubPane(btnSubmit);

        modifyCard.cntMaxHeight();
        addDetailsSubPane(modifyCard);
        modifyCallback = () -> callback.run(
                modifyItems.stream()
                        .map(x -> x.getValueTxt().getValue())
                        .collect(Collectors.toList())
        );
    }

    public interface ModifyCallback {
        void run(List<String> values);
    }

    public Contact getContact() {
        return contact;
    }

    public static class MainDetailsPanelRender extends PaneRender {
        public MainDetailsPanelRender(WindowInstance window, MainDetailsPanel nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            super.onDraw();
            nodeObj.getChildren().forEach(x -> {
                if (x.getName().equals("bg")) {
                    x.getSize().setWidth(
                            Math.min(nodeObj.getSize().toSeparatedAttribute().getWidth() - 20, 600)
                    );
                    var maxHeight = 0.0;
                    for (var p : x.getChildren(true)) {
                        maxHeight = Math.max(maxHeight, p.getPosition().getTop() + p.getSize().toSeparatedAttribute().getHeight());

                        p.getSize().setWidth(x.getSize().toSeparatedAttribute().getWidth() - p.getPosition().getLeft() * 2);
                    }
                    x.getSize().setHeight(maxHeight + 10);
                    x.getPosition().setLeft((nodeObj.getSize().getWidth() - x.getSize().getWidth()) / 2);
                }
            });
        }


    }

}
