package util;

public class Timer {

    public boolean active = false;
    public boolean timeout = false;
    public double waitTime;
    public double time;

    public Timer(double waitTime) {
        this.waitTime = waitTime;
        this.time = waitTime;
    }

    public void start(double overflow) {
        if (!active) {
            active = true;
            time = waitTime + overflow;
            timeout = false;
        }
        else {
            System.out.println("Timer already started");
        }
    }

    public void pause() {
        if (active) {
            active = false;
        }
    }

    public void resume() {
        if (active) {
            active = false;
        }
    }

    public double update(double tickIncrement) {
        timeout = false;

        if (active) {
            time -= tickIncrement;
            if (time <= 0) {
                active = false;
                timeout = true;
                return time;
            }
        }
        return 0;
    }

    public float percentage() {
        if (!timeout) {
            return (float) (1 - (time / waitTime));
        }
        return 1;
    }

}