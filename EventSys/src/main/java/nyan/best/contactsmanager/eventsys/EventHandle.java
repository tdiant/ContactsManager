package nyan.best.contactsmanager.eventsys;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandle {

    EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default false;

}
