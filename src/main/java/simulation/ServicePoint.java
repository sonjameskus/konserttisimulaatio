package simulation;

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