package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import components.*;

import objects.*;

public final class SceneLevel2 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, LevelData.level2.playerSpawn, LevelData.level2.enemySpawns, LevelData.level2.nextLevel, LevelData.level2.tilemap);

        System.out.println("SceneLevel2 init");
    }

}