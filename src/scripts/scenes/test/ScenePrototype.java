package scripts.scenes.test;

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

public class ScenePrototype extends Scene {

    Dominion cherry = Dominion.create("cherry");
    State state = new State();

    Scheduler updateScheduler = cherry.createScheduler();
    Scheduler renderScheduler = cherry.createScheduler();

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        //region entity setup (with precise collision)
        // Objects.createSimpleCharacterActor(cherry, 100f, 110f, true);

        // cherry.createEntity(
        //     new PositionComponent(160f, 175f),
        //     new BoxColliderComponent(320f, 8f),
        //     new GraphicsComponent(320f, 8f, Color.rgb(207, 207, 207), true)
        // );
        // cherry.createEntity(
        //     new PositionComponent(160f, 140f),
        //     new BoxColliderComponent(100f, 4f),
        //     new GraphicsComponent(100f, 4f, Color.rgb(255, 63, 143), true)
        // );
        // cherry.createEntity(
        //     new PositionComponent(120f, 100f),
        //     new BoxColliderComponent(40f, 4f),
        //     new GraphicsComponent(40f, 4f, Color.rgb(255, 63, 143), true)
        // );
        // cherry.createEntity(
        //     new PositionComponent(160f, 60f),
        //     new BoxColliderComponent(20f, 4f),
        //     new GraphicsComponent(20f, 4f, Color.rgb(255, 63, 143), true)
        // );
        // cherry.createEntity(
        //     new PositionComponent(260f, 20f),
        //     new BoxColliderComponent(20f, 4f),
        //     new GraphicsComponent(20f, 4f, Color.rgb(255, 63, 143), true)
        // );
        //endregion

        //region entity setup (with grid collision)
        Objects.createSimpleEnemyActor(cherry, 100f, 40f, true);
        Objects.createSimpleEnemyActor(cherry, 160f, 80f, true);
        Objects.createSimpleEnemyActor(cherry, 20f, 100f, true);
        Objects.createSimpleEnemyActor(cherry, 120f, 160f, true);
        Objects.createTestCharacterActor(cherry, 108f, 170f, true, new HashMap<String, InventoryItem>(Map.ofEntries(
                Map.entry("melee", new InventoryItem(
                    Objects.createSwordActor(cherry, 108f, 170f, true),
                    new InventoryLogic() {
                        @Override
                        public void update(Entity item, Entity owner) {}
                    }
                ))
            ))
        );

        cherry.createEntity(
            new TilemapComponent(new int[][] {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            })
        );
        //endregion

        cherry.createEntity(
            new PositionComponent(25f, 50f),
            new TextComponent("     false\nfalse     false\n     false", "PT Mono", FontWeight.BOLD, 12)
        );

        //region system setup
        TimerSystem timerSystem = new TimerSystem(cherry, state);
        GravitySystem gravitySystem = new GravitySystem(cherry, state);
        PlayerControllerSystem playerControllerSystem = new PlayerControllerSystem(cherry, state);
        MoveSystem moveSystem = new MoveSystem(cherry, state);
        BorderCollisionSystem borderCollisionSystem = new BorderCollisionSystem(cherry, state);
        AreaCollisionSystem areaCollisionSystem = new AreaCollisionSystem(cherry, state);
        EnemyAISystem enemyAISystem = new EnemyAISystem(cherry, state);
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);

        updateScheduler.schedule(timerSystem);
        updateScheduler.schedule(gravitySystem);
        updateScheduler.schedule(playerControllerSystem);
        updateScheduler.schedule(enemyAISystem);
        updateScheduler.schedule(moveSystem);
        updateScheduler.schedule(borderCollisionSystem);
        updateScheduler.schedule(areaCollisionSystem);
        updateScheduler.schedule(spriteSystem);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, 0, 0, images);
        TextSystem textSystem = new TextSystem(cherry, ctx);

        renderScheduler.schedule(renderSystem);
        renderScheduler.schedule(textSystem);
        //endregion

        System.out.println("ScenePrototype init");
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
        System.out.println("ScenePrototype shut down");
        updateScheduler.shutDown();
        renderScheduler.shutDown();
    }

}