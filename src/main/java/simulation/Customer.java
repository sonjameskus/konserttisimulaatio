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
     * Checks if a Customer-instance's lippu-value is true or false.
     * @return Returns a Customer-instance's lippu-value.
     */
    public boolean isLippu() {
        return lippu;

    }

    /**
     * Gets a Customer-instance's lippu-value and returns "VIP" if the lippu-value is true and "GA" if the lippu-value is false.
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
     * Checks if a Customer-instance's ostaako-value is true or false.
     * @return Returns whether a Customer-instance will visit the merch store or not.
     */
    public boolean isOstaako() {

        return ostaako;
    }


    /**
     * Checks a Customer-instance's tavaraMaara-value and returns it.
     * @return Returns the amount of items a Customer-instance has.
     */
    public int getTavaraMäärä() {

        return tavaraMaara;
    }


    /**
     * Gets a Customer-instance's securityTime-value and returns it.
     * @return Returns the amount of time a Customer-instance will take in the security check.
     */
    public int getSecurityTime() {
        return securityTime;
    }

    /**
     * Gets a Customer-instance's cloakroomTime-value if the Customer-instance will visit the cloakroom and returns 0 if the Customer-instance will not visit the cloakroom.
     * @return Returns the amount of time a Customer-instance will take in the cloakroom.
     */
    public int getCloakroomTime() {
        if (isKäyNarikassa()) {
            return cloakroomTime;
        } else {
            return 0;
        }
    }

    /**
     * Gets a Customer-instance's merchTime-value if the Customer-instance will visit the merch store and returns 0 if the Customer-instance will not visit the merch store.
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
     * Checks a Customer-instance's tavaraMaara-value and returns true if the tavaraMaara-value is greater than 2 and false if the tavaraMaara-value is 2 or less.
     * @return Returns whether a Customer-instance will visit the cloakroom or not.
     */
    public boolean isKäyNarikassa() {
        if (tavaraMaara > 2) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks a Customer-instance's lippu-value and returns true if the lippu-value is true and false if the lippu-value is false.
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
     * Gets a Customer-instance's id number and returns it.
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
     * Gets the current amount of people in the performance-room and returns it.
     * @return Returns the amount of people currently in the performance-room.
     */
    public static int getPääsiSaliin () {
        return paasiSaliin;
    }

    /**
     * Gets a Customer-instance's current x-position and returns it.
     * @return Returns a Customer-instance's current x-position.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets a Customer-instance's current y-position and returns it.
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
     * Sets a Customer-instance's targetX-value and targetY-value based on the given parameters.
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
     * Sets a Customer-instance's queuePosition-value based on the given parameter.
     * @param pos An integer value that is set as a Customer-instance's new queuePosition-value.
     */
    public void setQueuePosition(int pos) {
        this.queuePosition = pos;
    }

    /**
     * Gets a Customer-instance's current queuePosition-value and returns it.
     * @return Returns a Customer-instance's current queuePosition-value.
     */
    public int getQueuePosition() {
        return queuePosition;
    }

}
