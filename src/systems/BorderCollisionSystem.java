package systems;

import dev.dominion.ecs.api.Dominion;

import components.PositionComponent;
import components.VelocityComponent;
import components.BoxColliderComponent;

import core.State;

public class BorderCollisionSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public BorderCollisionSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(PositionComponent.class, VelocityComponent.class, BoxColliderComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            VelocityComponent vel = e.comp2();
            BoxColliderComponent boxCol = e.comp3();

            float rightSide = pos.x + boxCol.x + boxCol.w;
            float leftSide = pos.x + boxCol.x;
            float bottomSide = pos.y + boxCol.y + boxCol.h;
            float topSide = pos.y + boxCol.y;

            if (bottomSide > 180) {
                vel.y = 0;
                pos.y = 180 - boxCol.y - boxCol.h;
                boxCol.bottom = true;
            }

            if (rightSide > 320) {
                vel.x = vel.x / 4;
                pos.x = 320 - boxCol.x - boxCol.w;
                boxCol.right = true;
            }
            else if (leftSide < 0) {
                vel.x = vel.x / 4;
                pos.x = 0 - boxCol.x;
                boxCol.left = true;
            }
        });
    }

}