package scripts.components;

import java.util.HashMap;

import dev.dominion.ecs.api.Entity;

public class InventoryComponent {

    public HashMap<String, Entity> inventory;

    public InventoryComponent(HashMap<String, Entity> inventory) {
        this.inventory = inventory;
    }

}