package components;

import java.util.HashMap;

import core.Input;

public class InputComponent {

    public HashMap<String, Input> inputs;

    public InputComponent(HashMap<String, Input> inputs) {
        this.inputs = inputs;
    }

    public void releaseKeys() {
        for (HashMap.Entry<String, Input> entry : inputs.entrySet()) {
            entry.getValue().release();
        }
    }

}