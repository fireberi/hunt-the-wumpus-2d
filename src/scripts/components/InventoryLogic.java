package scripts.components;

import dev.dominion.ecs.api.Entity;

public interface InventoryLogic {
    void update(Entity item, Entity owner);
}