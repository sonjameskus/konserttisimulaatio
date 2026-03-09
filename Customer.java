package Model;

public class Customer {
    private boolean lippu; //VIP tai GA
    private int tavaraMäärä; //1-4, enemmän tavaraa kestää pitempään
    private boolean ostaako;

    public Customer(boolean lippu, int tavaraMäärä, boolean ostaako) {
        this.lippu = lippu;
        this.tavaraMäärä = tavaraMäärä;
        this.ostaako = ostaako;
    }

    public void setLippu(boolean lippu) {
        this.lippu = lippu;
    }

    public boolean isLippu() {
        return lippu;
    }

    public void setOstaako(boolean ostaako) {
        this.ostaako = ostaako;
    }

    public boolean isOstaako() {
        return ostaako;
    }

    public void setTavaraMäärä(int tavaraMäärä) {
        this.tavaraMäärä = tavaraMäärä;
    }

    public int getTavaraMäärä() {
        return tavaraMäärä;
    }
}
