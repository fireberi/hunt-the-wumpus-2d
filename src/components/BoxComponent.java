package components;

public class BoxComponent {

    public float x;
    public float y;
    public float w;
    public float h;

    public BoxComponent(float w, float h) {
        this.x = -w / 2;
        this.y = -h / 2;
        this.w = w;
        this.h = h;
    }

    public BoxComponent(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

}