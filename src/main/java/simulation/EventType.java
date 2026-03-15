package simulation;

/**
 * EventType is an enum that defines the different types of events that can occur in the simulation. Each event type represents a specific action or state change for a customer, such as finishing security checks, leaving the cloakroom, purchasing merchandise, or entering the concert hall.
 */
public enum EventType {
    /**
     * FINISH_VIP_SECURITY: This event type indicates that a VIP customer has completed the VIP security check and is ready to proceed to the next step in the simulation.
     */
    FINISH_VIP_SECURITY, //VIP-asiakas pääsee VIP-turvatarkastuksesta pois
    /**
     * FINISH_GA_SECURITY: This event type indicates that a GA (General Admission) customer has completed the GA security check and is ready to proceed to the next step in the simulation.
     */
    FINISH_GA_SECURITY, //GA-asiakas pääsee GA-turvatarkastuksesta pois
    /**
     * FINISH_VIP_CLOAKROOM: This event type indicates that a VIP customer has finished using the cloakroom service and is ready to proceed to the next step in the simulation.
     */
    FINISH_VIP_CLOAKROOM, //VIP-asiakas poistuu narikasta
    /**
     * FINISH_GA_CLOAKROOM: This event type indicates that a GA customer has finished using the cloakroom service and is ready to proceed to the next step in the simulation.
     */
    FINISH_GA_CLOAKROOM, //GA-asiakas poistuu narikasta
    /**
     * FINISH_MERCH: This event type indicates that a customer has completed their purchase of merchandise and is ready to proceed to the next step in the simulation, which could be leaving the merchandise area or entering the concert hall.
     */
    FINISH_MERCH, //Asiakas poistuu oheistuotemyyntipisteeltä
    /**
     * FINISH_ENTER_CONCERT_HALL: This event type indicates that a customer has entered the concert hall and is now waiting for the concert to start. This marks the final step in the customer's journey through the simulation, as they have completed all previous services and are now in the concert hall.
     */
    FINISH_ENTER_CONCERT_HALL, //Asiakas on saapunut konserttisaliin ja odottaa konsertin alkua

    /**
     * START_VIP_SECURITY: This event type indicates that a VIP customer is starting the VIP security check. This marks the beginning of the customer's journey through the simulation, as they will go through various services before eventually entering the concert hall.
     */
    START_VIP_SECURITY, //VIP-turvatarkastus alkaa
    /**
     * START_GA_SECURITY: This event type indicates that a GA customer is starting the GA security check. Similar to the START_VIP_SECURITY event, this marks the beginning of the customer's journey through the simulation, as they will go through various services before eventually entering the concert hall.
     */
    START_GA_SECURITY, //GA-turvatarkastus alkaa
    /**
     * START_VIP_CLOAKROOM: This event type indicates that a VIP customer is starting to use the cloakroom service. This occurs after the customer has completed the VIP security check and is now proceeding to the next step in their journey through the simulation.
     */
    START_VIP_CLOAKROOM, //VIP-narikka alkaa
    /**
     * START_GA_CLOAKROOM: This event type indicates that a GA customer is starting to use the cloakroom service. Similar to the START_VIP_CLOAKROOM event, this occurs after the customer has completed the GA security check and is now proceeding to the next step in their journey through the simulation.
     */
    START_GA_CLOAKROOM, //GA-narikka alkaa
    /**
     * START_MERCH: This event type indicates that a customer is starting to purchase merchandise. This occurs after the customer has completed the security checks and cloakroom services, and is now proceeding to the merchandise area before eventually entering the concert hall.
     */
    START_MERCH, //Oheistuotteiden osto alkaa
    /**
     * START_ENTER_CONCERT_HALL: This event type indicates that a customer is starting to enter the concert hall. This occurs after the customer has completed all previous services (security checks, cloakroom, and merchandise purchase) and is now proceeding to the final step in their journey through the simulation, which is entering the concert hall and waiting for the concert to start.
     */
    START_ENTER_CONCERT_HALL //Asiakas siirtyy konserttisaliin
}
