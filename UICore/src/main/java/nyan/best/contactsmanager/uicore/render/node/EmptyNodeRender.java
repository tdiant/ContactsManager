package nyan.best.contactsmanager.uicore.render.node;

import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;

public class EmptyNodeRender<T extends Node> extends NodeRender<T> {
    public EmptyNodeRender(WindowInstance window, T nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {

    }

}
