package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.node.Node;

import java.util.HashMap;

public class SimpleRectangle extends Node {
    public SimpleRectangle() {
        getStyle().putAll(new HashMap<>() {{
            put("Color", "255,0,0,0");
            put("CirRadius", "0");
            put("Rotate", "0");
        }});
    }
}
