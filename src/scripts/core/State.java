package scripts.core;

public class State {

    public double delta = 0;

    public State() {}

    public void updateDelta(double tickInterval) {
        // tickInterval is in seconds
        delta = tickInterval;
    }

}