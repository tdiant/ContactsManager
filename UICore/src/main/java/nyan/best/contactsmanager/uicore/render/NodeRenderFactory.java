package nyan.best.contactsmanager.uicore.render;

import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.node.pane.Card;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.*;
import nyan.best.contactsmanager.uicore.render.node.EmptyNodeRender;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;
import nyan.best.contactsmanager.uicore.render.node.pane.CardRender;
import nyan.best.contactsmanager.uicore.render.node.pane.PaneRender;
import nyan.best.contactsmanager.uicore.render.node.spirit.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class NodeRenderFactory {

    public static final Map<Class<? extends Node>, Class<? extends NodeRender<?>>> NODE_RENDER_CLZ = new HashMap<>() {{
        put(Pane.class, PaneRender.class);
        put(Card.class, CardRender.class);
        put(SimpleLabel.class, SimpleLabelRender.class);
        put(SimpleRectangle.class, SimpleRectangleRender.class);
        put(SimpleLine.class, SimpleLineRender.class);
        put(SimpleCircle.class, SimpleCircleRender.class);
        put(Button.class, ButtonRender.class);
        put(TextInput.class, TextInputRender.class);
        put(ListView.class, ListViewRender.class);
        put(ListView.ListViewItem.class, ListView.ListViewItem.ListViewItemRender.class);
        put(ScrollBar.class, ScrollBarRender.class);
        put(TextInput.TextInputLabel.class, TextInputRender.TextInputValLabelRender.class);
    }};

    public static <T extends Node> NodeRender<T> gottaNodeRender(WindowInstance window, T nodeObj) {
        if (nodeObj == null) return null;
        var cls = NODE_RENDER_CLZ.get(nodeObj.getClass());
        if (cls == null) {
            return new EmptyNodeRender<>(window, nodeObj);
        }
        try {
            return (NodeRender<T>) cls
                    .getConstructor(WindowInstance.class, nodeObj.getClass())
                    .newInstance(window, nodeObj);
        } catch (NoSuchMethodException
                 | InvocationTargetException
                 | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
