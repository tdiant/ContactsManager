package nyan.best.contactsmanager.uicore.attribute;


public abstract class AliveAttribute<T extends AliveAttribute<?>> {

    private AliveType type;
    private T parent;

    private AliveAttribute() {
    }

    public AliveAttribute( AliveType type) {
        this.type = type;
    }

    public AliveAttribute(AliveType type, T parent) {
        this.type = type;
        this.parent = parent;
    }

    public AliveType getType() {
        return type;
    }

    public void setType(AliveType type) {
        this.type = type;
        refreshAfterSetterCalled();
    }

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
        refreshAfterSetterCalled();
    }

    protected abstract void refreshAfterSetterCalled();

    public abstract T toSeparatedAttribute();

}
