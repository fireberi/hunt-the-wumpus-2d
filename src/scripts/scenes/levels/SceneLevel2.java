package scripts.scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import scripts.scenes.SceneBaseLevel;

import scripts.components.*;

import scripts.objects.*;

public final class SceneLevel2 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, LevelData.level2PlayerSpawn, LevelData.level2EnemySpawns, LevelData.level2, LevelData.level2NextLevel);

        System.out.println("SceneLevel2 init");
    }

}