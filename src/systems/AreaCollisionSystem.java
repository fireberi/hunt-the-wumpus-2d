package systems;

import dev.dominion.ecs.api.Dominion;

import components.PositionComponent;
import components.HitboxComponent;
import components.HurtboxComponent;

import core.State;
import util.Collision;

// NOTE:
// A note on the layers:
// - layer 0 is for the player
// - layer 1 is for enemies

public class AreaCollisionSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public AreaCollisionSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(PositionComponent.class, HitboxComponent.class).stream().forEach(hitbox -> {
            PositionComponent pos = hitbox.comp1();
            HitboxComponent hit = hitbox.comp2();

            cherry.findEntitiesWith(PositionComponent.class, HurtboxComponent.class).stream().forEach(hurtbox -> {
                PositionComponent rpos = hurtbox.comp1();
                HurtboxComponent hrt = hurtbox.comp2();

                if (!hrt.active) {
                    hrt.resetJustEnteredAndJustExited();
                    hrt.entered.clear();
                    return;
                }

                boolean isOnSameLayer = false;
                for (int l = 0; l < hit.layer.length; l++) {
                    if (hit.layer[l] == true && hrt.layer[l] == true) {
                        isOnSameLayer = true;
                        break;
                    }
                }

                if (hit.active && isOnSameLayer && Collision.AABB(pos.x + hit.x, pos.y + hit.y, hit.w, hit.h, rpos.x + hrt.x, rpos.y + hrt.y, hrt.w, hrt.h)) {
                    hrt.enter(hit);
                }
                else {
                    hrt.exit(hit);
                }

                hit.updateLogic.update(hitbox.entity(), hurtbox.entity(), hrt.entered.contains(hit), hrt.justEntered.contains(hit), hrt.justExited.contains(hit));

                hrt.resetJustEnteredAndJustExited();
            });

            hit.updateLogic.clean(cherry, hitbox.entity());
        });
    }

}