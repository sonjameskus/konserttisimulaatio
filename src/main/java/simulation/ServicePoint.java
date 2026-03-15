package simulation;

/**
 * Service point entity with a defined amount of employees.
 */
public class ServicePoint {
    
    /**
     * The number of employees at the service point. This value is set when the ServicePoint instance is created and does not change during the simulation.
     */
    private int työntekijäMäärä;
    /**
     * The number of employees currently reserved for service. This value changes during the simulation as customers are served and employees are reserved or released.
     */
    private int työntekijäMääräVarattu;


    /**
     * Creates a service point with the given number of employees.
     * @param työntekijäMäärä the number of employees at the service point
     */
    public ServicePoint(int työntekijäMäärä) {
        this.työntekijäMäärä = työntekijäMäärä;
        this.työntekijäMääräVarattu = 0;
    }

    /**
     * Checks if the service point has any available employees.
     * @return true if there are available employees, false otherwise
     */
    public boolean isAvailable() {
        return työntekijäMäärä > työntekijäMääräVarattu;
    }

    /**
     * Reserves an employee for a service. Increases the count of reserved employees by one.
     */
    public void aloitaPalvelu() {
        työntekijäMääräVarattu++;
    }

    /**
     * Releases an employee after a service is completed. Decreases the count of reserved employees by one.
     */
    public void lopetaPalvelu() {
        työntekijäMääräVarattu--;
    }
}package simulation;

public class ServicePoint {
    private int tyontekijaMaara;
    private int tyontekijaMaaraVarattu;



    public ServicePoint(int tyontekijaMaara) {
        this.tyontekijaMaara = tyontekijaMaara;
        this.tyontekijaMaaraVarattu = 0;
    }



    public boolean isAvailable() {
        return tyontekijaMaara > tyontekijaMaaraVarattu;
    }


    public void aloitaPalvelu() {
        tyontekijaMaaraVarattu++;
    }

    public void lopetaPalvelu() {
        tyontekijaMaaraVarattu--;
    }
}
