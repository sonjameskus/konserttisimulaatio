package simulation;

import java.util.PriorityQueue;

/**
 * EventList is a priority queue that holds events in the simulation in the required order.
 */
public class EventList {

    /**
     * A PriorityQueue that holds Event objects.
     */
    private PriorityQueue<Event> events;

    /**
     * Creates an empty EventList.
     */
    public EventList() {
        events = new PriorityQueue<>();
    }

    /**
     * Adds an event to the EventList. The event will be placed last.
     * @param event the event to be added to the EventList
     */
    public void add(Event event) {
        events.add(event);
    }

    /**
     * Removes and returns the first event in the EventList.
     * @return the first event in the EventList, or null if the EventList is empty
     */
    public Event remove() {
        return events.poll();
    }

    /**
     * Returns the first event in the EventList without removing it.
     * @return the first event in the EventList, or null if the EventList is empty
     */
    public Event peek() {
        return events.peek();
    }

    /**
     * Checks if the EventList is empty.
     * @return true if the EventList is empty, false otherwise
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }

    /**
     * Returns the number of events in the EventList.
     * @return the number of events in the EventList
     */
    public int size() {
        return events.size();
    }
}
