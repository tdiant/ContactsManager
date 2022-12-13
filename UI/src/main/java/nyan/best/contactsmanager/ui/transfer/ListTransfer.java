package nyan.best.contactsmanager.ui.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class ListTransfer<T> extends ArrayList<T> {

    private final List<Runnable> runnables = new ArrayList<>();

    public void addRunnable(Runnable runnable) {
        this.runnables.add(runnable);
        callRunnable();
    }

    @Override
    public boolean add(T t) {
        var result = super.add(t);
        callRunnable();
        return result;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        callRunnable();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        var result = super.addAll(c);
        callRunnable();
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        var result = super.addAll(index, c);
        callRunnable();
        return result;
    }

    @Override
    public boolean remove(Object o) {
        var result = super.remove(o);
        callRunnable();
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var result = super.removeAll(c);
        callRunnable();
        return result;
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        var result = super.removeIf(filter);
        callRunnable();
        return result;
    }

    @Override
    public T set(int index, T element) {
        var result = super.set(index, element);
        callRunnable();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        callRunnable();
    }

    private void callRunnable() {
        runnables.forEach(Runnable::run);
    }

}
