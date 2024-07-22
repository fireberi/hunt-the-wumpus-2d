package systems;

import java.util.ArrayList;

import dev.dominion.ecs.api.Dominion;

import components.TimerComponent;

import util.Timer;
import core.State;

public class TimerSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public TimerSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(TimerComponent.class).stream().forEach(e -> {
            TimerComponent tmc = e.comp();
            Timer[] timers = tmc.timers;

            if (!tmc.repeat) {
                double overflow = 0;
                for (Timer t : timers) {
                    double result = t.update(state.delta);
                    if (result < 0) {
                        overflow = result;
                    }
                    else if (result > 0) {
                        System.out.println("TimerSystem ERROR: overflow became positive (should be negative)");
                    }
                }
                if (timers[tmc.current].timeout) {
                    tmc.current += 1;
                    if (tmc.current < timers.length) {
                        timers[tmc.current].start(overflow);
                    }
                    else {
                        tmc.current = 0;
                    }
                }
            }

            /*
            How this works:
            - start one of the timers from the timer component
            - when that one ends, move on to the next one
            - if the next one is non-existent and the timer component has repeat, start the first timer, otherwise do nothing
            */
        });
    }

}