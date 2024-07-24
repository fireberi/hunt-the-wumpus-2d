package scenes;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Scheduler;

import components.*;
import components.helpers.*;
import systems.*;

import objects.*;

import core.Scene;
import core.State;
import core.Constants;

public class SceneBaseLevel extends SceneCherry {

    public void loadLevel(GraphicsContext ctx, HashMap<String, Image> images, String levelName, int[] playerSpawn, float[][] enemySpawns, String nextLevel, TilemapComponent mapData) {
        // create entities
        float playerSpawnX = playerSpawn[0] * Constants.TILESIZE;
        float playerSpawnY = playerSpawn[1] * Constants.TILESIZE;
        for (float[] enemyData : enemySpawns) {
            int e = (int) enemyData[0];
            float x = enemyData[1] * Constants.TILESIZE;
            float y = enemyData[2] * Constants.TILESIZE;
            Objects.createEnemyActor(cherry, x, y, e);
        }

        Objects.createCharacterActor(cherry, playerSpawnX, playerSpawnY, 200f, true);

        // HUD
        int hudY = 16;
        cherry.createEntity(
            new PositionComponent(Constants.WIDTH / 2, hudY, true),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(200, 16, Color.web("rgba(95, 63, 79, 0.5)"), true),
                new GraphicsComponent(196, 12, Color.web("rgba(111, 63, 63, 0.5)"), true),
            }),
            new RenderLayerComponent((byte) 3)
        );

        Objects.createTextActor(cherry,
            70,
            hudY + 2,
            true,
            "",
            20, TextAlignment.LEFT, Color.WHITE, new TextLogic() {
            @Override
            public void update(Dominion cherry, TextComponent txt) {
                cherry.findEntitiesWith(PlayerControllerComponent.class).stream().forEach(e -> {
                    HealthComponent hth = e.entity().get(HealthComponent.class);
                    txt.text = "HEALTH: " + (int) hth.health;
                });
            }
        });
        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            hudY + 2,
            true,
            "< NO WEAPON >",
            20, TextAlignment.CENTER, Color.WHITE, new TextLogic() {
            @Override
            public void update(Dominion cherry, TextComponent txt) {
                cherry.findEntitiesWith(PlayerControllerComponent.class, InventoryComponent.class).stream().forEach(e -> {
                    HealthComponent hth = e.entity().get(HealthComponent.class);
                    InventoryComponent inv = e.comp2();
                    String weaponText = "NO WEAPON";
                    if (inv.getCurrentItemType() == "melee") {
                        weaponText = "RUSTY (sword)";
                    }
                    else if (inv.getCurrentItemType() == "spawner") {
                        weaponText = "DUSTY (bow)";
                    }
                    txt.text = "< " + weaponText + " >";
                });
            }
        });
        Objects.createTextActor(cherry,
            250,
            hudY + 2,
            true,
            levelName,
            20, TextAlignment.RIGHT, Color.WHITE, null);

        // create map
        cherry.createEntity(new PositionComponent(0f, 0f), mapData, new RenderLayerComponent((byte) 0));

        // create systems
        TimerSystem timerSystem = new TimerSystem(cherry, state);
        GravitySystem gravitySystem = new GravitySystem(cherry, state);
        PlayerControllerSystem playerControllerSystem = new PlayerControllerSystem(cherry, state);
        EnemyAISystem enemyAISystem = new EnemyAISystem(cherry, state);
        EntityControllerSystem entityControllerSystem = new EntityControllerSystem(cherry, state);
        MoveSystem moveSystem = new MoveSystem(cherry, state);
        InventorySystem inventorySystem = new InventorySystem(cherry, state);
        AreaCollisionSystem areaCollisionSystem = new AreaCollisionSystem(cherry, state);
        TileCollisionSystem tileCollisionSystem;
        if (nextLevel != null || nextLevel != "") {
            tileCollisionSystem = new TileCollisionSystem(cherry, state, this, nextLevel);
        }
        else {
            tileCollisionSystem = new TileCollisionSystem(cherry, state);
        }
        EffectsSystem effectsSystem = new EffectsSystem(cherry, state);
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);
        InputSystem inputSystem = new InputSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, playerSpawnX - 160f, playerSpawnY - 90f, images);

        EntityDeletionSystem entityDeletionSystem = new EntityDeletionSystem(cherry, state, renderSystem);

        updateScheduler.schedule(entityDeletionSystem);
        updateScheduler.schedule(timerSystem);
        updateScheduler.schedule(gravitySystem);
        updateScheduler.schedule(playerControllerSystem);
        updateScheduler.schedule(enemyAISystem);
        updateScheduler.schedule(entityControllerSystem);
        updateScheduler.schedule(moveSystem);
        updateScheduler.schedule(inventorySystem);
        updateScheduler.schedule(areaCollisionSystem);
        updateScheduler.schedule(tileCollisionSystem);
        updateScheduler.schedule(effectsSystem);
        updateScheduler.schedule(spriteSystem);
        updateScheduler.schedule(inputSystem);

        renderScheduler.schedule(renderSystem);
    }

}