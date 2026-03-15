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

    public Controller(int simulaationKesto, int gaKavijamaara, int vipKavijamaara, int vipSecurityTyontekijaMaara, int gaSecurityTyontekijaMaara, int vipNarikkaTyontekijaMaara, int gaNarikkaTyontekijaMaara, int merchTyontekijaMaara) {
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
        this.vipSecurity = new ServicePoint(vipSecurityTyontekijaMaara);
        this.gaSecurity = new ServicePoint(gaSecurityTyontekijaMaara);
        this.vipNarikka = new ServicePoint(vipNarikkaTyontekijaMaara);
        this.gaNarikka = new ServicePoint(gaNarikkaTyontekijaMaara);
        this.merch = new ServicePoint(merchTyontekijaMaara);
    }

    public void handleEvent(Event event) throws Exception {
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
                System.out.println("Asiakas numero " + customer.getId() + ". VIP Security paattyy. Kesto: " + customer.getSecurityTime() + "sekuntia.");

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
                System.out.println("Asiakas numero " + customer.getId() + ". VIP narikka paattyy. Kesto: " + customer.getCloakroomTime() + "sekuntia.");
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
                System.out.println("Asiakas numero " + customer.getId() + ". GA Security paattyy. Kesto: " + customer.getSecurityTime() + "sekuntia.");
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
                System.out.println("Asiakas numero " + customer.getId() + ". GA narikka paattyy. Kesto: " + customer.getCloakroomTime() + "sekuntia.");
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
                System.out.println("Asiakas numero " + customer.getId() + ". Oheistuotemyyntipiste paattyy. Kesto: " + customer.getMerchTime() + "sekuntia.");
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

    private double queueX(double serviceX, Customer c) {
        return serviceX - (c.getQueuePosition() * 20);
    }

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

    public void run() throws Exception {
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
