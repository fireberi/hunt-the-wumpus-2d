package systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import components.InventoryComponent;
import components.helpers.InventoryItem;

import core.State;

public class InventorySystem implements Runnable {

    private Dominion cherry;
    private State state;

    public InventorySystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(InventoryComponent.class).stream().forEach(e -> {
            InventoryComponent inv = e.comp();
            Entity entity = e.entity();

            for (InventoryItem i : inv.inventory.values()) {
                i.updateLogic.update(i.item, entity);
            }
        });
    }

}