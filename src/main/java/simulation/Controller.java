package simulation;
import javafx.application.Platform;
import view.SimGUI;

import java.util.LinkedList;
import java.util.Random;

public class Controller {
    static Random random = new Random();
    LinkedList<Customer> arrivalQueue;
    LinkedList<Customer> vipSecurityQueue;
    LinkedList<Customer> gaSecurityQueue;
    LinkedList<Customer> vipCloakroomQueue;
    LinkedList<Customer> gaCloakroomQueue;
    LinkedList<Customer> merchQueue;
    public Arrival entry;
    public static EventList eventList;
    public int gaAsiakasmaara = 0;
    public int gaKavijamaara;
    public int vipAsiakasmaara = 0;
    public int vipKavijamaara;
    int simulaationKesto;
    private ServicePoint vipSecurity;
    private ServicePoint gaSecurity;
    private ServicePoint vipNarikka;
    private ServicePoint gaNarikka;
    private ServicePoint merch;
    public static int simulationSpeed = 30;

    /**
     * Simulation Engine, handles the event queue and arrival classes
     * @param simulaationKesto how long the simulation will run in seconds
     * @param gaKavijamaara how many GA customers will arrive during the simulation
     * @param vipKavijamaara how many VIP customers will arrive during the simulation
     * @param vipSecurityTyöntekijaMaara how many security workers are at the VIP security check
     * @param gaSecurityTyöntekijaMaara how many security workers are at the GA security check
     * @param vipNarikkaTyöntekijaMaara how many cloakroom workers are at the VIP cloakroom
     * @param gaNarikkaTyöntekijaMaara how many cloakroom workers are at the GA cloakroom
     * @param merchTyöntekijaMaara how many workers are at the merch stand
     */
    public Controller(int simulaationKesto, int gaKavijamaara, int vipKavijamaara, int vipSecurityTyöntekijaMaara, int gaSecurityTyöntekijaMaara, int vipNarikkaTyöntekijaMaara, int gaNarikkaTyöntekijaMaara, int merchTyöntekijaMaara) {
        arrivalQueue = new LinkedList<>();
        vipSecurityQueue = new LinkedList<>();
        gaSecurityQueue = new LinkedList<>();
        vipCloakroomQueue = new LinkedList<>();
        gaCloakroomQueue = new LinkedList<>();
        merchQueue = new LinkedList<>();
        eventList = new EventList();
        entry = new Arrival();
        this.simulaationKesto = simulaationKesto;
        this.gaKavijamaara = gaKavijamaara;
        this.vipKavijamaara = vipKavijamaara;
        this.vipSecurity = new ServicePoint(vipSecurityTyöntekijaMaara);
        this.gaSecurity = new ServicePoint(gaSecurityTyöntekijaMaara);
        this.vipNarikka = new ServicePoint(vipNarikkaTyöntekijaMaara);
        this.gaNarikka = new ServicePoint(gaNarikkaTyöntekijaMaara);
        this.merch = new ServicePoint(merchTyöntekijaMaara);
    }

    /**
     * Handles the event, and changes the type according to the need of the program. Also updates the database.
     * @param event the event to be handled
     */
    public void handleEvent(Event event){
        EventType type = event.getType();
        Customer customer = event.getCustomer();
        customer.getPosition();

        Clock.getInstance().setTime(event.getTime());
        {
            if (type == EventType.START_VIP_SECURITY) {
                if (vipSecurity.isAvailable()) {
                    vipSecurity.aloitaPalvelu();
                    customer.moveTo(queueX(240, customer), 460);

                    Platform.runLater(() -> {
                        SimGUI.updateCustomer(customer);
                    });

                    System.out.println("Asiakas numero " + customer.getId() + ". VIP Security alkaa.");

                    eventList.add(new Event(
                            Clock.getInstance().getCurrentTime() + customer.getSecurityTime() + random.nextInt(20),
                            EventType.FINISH_VIP_SECURITY, customer));
                } else {
                    customer.setQueuePosition(vipSecurityQueue.size());
                    vipSecurityQueue.add(customer);
                    customer.moveTo(queueX(180, customer), 460);
                }

            } else if (type == EventType.FINISH_VIP_SECURITY) {
                vipSecurity.lopetaPalvelu();
                System.out.println("Asiakas numero " + customer.getId() + ". VIP Security päättyy. Kesto: " + customer.getSecurityTime() + "sekuntia.");

                if (!vipSecurityQueue.isEmpty()) {
                    Customer next = vipSecurityQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_VIP_SECURITY, next));
                    updateQueuePositions(vipSecurityQueue, 180, 460);
                }
                if (customer.isVIP() && customer.isKayNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_VIP_CLOAKROOM, customer));
                } else if (customer.isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_MERCH, customer));
                } else if (!customer.isOstaako() && !customer.isKayNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_VIP_CLOAKROOM) {
                if (vipNarikka.isAvailable()) {
                    vipNarikka.aloitaPalvelu();
                    customer.moveTo(queueX(640, customer), 460);

                    Platform.runLater(() -> {
                        SimGUI.updateCustomer(customer);
                    });

                    System.out.println("Asiakas numero " + customer.getId() + ". VIP Narikka alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getCloakroomTime()), EventType.FINISH_VIP_CLOAKROOM, customer));
                } else {
                    customer.setQueuePosition(vipCloakroomQueue.size());
                    vipCloakroomQueue.add(customer);
                    customer.moveTo(queueX(620, customer), 460);
                }

            } else if (type == EventType.FINISH_VIP_CLOAKROOM) {
                vipNarikka.lopetaPalvelu();
                System.out.println("Asiakas numero " + customer.getId() + ". VIP narikka päättyy. Kesto: " + customer.getCloakroomTime() + "sekuntia.");
                if (!vipCloakroomQueue.isEmpty()) {
                    Customer next = vipCloakroomQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_VIP_CLOAKROOM, next));
                    updateQueuePositions(vipCloakroomQueue, 620, 460);
                }
                if (customer.isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_MERCH, customer));
                } else {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_GA_SECURITY) {
                if (gaSecurity.isAvailable()) {
                    gaSecurity.aloitaPalvelu();
                    customer.moveTo(queueX(240, customer), 260);

                    Platform.runLater(() -> {
                        SimGUI.updateCustomer(customer);
                    });

                    System.out.println("Asiakas numero " + customer.getId() + ". GA Security alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getSecurityTime()) + random.nextInt(20), EventType.FINISH_GA_SECURITY, customer));

                } else {
                    customer.setQueuePosition(gaSecurityQueue.size());
                    gaSecurityQueue.add(customer);
                    customer.moveTo(queueX(180, customer), 260);
                }
            } else if (type == EventType.FINISH_GA_SECURITY) {
                gaSecurity.lopetaPalvelu();
                System.out.println("Asiakas numero " + customer.getId() + ". GA Security päättyy. Kesto: " + customer.getSecurityTime() + "sekuntia.");
                if (!gaSecurityQueue.isEmpty()) {
                    Customer next = gaSecurityQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_GA_SECURITY, next));
                    updateQueuePositions(gaSecurityQueue, 180, 260);
                }
                if (!customer.isVIP() && customer.isKayNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_GA_CLOAKROOM, customer));
                } else if (customer.isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_MERCH, customer));
                } else if (!customer.isOstaako() && !customer.isKayNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_GA_CLOAKROOM) {
                if (gaNarikka.isAvailable()) {
                    gaNarikka.aloitaPalvelu();
                    customer.moveTo(queueX(640, customer), 260);

                    Platform.runLater(() -> {
                        SimGUI.updateCustomer(customer);
                    });

                    System.out.println("Asiakas numero " + customer.getId() + ". GA Narikka alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getCloakroomTime()), EventType.FINISH_GA_CLOAKROOM, customer));

                } else {
                    customer.setQueuePosition(gaCloakroomQueue.size());
                    gaCloakroomQueue.add(customer);
                    customer.moveTo(queueX(620, customer), 260);
                }
            } else if (type == EventType.FINISH_GA_CLOAKROOM) {
                gaNarikka.lopetaPalvelu();
                System.out.println("Asiakas numero " + customer.getId() + ". GA narikka päättyy. Kesto: " + customer.getCloakroomTime() + "sekuntia.");
                if (!gaCloakroomQueue.isEmpty()) {
                    Customer next = gaCloakroomQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_GA_CLOAKROOM, next));
                    updateQueuePositions(gaCloakroomQueue, 620, 260);
                }
                if (customer.isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_MERCH, customer));
                } else {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_MERCH) {
                if (merch.isAvailable()) {
                    merch.aloitaPalvelu();
                    customer.moveTo(queueX(1040, customer), 360);

                    Platform.runLater(() -> {
                        SimGUI.updateCustomer(customer);
                    });

                    System.out.println("Asiakas numero " + customer.getId() + ". Oheistuotemyyntipiste alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getMerchTime()), EventType.FINISH_MERCH, customer));
                } else {
                    customer.setQueuePosition(merchQueue.size());
                    merchQueue.add(customer);
                    customer.moveTo(queueX(1020, customer), 360);
                }
            } else if (type == EventType.FINISH_MERCH) {
                merch.lopetaPalvelu();
                System.out.println("Asiakas numero " + customer.getId() + ". Oheistuotemyyntipiste päättyy. Kesto: " + customer.getMerchTime() + "sekuntia.");
                if (!merchQueue.isEmpty()) {
                    Customer next = merchQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_MERCH, next));
                    updateQueuePositions(merchQueue, 1020, 360);
                }
                eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_ENTER_CONCERT_HALL, customer));
            } else if (type == EventType.START_ENTER_CONCERT_HALL) {
                eventList.add(new Event((Clock.getInstance().getCurrentTime()), EventType.FINISH_ENTER_CONCERT_HALL, customer));
                System.out.println("Asiakas numero " + customer.getId() + ". Konserttisaliin siirtyminen alkaa.");
                customer.moveTo(1270, 360);

                Platform.runLater(() -> {
                    SimGUI.updateCustomer(customer);
                });


            } else if (type == EventType.FINISH_ENTER_CONCERT_HALL) {
                System.out.println("Asiakas numero " + customer.getId() + " on siirtynyt konserttisaliin onnistuneesti.");
                Customer.meneSaliin();
                System.out.println("Salissa nyt: " + Customer.getPaasiSaliin());
                Platform.runLater(() -> {
                    SimGUI.updateHallCount(Customer.getPaasiSaliin());
                });
                Customer c = event.getCustomer();
                Database.saveCustomer(c);

            }


        }

    }

    /**
     * Calculates the x coordinate for the customer in the queue, based on the service point and the queue position. Each customer is 20 pixels apart.
     * @param serviceX the x coordinate of the service point
     * @param c the customer for which the x coordinate is being calculated
     * @return the x coordinate for the customer in the queue
     */
    private double queueX(double serviceX, Customer c) {
        return serviceX - (c.getQueuePosition() * 20);
    }

    /**
     * Updates the positions of the customers in the queue, when a customer is removed from the queue. Each customer is 20 pixels apart.
     * @param queue the queue in which the customer was removed
     * @param serviceX the x coordinate of the service point
     * @param y the y coordinate of the queue
     */
    private void updateQueuePositions(LinkedList<Customer> queue, double serviceX, double y) {

        int position = 0;

        for (Customer c : queue) {
            c.setQueuePosition(position);

            double newX = serviceX - position * 20;

            c.moveTo(newX, y);

            Platform.runLater(() -> {
                SimGUI.updateCustomer(c);
            });

            position++;
        }
    }

    /**
     * Runs the simulation and ends it when the time is over or there are no more events in the list.
     */
    public void run(){
        Database.connect();
        Database.deleteDatabase();
        while (!eventList.isEmpty()) {
            if (Clock.getInstance().getCurrentTime() < simulaationKesto) {
                //System.out.println(eventList.size());
                if (vipAsiakasmaara < vipKavijamaara) {
                    eventList.add(entry.moveQueue(true));
                    vipAsiakasmaara++;
                }
                if (gaAsiakasmaara < gaKavijamaara) {
                    eventList.add(entry.moveQueue(false));
                    gaAsiakasmaara++;
                }
                Event event = eventList.remove();
                if (event == null) {
                    break;
                }
                handleEvent(event);
                try {
                    Thread.sleep(simulationSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("Restarting loop...");
            } else {
                break;
            }
        }
    }
}
