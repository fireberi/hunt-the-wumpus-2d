package components;

public class PositionComponent {

    public float x;
    public float y;
    public boolean fixedPosition;

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
        this.fixedPosition = false;
    }

    public PositionComponent(float x, float y, boolean fixedPosition) {
        this.x = x;
        this.y = y;
        this.fixedPosition = fixedPosition;
    }

}