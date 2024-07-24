package systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import components.PositionComponent;
import components.VelocityComponent;
import components.BoxColliderComponent;
import components.TilemapComponent;
import components.PlayerControllerComponent;
import components.HealthComponent;
import components.EffectReceiverComponent;
import components.EffectComponent;

import data.effects.*;
import data.Tiles;

import core.Constants;
import core.State;
import core.Scene;
import util.Collision;

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
            EffectReceiverComponent fxr = entity.get(EffectReceiverComponent.class);
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

                    Effect[] effects = {null};
                    boolean healthAffected = false;

                    if ((tile == Tiles.NA || tile == Tiles.NB || tile == Tiles.NC || tile == Tiles.ND) && pcc != null && pcc.inputs.get("down").justPressed() && nextLevel != "") {
                        // proceed to next level
                        System.out.println("next level");
                        scene.nextScene = nextLevel;
                    }
                    else if ((tile == Tiles.LAVA_TOP || tile == Tiles.LAVA_BODY) && fxr != null) {
                        // reduce lots of health, knockback character
                        pos.y = ty + boxCol.y;
                        vel.y = -0.8f - Constants.GRAVITY;
                        effects[0] = new InstantDamageEffect(50f);
                        healthAffected = true;
                    }
                    else if (tile == Tiles.SPIKE && fxr != null) {
                        // reduce a bit of health, knockback character
                        pos.y = ty + boxCol.y;
                        vel.y = -0.8f - Constants.GRAVITY;
                        effects[0] = new InstantDamageEffect(20f);
                        healthAffected = true;
                    }

                    if (fxr != null && healthAffected) {
                        fxr.add(effects);
                    }
                }
            });
        });
    }

}