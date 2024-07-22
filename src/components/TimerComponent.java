package components;

import util.Timer;

public class TimerComponent {

    public Timer[] timers;
    public boolean repeat;
    public int current;

    public TimerComponent(boolean repeat, Timer[] timers) {
        this.timers = timers;
        this.repeat = repeat; // implement this
        this.current = 0;
    }

    public boolean active() {
        for (Timer t : timers) {
            if (t.active) {
                return true;
            }
        }
        return false;
    }

    public void startTimer(int index, double overflow) {
        current = index;
        timers[current].start(overflow);
    }

}