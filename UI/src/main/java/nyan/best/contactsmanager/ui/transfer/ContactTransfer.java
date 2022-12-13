package nyan.best.contactsmanager.ui.transfer;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.common.entity.FakeContact;

import java.util.ArrayList;
import java.util.List;

public class ContactTransfer {

    private final List<Runnable> fakeRunnables = new ArrayList<>();
    private final List<Runnable> setRunnables = new ArrayList<>();

    private final List<Runnable> updateRunnables = new ArrayList<>();
    private final List<Runnable> removeRunnables = new ArrayList<>();
    private final List<Runnable> createRunnables = new ArrayList<>();

    public String contactName = "null";

    private FakeContact fakeContact;
    private Contact contact = null;

    public FakeContact getFakeContact() {
        return fakeContact;
    }

    public void setFakeContact(FakeContact fakeContact) {
        this.fakeContact = fakeContact;
        fakeRunnables.forEach(Runnable::run);
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        setRunnables.forEach(Runnable::run);
    }

    public void addFakeRunnable(Runnable runnable) {
        fakeRunnables.add(runnable);
    }

    public void addSetRunnable(Runnable runnable) {
        setRunnables.add(runnable);
    }

    public void addUpdateRunnable(Runnable runnable) {
        updateRunnables.add(runnable);
    }

    public void addRemoveRunnable(Runnable runnable) {
        removeRunnables.add(runnable);
    }

    public void addCreateRunnable(Runnable runnable) {
        createRunnables.add(runnable);
    }

    public List<Runnable> getUpdateRunnables() {
        return updateRunnables;
    }

    public List<Runnable> getRemoveRunnables() {
        return removeRunnables;
    }

    public List<Runnable> getCreateRunnables() {
        return createRunnables;
    }
}
