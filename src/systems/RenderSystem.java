package systems;

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
import dev.dominion.ecs.api.Entity;

import components.PositionComponent;
import components.GraphicsComponent;
import components.GraphicsListComponent;
import components.TilemapComponent;
import components.FocusComponent;
import components.ImageComponent;
import components.SpriteComponent;
import components.TextComponent;
import components.RenderLayerComponent;
import components.helpers.Frame;
import components.helpers.TextLogic;

import data.Tiles;

import core.Constants;
import core.State;
import util.GameMath;

public class RenderSystem implements Runnable {

    private Dominion cherry;
    private GraphicsContext ctx;
    private State state;
    private PositionComponent camPos = new PositionComponent(0, 0);
    private PositionComponent targetCamPos = new PositionComponent(0, 0);
    private HashMap<String, Image> images;

    private HashMap<Byte, ArrayList<PositionComponent>> positionLayers = new HashMap<Byte, ArrayList<PositionComponent>>();
    private HashMap<Byte, ArrayList<TilemapComponent>> tilemapLayers = new HashMap<Byte, ArrayList<TilemapComponent>>();
    private HashMap<Byte, ArrayList<SpriteComponent>> spriteLayers = new HashMap<Byte, ArrayList<SpriteComponent>>();
    private HashMap<Byte, ArrayList<ImageComponent>> imageLayers = new HashMap<Byte, ArrayList<ImageComponent>>();
    private HashMap<Byte, ArrayList<GraphicsListComponent>> graphicsListLayers = new HashMap<Byte, ArrayList<GraphicsListComponent>>();
    private HashMap<Byte, ArrayList<TextComponent>> textLayers = new HashMap<Byte, ArrayList<TextComponent>>();
    private byte maxLayers = 4;

    // layer 0: map
    // layer 1: entities
    // layer 2: weapons
    // layer 3: text

    private int cameraDecay = 10;

    public RenderSystem(Dominion cherry, State state, GraphicsContext ctx, float cameraX, float cameraY, HashMap <String, Image> images) {
        this.cherry = cherry;
        this.state = state;
        this.ctx = ctx;
        this.camPos.x = cameraX;
        this.camPos.y = cameraY;
        this.images = images;
        for (byte lyr = 0; lyr < maxLayers; lyr++) {
            positionLayers.put(lyr, new ArrayList<PositionComponent>());
            tilemapLayers.put(lyr, new ArrayList<TilemapComponent>());
            spriteLayers.put(lyr, new ArrayList<SpriteComponent>());
            imageLayers.put(lyr, new ArrayList<ImageComponent>());
            graphicsListLayers.put(lyr, new ArrayList<GraphicsListComponent>());
            textLayers.put(lyr, new ArrayList<TextComponent>());
        }
    }

    public void run() {
        // drawGrid(ctx, 40, 22, Constants.SCALE);

        //region calculate camera position
        PositionComponent totalPos = new PositionComponent(0, 0);

        // not using int count = 0; because local variables cannot be mutated in a lambda
        var vars = new Object() {
            int count = 0;
            boolean focused = false;
        };
        cherry.findEntitiesWith(FocusComponent.class, PositionComponent.class).stream().forEach(e -> {
            FocusComponent fcs = e.comp1();
            PositionComponent pos = e.comp2();

            if (!fcs.enabled) {
                return;
            }

            // get the average of the positions
            totalPos.x += pos.x + fcs.x;
            totalPos.y += pos.y + fcs.y;
            vars.count++;
            vars.focused = true;
        });

        if (vars.focused) {
            targetCamPos.x = (totalPos.x / vars.count) - (Constants.WIDTH / 2);
            targetCamPos.y = (totalPos.y / vars.count) - (Constants.HEIGHT / 2);
        }

        camPos.x = (float) GameMath.expDecay(camPos.x, targetCamPos.x, cameraDecay, (float) state.delta);
        camPos.y = (float) GameMath.expDecay(camPos.y, targetCamPos.y, cameraDecay, (float) state.delta);
        //endregion

        //region sort render layers
        // reset the layers
        for (byte lyr = 0; lyr < maxLayers; lyr++) {
            positionLayers.get(lyr).clear();
            tilemapLayers.get(lyr).clear();
            spriteLayers.get(lyr).clear();
            imageLayers.get(lyr).clear();
            graphicsListLayers.get(lyr).clear();
            textLayers.get(lyr).clear();
        }
        cherry.findEntitiesWith(RenderLayerComponent.class).stream().forEach(e -> {
            Entity entity = e.entity();
            byte lyr = e.comp().layer;
            PositionComponent pos = entity.get(PositionComponent.class);
            TilemapComponent map = entity.get(TilemapComponent.class);
            SpriteComponent spr = entity.get(SpriteComponent.class);
            ImageComponent img = entity.get(ImageComponent.class);
            GraphicsListComponent gfl = entity.get(GraphicsListComponent.class);
            TextComponent txt = entity.get(TextComponent.class);

            if (pos != null || map != null) {
                positionLayers.get(lyr).add(pos);
                tilemapLayers.get(lyr).add(map);
                spriteLayers.get(lyr).add(spr);
                imageLayers.get(lyr).add(img);
                graphicsListLayers.get(lyr).add(gfl);
                textLayers.get(lyr).add(txt);
            }
        });
        //endregion

        //region render components
        for (byte lyr = 0; lyr < maxLayers; lyr++) {
            int i = 0;
            for (PositionComponent pos : positionLayers.get(lyr)) {
                TilemapComponent map = tilemapLayers.get(lyr).get(i);
                SpriteComponent spr = spriteLayers.get(lyr).get(i);
                ImageComponent img = imageLayers.get(lyr).get(i);
                GraphicsListComponent gfl = graphicsListLayers.get(lyr).get(i);
                TextComponent txt = textLayers.get(lyr).get(i);
                int s = Constants.SCALE;
                int vs = Constants.VIEWPORT_SCALE;

                float cameraX = camPos.x;
                float cameraY = camPos.y;
                if (pos.fixedPosition) {
                    cameraX = 0;
                    cameraY = 0;
                }

                if (map != null) {
                    // render tilemap
                    int[][] grid = map.grid;

                    Image image = images.get("mapSpritesheet");
                    int imgX = 0;
                    int imgY = 0;
                    int imgW = 8;
                    int imgH = 8;

                    int y = 0;
                    for (int[] row : grid) {
                        int x = 0;
                        for (int tile : row) {
                            boolean draw = true;
                            boolean drawImage = false;
                            if (tile == Tiles.BORDER) {
                                ctx.setFill(Color.rgb(207, 207, 207));
                            }
                            else if (tile == Tiles.EMPTY) {
                                imgX = 8;
                                imgY = 8;
                                drawImage = true;
                            }
                            else if (tile == Tiles.W0) {
                                imgX = 0;
                                imgY = 0;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W1) {
                                imgX = 8;
                                imgY = 0;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W2) {
                                imgX = 16;
                                imgY = 0;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W3) {
                                imgX = 0;
                                imgY = 8;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W5) {
                                imgX = 16;
                                imgY = 8;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W6) {
                                imgX = 0;
                                imgY = 16;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W7) {
                                imgX = 8;
                                imgY = 16;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.W8) {
                                imgX = 16;
                                imgY = 16;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.WA) {
                                imgX = 24;
                                imgY = 0;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.WB) {
                                imgX = 32;
                                imgY = 0;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.WC) {
                                imgX = 24;
                                imgY = 8;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.WD) {
                                imgX = 32;
                                imgY = 8;
                                drawImage = true;
                                ctx.setFill(Color.rgb(255, 63, 143));
                            }
                            else if (tile == Tiles.LAVA_TOP) {
                                imgX = 40;
                                imgY = 8;
                                drawImage = true;
                                ctx.setFill(Color.rgb(111, 111, 111));
                            }
                            else if (tile == Tiles.LAVA_BODY) {
                                imgX = 40;
                                imgY = 16;
                                drawImage = true;
                                ctx.setFill(Color.rgb(111, 111, 111));
                            }
                            else if (tile == Tiles.SPIKE) {
                                imgX = 40;
                                imgY = 0;
                                drawImage = true;
                                ctx.setFill(Color.rgb(128, 128, 128));
                            }
                            else if (tile == Tiles.NEXT_LEVEL_TOP_LEFT) {
                                imgX = 24;
                                imgY = 16;
                                drawImage = true;
                                ctx.setFill(Color.rgb(127, 207, 239));
                            }
                            else if (tile == Tiles.NEXT_LEVEL_TOP_RIGHT) {
                                imgX = 32;
                                imgY = 16;
                                drawImage = true;
                                ctx.setFill(Color.rgb(127, 207, 239));
                            }
                            else if (tile == Tiles.NEXT_LEVEL_BOTTOM_LEFT) {
                                imgX = 24;
                                imgY = 24;
                                drawImage = true;
                                ctx.setFill(Color.rgb(127, 207, 239));
                            }
                            else if (tile == Tiles.NEXT_LEVEL_BOTTOM_RIGHT) {
                                imgX = 32;
                                imgY = 24;
                                drawImage = true;
                                ctx.setFill(Color.rgb(127, 207, 239));
                            }
                            else {
                                draw = false;
                            }
                            int renderX = (int) Math.floor((x + pos.x) * s - cameraX * vs);
                            int renderY = (int) Math.floor((y + pos.y) * s - cameraY * vs);
                            if (draw && !drawImage) {
                                ctx.fillRect(renderX, renderY, s, s);
                            }
                            if (drawImage) {
                                ctx.drawImage(image, imgX, imgY, imgW, imgH,
                                    renderX, renderY,
                                    imgW * vs, imgH * vs);
                            }
                            x += 1;
                        }
                        y += 1;
                    }
                }
                if (spr != null && spr.image.active) {
                    // render sprite
                    Image image = images.get(spr.image.imageName);
                    Frame f = spr.frame();

                    if (f != null) {
                        if (!spr.image.flip) {
                            ctx.drawImage(image, f.x, f.y, f.w, f.h,
                                (pos.x + f.ox - cameraX) * vs,
                                (pos.y + f.oy - cameraY) * vs,
                                f.w * vs, f.h * vs);
                        }
                        else {
                            ctx.drawImage(image, f.x, f.y, f.w, f.h,
                                (pos.x - f.ox - cameraX) * vs,
                                (pos.y + f.oy - cameraY) * vs,
                                -f.w * vs, f.h * vs);
                        }
                    }
                }
                if (img != null && img.active) {
                    // render image
                    Image image = images.get(img.imageName);
                    if (!img.flip) {
                        ctx.drawImage(image,
                            img.sx, img.sy, img.sw, img.sh,
                            (pos.x + img.x - cameraX) * vs,
                            (pos.y + img.y - cameraY) * vs,
                            img.w * vs, img.h * vs);
                    }
                    else {
                        ctx.drawImage(image,
                            img.sx, img.sy, img.sw, img.sh,
                            (pos.x - img.x - cameraX) * vs,
                            (pos.y + img.y - cameraY) * vs,
                            -img.w * vs, img.h * vs);
                    }
                }
                if (gfl != null) {
                    // render graphics
                    for (GraphicsComponent gfx : gfl.list) {
                        drawRect(pos, gfx, gfx.fill, cameraX, cameraY);
                    }
                }
                if (txt != null) {
                    if (txt.textLogic != null) {
                        txt.textLogic.update(cherry, txt);
                    }
                    ctx.setFill(Color.WHITE);
                    ctx.setFont(Font.font(txt.font.getFamily(), txt.font.getSize() * Constants.VIEWPORT_SCALE / 3));
                    ctx.setTextAlign(txt.alignment);
                    ctx.fillText(txt.text, (pos.x - cameraX) * vs, (pos.y - cameraY) * vs);
                }
                i++;
            }
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

    private void drawRect(PositionComponent pos, GraphicsComponent gfx, boolean fill, float cameraX, float cameraY) {
        ctx.setFill(gfx.color);
        ctx.setStroke(gfx.color);
        if (fill) {
            ctx.fillRect(Math.floor((pos.x + gfx.x - cameraX) * Constants.VIEWPORT_SCALE), Math.floor((pos.y + gfx.y - cameraY) * Constants.VIEWPORT_SCALE), gfx.w * Constants.VIEWPORT_SCALE, gfx.h * Constants.VIEWPORT_SCALE);
        }
        else {
            ctx.setLineWidth(1 * Constants.VIEWPORT_SCALE);
            ctx.strokeRect(Math.floor((pos.x + gfx.x - camPos.x + 0.5) * Constants.VIEWPORT_SCALE), Math.floor((pos.y + gfx.y - camPos.y + 0.5) * Constants.VIEWPORT_SCALE), (gfx.w - 1) * Constants.VIEWPORT_SCALE, (gfx.h - 1) * Constants.VIEWPORT_SCALE);
        }
    }

}