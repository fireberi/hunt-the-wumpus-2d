package core;

import java.util.HashMap;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

public class InputManager {

    public static HashMap<String, Input> inputs = new HashMap<String, Input>();
    private static char devPrefix = '_';

    static {
        inputs.put("right", new Input(KeyCode.D, KeyCode.RIGHT));
        inputs.put("left", new Input(KeyCode.A, KeyCode.LEFT));
        inputs.put("up", new Input(KeyCode.W, KeyCode.UP));
        inputs.put("down", new Input(KeyCode.S, KeyCode.DOWN));
        inputs.put("confirm", new Input(KeyCode.J, KeyCode.C));
        inputs.put("cancel", new Input(KeyCode.K, KeyCode.X));
        inputs.put("alt", new Input(KeyCode.L, KeyCode.Z));

        // dev inputs
        inputs.put("_freeze", new Input(KeyCode.BACK_QUOTE));
        inputs.put("_advance", new Input(KeyCode.DIGIT1));
        inputs.put("_retreat", new Input(KeyCode.DIGIT2));
        inputs.put("_dump", new Input(KeyCode.DIGIT3));
    }

    public static void handleKeyPressed(KeyEvent e) {
        KeyCode key = e.getCode();

        for (Input input : inputs.values()) {
            if (input.hasKey(key)) {
                input.press();
            }
        }
    }

    public static void handleKeyReleased(KeyEvent e) {
        KeyCode key = e.getCode();

        for (Input input : inputs.values()) {
            if (input.hasKey(key)) {
                input.release();
            }
        }
    }

    public static void cleanUpInputs() {
        for (HashMap.Entry<String, Input> entry : inputs.entrySet()) {
            if (entry.getKey().charAt(0) != devPrefix) {
                entry.getValue().resetJustPressedAndJustReleased();
            }
        }
    }

    public static void cleanUpDevInputs() {
        for (HashMap.Entry<String, Input> entry : inputs.entrySet()) {
            if (entry.getKey().charAt(0) == devPrefix) {
                entry.getValue().resetJustPressedAndJustReleased();
            }
        }
    }

}