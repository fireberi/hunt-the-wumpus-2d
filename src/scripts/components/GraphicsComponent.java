package scripts.components;

import javafx.scene.paint.Color;

public class GraphicsComponent {

    public float x;
    public float y;
    public float w;
    public float h;
    public Color color;
    public boolean fill;

    public GraphicsComponent(float w, float h, Color color, boolean fill) {
        this.x = -w / 2;
        this.y = -h / 2;
        this.w = w;
        this.h = h;
        this.color = color;
        this.fill = fill;
    }

    public GraphicsComponent(float x, float y, float w, float h, Color color, boolean fill) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.fill = fill;
    }

    public GraphicsComponent(float w, float h, String boxType, boolean fill) {
        this.x = -w / 2;
        this.y = -h / 2;
        this.w = w;
        this.h = h;
        if (boxType == "hitbox") {
            this.color = Color.rgb(255, 239, 0, 0.8);
        }
        else if (boxType == "hurtbox") {
            this.color = Color.rgb(223, 0, 31, 0.8);
        }
        else {
            throw new IllegalArgumentException("GraphicsComponent param boxType was invalid");
        }
        this.fill = fill;
    }

    public GraphicsComponent(float x, float y, float w, float h, String boxType, boolean fill) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        if (boxType == "hitbox") {
            this.color = Color.rgb(255, 239, 0, 0.8);
        }
        else if (boxType == "hurtbox") {
            this.color = Color.rgb(223, 0, 31, 0.8);
        }
        else {
            throw new IllegalArgumentException("GraphicsComponent param boxType was invalid");
        }
        this.fill = fill;
    }

}