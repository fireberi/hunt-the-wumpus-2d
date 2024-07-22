package components;

import java.util.HashMap;

import core.InputManager;
import core.Input;

public class PlayerControllerComponent {

    public HashMap<String, Input> inputs = InputManager.inputs;

    public PlayerControllerComponent() {}

}