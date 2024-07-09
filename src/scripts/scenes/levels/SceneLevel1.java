package scripts.scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Scheduler;

import scripts.components.*;

import scripts.systems.*;

import scripts.objects.*;

import scripts.core.Scene;
import scripts.core.Constants;
import scripts.core.State;
import scripts.util.Collision;

public class SceneLevel1 extends Scene {

    Dominion cherry = Dominion.create("cherry");
    State state = new State();

    Scheduler updateScheduler = cherry.createScheduler();
    Scheduler renderScheduler = cherry.createScheduler();

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        //region entity setup
        final float characterX = 32f;
        final float characterY = 64f;
        Objects.createEnemyActor(cherry, 380f, 80f, true);
        Objects.createCharacterActor(cherry, characterX, characterY, true, new HashMap<String, Entity>(Map.ofEntries(
            Map.entry("sword", Objects.createSwordActor(cherry, 108f, 170f, true))
        )));

        cherry.createEntity(new RenderLayerComponent((byte) 0), LevelData.level1);
        //endregion

        //region system setup
        TimerSystem timerSystem = new TimerSystem(cherry, state);
        GravitySystem gravitySystem = new GravitySystem(cherry, state);
        PlayerControllerSystem playerControllerSystem = new PlayerControllerSystem(cherry, state);
        EnemyAISystem enemyAISystem = new EnemyAISystem(cherry, state);
        MoveSystem moveSystem = new MoveSystem(cherry, state);
        // BorderCollisionSystem borderCollisionSystem = new BorderCollisionSystem(cherry, state);
        AreaCollisionSystem areaCollisionSystem = new AreaCollisionSystem(cherry, state);
        HealthDamageSystem healthDamageSystem = new HealthDamageSystem(cherry, state);
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, characterX - 160f, characterY - 90f, images);
        TextSystem textSystem = new TextSystem(cherry, ctx);

        EntityDeletionSystem entityDeletionSystem = new EntityDeletionSystem(cherry, state, renderSystem);

        updateScheduler.schedule(entityDeletionSystem);
        updateScheduler.schedule(timerSystem);
        updateScheduler.schedule(gravitySystem);
        updateScheduler.schedule(playerControllerSystem);
        updateScheduler.schedule(enemyAISystem);
        updateScheduler.schedule(moveSystem);
        // updateScheduler.schedule(borderCollisionSystem);
        updateScheduler.schedule(areaCollisionSystem);
        updateScheduler.schedule(healthDamageSystem);
        updateScheduler.schedule(spriteSystem);

        renderScheduler.schedule(renderSystem);
        renderScheduler.schedule(textSystem);
        //endregion

        System.out.println("SceneLevel1 init");
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