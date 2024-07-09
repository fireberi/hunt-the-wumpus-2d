package scripts.scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import scripts.scenes.SceneBaseLevel;

import scripts.components.*;

import scripts.objects.*;

public final class SceneLevel1 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, LevelData.level1PlayerSpawn, LevelData.level1EnemySpawns, LevelData.level1);

        System.out.println("SceneLevel1 init");
    }

}