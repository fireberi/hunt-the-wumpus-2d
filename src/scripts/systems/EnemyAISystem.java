package scripts.systems;

import dev.dominion.ecs.api.Dominion;

import scripts.components.EnemyAIComponent;
import scripts.components.VelocityComponent;
import scripts.components.BoxColliderComponent;
import scripts.components.SpeedComponent;
import scripts.core.State;

public class EnemyAISystem implements Runnable {

    private Dominion cherry;
    private State state;

    public EnemyAISystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(EnemyAIComponent.class, BoxColliderComponent.class).stream().forEach(e -> {
            EnemyAIComponent eAI = e.comp1();
            BoxColliderComponent box = e.comp2();

            if (eAI.mode == "left-right") {
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                SpeedComponent spd = e.entity().get(SpeedComponent.class);

                if (box.left) {
                    vel.facingRight = true;
                }
                else if (box.right) {
                    vel.facingRight = false;
                }

                vel.x = spd.maxX * ((vel.facingRight) ? 1 : -1);
            }
        });
    }

}