package Model;

import java.util.LinkedList;

public class Arrival {
    private Customer customer;
    private ServicePoint entrance;

    public Arrival(Customer customer, ServicePoint servicePoint) {
        this.customer = customer;
        this.entrance = servicePoint;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setServicePoint(ServicePoint servicePoint) {
        this.entrance = servicePoint;
    }

    public ServicePoint getServicePoint() {
        return entrance;
    }

    public void insertCustomerToQueue(boolean vip) {
        //TODO: prosessointi aika tähän
        if (vip) {
            Customer vipCustomer = customer;
            vipCustomer.isLippu();
        } else {
            Customer gaCustomer = customer;;
        }
        //TODO: syötetään asiakas simulaatioon
    }
}
