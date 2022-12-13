package nyan.best.contactsmanager.eventsys;

public interface EventCancellable {

    void setCancelled(boolean status);

    boolean isCancelled();

}
