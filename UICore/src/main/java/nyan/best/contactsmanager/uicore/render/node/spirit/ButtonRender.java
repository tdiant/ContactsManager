package nyan.best.contactsmanager.uicore.render.node.spirit;

import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.spirit.Button;

public class ButtonRender extends NodeRender<Button> {

    public ButtonRender(WindowInstance window, Button nodeObj) {
        super(window, nodeObj);
    }

    @Override
    public void onDraw() {
        nodeObj._initInnerChildren();
    }

}
