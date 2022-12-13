package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.event.text.UITextInputValueChangeEvent;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.render.node.spirit.TextInputRender;

import java.util.HashMap;

public class TextInput extends Node {

    private String value;
    private String placeholder;

    public SimpleRectangle bgRec;
    public SimpleRectangle ibeamPosRec;
    public SimpleLabel inputValLabel;

    private int ibeamPosCharIdx = 0;

    public TextInput() {
        this("");
    }

    public TextInput(String placeholder) {
        this("", placeholder);
    }

    public TextInput(String value, String placeholder) {
        this.value = value;
        this.placeholder = placeholder;
        _initInnerChildren();
        getStyle().putAll(new HashMap<>() {{
            put("TextInputBackgroundColor", "255,233,233,233");
            put("OverflowShow", "false");
        }});
    }

    public void _initInnerChildren() {
        _initInnerChildrenInputBg();
        _initInnerChildrenInputValue();
        _initInnerIbeamPosRec();
        moveIbeamPos(this.ibeamPosCharIdx);
    }

    private void _initInnerIbeamPosRec() {
        if (ibeamPosRec == null) {
            ibeamPosRec = new SimpleRectangle();
            ibeamPosRec.setParent(this);
            ibeamPosRec.setInnerTag(true);
            addChild(ibeamPosRec);
        }
        var height = this.size.toSeparatedAttribute().getHeight();
        ibeamPosRec.setSize(new AliveSize(AliveType.SEPARATED, 1, height * 0.85));
        ibeamPosRec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, 0, 0.075 * height));
    }

    private void _initInnerChildrenInputValue() {
        if (inputValLabel == null) {
            inputValLabel = new SimpleLabel();
            inputValLabel.setInnerTag(true);
            inputValLabel.setParent(this);
            inputValLabel.getStyle().put("OverflowShow", "false");
            inputValLabel.getStyle().put("IgnoreParentIntersect", "true");
            inputValLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 10, 0));
            addChild(inputValLabel);
        }
        inputValLabel.getStyle().put("TextCenterX", "false");

//        inputTxt.getStyle().put("Background", "255,0,0,0");

        if (value.isEmpty()) {
            inputValLabel.setText(placeholder);
        } else {
            inputValLabel.setText(value);
        }
    }

    private void _initInnerChildrenInputBg() {
        if (bgRec == null) {
            bgRec = new SimpleRectangle();
            bgRec.setInnerTag(true);
            bgRec.setParent(this);
            addChild(bgRec);
        }
        bgRec.getStyle().put(
                "CirRadius",
                "6"
        );
        bgRec.getStyle().put(
                "Color",
                this.getStyle().getOrDefault(
                        "#TextInputBackgroundColor",
                        this.getStyle().get("TextInputBackgroundColor")
                )
        );
        bgRec.setSize(new AliveSize(AliveType.RELATED_RATIO, this.size, 1, 1));
        bgRec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 0, 0));
    }

    public void moveIbeamPos(int idx) {
        idx = Math.min(Math.max(0, idx), value.length());
        if (getNodeRender() != null)
            ((TextInputRender) getNodeRender()).moveIbeamPos(idx);
        this.ibeamPosCharIdx = idx;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (this.getNodeRender() != null)
            EventManager.callEvent(new UITextInputValueChangeEvent(this.getNodeRender().getWindow(), this));
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public int getIbeamPosCharIdx() {
        return ibeamPosCharIdx;
    }

    public static class TextInputLabel extends SimpleLabel {
    }

}
