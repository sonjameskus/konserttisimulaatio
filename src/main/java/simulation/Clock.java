package simulation;

/**
 * Creates Clock-instances that track time during the simulation.
 */
public class Clock {

    private static Clock instance = new Clock();

    private double currentTime;

    private Clock() {
        currentTime = 0;
    }

    /**
     * @return Returns the Clock instance.
     */
    public static Clock getInstance() {
        return instance;
    }

    /**
     * @return Returns currentTime value of the clock instance.
     */
    public double getCurrentTime() {
        return currentTime;
    }

    /**
     * @param time A new value that currentTime is set to.
     */
    public void setTime(double time) {
        currentTime = time;
    }
}