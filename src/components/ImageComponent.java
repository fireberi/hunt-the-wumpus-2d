package components;

public class ImageComponent {

    public String imageName;
    public float x;
    public float y;
    public float w;
    public float h;
    public float sx;
    public float sy;
    public float sw;
    public float sh;
    public boolean flip = false;
    public boolean active = true;

    public ImageComponent(String imageName, float w, float h) {
        this.imageName = imageName;
        this.x = 0;
        this.y = 0;
        this.w = w;
        this.h = h;
        this.sx = 0;
        this.sy = 0;
        this.sw = w;
        this.sh = h;
    }

    public ImageComponent(String imageName, float w, float h, boolean flip) {
        this.imageName = imageName;
        this.x = 0;
        this.y = 0;
        this.w = w;
        this.h = h;
        this.sx = 0;
        this.sy = 0;
        this.sw = w;
        this.sh = h;
        this.flip = flip;
    }

    public ImageComponent(String imageName, float x, float y, float w, float h) {
        this.imageName = imageName;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sx = 0;
        this.sy = 0;
        this.sw = w;
        this.sh = h;
    }

    public ImageComponent(String imageName, float x, float y, float w, float h, boolean flip) {
        this.imageName = imageName;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sx = 0;
        this.sy = 0;
        this.sw = w;
        this.sh = h;
        this.flip = flip;
    }

}