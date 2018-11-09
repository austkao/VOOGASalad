package messenger.external;

/** Superclass for anything that will be sent through the {@code EventBus}*/
public abstract class Event {

    /** Return the name of the event*/
    public abstract String getName();
}
