package simulation;

public class Event implements Comparable<Event> {

    private double time;
    private EventType type;
    private Customer customer;

    /**
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
     * @return Returns the Customer-instance.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return Returns the time-value.
     */
    public double getTime() {
        return time;
    }

    /**
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