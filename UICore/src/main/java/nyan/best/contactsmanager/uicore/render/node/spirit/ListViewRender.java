package nyan.best.contactsmanager.uicore.render.node.spirit;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.spirit.ListView;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

public class ListViewRender extends NodeRender<ListView> {

    public final ListView instance;

    public ListViewRender(WindowInstance window, ListView nodeObj) {
        super(window, nodeObj);
        this.instance = nodeObj;
    }

    @Override
    public void onDraw() {
        var selfSize = this.nodeObj.getSize().toSeparatedAttribute();
        this.nodeObj.scrollBar.setSize(new AliveSize(AliveType.SEPARATED, this.nodeObj.getSize(), 6, selfSize.getHeight()));
        this.nodeObj.scrollBar.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.nodeObj.getPosition(), selfSize.getWidth() - 6, 0));
        this.nodeObj.scrollBar.proRec.setSize(new AliveSize(AliveType.SEPARATED, this.nodeObj.scrollBar.getSize(), this.nodeObj.scrollBar.getSize().toSeparatedAttribute().getWidth(), 20));
        this.nodeObj.scrollBar.proRec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.nodeObj.scrollBar.getPosition(), 0, 0));

        this.nodeObj.scrollBar.offsetRatio = 1 - (this.nodeObj.cntMaxHeight() / this.nodeObj.lastCntMaxHeight);
        this.nodeObj.scrollBar.totalHeight = getNodeObj().lastCntMaxHeight;
        this.nodeObj.scrollBar.singleHeight = selfSize.getHeight();
//        System.out.println("offsetRatio::" + this.nodeObj.scrollBar.offsetRatio);
    }

}
