package components.helpers;

public class Frame {

    public float x;
    public float y;
    public float w;
    public float h;
    // offset x, offset y
    public float ox;
    public float oy;
    public double time = 0;

    public Frame(float x, float y, float w, float h, float ox, float oy) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ox = ox;
        this.oy = oy;
    }

}