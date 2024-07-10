package scripts.systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.PositionComponent;
import scripts.components.VelocityComponent;
import scripts.components.BoxColliderComponent;
import scripts.components.TilemapComponent;
import scripts.components.PlayerControllerComponent;
import scripts.components.HealthComponent;

import scripts.core.Constants;
import scripts.core.State;
import scripts.core.Scene;
import scripts.util.Collision;
import scripts.util.Tiles;
import scripts.util.DamageTypes.Damage;

public class TileCollisionSystem implements Runnable {

    private Dominion cherry;
    private State state;
    private Scene scene;
    private String nextLevel;

    public TileCollisionSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public TileCollisionSystem(Dominion cherry, State state, Scene scene, String nextLevel) {
        this.cherry = cherry;
        this.state = state;
        this.scene = scene;
        this.nextLevel = nextLevel;
    }

    public void run() {
        if (!Constants.GRID_COLLISION) {
            return;
        }

        cherry.findEntitiesWith(PositionComponent.class, VelocityComponent.class, BoxColliderComponent.class).stream().forEach(e -> {
            Entity entity = e.entity();
            PositionComponent pos = e.comp1();
            VelocityComponent vel = e.comp2();
            BoxColliderComponent boxCol = e.comp3();

            // optional components
            PlayerControllerComponent pcc = entity.get(PlayerControllerComponent.class);
            HealthComponent hth = entity.get(HealthComponent.class);

            cherry.findEntitiesWith(TilemapComponent.class).stream().forEach(tilemap -> {
                TilemapComponent map = tilemap.comp();

                int[] collision = Collision.gridAABB(Constants.TILESIZE, map.grid, Tiles.interactables, pos.x + boxCol.x, pos.y + boxCol.y, boxCol.w, boxCol.h);

                int tile = collision[0];
                // TODO:
                // add support for multiple collisions (not just with one block)
                // this way interactable blocks can stack their effects
                // e.g. touching a lava block and a spike block will cause the player's health to decline their accumulated values
                // the reason this is a feature to be added is because Collision.gridAABB only returns the first collision it detects

                if (tile != 0) {
                    int tx = collision[1] * Constants.TILESIZE;
                    int ty = collision[2] * Constants.TILESIZE;

                    Damage[] effects = {null};
                    float[] values = {0f};
                    boolean healthAffected = false;

                    if (tile == Tiles.NEXT_LEVEL && pcc != null && nextLevel != "") {
                        // proceed to next level
                        System.out.println("next level");
                        System.out.println("nextLevel: " + nextLevel);
                        System.out.println("scene: " + scene);
                        System.out.println("scene.nextScene (before): " + scene.nextScene);
                        scene.nextScene = nextLevel;
                        System.out.println("scene.nextScene (after): " + scene.nextScene);
                        System.out.println();
                    }
                    else if (tile == Tiles.LAVA && hth != null) {
                        // reduce lots of health, knockback character
                        pos.y = ty + boxCol.y;
                        vel.y = -0.8f - Constants.GRAVITY;
                        effects[0] = Damage.INSTANT;
                        values[0] = 50f;
                        healthAffected = true;
                    }
                    else if (tile == Tiles.SPIKE && hth != null) {
                        // reduce a bit of health, knockback character
                        pos.y = ty + boxCol.y;
                        vel.y = -0.8f - Constants.GRAVITY;
                        effects[0] = Damage.INSTANT;
                        values[0] = 20f;
                        healthAffected = true;
                    }

                    if (hth != null && healthAffected) {
                        hth.add(effects, values);
                    }
                }
            });
        });
    }

}