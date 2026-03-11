import java.util.LinkedList;
import java.util.Random;

public class Arrival extends Thread{
    private Random random = new Random();

    public Arrival() {
    }

    public static Event moveQueue(boolean isVIP) {
        Customer customer = new Customer(isVIP);
        Event event = null;
        if (isVIP) {
            event = new Event(Clock.getInstance().getCurrentTime(), EventType.START_VIP_SECURITY, customer);

        } else  {
            event = new Event(Clock.getInstance().getCurrentTime(), EventType.START_GA_SECURITY, customer);
        } return event;
    }

}