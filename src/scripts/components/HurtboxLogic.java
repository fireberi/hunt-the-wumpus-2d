package scripts.components;

import dev.dominion.ecs.api.Entity;

public interface HurtboxLogic {
    void update(Entity receiver, Entity attacker, boolean entered, boolean justEntered, boolean justExited);
}