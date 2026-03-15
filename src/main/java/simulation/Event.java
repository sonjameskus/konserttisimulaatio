package simulation;

/**
 * Event class represents an event in the simulation, containing information about the time of the event, the type of event, and the customer involved.
 */
public class Event implements Comparable<Event> {

    /**
     * The time at which the event occurs, represented as a double value.
     */
    private double time;
    /**
     * The type of event, represented as an EventType enum value. This indicates what kind of event is occurring (e.g., arrival, start of service, end of service, etc.).
     */
    private EventType type;
    /**
     * The customer involved in the event, represented as a Customer instance. This provides information about the customer associated with the event, such as whether they are a VIP or not.
     */
    private Customer customer;

    /**
     * Creates an Event-instance.
     * @param time A double value that set's the time-value.
     * @param type EventType type-value.
     * @param customer Customer customer-instance.
     */
    public Event(double time, EventType type, Customer customer) {
        this.time = time;
        this.type = type;
        this.customer = customer;
    }

    /**
     * Gets a Customer-instance and returns it.
     * @return Returns the Customer-instance.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets a double value and returns it.
     * @return Returns the time-value.
     */
    public double getTime() {
        return time;
    }

    /**
     * Gets an EventType value and returns it.
     * @return Returns the type-value.
     */
    public EventType getType() {
        return type;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }

}
