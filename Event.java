package simulation;

public class Event implements Comparable<Event> {

    private double time;
    private EventType type;
    private Customer customer;

    public Event(double time, EventType type, Customer customer) {
        this.time = time;
        this.type = type;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
    public double getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }

}