package core;

import java.util.HashMap;

import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import scenes.test.*;
import scenes.levels.*;
import scenes.menus.*;

class GameManager {

    private HashMap<String, Scene> sceneData = new HashMap<String, Scene>();
    private String sceneName = "";
    private HashMap<String, Image> images = new HashMap<String, Image>();

    void init() {
        sceneName = "Start";
        sceneData.put("Test", new SceneTest());
        sceneData.put("Prototype", new ScenePrototype());
        sceneData.put("Start", new SceneStart());
        sceneData.put("Introduction1", new SceneIntroduction1());
        sceneData.put("Introduction2", new SceneIntroduction2());
        sceneData.put("Introduction3", new SceneIntroduction3());
        sceneData.put("Level1", new SceneLevel1());
        sceneData.put("Level2", new SceneLevel2());
        sceneData.put("Level3", new SceneLevel3());
        sceneData.put("EndScreen", new SceneEndScreen());

        //region load images
        images.put("mapSpritesheet", new Image("graphics/map.png", 0, 0, false, true));
        images.put("hunter", new Image("graphics/hunter.png", 0, 0, false, true));
        images.put("superWorm", new Image("graphics/super_worm.png", 0, 0, false, true));
        images.put("superBat", new Image("graphics/super_bat.png", 0, 0, false, true));
        images.put("superSpider", new Image("graphics/super_spider.png", 0, 0, false, true));
        images.put("ghoul", new Image("graphics/ghoul.png", 0, 0, false, true));
        images.put("rusty", new Image("graphics/rusty.png", 0, 0, false, true));
        images.put("dusty", new Image("graphics/dusty.png", 0, 0, false, true));
        images.put("arrow", new Image("graphics/arrow.png", 0, 0, false, true));
        images.put("spiderweb", new Image("graphics/spiderweb.png", 0, 0, false, true));
        //endregion

        System.out.println("GameManager init");
    }

    void update(double TICKINTERVAL) {
        // TICKINTERVAL is in seconds

        Scene scene = sceneData.get(sceneName);

        if (scene.active) {
            scene.update(TICKINTERVAL);
        }
    }

    void render(GraphicsContext ctx, Stage stage) {
        Scene scene = sceneData.get(sceneName);

        ctx.setFill(Color.rgb(15, 15, 31));
        ctx.fillRect(0, 0, stage.getWidth(), stage.getHeight());

        if (scene.active) {
            scene.render(ctx);
        }
    }

    void checkNextScene(GraphicsContext ctx) {
        Scene scene = sceneData.get(sceneName);

        if (scene.nextScene != "") {
            scene.active = false;
            scene.shutDown();
            sceneName = scene.nextScene;
            scene.nextScene = "";
            scene = sceneData.get(sceneName);
        }

        if (!scene.active) {
            scene.init(ctx, images);
            scene.active = true;
        }
    }

    void shutDown() {
        Scene scene = sceneData.get(sceneName);

        scene.shutDown();
        System.out.println("GameManager shut down");
    }

}