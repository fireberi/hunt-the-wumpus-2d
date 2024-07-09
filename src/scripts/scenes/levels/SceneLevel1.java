package scripts.scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Entity;

import scripts.scenes.BaseLevel;

import scripts.components.*;

import scripts.objects.*;

public final class SceneLevel1 extends BaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        final float characterX = 32f;
        final float characterY = 64f;

        Objects.createEnemyActor(cherry, 320f, 80f, true);
        // temp (for fun)
        for (int i = 0; i < 20; i++) {
            Objects.createEnemyActor(cherry, 160f + (float) (Math.random() * 201), 80f, true);
        }
        Objects.createCharacterActor(cherry, characterX, characterY, true, new HashMap<String, Entity>(Map.ofEntries(
            Map.entry("sword", Objects.createSwordActor(cherry, 108f, 170f, true))
        )));

        cherry.createEntity(new RenderLayerComponent((byte) 0), LevelData.level1);

        initSystems(ctx, images, characterX, characterY);
        System.out.println("SceneLevel1 init");
    }

}