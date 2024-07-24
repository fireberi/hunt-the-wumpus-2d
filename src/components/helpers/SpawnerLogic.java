package components.helpers;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public interface SpawnerLogic {
    public void spawn(Dominion cherry, Entity spawner);
}