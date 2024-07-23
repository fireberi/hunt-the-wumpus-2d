package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import components.*;

import objects.*;

public final class SceneLevel1 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, "Level 1", LevelData.level1.playerSpawn, LevelData.level1.enemySpawns, LevelData.level1.nextLevel, LevelData.level1.tilemap);

        System.out.println("SceneLevel1 init");
    }

}