package components;

public class VelocityComponent {

    public float x;
    public float y;
    public boolean facingRight;
    public boolean facingDown = true;
    public boolean gravity;

    public VelocityComponent(float x, float y, boolean facingRight) {
        this.x = x;
        this.y = y;
        this.facingRight = facingRight;
        this.gravity = true;
    }

    public VelocityComponent(float x, float y, boolean facingRight, boolean gravity) {
        this.x = x;
        this.y = y;
        this.facingRight = facingRight;
        this.gravity = gravity;
    }

}