package nyan.best.contactsmanager.uicore.attribute;

import java.util.Objects;

public class AlivePosition extends AliveAttribute<AlivePosition> {

    protected AliveValue<Double> top;
    protected AliveValue<Double> left;

    public AlivePosition(AliveType type, double x, double y) {
        super(type);
        this.top = new AliveValue<>(type, y);
        this.left = new AliveValue<>(type, x);
    }

    public AlivePosition(AliveType type, AlivePosition parent, double x, double y) {
        this(type, x, y);
        super.setParent(parent);
        if (parent != null) {
            this.top = new AliveValue<>(type, parent.top, y);
            this.left = new AliveValue<>(type, parent.left, x);
        }
    }

    public double getTop() {
        return top.getValue();
    }

    public void setTop(double top) {
        this.top.setValue(top);
        refreshAfterSetterCalled();
    }

    public double getLeft() {
        return left.getValue();
    }

    public void setLeft(double left) {
        this.left.setValue(left);
        refreshAfterSetterCalled();
    }

    @Override
    protected void refreshAfterSetterCalled() {
        if (getParent() != null) {
            top.setParent(this.getParent().top);
            left.setParent(this.getParent().left);
        }
        top.setType(this.getType());
        left.setType(this.getType());
    }

    @Override
    public AlivePosition toSeparatedAttribute() {
        return new AlivePosition(
                AliveType.SEPARATED,
                getParent(),
                left.getSeparatedValue(),
                top.getSeparatedValue()
        );
    }

    @Override
    public String toString() {
        return "AlivePosition{" +
                "top=" + top.getValue() +
                ", left=" + left.getValue() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlivePosition that = (AlivePosition) o;
        return Objects.equals(top, that.top) && Objects.equals(left, that.left);
    }

    @Override
    public int hashCode() {
        return Objects.hash(top, left);
    }
}
