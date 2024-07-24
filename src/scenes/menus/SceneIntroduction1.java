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

public final class SceneIntroduction1 extends SceneCherry {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {

        Objects.createTextActor(cherry,
            Constants.WIDTH / 2,
            Constants.HEIGHT / 2 - 40,
            true,
            "For many centuries, the legendary Wumpus has terrorised the citizens of the\n\nAlmighty Kingdom. On the last day of every month, the Wumpus comes out to hunt,\n\nand gobbles up any person it sees. The King has already put in vast amounts of\n\nresources and effort on troops to search the wilderness and destroy the Wumpus,\n\nand each time little troops arrive back. Often, they remark that they\n\nencountered bats, giant worms, weird ghosts that slow you down... the list goes\n\non (at least I think it does).",
            FontWeight.NORMAL, 16, TextAlignment.CENTER, Color.WHITE, null);

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
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__},
                {W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0},
                {WC,W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0,WD},
                {W4,WC,W2,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,W0,WD,W4},
                {W4,W4,WC,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,W1,WD,W4,W4},
            }),
            new RenderLayerComponent((byte) 0)
        );


        // schedule systems
        SpriteSystem spriteSystem = new SpriteSystem(cherry, state);

        RenderSystem renderSystem = new RenderSystem(cherry, state, ctx, 0, 0, images);

        updateScheduler.schedule(spriteSystem);
        updateScheduler.schedule(() -> {
            if (InputManager.inputs.get("confirm").justReleased()) {
                nextScene = "Introduction2";
            }
        });

        renderScheduler.schedule(renderSystem);

        System.out.println("Introduction1 init");
    }

}