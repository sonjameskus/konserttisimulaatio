package simulation;

/**
 * Creates Customer-instances that travel through the simulation.
 */
public class Customer {
    private boolean lippu; //VIP tai GA
    private int tavaraMaara; //1-4, enemman tavaraa kestaa pitempaan
    private boolean ostaako;
    private int securityTime;
    private int cloakroomTime;
    private int merchTime;
    private static int counter = 1;
    private int id;
    private static int paasiSaliin = 0;
    private double x, y;
    private double targetX, targetY;
    private int queuePosition = 0;

    /**
     * Creates a Customer-instance.
     * @param lippu A boolean value that determines the VIP-status of a Customer-instance. (True = VIP, False = GA)
     */
    public Customer(boolean lippu) {
        this.lippu = lippu;
        this.tavaraMaara = 1 + Controller.random.nextInt(3);
        this.securityTime = 5 + Controller.random.nextInt(3) * 10;
        this.cloakroomTime = 10 + Controller.random.nextInt(3) * 10;
        this.merchTime = 20 + Controller.random.nextInt(5) * 10;
        this.ostaako = Controller.random.nextBoolean();
        this.id = counter++;
        this.x = 50;
        this.y = 360;

    }

    /**
     * @return Returns a Customer-instance's lippu-value.
     */
    public boolean isLippu() {
        return lippu;

    }

    /**
     * @return Returns whether a Customer-instance's ticket is VIP or GA.
     */
    public String getLippu() {
        if (isLippu()) {
            return "VIP";
        } else {
            return "GA";
        }
    }

    /**
     * @return Returns whether a Customer-instance will visit the merch store or not.
     */
    public boolean isOstaako() {

        return ostaako;
    }


    /**
     * @return Returns the amount of items a Customer-instance has.
     */
    public int getTavaraMaara() {

        return tavaraMaara;
    }


    /**
     * @return Returns the amount of time a Customer-instance will take in the security check.
     */
    public int getSecurityTime() {

        return securityTime;
    }

    /**
     * @return Returns the amount of time a Customer-instance will take in the cloakroom.
     */
    public int getCloakroomTime() {
        if (isKayNarikassa()) {
            return cloakroomTime;
        } else {
            return 0;
        }
    }

    /**
     * @return Returns the amount of time a Customer-instance will take in the merch store.
     */
    public int getMerchTime() {
        if (isOstaako()) {
            return merchTime;
        } else {
            return 0;
        }
    }

    /**
     * @return Returns whether a Customer-instance will visit the cloakroom or not.
     */
    public boolean isKayNarikassa() {
        if (tavaraMaara > 2) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @return Return's whether a Customer-instance's lippu-value is that of a VIP-ticket or not.
     */
    public boolean isVIP() {
        if (isLippu()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Returns a Customer-instance's id number.
     */
    public int getId() {
        return id;
    }


    /**
     * Increases the amount of people in the performance-room by 1.
     */
        public static void meneSaliin () {
            paasiSaliin++;
        }

    /**
     * @return Returns the amount of people currently in the performance-room.
     */
        public static int getPaasiSaliin () {
            return paasiSaliin;
        }

    /**
     * @return Returns a Customer-instance's current x-position.
     */
    public double getX() {
        return x;
    }

    /**
     * @return Returns a Customer-instance's current y-position
     */
    public double getY() {
        return y;
    }


    /**
     * Sets a Customer-instance's x-position and y-position based on the x-value and y-value.
     */
    public void setPosition() {
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
    }

    /**
     * Sets a Customer-instance's y-value based on it's VIP-status.
     */
    public void getPosition() {
        if (isVIP()) {
            x = 0;
            y = 460;
        } else {
            x = 0;
            y = 260;
        }
    }

    /**
     * @param targetX A double value that is set as a Customer-instance's new targetX-value.
     * @param targetY A double value that is set as a Customer-instance's new targetY-value.
     */
    public void moveTo(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * Increases a Customer-instance's x-value and y-value toward TargetX-value and TargetY-value.
     */
    public void moveStep() {
        x += (targetX - x) * 0.05;
        y += (targetY - y) * 0.05;
    }

    /**
     * @param pos An integer value that is set as a Customer-instance's new queuePosition-value.
     */
    public void setQueuePosition(int pos) {
        this.queuePosition = pos;
    }

    /**
     * @return Returns a Customer-instance's current queuePosition-value.
     */
    public int getQueuePosition() {
        return queuePosition;
    }

}

