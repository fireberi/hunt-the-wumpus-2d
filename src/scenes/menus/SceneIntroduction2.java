package scenes.menus;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
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

public final class SceneIntroduction2 extends SceneCherry {

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {

        Objects.createTextActor(cherry, "The fate of the Almighty Kingdom is up to you. You are the land's most renowned\n\nfighter, warrior, hunter. You have traveled far and wide, conquering numerous\n\nhostile beasts. Your years of experience are... about 150 plus? 160 plus? You\n\ncan't really keep up with the decades now.", Constants.WIDTH / 2, Constants.HEIGHT / 2 - 20, 16, TextAlignment.CENTER, true, null);

        Objects.createTextActor(cherry, "Press C to continue", Constants.WIDTH / 2, Constants.HEIGHT - 16, 16, TextAlignment.CENTER, true, null);

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
                nextScene = "Introduction3";
            }
        });

        renderScheduler.schedule(renderSystem);

        System.out.println("Introduction2 init");
    }

}