package systems;

import dev.dominion.ecs.api.Dominion;

import components.VelocityComponent;
import components.SpeedComponent;
import components.GravityComponent;

import core.State;

public class GravitySystem implements Runnable {

    private Dominion cherry;
    private State state;

    public GravitySystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(VelocityComponent.class, SpeedComponent.class, GravityComponent.class).stream().forEach(e -> {
            VelocityComponent vel = e.comp1();
            SpeedComponent spd = e.comp2();
            GravityComponent grv = e.comp3();

            if (!vel.gravity) {
                return;
            }

            vel.y += grv.gravity;
            if (vel.y > spd.maxY) {
                vel.y = spd.maxY;
            }
        });
    }

}