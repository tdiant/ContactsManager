package nyan.best.contactsmanager.uicore.attribute;

import java.util.Objects;

public class AliveSize extends AliveAttribute<AliveSize> {

    protected AliveValue<Double> width;
    protected AliveValue<Double> height;

    public AliveSize(AliveType type, double width, double height) {
        super(type);
        this.width = new AliveValue<>(type, width);
        this.height = new AliveValue<>(type, height);
    }

    public AliveSize(AliveType type, AliveSize parent, double width, double height) {
        this(type, width, height);
        super.setParent(parent);
        if (parent != null) {
            this.width = new AliveValue<>(type, parent.width, width);
            this.height = new AliveValue<>(type, parent.height, height);
        }
    }

    public double getWidth() {
        return width.getValue();
    }

    public void setWidth(double width) {
        this.width.setValue(width);
        refreshAfterSetterCalled();
    }

    public double getHeight() {
        return height.getValue();
    }

    public void setHeight(double height) {
        this.height.setValue(height);
        refreshAfterSetterCalled();
    }

    @Override
    protected void refreshAfterSetterCalled() {
        if (this.getParent() != null) {
            width.setParent(this.getParent().width);
            height.setParent(this.getParent().height);
        }
        width.setType(this.getType());
        height.setType(this.getType());
    }

    @Override
    public AliveSize toSeparatedAttribute() {
        return new AliveSize(
                AliveType.SEPARATED,
                getParent(),
                width.getSeparatedValue(),
                height.getSeparatedValue()
        );
    }

    public AliveSize clone() {
        return new AliveSize(getType(), getParent(), width.getValue(), height.getValue());
    }

    @Override
    public String toString() {
        return "AliveSize{" +
                "width=" + width.getValue() +
                ", height=" + height.getValue() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliveSize aliveSize = (AliveSize) o;
        return Objects.equals(width, aliveSize.width) && Objects.equals(height, aliveSize.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
