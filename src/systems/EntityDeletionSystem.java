package systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import components.HitboxComponent;
import components.HurtboxComponent;
import components.HealthComponent;
import components.InventoryComponent;
import components.helpers.InventoryItem;

import systems.RenderSystem;

import core.State;

public class EntityDeletionSystem implements Runnable {

    private Dominion cherry;
    private State state;
    private RenderSystem renderSystem;

    public EntityDeletionSystem(Dominion cherry, State state, RenderSystem renderSystem) {
        this.cherry = cherry;
        this.state = state;
        this.renderSystem = renderSystem;
    }

    public void run() {
        cherry.findEntitiesWith(HitboxComponent.class).stream().forEach(e -> {
            HitboxComponent hit = e.comp();
            Entity entity = e.entity();
            if (hit.markDelete) {
                delete(entity);
            }
        });
        cherry.findEntitiesWith(HurtboxComponent.class).stream().forEach(e -> {
            HurtboxComponent hrt = e.comp();
            Entity entity = e.entity();
            if (hrt.markDelete) {
                delete(entity);
            }
        });
        cherry.findEntitiesWith(HealthComponent.class).stream().forEach(e -> {
            HealthComponent hth = e.comp();
            Entity entity = e.entity();
            if (hth.health <= 0) {
                if (hth.respawnLogic == null) {
                    delete(entity);
                }
                else {
                    hth.respawnLogic.respawn();
                    delete(entity);
                }
            }
        });
    }

    private void delete(Entity entity) {
        InventoryComponent inv = entity.get(InventoryComponent.class);
        if (inv != null) {
            for (InventoryItem item : inv.inventory) {
                cherry.deleteEntity(item.item);
            }
        }
        cherry.deleteEntity(entity);
    }

}