package simulation;

/**
 * Service point entity with a defined amount of employees.
 */
public class ServicePoint {
    private int työntekijäMäärä;
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
