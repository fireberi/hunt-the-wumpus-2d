package components;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public interface SpawnerLogic {
    void spawn(Dominion cherry, Entity spawner);
}