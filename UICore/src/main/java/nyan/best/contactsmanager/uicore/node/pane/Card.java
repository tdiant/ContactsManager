package nyan.best.contactsmanager.uicore.node.pane;

import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleLabel;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleRectangle;

public class Card extends SimpleRectangle {

    private String title;

    private final SimpleLabel titleLabel;

    public Card() {
        this("");
    }

    public Card(String title) {
        this.title = title;

        this.getStyle().put("CirRadius", "6");
        this.getStyle().put("Color", "255,255,255,255");

        this.titleLabel = new SimpleLabel(title.isEmpty() ? " " : "");
        this.titleLabel.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, this.getPosition(), 10, 10));
        this.titleLabel.getStyle().put("TextCenterX", "false");

        setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleLabel.setText(title.isEmpty() ? " " : "");
        if (title.isEmpty()) {
            this.getChildren().remove(titleLabel);
        }
    }

}
