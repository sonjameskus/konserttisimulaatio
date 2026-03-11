public class Customer {
    private boolean lippu; //VIP tai GA
    private int tavaraMäärä; //1-4, enemmän tavaraa kestää pitempään
    private boolean ostaako;
    private int securityTime;
    private int cloakroomTime;
    private int merchTime;
    private static int counter = 1;
    private int id;

    public Customer(boolean lippu) {
        this.lippu = lippu;
        this.tavaraMäärä = 1 + Controller.random.nextInt(3);
        this.securityTime = 10 + Controller.random.nextInt(2) * 10;
        this.cloakroomTime = 10 + Controller.random.nextInt(3) * 10;
        this.merchTime = 20 + Controller.random.nextInt(5) * 10;
        this.ostaako = Controller.random.nextBoolean();
        this.id = counter++;

    }

    public boolean isLippu() {
        return lippu;

    }

    public String getLippu() {
        if (isLippu()) {
            return "VIP";
        } else {
            return "GA";
        }
    }

    public boolean isOstaako() {
        return ostaako;
    }


    public int getTavaraMäärä() {

        return tavaraMäärä;
    }


    public int getSecurityTime() {
        return securityTime;
    }

    public int getCloakroomTime() {
        if (isKäyNarikassa()) {
            return cloakroomTime;
        } else {
            return 0;
        }
    }

    public int getMerchTime() {
        if (isOstaako()) {
            return merchTime;
        } else {
            return 0;
        }
    }

    public boolean isKäyNarikassa() {
        if (tavaraMäärä > 2) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isVIP() {
        if (isLippu()) {
            return true;
        } else {
            return false;
        }

    }

    public int getId() {
        return id;
    }
}
