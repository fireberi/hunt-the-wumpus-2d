package scripts.components;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public interface HitboxLogic {
    void update(Dominion cherry, Entity hitbox);
    void clean(Dominion cherry, Entity hitbox);
}