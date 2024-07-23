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

public class SceneBaseLevel extends Scene {

    public Dominion cherry = Dominion.create("cherry");
    State state = new State();

    Scheduler updateScheduler = cherry.createScheduler();
    Scheduler renderScheduler = cherry.createScheduler();

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
        cherry.createEntity(
            new PositionComponent(Constants.WIDTH / 2, Constants.HEIGHT - 20, true),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(280, 16, Color.web("rgba(47, 47, 63, 0.5)"), true),
                new GraphicsComponent(276, 12, Color.web("rgba(79, 79, 95, 0.5)"), true),
            }),
            new RenderLayerComponent((byte) 3)
        );
        Objects.createTextActor(cherry, levelName, 290, Constants.HEIGHT - 18, TextAlignment.RIGHT, true, null);
        Objects.createTextActor(cherry, "", 30, Constants.HEIGHT - 18, TextAlignment.LEFT, true, new TextLogic() {
            @Override
            public void update(Dominion cherry, TextComponent txt) {
                cherry.findEntitiesWith(PlayerControllerComponent.class).stream().forEach(e -> {
                    HealthComponent hth = e.entity().get(HealthComponent.class);
                    txt.text = "HEALTH: " + (int) hth.health;
                });
            }
        });

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

    @Override
    public void update(double tickInterval) {
        // tickInterval is in seconds
        state.updateDelta(tickInterval);
        updateScheduler.tick();
    }

    @Override
    public void render(GraphicsContext ctx) {
        renderScheduler.tick();
    }

    @Override
    public void shutDown() {
        updateScheduler.shutDown();
        renderScheduler.shutDown();
        System.out.println("level shut down");
    }

}