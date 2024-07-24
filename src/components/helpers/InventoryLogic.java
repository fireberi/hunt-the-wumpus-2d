package components.helpers;

import dev.dominion.ecs.api.Entity;

public interface InventoryLogic {
    public void update(Entity item, Entity owner);
}