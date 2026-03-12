package simulation;

public class Clock {

    private static Clock instance = new Clock();

    private double currentTime;

    private Clock() {
        currentTime = 0;
    }

    public static Clock getInstance() {
        return instance;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setTime(double time) {
        currentTime = time;
    }
}