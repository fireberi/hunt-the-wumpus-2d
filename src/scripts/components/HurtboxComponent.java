package scripts.components;

import java.util.ArrayList;

public class HurtboxComponent extends BoxComponent {

    public boolean active;
    public boolean markDelete = false;
    public final ArrayList<HitboxComponent> justEntered = new ArrayList<HitboxComponent>();
    public final ArrayList<HitboxComponent> entered = new ArrayList<HitboxComponent>();
    public final ArrayList<HitboxComponent> justExited = new ArrayList<HitboxComponent>();
    public HurtboxLogic updateLogic;

    public HurtboxComponent(boolean active, float w, float h, HurtboxLogic updateLogic) {
        super(w, h);
        this.active = active;
        this.updateLogic = updateLogic;
    }

    public HurtboxComponent(boolean active, float x, float y, float w, float h, HurtboxLogic updateLogic) {
        super(x, y, w, h);
        this.active = active;
        this.updateLogic = updateLogic;
    }

    public void enter(HitboxComponent hit) {
        if (!entered.contains(hit)) {
            justEntered.add(hit);
            if (justExited.contains(hit)) {
                justExited.remove(justExited.indexOf(hit));
            }
            entered.add(hit);
        }
    }

    public void exit(HitboxComponent hit) {
        if (entered.contains(hit)) {
            justExited.add(hit);
            if (justEntered.contains(hit)) {
                justEntered.remove(justEntered.indexOf(hit));
            }
            entered.remove(entered.indexOf(hit));
        }
    }

    public void resetJustEnteredAndJustExited() {
        justEntered.clear();
        justExited.clear();
    }

}