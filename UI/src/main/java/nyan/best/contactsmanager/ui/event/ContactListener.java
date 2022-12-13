package nyan.best.contactsmanager.ui.event;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.common.entity.InstanceMessageItem;
import nyan.best.contactsmanager.common.entity.PhoneItem;
import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.ui.node.details.DetailEmailItem;
import nyan.best.contactsmanager.ui.node.details.DetailInstanceMsgItem;
import nyan.best.contactsmanager.ui.node.details.DetailPhoneItem;
import nyan.best.contactsmanager.ui.stage.MainStage;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseClickEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseEvent;
import nyan.best.contactsmanager.uicore.node.spirit.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactListener implements EventListener {

    private final MainStage mainStage;

    public ContactListener(MainStage mainStage) {
        this.mainStage = mainStage;
    }

    @EventHandle
    public void onRemoveContactClick(UIMouseClickEvent e) {
        if (e.getClickedNode() instanceof Button btn) {
            if (!btn.getName().equalsIgnoreCase("BtnRemoveContact"))
                return;
            var contact = mainStage.getContactTransfer().getContact();
            System.out.println(contact);
            mainStage.getContactList().removeIf(x -> x.getUuid().equals(contact.getUuid()));
            mainStage.getContactTransfer().getRemoveRunnables().forEach(Runnable::run);
            mainStage.getContactTransfer().setFakeContact(null);
            mainStage.getContactTransfer().setContact(null);
        }
    }

    @EventHandle
    public void onModifySubmit(UIMouseClickEvent e) {
        if (e.getClickedNode() instanceof Button btn) {
            if (btn.getName() == null)
                return;
            if (mainStage.detailsPanel == null)
                return;
            if (btn.getName().equalsIgnoreCase("BtnSubmitModify"))
                mainStage.detailsPanel.modifyCallback.run();
        }
    }

    @EventHandle
    public void onAddContact(UIMouseClickEvent e) {
        if (e.getClickedNode() instanceof Button btn) {
            if (btn.getName() == null)
                return;
            if (btn.getName().equalsIgnoreCase("BtnAddContact")) {
                mainStage.getContactTransfer().setContact(new Contact(
                        UUID.randomUUID(),
                        "新建联系人",
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), ""
                ));
                mainStage.detailsPanel.modifyModeShow(
                        "联系人姓名",
                        List.of("姓名"),
                        (ls) -> {
                            mainStage.getContactTransfer().contactName = ls.size() > 0 ? ls.get(0) : "";
                            mainStage.getContactTransfer().getCreateRunnables().forEach(Runnable::run);

                        }
                );
            }
        }
    }

    @EventHandle
    public void onModifyItem(UIMouseClickEvent e) {
        if (e.getBtnCode() != UIMouseEvent.MOUSE_BTN_CODE_LEFT)
            return;
        if (mainStage.detailsPanel == null || mainStage.getContactTransfer().getContact() == null)
            return;
        var node = e.getClickedNode();
        var contact = mainStage.getContactTransfer().getContact();

        if (node instanceof DetailPhoneItem phoneItem) {
            mainStage.detailsPanel.modifyModeShow(
                    "修改联系方式",
                    List.of("号码"),
                    (ls) -> {
                        var value = ls.size() > 0 ? ls.get(0) : "";
                        phoneItem.phoneItem.setValue(value);

                        mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
                        mainStage.getContactTransfer().setFakeContact(
                                mainStage.getContactTransfer().getFakeContact()
                        );
                    }
            );
        }
        if (node instanceof DetailEmailItem emailItem) {
            mainStage.detailsPanel.modifyModeShow(
                    "修改邮箱",
                    List.of("地址"),
                    (ls) -> {
                        var value = ls.size() > 0 ? ls.get(0) : "";
                        contact.setEmails(new ArrayList<>(contact.getEmails()));
                        contact.getEmails().set(emailItem.index, value);

                        mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
                        mainStage.getContactTransfer().setFakeContact(
                                mainStage.getContactTransfer().getFakeContact()
                        );
                    }
            );
        }
        if (node instanceof DetailInstanceMsgItem instanceMsgItem) {
            mainStage.detailsPanel.modifyModeShow(
                    "修改聊天工具",
                    List.of("种类","地址"),
                    (ls) -> {
                        var type = ls.size() > 0 ? ls.get(0) : "";
                        var value = ls.size() > 1 ? ls.get(1) : "";

                        instanceMsgItem.instanceMsgItem.setType(type);
                        instanceMsgItem.instanceMsgItem.setValue(value);

                        mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
                        mainStage.getContactTransfer().setFakeContact(
                                mainStage.getContactTransfer().getFakeContact()
                        );
                    }
            );
        }
    }

    @EventHandle
    public void onRemoveItem(UIMouseClickEvent e) {
        if (e.getBtnCode() != UIMouseEvent.MOUSE_BTN_CODE_RIGHT)
            return;
        if (mainStage.detailsPanel == null || mainStage.getContactTransfer().getContact() == null)
            return;
        var node = e.getClickedNode();
        var contact = mainStage.getContactTransfer().getContact();

        if (node instanceof DetailPhoneItem phoneItem) {
            var ls = new ArrayList<>(contact.getPhones());
            ls.remove(phoneItem.phoneItem);
            contact.setPhones(ls);
            mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
            mainStage.getContactTransfer().setContact(mainStage.getContactTransfer().getContact());
        }
        if (node instanceof DetailEmailItem emailItem) {
            var ls = new ArrayList<>(contact.getEmails());
            ls.remove(emailItem.email);
            contact.setEmails(ls);
            mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
            mainStage.getContactTransfer().setContact(mainStage.getContactTransfer().getContact());
        }
        if (node instanceof DetailInstanceMsgItem instanceMsgItem) {
            var ls = new ArrayList<>(contact.getInstanceMessages());
            ls.remove(instanceMsgItem.instanceMsgItem);
            contact.setInstanceMessages(ls);
            mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
            mainStage.getContactTransfer().setContact(mainStage.getContactTransfer().getContact());
        }
    }

    @EventHandle
    public void onAddContactSubItem(UIMouseClickEvent e) {
        if (e.getClickedNode() instanceof Button btn) {
            if (btn.getName() == null)
                return;
            if (mainStage.detailsPanel == null)
                return;
            switch (btn.getName()) {
                case "addPhoneBtn" -> {
                    mainStage.detailsPanel.modifyModeShow(
                            "添加联系方式",
                            List.of("号码"),
                            (ls) -> {
                                var contact = mainStage.getContactTransfer().getContact();
                                var value = ls.size() > 0 ? ls.get(0) : "";

                                var phones = new ArrayList<>(contact.getPhones());
                                phones.add(new PhoneItem(PhoneItem.PhoneType.MOBILE, value, contact.getUuid()));
                                contact.setPhones(phones);

                                mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
                                mainStage.getContactTransfer().setFakeContact(
                                        mainStage.getContactTransfer().getFakeContact()
                                );
                            }
                    );
                }
                case "addEmailBtn" -> {
                    mainStage.detailsPanel.modifyModeShow(
                            "添加邮箱",
                            List.of("邮箱地址"),
                            (ls) -> {
                                var contact = mainStage.getContactTransfer().getContact();
                                var value = ls.size() > 0 ? ls.get(0) : "";

                                var emails = new ArrayList<>(contact.getEmails());
                                emails.add(value);
                                contact.setEmails(emails);

                                mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
                                mainStage.getContactTransfer().setFakeContact(
                                        mainStage.getContactTransfer().getFakeContact()
                                );
                            }
                    );
                }
                case "addInstantMsgBtn" -> {
                    mainStage.detailsPanel.modifyModeShow(
                            "添加聊天工具",
                            List.of("类别", "地址"),
                            (ls) -> {
                                var contact = mainStage.getContactTransfer().getContact();
                                var type = ls.size() > 0 ? ls.get(0) : "";
                                var value = ls.size() > 1 ? ls.get(1) : "";

                                var instantMsgs = new ArrayList<>(contact.getInstanceMessages());
                                instantMsgs.add(new InstanceMessageItem(type, value, contact.getUuid()));
                                contact.setInstanceMessages(instantMsgs);

                                mainStage.getContactTransfer().getUpdateRunnables().forEach(Runnable::run);
                                mainStage.getContactTransfer().setFakeContact(
                                        mainStage.getContactTransfer().getFakeContact()
                                );
                            }
                    );
                }
            }
        }
    }

}
