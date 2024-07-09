package scripts.scenes;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Scheduler;

import scripts.components.TilemapComponent;
import scripts.components.RenderLayerComponent;
import scripts.systems.*;

import scripts.objects.*;

import scripts.core.Scene;
import scripts.core.State;
import scripts.core.Constants;

public class SceneBaseLevel extends Scene {

    public Dominion cherry = Dominion.create("cherry");
    State state = new State();

    Scheduler updateScheduler = cherry.createScheduler();
    Scheduler renderScheduler = cherry.createScheduler();

    public void loadLevel(GraphicsContext ctx, HashMap<String, Image> images, int[] playerSpawn, int[][] enemySpawns, TilemapComponent mapData) {
        // create entities
        float playerSpawnX = playerSpawn[0] * Constants.TILESIZE;
        float playerSpawnY = playerSpawn[1] * Constants.TILESIZE;
        for (int[] enemyData : enemySpawns) {
            int e = enemyData[0];
            float x = enemyData[1] * Constants.TILESIZE;
            float y = enemyData[2] * Constants.TILESIZE;
            Objects.createEnemyActor(cherry, x, y, true);
        }

        Objects.createCharacterActor(cherry, playerSpawnX, playerSpawnY, true, new HashMap<String, Entity>(Map.ofEntries(
            Map.entry("sword", Objects.createSwordActor(cherry, 108f, 170f, true))
        )));

        // create map
        cherry.createEntity(new RenderLayerComponent((byte) 0), mapData);

        // create systems
        TimerSystem timerSystem = new TimerSystem(cherry, state);
        GravitySystem gravitySystem = new GravitySystem(cherry, state);
        PlayerControllerSystem playerControllerSystem = new PlayerControllerSystem(cherry, state);
        EnemyAISystem enemyAISystem = new EnemyAISystem(cherry, state);
        MoveSystem moveSystem = new MoveSystem(cherry, state);
        AreaCollisionSystem areaCollisionSystem = new AreaCollisionSystem(cherry, state);
        HealthDamageSystem healthDamageSystem = new HealthDamageSystem(cherry, state);
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, playerSpawnX - 160f, playerSpawnY - 90f, images);
        TextSystem textSystem = new TextSystem(cherry, ctx);

        EntityDeletionSystem entityDeletionSystem = new EntityDeletionSystem(cherry, state, renderSystem);

        updateScheduler.schedule(entityDeletionSystem);
        updateScheduler.schedule(timerSystem);
        updateScheduler.schedule(gravitySystem);
        updateScheduler.schedule(playerControllerSystem);
        updateScheduler.schedule(enemyAISystem);
        updateScheduler.schedule(moveSystem);
        updateScheduler.schedule(areaCollisionSystem);
        updateScheduler.schedule(healthDamageSystem);
        updateScheduler.schedule(spriteSystem);

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

    public void shutDown() {
        System.out.println("SceneLevel1 shut down");
        updateScheduler.shutDown();
        renderScheduler.shutDown();
    }

}