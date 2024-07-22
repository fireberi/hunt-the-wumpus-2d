package core;

import java.util.Arrays;

import javafx.scene.input.KeyCode;

public class Input {
    private boolean pressed = false;
    private boolean justPressed = false;
    private boolean justReleased = false;
    KeyCode[] keyData;

    public Input() {
        keyData = new KeyCode[0];
    }

    public Input(KeyCode... keys) {
        keyData = keys;
    }

    public boolean hasKey(KeyCode key) {
        return Arrays.asList(keyData).contains(key);
    }

    public boolean pressed() {
        return pressed;
    }

    public boolean justPressed() {
        return justPressed;
    }

    public boolean justReleased() {
        return justReleased;
    }

    public void press() {
        if (!pressed) {
            justPressed = true;
            justReleased = false;
            pressed = true;
        }
    }

    public void release() {
        if (pressed) {
            justReleased = true;
            justPressed = false;
            pressed = false;
        }
    }

    public void resetJustPressedAndJustReleased() {
        justPressed = false;
        justReleased = false;
    }
}