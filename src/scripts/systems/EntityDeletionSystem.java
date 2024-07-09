package scripts.systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.HitboxComponent;
import scripts.components.HurtboxComponent;
import scripts.components.HealthComponent;
import scripts.components.SpriteComponent;
import scripts.components.PositionComponent;

import scripts.systems.RenderSystem;

import scripts.core.State;

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
                cherry.deleteEntity(entity);
            }
        });
        cherry.findEntitiesWith(HurtboxComponent.class).stream().forEach(e -> {
            HurtboxComponent hrt = e.comp();
            Entity entity = e.entity();
            if (hrt.markDelete) {
                cherry.deleteEntity(entity);
            }
        });
        cherry.findEntitiesWith(HealthComponent.class).stream().forEach(e -> {
            HealthComponent hth = e.comp();
            Entity entity = e.entity();
            if (hth.health <= 0) {
                cherry.deleteEntity(entity);
            }
        });
    }

}