package scripts.util;

public class Timer {

    /*
    This timer implementation has an accuracy of nanoseconds.
    */

    public boolean active = false;
    public boolean timeout = false;
    public boolean repeat;
    public double waitTime;
    public double time;
    public boolean dev = false;

    public Timer(double waitTime, boolean repeat) {
        this.time = waitTime;
        this.waitTime = waitTime;
        this.repeat = repeat;
    }

    public Timer(double waitTime, boolean repeat, boolean dev) {
        this.time = waitTime;
        this.waitTime = waitTime;
        this.repeat = repeat;
        this.dev = dev;
    }

    public void start() {
        active = true;
        timeout = false;
    }

    public void start(double overflow) {
        if (!active && time == waitTime) {
            active = true;
            timeout = false;
            time = waitTime + overflow;
        }
    }

    public void pause() {
        if (active) {
            active = false;
        }
    }

    public void resume() {
        if (!active) {
            active = true;
        }
    }

    public void reset(double overflow) {
        if (repeat) {
            time = waitTime + overflow;
        }
        else {
            time = waitTime;
        }
        active = false;
        timeout = false;
    }

    public void tick(double tickIncrement) {
        if (active) {
            time -= tickIncrement;
        }
    }

    public void update(double tickIncrement) {
        timeout = false;

        tick(tickIncrement);

        if (time <= 0) {
            reset(time);
            timeout = true;
            if (dev) {
                System.out.println("hmm");
            }
        }
    }

    public void update(double tickIncrement, Timer nextTimer) {
        timeout = false;

        tick(tickIncrement);

        if (time <= 0) {
            reset(0);
            nextTimer.start(time);
            timeout = true;
        }
    }

    public float percentage() {
        if (!timeout) {
            return (float) (1 - (time / waitTime));
        }
        return 1;
    }

}