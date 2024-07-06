package scripts.components;

import scripts.util.Timer;

public class TimerComponent {

    public Timer[] timers;
    public boolean repeat;
    public int current;

    public TimerComponent(boolean repeat, Timer[] timers) {
        this.timers = timers;
        this.repeat = repeat; // implement this
        this.current = 0;
    }

}