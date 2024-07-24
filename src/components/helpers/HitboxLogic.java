package components.helpers;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public interface HitboxLogic {
    public void update(Entity hitbox, Entity hurtbox, boolean entered, boolean justEntered, boolean justExited);
    public void clean(Dominion cherry, Entity hitbox);
}