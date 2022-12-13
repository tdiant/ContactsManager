package nyan.best.contactsmanager.uicore.event.transfer;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseScrollEvent;
import nyan.best.contactsmanager.uicore.node.spirit.ListView;

public class UIListViewMouseScrollEventTransfer implements EventTransferListener {

    @EventHandle
    public void onMouseScroll(UIMouseScrollEvent e) {
        var node = e.getWindow().getStage().rayToNodeByPos(e.getCurrentCursorPos());
        if (node == null)
            return;

        ListView listView = null;
        if (node instanceof ListView)
            listView = (ListView) node;
        if (node.getParent() != null && node.getParent() instanceof ListView)
            listView = (ListView) node.getParent();

        if (listView == null)
            return;

        listView.setScrollOffsetY((float) (e.getOffsetY() * 10));

    }

}
