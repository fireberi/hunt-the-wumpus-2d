package scenes;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Scheduler;

import components.TilemapComponent;
import components.RenderLayerComponent;
import components.InventoryItem;
import components.InventoryLogic;
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

    public void loadLevel(GraphicsContext ctx, HashMap<String, Image> images, int[] playerSpawn, float[][] enemySpawns, String nextLevel, TilemapComponent mapData) {
        // create entities
        float playerSpawnX = playerSpawn[0] * Constants.TILESIZE;
        float playerSpawnY = playerSpawn[1] * Constants.TILESIZE;
        for (float[] enemyData : enemySpawns) {
            int e = (int) enemyData[0];
            float x = enemyData[1] * Constants.TILESIZE;
            float y = enemyData[2] * Constants.TILESIZE;
            Objects.createEnemyActor(cherry, x, y, e);
        }

        Objects.createCharacterActor(cherry, playerSpawnX, playerSpawnY, true, new HashMap<String, InventoryItem>(Map.ofEntries(
                Map.entry("melee", Objects.createSwordItem(cherry))
            ))
        );

        // create map
        cherry.createEntity(new RenderLayerComponent((byte) 0), mapData);

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
        HealthDamageSystem healthDamageSystem = new HealthDamageSystem(cherry, state);
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);
        InputSystem inputSystem = new InputSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, playerSpawnX - 160f, playerSpawnY - 90f, images);
        TextSystem textSystem = new TextSystem(cherry, ctx);

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
        updateScheduler.schedule(healthDamageSystem);
        updateScheduler.schedule(spriteSystem);
        updateScheduler.schedule(inputSystem);

        renderScheduler.schedule(renderSystem);
        renderScheduler.schedule(textSystem);
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