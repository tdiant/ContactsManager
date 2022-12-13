package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.node.Node;

import java.util.HashMap;

public class SimpleLabel extends Node {
    private String text = "";

    public SimpleLabel() {
        getStyle().putAll(new HashMap<>() {{
            put("FontFamily", "Microsoft YaHei");
            put("FontType", "NORMAL");
            put("FontSize", "14");
            put("TextColor", "255,0,0,0");
            put("TextStroke", "false");
            put("TextCenterX", "true");
            put("TextCenterY", "true");
        }});
    }

    public SimpleLabel(String text) {
        this();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
