package scripts.systems;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;

import dev.dominion.ecs.api.Dominion;

import scripts.components.PositionComponent;
import scripts.components.GraphicsComponent;
import scripts.components.GraphicsListComponent;
import scripts.components.HitboxGraphicsListComponent;
import scripts.components.HurtboxGraphicsListComponent;
import scripts.components.TilemapComponent;
import scripts.components.FocusComponent;
import scripts.components.ImageComponent;
import scripts.components.SpriteComponent;

import scripts.core.Constants;
import scripts.core.State;
import scripts.util.GameMath;
import scripts.util.Frame;

public class RenderSystem implements Runnable {

    private Dominion cherry;
    private GraphicsContext ctx;
    private State state;
    private PositionComponent camPos = new PositionComponent(0, 0);
    private HashMap<String, Image> images;
    private ArrayList<SpriteComponent> spriteLayer0 = new ArrayList<SpriteComponent>();
    private ArrayList<SpriteComponent> spriteLayer1 = new ArrayList<SpriteComponent>();
    private ArrayList<SpriteComponent> spriteLayer2 = new ArrayList<SpriteComponent>();
    private ArrayList<SpriteComponent> spriteLayer3 = new ArrayList<SpriteComponent>();
    private ArrayList<PositionComponent> spritePosLayer0 = new ArrayList<PositionComponent>();
    private ArrayList<PositionComponent> spritePosLayer1 = new ArrayList<PositionComponent>();
    private ArrayList<PositionComponent> spritePosLayer2 = new ArrayList<PositionComponent>();
    private ArrayList<PositionComponent> spritePosLayer3 = new ArrayList<PositionComponent>();
    private int decay = 10;

    public RenderSystem(Dominion cherry, State state, GraphicsContext ctx, float cameraX, float cameraY, HashMap <String, Image> images) {
        this.cherry = cherry;
        this.state = state;
        this.ctx = ctx;
        this.camPos.x = cameraX;
        this.camPos.y = cameraY;
        this.images = images;
    }

    public void run() {
        // drawGrid(ctx, 40, 22, Constants.SCALE);

        //region calculate camera position
        PositionComponent avgPos = new PositionComponent(0, 0);

        // not using int count = 0; because local variables cannot be mutated in a lambda
        var count = new Object() {int count = 0;};
        cherry.findEntitiesWith(FocusComponent.class, PositionComponent.class).stream().forEach(e -> {
            FocusComponent fcs = e.comp1();
            PositionComponent pos = e.comp2();

            if (!fcs.enabled) {
                return;
            }

            // get the average of the positions
            avgPos.x += pos.x;
            avgPos.y += pos.y;
            count.count++;
        });

        avgPos.x /= count.count;
        avgPos.y /= count.count;

        float newX = avgPos.x - (Constants.WIDTH / 2);
        float newY = avgPos.y - (Constants.HEIGHT / 2);

        camPos.x = (float) GameMath.expDecay(camPos.x, newX, decay, (float) state.delta);
        camPos.y = (float) GameMath.expDecay(camPos.y, newY, decay, (float) state.delta);
        //endregion

        //region tile rendering
        cherry.findEntitiesWith(TilemapComponent.class).stream().forEach(e -> {
            TilemapComponent map = e.comp();
            int[][] grid = map.grid;

            int y = 0;
            for (int[] row : grid) {
                int x = 0;
                for (int tile : row) {
                    boolean draw = false;
                    if (tile == 1) {
                        ctx.setFill(Color.rgb(207, 207, 207));
                        draw = true;
                    }
                    else if (tile == 2) {
                        ctx.setFill(Color.rgb(255, 63, 143));
                        draw = true;
                    }
                    else if (tile == 3) {
                        ctx.setFill(Color.rgb(191, 0, 0));
                        draw = true;
                    }
                    if (draw) {
                        ctx.fillRect(Math.floor(x * Constants.SCALE - camPos.x * Constants.VIEWPORT_SCALE), Math.floor(y * Constants.SCALE - camPos.y * Constants.VIEWPORT_SCALE), Constants.SCALE, Constants.SCALE);
                    }
                    x += 1;
                }
                y += 1;
            }
        });
        cherry.findEntitiesWith(PositionComponent.class, GraphicsComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            GraphicsComponent gfx = e.comp2();
            drawRect(pos, gfx, gfx.fill);
        });
        cherry.findEntitiesWith(PositionComponent.class, GraphicsListComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            GraphicsListComponent gfl = e.comp2();
            for (GraphicsComponent gfx : gfl.list) {
                drawRect(pos, gfx, gfx.fill);
            }
        });
        cherry.findEntitiesWith(PositionComponent.class, HitboxGraphicsListComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            HitboxGraphicsListComponent hitgfl = e.comp2();
            for (GraphicsComponent gfx : hitgfl.list) {
                drawRect(pos, gfx, gfx.fill);
            }
        });
        cherry.findEntitiesWith(PositionComponent.class, HurtboxGraphicsListComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            HurtboxGraphicsListComponent hrtgfl = e.comp2();
            for (GraphicsComponent gfx : hrtgfl.list) {
                drawRect(pos, gfx, gfx.fill);
            }
        });
        //endregion

        //region image rendering
        cherry.findEntitiesWith(ImageComponent.class, PositionComponent.class).stream().forEach(e -> {
            ImageComponent img = e.comp1();
            PositionComponent pos = e.comp2();
            Image i = images.get(img.imageName);

            ctx.drawImage(i, (pos.x + img.x - camPos.x) * Constants.VIEWPORT_SCALE, (pos.y + img.y - camPos.y) * Constants.VIEWPORT_SCALE, img.w * Constants.VIEWPORT_SCALE, img.h * Constants.VIEWPORT_SCALE);
        });
        //endregion

        //region sprite animation rendering
        cherry.findEntitiesWith(SpriteComponent.class, PositionComponent.class).stream().forEach(e -> {
            // cache the sprites
            SpriteComponent spr = e.comp1();
            if (!spr.hasCached) {
                PositionComponent pos = e.comp2();
                if (spr.layer == 0) {
                    spriteLayer0.add(spr);
                    spritePosLayer0.add(pos);
                }
                else if (spr.layer == 1) {
                    spriteLayer1.add(spr);
                    spritePosLayer1.add(pos);
                }
                else if (spr.layer == 2) {
                    spriteLayer2.add(spr);
                    spritePosLayer2.add(pos);
                }
                else if (spr.layer == 3) {
                    spriteLayer3.add(spr);
                    spritePosLayer3.add(pos);
                }
                else {
                    System.out.println("invalid layer for SpriteComponent");
                }
                spr.hasCached = true;
            }
        });
        // render the sprites
        int idx = 0;
        for (SpriteComponent spr : spriteLayer0) {
            renderSprite(spr, spr.image, spritePosLayer0.get(idx));
            idx += 1;
        }
        idx = 0;
        for (SpriteComponent spr : spriteLayer1) {
            renderSprite(spr, spr.image, spritePosLayer1.get(idx));
            idx += 1;
        }
        idx = 0;
        for (SpriteComponent spr : spriteLayer2) {
            renderSprite(spr, spr.image, spritePosLayer2.get(idx));
            idx += 1;
        }
        idx = 0;
        for (SpriteComponent spr : spriteLayer3) {
            renderSprite(spr, spr.image, spritePosLayer3.get(idx));
            idx += 1;
        }
        //endregion
    }

    private void drawGrid(GraphicsContext ctx, int w, int h, int size) {
        boolean draw = false;
        for (int y = 0; y < h; y++) {
            draw = y % 2 == 0;
            for (int x = 0; x < w; x++) {
                if (draw) {
                    ctx.setFill(Color.rgb(31, 31, 31));
                    ctx.fillRect(x * size, y * size, size, size);
                }

                ctx.setFill(Color.rgb(159, 159, 159));
                ctx.setFont(Font.font("PT Mono", FontWeight.BOLD, 8));
                ctx.setTextAlign(TextAlignment.CENTER);
                ctx.fillText("" + x, (x * size) + (size / 2), (y * size) + ((size * 2) / 5));
                ctx.fillText("" + y, (x * size) + (size / 2), (y * size) + ((size * 4) / 5));
                draw = !draw;
            }
        }
    }

    private void drawRect(PositionComponent pos, GraphicsComponent gfx, boolean fill) {
        ctx.setFill(gfx.color);
        ctx.setStroke(gfx.color);
        if (fill) {
            ctx.fillRect(Math.floor((pos.x + gfx.x - camPos.x) * Constants.VIEWPORT_SCALE), Math.floor((pos.y + gfx.y - camPos.y) * Constants.VIEWPORT_SCALE), gfx.w * Constants.VIEWPORT_SCALE, gfx.h * Constants.VIEWPORT_SCALE);
        }
        else {
            ctx.setLineWidth(1 * Constants.VIEWPORT_SCALE);
            ctx.strokeRect(Math.floor((pos.x + gfx.x - camPos.x + 0.5) * Constants.VIEWPORT_SCALE), Math.floor((pos.y + gfx.y - camPos.y + 0.5) * Constants.VIEWPORT_SCALE), (gfx.w - 1) * Constants.VIEWPORT_SCALE, (gfx.h - 1) * Constants.VIEWPORT_SCALE);
        }
    }

    private void renderSprite(SpriteComponent spr, ImageComponent img, PositionComponent pos) {
        Image i = images.get(img.imageName);
        Frame f = spr.frame();
        int s = Constants.VIEWPORT_SCALE;

        if (!img.flip) {
            ctx.drawImage(i, f.x, f.y, f.w, f.h,
                (pos.x + f.ox - camPos.x) * s,
                (pos.y + f.oy - camPos.y) * s,
                f.w * s, f.h * s);
        }
        else {
            ctx.drawImage(i, f.x, f.y, f.w, f.h,
                (pos.x - f.ox - camPos.x) * s,
                (pos.y + f.oy - camPos.y) * s,
                -f.w * s, f.h * s);
        }
    }

}