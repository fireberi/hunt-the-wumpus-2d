package systems;

import java.util.HashMap;

import dev.dominion.ecs.api.Dominion;

import components.InputComponent;

import core.Input;
import core.State;

public class InputSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public InputSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(InputComponent.class).stream().forEach(e -> {
            HashMap<String, Input> inputs = e.comp().inputs;
            for (HashMap.Entry<String, Input> entry : inputs.entrySet()) {
                entry.getValue().resetJustPressedAndJustReleased();
            }
        });
    }

}