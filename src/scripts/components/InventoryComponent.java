package scripts.components;

import java.util.HashMap;

import dev.dominion.ecs.api.Entity;

public class InventoryComponent {

    public HashMap<String, InventoryItem> inventory;
    public String current;

    public InventoryComponent(String current, HashMap<String, InventoryItem> inventory) {
        this.current = current;
        this.inventory = inventory;
    }

    public Entity getCurrent() {
        if (current == "") {
            return null;
        }
        return inventory.get(current).item;
    }

}