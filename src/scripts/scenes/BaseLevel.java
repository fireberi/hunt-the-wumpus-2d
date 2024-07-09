package scripts.scenes;

import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Scheduler;

import scripts.systems.*;

import scripts.core.Scene;
import scripts.core.State;

public class BaseLevel extends Scene {

    public Dominion cherry = Dominion.create("cherry");
    State state = new State();

    Scheduler updateScheduler = cherry.createScheduler();
    Scheduler renderScheduler = cherry.createScheduler();

    public void initSystems(GraphicsContext ctx, HashMap<String, Image> images, float characterX, float characterY) {
        //region system setup
        TimerSystem timerSystem = new TimerSystem(cherry, state);
        GravitySystem gravitySystem = new GravitySystem(cherry, state);
        PlayerControllerSystem playerControllerSystem = new PlayerControllerSystem(cherry, state);
        EnemyAISystem enemyAISystem = new EnemyAISystem(cherry, state);
        MoveSystem moveSystem = new MoveSystem(cherry, state);
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