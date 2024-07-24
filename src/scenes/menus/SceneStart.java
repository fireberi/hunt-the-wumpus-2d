package scenes.menus;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

import components.*;
import components.helpers.*;
import systems.*;

import objects.*;

import data.Tiles;

import scenes.SceneCherry;

import core.Constants;
import core.InputManager;

public final class SceneStart extends SceneCherry {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {

        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            Constants.HEIGHT / 2 - 20,
            true,
            "HUNT THE WUMPUS!",
            40, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            Constants.HEIGHT / 2 + 50,
            true,
            "Press C to play",
            16, TextAlignment.CENTER, Color.rgb(223, 223, 0), null);
        Objects.createTextActor(cherry,
            1,
            Constants.HEIGHT - 10,
            true,
            "Game, programming and art by Daniel C",
            10, TextAlignment.LEFT, Color.WHITE, null);
        Objects.createTextActor(cherry,
            1,
            Constants.HEIGHT - 6,
            true,
            "Made for Software Engineering class",
            10, TextAlignment.LEFT, Color.WHITE, null);
        Objects.createTextActor(cherry,
            1,
            Constants.HEIGHT - 2,
            true,
            "Programmed with Java, JavaFX and the Dominion ECS library",
            10, TextAlignment.LEFT, Color.WHITE, null);

        cherry.createEntity(
            new PositionComponent(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 15),
            new SpriteComponent(new ImageComponent("hunter", 48, 32), 0, "idle", true,
                new String[] {"idle"},
                new boolean[] {true},
                new double[] {0.75},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 0f, 16f, 16f, -7f, -9f),
                    },
                }
            ),
            new RenderLayerComponent((byte) 1)
        );

        final int __ = Tiles.__;
        final int W0 = Tiles.W0;
        final int W1 = Tiles.W1;
        final int W2 = Tiles.W2;
        final int W3 = Tiles.W3;
        final int W4 = Tiles.W4;
        final int W5 = Tiles.W5;
        final int W6 = Tiles.W6;
        final int W7 = Tiles.W7;
        final int W8 = Tiles.W8;
        final int WA = Tiles.WA;
        final int WB = Tiles.WB;
        final int WC = Tiles.WC;
        final int WD = Tiles.WD;

        cherry.createEntity(
            new PositionComponent(0, 0),
            new TilemapComponent(new int[][] {
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,WA,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,W7,WB,W4,W4},
                {W4,WA,W8,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W6,WB,W4},
                {WA,W8,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W6,WB},
                {W8,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W6},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {W1,W1,W1,W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0,W1,W1,W1},
                {W4,W4,W4,WC,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,WD,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
                {W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4},
            }),
            new RenderLayerComponent((byte) 0)
        );


        // schedule systems
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, 0, 0, images);

        updateScheduler.schedule(spriteSystem);
        updateScheduler.schedule(() -> {
            if (InputManager.inputs.get("confirm").justReleased()) {
                nextScene = "Introduction1";
            }
        });

        renderScheduler.schedule(renderSystem);

        System.out.println("Start init");
    }

}