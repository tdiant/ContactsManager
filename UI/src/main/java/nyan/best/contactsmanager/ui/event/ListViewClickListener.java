package nyan.best.contactsmanager.ui.event;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.ui.stage.MainStage;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseClickEvent;
import nyan.best.contactsmanager.uicore.node.spirit.ListView;

public class ListViewClickListener implements EventListener {

    private final MainStage mainStage;

    public ListViewClickListener(MainStage mainStage) {
        this.mainStage = mainStage;
    }

    @EventHandle
    public void onClickListView(UIMouseClickEvent e) {
        var node = e.getClickedNode();
        if (!(node instanceof ListView.ListViewItem listViewItem))
            return;
        var index = listViewItem.getIndex();
        var c = mainStage.getContactList().get(index);
        mainStage.getContactTransfer().setFakeContact(c);
    }

}
