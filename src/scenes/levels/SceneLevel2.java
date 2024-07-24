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
            80,
            80,
            false,
            "You also have your trusty dusty bow, Dusty!\n\nPress X or K to cycle weapons",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            220,
            110,
            false,
            "Try killing this bat with Dusty\n\nHint: down arrow key or S\nchanges the direction of bow",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            360,
            96,
            false,
            "Watch out!\n\nLava does lots\n\nof damage!",
             16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            720,
            140,
            false,
            "Spiders shoot webs that poison you...\n\nGhouls slow you down :o",
            16, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            920,
            144,
            false,
            "Your on your own now!\n\nGood luck hunting the Wumpus!",
             16, TextAlignment.CENTER, Color.WHITE, null);

        System.out.println("SceneLevel2 init");
    }

}