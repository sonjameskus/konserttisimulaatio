package simulation;

public class ServicePoint {
    private int työntekijäMäärä;
    private int työntekijäMääräVarattu;



    public ServicePoint(int työntekijäMäärä) {
        this.työntekijäMäärä = työntekijäMäärä;
        this.työntekijäMääräVarattu = 0;
    }



    public boolean isAvailable() {
        return työntekijäMäärä > työntekijäMääräVarattu;
    }


    public void aloitaPalvelu() {
        työntekijäMääräVarattu++;
    }

    public void lopetaPalvelu() {
        työntekijäMääräVarattu--;
    }
}