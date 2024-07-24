package scenes.menus;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
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

public final class SceneEndScreen extends SceneCherry {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {

        cherry.createEntity(
            new PositionComponent(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 7),
            new SpriteComponent(new ImageComponent("hunter", 48, 32), "victory", true,
                new String[] {"victory"},
                new boolean[] {true},
                new double[] {0.05},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(32f, 0f, 16f, 16f, -7f, -11f),
                        new Frame(32f, 0f, 16f, 16f, -7f, -13f),
                        new Frame(32f, 0f, 16f, 16f, -7f, -14f),
                        new Frame(32f, 0f, 16f, 16f, -7f, -13f),
                        new Frame(32f, 0f, 16f, 16f, -7f, -11f),
                        new Frame(16f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                    },
                }
            ),
            new RenderLayerComponent((byte) 1)
        );

        cherry.createEntity(
            new PositionComponent(Constants.WIDTH / 2 + 9, Constants.HEIGHT / 2 + 10),
            new SpriteComponent(new ImageComponent("rusty", 48, 32), "victory", true,
                new String[] {"victory"},
                new boolean[] {true},
                new double[] {0.05},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -11f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -11f),
                        new Frame(0f, 16f, 16f, 16f, -18f, -19f),
                        new Frame(0f, 16f, 16f, 16f, -18f, -21f),
                        new Frame(0f, 16f, 16f, 16f, -18f, -22f),
                        new Frame(0f, 16f, 16f, 16f, -18f, -21f),
                        new Frame(0f, 16f, 16f, 16f, -18f, -19f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -11f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -11f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                    },
                }
            ),
            new RenderLayerComponent((byte) 2)
        );

        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            Constants.HEIGHT / 2 - 48,
            true,
             "Congratulations!",
            FontWeight.BOLD, 32, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            Constants.HEIGHT / 2 - 32,
            true,
            "You beat the game! :D",
            FontWeight.NORMAL, 24, TextAlignment.CENTER, Color.WHITE, null);
        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            Constants.HEIGHT - 16,
            true,
            "Press C to continue",
            FontWeight.BOLD, 16, TextAlignment.CENTER, Color.rgb(223, 223, 0), null);

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
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0,W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W3,W5,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0,W1,W1,WD,WC,W1,W1,W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0,W1,WD,W4,W4,W4,W4,W4,W4,WC,W1,W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,W0,W1,WD,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,WC,W1,W2,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,W0,W1,WD,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,WC,W1,W2,__,__,__,__,__,__,__,__,__,__},
                {W2,__,__,__,__,__,__,__,__,W0,WD,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,WC,W2,__,__,__,__,__,__,__,__,W0},
                {WC,W2,__,__,__,__,__,__,W0,WD,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,WC,W2,__,__,__,__,__,__,W0,WD},
                {W4,WC,W2,__,__,__,__,W0,WD,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,WC,W2,__,__,__,__,W0,WD,W4},
                {W4,W4,WC,W1,W1,W1,W1,WD,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,W4,WC,W1,W1,W1,W1,WD,W4,W4},
            }),
            new RenderLayerComponent((byte) 0)
        );


        // schedule systems
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, 0, 0, images);

        updateScheduler.schedule(spriteSystem);
        updateScheduler.schedule(() -> {
            if (InputManager.inputs.get("confirm").justReleased()) {
                nextScene = "Start";
            }
        });

        renderScheduler.schedule(renderSystem);

        System.out.println("EndScreen init");
    }

}