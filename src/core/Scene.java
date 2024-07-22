package core;

import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Scene {

    public boolean active = false;
    public String nextScene = "";

    public void init(GraphicsContext ctx, HashMap<String, Image> images) {};
    public void update(double tickInterval) {};
    public void render(GraphicsContext ctx) {};
    public void shutDown() {};

}