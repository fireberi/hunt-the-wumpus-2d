package components;

public class BoxColliderComponent extends BoxComponent {

    public boolean right = false;
    public boolean left = false;
    public boolean bottom = false;
    public boolean top = false;
    public boolean active;

    public BoxColliderComponent(boolean active, float w, float h) {
        super(w, h);
        this.active = active;
    }

    public BoxColliderComponent(boolean active, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.active = active;
    }

}