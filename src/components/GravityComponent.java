package components;

import core.Constants;

public class GravityComponent {

    public float gravity;

    public GravityComponent() {
        this.gravity = Constants.GRAVITY;
    }

    public GravityComponent(float gravity) {
        this.gravity = gravity;
    }

}