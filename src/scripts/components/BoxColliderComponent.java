package scripts.components;

public class BoxColliderComponent extends BoxComponent {

    public boolean right = false;
    public boolean left = false;
    public boolean bottom = false;
    public boolean top = false;

    public BoxColliderComponent(float w, float h) {
        super(w, h);
    }

    public BoxColliderComponent(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

}