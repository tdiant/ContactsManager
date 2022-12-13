package nyan.best.contactsmanager.uicore.attribute;

import java.util.Objects;

public class AliveValue<T> extends AliveAttribute<AliveValue<T>> {

    private double value;

    public AliveValue(AliveType type, double value) {
        super(type);
        this.value = value;
    }

    public AliveValue(AliveType type, AliveValue<T> parent, double value) {
        super(type, parent);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    protected void refreshAfterSetterCalled() {
    }

    public double getSeparatedValue() {
        return toSeparatedAttribute().getValue();
    }

    @Override
    public AliveValue<T> toSeparatedAttribute() {
        if (getType() == AliveType.RELATED_RATIO) {
            return new AliveValue<>(
                    AliveType.SEPARATED,
                    getParent(),
                    Objects.requireNonNullElse(
                            getParent(),
                            new AliveValue<>(AliveType.SEPARATED, 0)
                    ).getSeparatedValue() * this.getValue()
            );
        } else if (getType() == AliveType.RELATED_SEPARATED) {
            return new AliveValue<>(
                    AliveType.SEPARATED,
                    getParent(),
                    Objects.requireNonNullElse(
                            getParent(),
                            new AliveValue<>(AliveType.SEPARATED, 0)
                    ).getSeparatedValue() + this.getValue()
            );
        }
        return new AliveValue<>(AliveType.SEPARATED, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliveValue<?> that = (AliveValue<?>) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
