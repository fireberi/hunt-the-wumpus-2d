package scripts.core;

import java.util.HashMap;

import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

// import scripts.scenes
import scripts.scenes.test.*;
import scripts.scenes.levels.*;

class GameManager {

    private HashMap<String, Scene> sceneData = new HashMap<String, Scene>();
    private String sceneName = "";
    private HashMap<String, Image> images = new HashMap<String, Image>();

    void init() {
        sceneName = "Level1";
        sceneData.put("Test", new SceneTest());
        sceneData.put("Prototype", new ScenePrototype());
        sceneData.put("Level1", new SceneLevel1());

        //region load images
        images.put("hunter", new Image("assets/graphics/hunter.png", 0, 0, false, true));
        images.put("rusty", new Image("assets/graphics/rusty.png", 0, 0, false, true));
        //endregion

        System.out.println("GameManager init");
    }

    void update(double TICKINTERVAL, GraphicsContext ctx) {
        // TICKINTERVAL is in seconds

        Scene scene = sceneData.get(sceneName);

        if (scene.nextScene != "") {
            scene.active = false;
            sceneName = scene.nextScene;
            scene = sceneData.get(sceneName);
        }

        if (!scene.active) {
            scene.init(ctx, images);
            scene.active = true;
        }
        else {
            scene.update(TICKINTERVAL);
        }
    }

    void render(GraphicsContext ctx, Stage stage) {
        Scene scene = sceneData.get(sceneName);

        ctx.setFill(Color.rgb(31, 31, 31));
        ctx.fillRect(0, 0, stage.getWidth(), stage.getHeight());

        if (scene.active) {
            scene.render(ctx);
        }
    }

    void shutDown() {
        Scene scene = sceneData.get(sceneName);

        scene.shutDown();
    }

}