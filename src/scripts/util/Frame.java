package scripts.util;

public class Frame {

    public double x;
    public double y;
    public double w;
    public double h;
    public double ox;
    public double oy;
    public double time = 0;

    public Frame(double x, double y, double w, double h, double ox, double oy) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ox = ox;
        this.oy = oy;
    }

}