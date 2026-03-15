package simulation;

import javafx.application.Platform;
import view.SimGUI;

import java.util.Random;

/**
 * Creates Arrival-instances that create new Customer-instances and set's their next eventType.
 */
public class Arrival extends Thread {

    private Random random = new Random();

    /**
     * Creates an Arrival-instance.
     */
    public Arrival() {}

    /**
     * @param isVIP A boolean value that determines the VIP-status of a Customer-instance. (True = VIP, False = GA)
     * @return Returns and Event-instance based on the VIP-status of the created Customer-instance
     */
    public static Event moveQueue(boolean isVIP) {

        Customer customer = new Customer(isVIP);

        Platform.runLater(() -> {
            SimGUI.updateCustomer(customer);
        });

        Event event;

        if (isVIP) {
            event = new Event(
                    Clock.getInstance().getCurrentTime(),
                    EventType.START_VIP_SECURITY,
                    customer
            );
        } else {
            event = new Event(
                    Clock.getInstance().getCurrentTime(),
                    EventType.START_GA_SECURITY,
                    customer
            );
        }

        return event;
    }
}