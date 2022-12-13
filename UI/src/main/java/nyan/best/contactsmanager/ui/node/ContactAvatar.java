package nyan.best.contactsmanager.ui.node;

import io.github.humbleui.skija.Path;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class ContactAvatar extends Node {

    static {
        NODE_RENDER_CLZ.put(ContactAvatar.class, ContactAvatarRender.class);
    }

    public static class ContactAvatarRender extends NodeRender<ContactAvatar> {
        public ContactAvatarRender(WindowInstance window, ContactAvatar nodeObj) {
            super(window, nodeObj);
        }

        @Override
        public void onDraw() {
            int layer = window.getCanvas().save();

            Path path = new Path();
            path.addCircle(0, 0, 0.5f);

            window.getCanvas().clipPath(path, true);

            window.getCanvas().restoreToCount(layer);
            path.close();
        }
    }

}
