package components.helpers;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

public class InventoryItem {

    public String type;
    public Entity item;
    public InventoryLogic updateLogic;

    public InventoryItem(String type, Entity item, InventoryLogic updateLogic) {
        this.type = type;
        this.item = item;
        this.updateLogic = updateLogic;
    }

}