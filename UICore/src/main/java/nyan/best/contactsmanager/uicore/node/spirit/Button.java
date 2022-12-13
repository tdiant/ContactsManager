package nyan.best.contactsmanager.uicore.node.spirit;

import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;

public class Button extends Node {

    private String label = "";

    public Button() {
        this("Button");
    }

    public Button(String label) {
        this.label = label;
        _initInnerChildren();
        getStyle().put("BtnBackgroundColor", "255,210,210,210");
    }

    public void _initInnerChildren() {
        var btnBg = this.getChildren(true).stream()
                .filter(x -> x instanceof SimpleRectangle)
                .findAny()
                .orElseGet(() -> {
                    SimpleRectangle rec = new SimpleRectangle();
                    rec.setInnerTag(true);
                    rec.setParent(this);
                    addChild(rec);
                    return rec;
                });
        double animationClickIdx = Double.parseDouble(this.getStyle().getOrDefault("#BtnClickProgressIdx", "0"));
        if (animationClickIdx == 0) {
            btnBg.setSize(new AliveSize(AliveType.RELATED_RATIO, this.size, 1, 1));
            btnBg.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, 0, 0));
        } else {
            btnBg.setSize(new AliveSize(AliveType.SEPARATED, size.getWidth() - (2 * animationClickIdx), size.getHeight() - (2 * animationClickIdx)));
            btnBg.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.position, animationClickIdx, animationClickIdx));
        }
        btnBg.getStyle().put(
                "CirRadius",
                "6"
        );
        btnBg.getStyle().put(
                "Color",
                this.getStyle().getOrDefault(
                        "#BtnBackgroundColor",
                        this.getStyle().get("BtnBackgroundColor")
                )
        );

        var btnLabel = this.getChildren(true).stream()
                .filter(x -> x instanceof SimpleLabel)
                .findAny()
                .orElseGet(() -> {
                    SimpleLabel label = new SimpleLabel(this.label);
                    label.setInnerTag(true);
                    label.setParent(this);
                    addChild(label);
                    return label;
                });
        btnLabel.setSize(new AliveSize(AliveType.RELATED_RATIO, this.size, 1, 1));
        btnLabel.setPosition(new AlivePosition(
                AliveType.RELATED_SEPARATED,
                this.position,
                0, 0
        ));
        btnLabel.getStyle().put("TextColor",
                this.getStyle().getOrDefault(
                        "BtnTextColor",
                        "255,0,0,0"
                )
        );

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
