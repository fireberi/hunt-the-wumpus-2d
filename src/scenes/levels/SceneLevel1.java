package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import objects.*;

public final class SceneLevel1 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, "Level 1", LevelData.level1.playerSpawn, LevelData.level1.enemySpawns, LevelData.level1.nextLevel, LevelData.level1.tilemap);

        Objects.createTextActor(cherry, "Welcome! Let's hunt the Wumpus!\n\nUse arrow keys or W, A, D to move", 40, 100, 16, TextAlignment.CENTER, false, null);
        Objects.createTextActor(cherry, "The cave has many dangers... including spikes...", 300, 100, 16, TextAlignment.LEFT, false, null);
        Objects.createTextActor(cherry, "and worms!\n\nPress C or J to use your\n\ntrusty rusty sword, Rusty!", 500, 95, 16, TextAlignment.LEFT, false, null);
        Objects.createTextActor(cherry, "Press Z or L to enter the next level", 832, 56, 16, TextAlignment.CENTER, false, null);

        System.out.println("SceneLevel1 init");
    }

}