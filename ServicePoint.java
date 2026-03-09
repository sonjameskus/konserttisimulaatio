package Model;

public class ServicePoint {
    private int palvelunKesto;
    private int työntekijäMäärä;
    private String ppID;

    public ServicePoint(int palvelunKesto, int työntekijäMäärä, String ppID) {
        this.palvelunKesto = palvelunKesto;
        this.työntekijäMäärä = työntekijäMäärä;
        this.ppID = ppID;
    }

    public void setPalvelunKesto(int palvelunKesto) {
        this.palvelunKesto = palvelunKesto;
    }

    public int getPalvelunKesto() {
        return palvelunKesto;
    }

    public void setTyöntekijäMäärä(int työntekijäMäärä) {
        this.työntekijäMäärä = työntekijäMäärä;
    }

    public int getTyöntekijäMäärä() {
        return työntekijäMäärä;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
    }

    public String getPpID() {
        return ppID;
    }

    public Customer processCustomer() {
        //TODO: prosessointi aika tähän
        //TODO: ota asiakas jonosta (LinkedList queue) ja prosessoi se
        return null; //TODO: palautetaan prosessoitu asiakas, jotta se voidaan syöttää simulaatiossa seuraavaan pisteeseen.
    }
}
