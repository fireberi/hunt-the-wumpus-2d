package scripts.systems;

import java.util.ArrayList;

import dev.dominion.ecs.api.Dominion;

import scripts.components.TimerComponent;

import scripts.util.Timer;
import scripts.core.State;

public class TimerSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public TimerSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(TimerComponent.class).stream().forEach(e -> {
            Timer[] timers = e.comp().timers;

            int count = 0;
            int length = timers.length;
            // System.out.println("\nTimer System");
            for (Timer timer : timers) {
                // System.out.println("timer " + count + ": " + timer.active);
                if (count + 1 < length) {
                    timer.update(state.delta, timers[count + 1]);
                    count += 1;
                    continue;
                }
                timer.update(state.delta);
            }

            /*
            loop through timers
                if current timer is not last timer
                    update the timer, pass in the next timer
                else if the current timer is the last timer
                    update the timer
                    get the overflow when timer times out
                    if the timercomponent repeats
                        start the next timer with the overflow

            */
        });
    }

}