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
    Arrival entry;
    static EventList eventList;
    //private int työntekijäMäärä;
    private int gaAsiakasmäärä = 0;
    int gaKävijämäärä;
    private int vipAsiakasmäärä = 0;
    int vipKävijämäärä;
    int simulaationKesto;
    private ServicePoint vipSecurity;
    private ServicePoint gaSecurity;
    private ServicePoint vipNarikka;
    private ServicePoint gaNarikka;
    private ServicePoint merch;
    private int previousTime;

    public Controller(int simulaationKesto, int gaKävijämäärä, int vipKävijämäärä, int vipSecurityTyöntekijäMäärä, int gaSecurityTyöntekijäMäärä, int vipNarikkaTyöntekijäMäärä, int gaNarikkaTyöntekijäMäärä, int merchTyöntekijäMäärä) {
        arrivalQueue = new LinkedList<>();
        vipSecurityQueue = new LinkedList<>();
        gaSecurityQueue = new LinkedList<>();
        vipCloakroomQueue = new LinkedList<>();
        gaCloakroomQueue = new LinkedList<>();
        merchQueue = new LinkedList<>();
        eventList = new EventList();
        entry = new Arrival();
        this.simulaationKesto = simulaationKesto;
        //this.työntekijäMäärä = työntekijäMäärä;
        this.gaKävijämäärä = gaKävijämäärä;
        this.vipKävijämäärä = vipKävijämäärä;
        this.vipSecurity = new ServicePoint(vipSecurityTyöntekijäMäärä);
        this.gaSecurity = new ServicePoint(gaSecurityTyöntekijäMäärä);
        this.vipNarikka = new ServicePoint(vipNarikkaTyöntekijäMäärä);
        this.gaNarikka = new ServicePoint(gaNarikkaTyöntekijäMäärä);
        this.merch = new ServicePoint(merchTyöntekijäMäärä);
    }


    public void moveQueue(boolean isVIP) {
        Customer customer = new Customer(isVIP);
        if (isVIP && vipAsiakasmäärä < vipKävijämäärä) {
            eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_VIP_SECURITY, customer));
            vipAsiakasmäärä++;
        } else if (!isVIP && gaAsiakasmäärä < gaKävijämäärä) {
            eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_GA_SECURITY, customer));
            gaAsiakasmäärä++;
        }
    }



    public void handleEvent(Event event) throws Exception {
        EventType type = event.getType();
        Customer customer = event.getCustomer();
        double time = event.getTime();
        //try {
        Clock.getInstance().setTime(event.getTime());
        {
            if (type == EventType.START_VIP_SECURITY) {
                if (vipSecurity.isAvailable()) {
                    vipSecurity.setAvailable(false);
                    System.out.println("VIP Security alkaa.");

                    eventList.add(new Event(
                            Clock.getInstance().getCurrentTime() + customer.getSecurityTime(),
                            EventType.FINISH_VIP_SECURITY, customer));
                } else {
                    vipSecurityQueue.add(customer);
                }

            } else if (type == EventType.FINISH_VIP_SECURITY) {
                vipSecurity.setAvailable(true);
                System.out.println("VIP Security päättyy. Kesto: " + customer.getSecurityTime() + "sekuntia.");

                if (!vipSecurityQueue.isEmpty()) {
                    Customer next = vipSecurityQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_VIP_SECURITY, next));
                }
                if (customer.isVIP() && customer.isKäyNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_VIP_CLOAKROOM, customer));
                } else if (customer.isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_MERCH, customer));
                } else if (!customer.isOstaako() && !customer.isKäyNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_VIP_CLOAKROOM) {
                if (vipNarikka.isAvailable()) {
                    vipNarikka.setAvailable(false);
                    System.out.println("VIP Narikka alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getCloakroomTime()), EventType.FINISH_VIP_CLOAKROOM, customer));
                } else {
                    vipCloakroomQueue.add(customer);
                }

            } else if (type == EventType.FINISH_VIP_CLOAKROOM) {
                vipNarikka.setAvailable(true);
                System.out.println("VIP narikka päättyy. Kesto: " + customer.getCloakroomTime() + "sekuntia.");
                if (!vipCloakroomQueue.isEmpty()) {
                    Customer next = vipCloakroomQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_VIP_CLOAKROOM, next));
                }
                if (event.getCustomer().isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_MERCH, customer));
                } else if (!event.getCustomer().isOstaako() && !event.getCustomer().isKäyNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime(), EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_GA_SECURITY) {
                if (gaSecurity.isAvailable()) {
                    gaSecurity.setAvailable(false);
                    System.out.println("GA Security alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getSecurityTime()), EventType.FINISH_GA_SECURITY, customer));

                } else {
                    gaSecurityQueue.add(customer);
                }
            } else if (type == EventType.FINISH_GA_SECURITY) {
                gaSecurity.setAvailable(true);
                System.out.println("GA Security päättyy. Kesto: " + customer.getSecurityTime() + "sekuntia.");
                if (!gaSecurityQueue.isEmpty()) {
                    Customer next = gaSecurityQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_GA_SECURITY, next));
                }
                if (!customer.isVIP() && customer.isKäyNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_GA_CLOAKROOM, customer));
                } else if (customer.isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_MERCH, customer));
                } else if (!customer.isOstaako() && !customer.isKäyNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_GA_CLOAKROOM) {
                if (gaNarikka.isAvailable()) {
                    gaNarikka.setAvailable(false);
                    System.out.println("GA Narikka alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getCloakroomTime()), EventType.FINISH_GA_CLOAKROOM, customer));

                } else {
                    gaCloakroomQueue.add(customer);
                }
            } else if (type == EventType.FINISH_GA_CLOAKROOM) {
                gaNarikka.setAvailable(true);
                System.out.println("GA narikka päättyy. Kesto: " + customer.getCloakroomTime() + "sekuntia.");
                if (!gaCloakroomQueue.isEmpty()) {
                    Customer next = gaCloakroomQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_GA_CLOAKROOM, next));
                }
                if (event.getCustomer().isOstaako()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_MERCH, customer));
                } else if (!event.getCustomer().isOstaako() && !event.getCustomer().isKäyNarikassa()) {
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_ENTER_CONCERT_HALL, customer));
                }
            } else if (type == EventType.START_MERCH) {
                if (merch.isAvailable()) {
                    merch.setAvailable(false);
                    System.out.println("Oheistuotemyyntipiste alkaa.");
                    eventList.add(new Event((Clock.getInstance().getCurrentTime() + customer.getMerchTime()), EventType.FINISH_MERCH, customer));
                } else {
                    merchQueue.add(customer);
                }
            } else if (type == EventType.FINISH_MERCH) {
                merch.setAvailable(true);
                System.out.println("Oheistuotemyyntipiste päättyy. Kesto: " + customer.getMerchTime() + "sekuntia.");
                if (!merchQueue.isEmpty()) {
                    Customer next = merchQueue.remove();
                    eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_MERCH, next));
                }
                eventList.add(new Event(Clock.getInstance().getCurrentTime() + 1, EventType.START_ENTER_CONCERT_HALL, customer));
            } else if (type == EventType.START_ENTER_CONCERT_HALL) {
                eventList.add(new Event((Clock.getInstance().getCurrentTime() + 1), EventType.FINISH_ENTER_CONCERT_HALL, customer));
                System.out.println("Konserttisaliin siirtyminen alkaa.");

            } else if (type == EventType.FINISH_ENTER_CONCERT_HALL) {
                System.out.println("Asiakas on siirtynyt konserttisaliin onnistuneesti.");
                Customer c = event.getCustomer();
                Database.saveCustomer(c);
            }

        }
    }

    public void run() throws Exception {
        while (!eventList.isEmpty() && Clock.getInstance().getCurrentTime() < simulaationKesto) {
            Event event = eventList.remove();
            if (event == null) {
                break;
            }
            handleEvent(event);
        }
    }

    public static void main(String[] args) throws Exception {
        Database.connect();
        Controller controller = new Controller(2000000, 50, 5, 2, 3, 3, 3, 2);
        if (Clock.getInstance().getCurrentTime() < controller.simulaationKesto) {
            for (int i = 0; i < controller.vipKävijämäärä; i++) {
                controller.moveQueue(true);
                }
            for (int i = 0; i < controller.gaKävijämäärä; i++) {
                controller.moveQueue(false);
            }

            controller.run();
        } System.out.println("Simulaatio on päättynyt.");
    }

}