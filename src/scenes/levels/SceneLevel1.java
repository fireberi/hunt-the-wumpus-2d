package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import objects.*;

public final class SceneLevel1 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, "Level 1", LevelData.level1.playerSpawn, LevelData.level1.enemySpawns, LevelData.level1.nextLevel, LevelData.level1.tilemap);

        Objects.createTextActor(cherry,
            40,
            100,
            false,
            "Welcome! Let's hunt the Wumpus!\n\nUse arrow keys or W, A, D to move",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            300,
            100,
            false,
            "The cave has many dangers... including spikes...",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            500,
            95,
            false,
            "and worms!\n\nPress C or J to use your\n\ntrusty rusty sword, Rusty!",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            832,
            56,
            false,
            "Press Z or L to enter the next level",
             16, TextAlignment.CENTER, Color.WHITE, null);

        System.out.println("SceneLevel1 init");
    }

}