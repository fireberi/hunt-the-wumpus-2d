package scripts.systems;

import dev.dominion.ecs.api.Dominion;

import scripts.components.PositionComponent;
import scripts.components.HitboxComponent;
import scripts.components.HurtboxComponent;

import scripts.core.State;
import scripts.util.Collision;

public class AreaCollisionSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public AreaCollisionSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(PositionComponent.class, HitboxComponent.class).stream().forEach(attacker -> {
            PositionComponent pos = attacker.comp1();
            HitboxComponent hit = attacker.comp2();

            hit.updateLogic.update(cherry, attacker.entity());

            cherry.findEntitiesWith(PositionComponent.class, HurtboxComponent.class).stream().forEach(receiver -> {
                PositionComponent rpos = receiver.comp1();
                HurtboxComponent hrt = receiver.comp2();

                if (!hrt.active) {
                    hrt.resetJustEnteredAndJustExited();
                    hrt.entered.clear();
                    return;
                }

                if (Collision.AABB(pos.x + hit.x, pos.y + hit.y, hit.w, hit.h, rpos.x + hrt.x, rpos.y + hrt.y, hrt.w, hrt.h) && hit.active) {
                    hrt.enter(hit);
                }
                else {
                    hrt.exit(hit);
                }

                hrt.updateLogic.update(receiver.entity(), attacker.entity(), hrt.entered.contains(hit), hrt.justEntered.contains(hit), hrt.justExited.contains(hit));

                hrt.resetJustEnteredAndJustExited();
            });

            hit.updateLogic.clean(cherry, attacker.entity());
        });
    }

}