package scripts.components;

public class HitboxComponent extends BoxComponent {

    public String type;
    public boolean active;
    public boolean markDelete = false;
    public boolean[] layer;
    public HitboxLogic updateLogic;

    public HitboxComponent(String type, boolean active, float w, float h, boolean[] layer, HitboxLogic updateLogic) {
        super(w, h);
        this.active = active;
        this.type = type;
        this.layer = layer;
        this.updateLogic = updateLogic;
    }

    public HitboxComponent(String type, boolean active, float x, float y, float w, float h, boolean[] layer, HitboxLogic updateLogic) {
        super(x, y, w, h);
        this.active = active;
        this.type = type;
        this.layer = layer;
        this.updateLogic = updateLogic;
    }

}