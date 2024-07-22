package components;

public class SpeedComponent {

    public float ax; // acceleration
    public float dx; // deceleration
    public float maxX;
    public float maxY;

    public float xMultiplier = 1;

    public SpeedComponent(float ax, float dx, float maxX, float maxY) {
        this.ax = ax;
        this.dx = dx;
        this.maxX = maxX;
        this.maxY = maxY;
    }

}