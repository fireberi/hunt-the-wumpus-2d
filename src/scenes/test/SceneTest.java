package scenes.test;

import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import core.Scene;
import core.Constants;
import core.InputManager;
import core.Input;

import util.Collision;

public class SceneTest extends Scene {

    float x = 10f;
    float y = 80f;
    float width = 20f;
    float height = 20f;

    float vx = 0f;
    float vy = 0f;
    float maxX = 4f;
    float maxY = 10f;

    float ax = 0.6f;
    float dx = 0.4f;

    float jumpForce = 5f;
    float smallJumpForce = 1.5f;

    float GRAVITY = 0.2f;

    boolean canJump = true;

    float targetX = 280f;
    float targetY = 100f;
    float targetSize = 10f;

    HashMap<String, Input> inputs = InputManager.inputs;

    @Override
    public void init(GraphicsContext ctx, HashMap<String, Image> images) {
        System.out.println("SceneTest init");
    }

    @Override
    public void update(double tickInterval) {
        vy += GRAVITY;
        if (vy > maxY) {
            vy = maxY;
        }

        if (inputs.get("right").pressed() && !inputs.get("left").pressed()) {
            vx += ax;
            if (vx > maxX) {
                vx = maxX;
            }
        }
        else if (!inputs.get("right").pressed() && inputs.get("left").pressed()) {
            vx -= ax;
            if (vx < -maxX) {
                vx = -maxX;
            }
        }
        else if (vx > 0) {
            vx -= dx;
            if (vx < 0) {
                vx = 0;
            }
        }
        else if (vx < 0) {
            vx += dx;
            if (vx > 0) {
                vx = 0;
            }
        }

        if (inputs.get("up").justPressed() && canJump) {
            vy = -jumpForce;
            canJump = false;
        }
        if (inputs.get("up").justReleased() && vy < -smallJumpForce && !canJump) {
            vy = -smallJumpForce;
        }

        x += vx;
        y += vy;

        if (y > 180 - height) {
            vy = 0;
            y = 180 - height;
            canJump = true;
        }

        if (x < 0) {
            vx = 0;
            x = 0;
        }
        else if (x > 320 - width) {
            vx = 0;
            x = 320 - width;
        }

        if (Collision.AABB(x, y, width, height, targetX, targetY, targetSize, targetSize)) {
            nextScene = "Prototype";
        }
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.setFill(Color.rgb(255, 255, 255));
        ctx.fillRect(x * Constants.VIEWPORT_SCALE, y * Constants.VIEWPORT_SCALE, width * Constants.VIEWPORT_SCALE, height * Constants.VIEWPORT_SCALE);
        ctx.fillRect(targetX * Constants.VIEWPORT_SCALE, targetY * Constants.VIEWPORT_SCALE, targetSize * Constants.VIEWPORT_SCALE, targetSize * Constants.VIEWPORT_SCALE);
    }

    public void shutDown() {
        System.out.println("SceneTest shutdown");
    }

}