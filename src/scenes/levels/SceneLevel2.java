package scenes.levels;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

import scenes.SceneBaseLevel;

import components.*;

import objects.*;

public final class SceneLevel2 extends SceneBaseLevel {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        loadLevel(ctx, images, "Level 2", LevelData.level2.playerSpawn, LevelData.level2.enemySpawns, LevelData.level2.nextLevel, LevelData.level2.tilemap);

        Objects.createTextActor(cherry, "You also have your trusty dusty bow, Dusty!\n\nPress X or K to cycle weapons", 80, 80, 16, TextAlignment.CENTER, false, null);
        Objects.createTextActor(cherry, "Try killing this bat with Dusty", 220, 110, 16, TextAlignment.CENTER, false, null);
        Objects.createTextActor(cherry, "Watch out!\n\nLava does lots\n\nof damage!", 360, 96, 16, TextAlignment.CENTER, false, null);
        Objects.createTextActor(cherry, "Spiders shoot webs that poison you...\n\nGhouls slow you down :o", 720, 140, 16, TextAlignment.CENTER, false, null);
        Objects.createTextActor(cherry, "Your on your own now!\n\nGood luck hunting the Wumpus!", 920, 144, 16, TextAlignment.CENTER, false, null);

        System.out.println("SceneLevel2 init");
    }

}