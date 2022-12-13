package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.node.Node;

import java.util.HashMap;

public class SimpleCircle extends Node {

    public SimpleCircle() {
        getStyle().putAll(new HashMap<>() {{
            put("Color", "255,0,0,0");
        }});
    }
}
