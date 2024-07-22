package components;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public interface HitboxLogic {
    void update(Entity hitbox, Entity hurtbox, boolean entered, boolean justEntered, boolean justExited);
    void clean(Dominion cherry, Entity hitbox);
}