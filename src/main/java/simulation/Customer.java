package simulation;

public class Customer {
    private boolean lippu; //VIP tai GA
    private int tavaraMäärä; //1-4, enemmän tavaraa kestää pitempään
    private boolean ostaako;
    private int securityTime;
    private int cloakroomTime;
    private int merchTime;
    private static int counter = 1;
    private int id;
    private static int pääsiSaliin = 0;
    private double x, y;
    private double targetX, targetY;
    private int queuePosition = 0;

    public Customer(boolean lippu) {
        this.lippu = lippu;
        this.tavaraMäärä = 1 + Controller.random.nextInt(3);
        this.securityTime = 5 + Controller.random.nextInt(3) * 10;
        this.cloakroomTime = 10 + Controller.random.nextInt(3) * 10;
        this.merchTime = 20 + Controller.random.nextInt(5) * 10;
        this.ostaako = Controller.random.nextBoolean();
        this.id = counter++;
        this.x = 50;
        this.y = 360;

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


        public static void meneSaliin () {
            pääsiSaliin++;
        }

        public static int getPääsiSaliin () {
            return pääsiSaliin;
        }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public void setPosition() {
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
    }

    public void getPosition() {
        if (isVIP()) {
            x = 0;
            y = 460;
        } else {
            x = 0;
            y = 260;
        }
    }

    public void moveTo(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void moveStep() {
        x += (targetX - x) * 0.05;
        y += (targetY - y) * 0.05;
    }
    public void setQueuePosition(int pos) {
        this.queuePosition = pos;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

}

