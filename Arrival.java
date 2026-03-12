package simulation;

import javafx.application.Platform;
import view.SimGUI;

import java.util.Random;

public class Arrival extends Thread {

    private Random random = new Random();

    public Arrival() {}

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