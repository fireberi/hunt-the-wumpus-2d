package components;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public class InventoryItem {

    public Entity item;
    public InventoryLogic updateLogic;

    public InventoryItem(Entity item, InventoryLogic updateLogic) {
        this.item = item;
        this.updateLogic = updateLogic;
    }

}