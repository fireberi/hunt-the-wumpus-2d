package scripts.components;

import java.util.HashMap;

import scripts.core.InputManager;
import scripts.core.Input;

public class PlayerControllerComponent {

    public HashMap<String, Input> inputs = InputManager.inputs;

    public PlayerControllerComponent() {}

}