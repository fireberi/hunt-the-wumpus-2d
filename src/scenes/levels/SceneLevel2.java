package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import components.*;

import objects.*;

public final class SceneLevel2 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, "Level 2", LevelData.level2.playerSpawn, LevelData.level2.enemySpawns, LevelData.level2.nextLevel, LevelData.level2.tilemap);

        Objects.createTextActor(cherry,
            48,
            72,
            false,
            "You also have your\n\ntrusty dusty bow, Dusty!",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            152,
            80,
            false,
            "Press X or K to\n\ncycle weapons",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            264,
            96,
            false,
            "Try killing this bat with Dusty",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            456,
            112,
            false,
            "Now try this one (hint: Press\n\nDown or S to change the angle)",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            640,
            96,
            false,
            "Watch out!\n\nLava does\n\nlots of damage!",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            1064,
            136,
            false,
            "Spiders shoot webs\n\nthat poison you...",
            16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            1200,
            140,
            false,
            "And ghouls slow...\n\nyou...\n\n...down...",
            16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            1484,
            144,
            false,
            "Your on your own now!\n\nGood luck hunting the Wumpus!",
             16, TextAlignment.CENTER, Color.WHITE, null);

        System.out.println("SceneLevel2 init");
    }

}