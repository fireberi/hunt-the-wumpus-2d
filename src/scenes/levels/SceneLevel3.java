package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import components.*;

import objects.*;

public final class SceneLevel3 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, LevelData.level3.playerSpawn, LevelData.level3.enemySpawns, LevelData.level3.nextLevel, LevelData.level3.tilemap);

        System.out.println("SceneLevel3 init");
    }

}