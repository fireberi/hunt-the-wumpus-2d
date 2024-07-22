package components;

import java.util.ArrayList;

import dev.dominion.ecs.api.Entity;

import components.helpers.InventoryItem;

public class InventoryComponent {

    public ArrayList<InventoryItem> inventory;
    private int current;

    public InventoryComponent(int current, ArrayList<InventoryItem> inventory) {
        this.current = current;
        this.inventory = inventory;
    }

    public Entity getCurrentItem() {
        int size = inventory.size();
        if (current < 0 || current >= size) {
            return null;
        }
        return inventory.get(current).item;
    }

    public Entity getItemWithType(String type) {
        for (InventoryItem i : inventory) {
            if (i.type == type) {
                return i.item;
            }
        }
        return null;
    }

    public String getCurrentItemType() {
        int size = inventory.size();
        if (current < 0 || current >= size) {
            return "";
        }
        return inventory.get(current).type;
    }

    public void nextItem() {
        int size = inventory.size();

        current += 1;
        if (current >= size) {
            current = 0;
        }
    }

}