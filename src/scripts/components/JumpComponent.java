package scripts.components;

public class JumpComponent {

    public float force;
    public float small;
    public boolean jump = false;

    public JumpComponent(float force, float small) {
        this.force = force;
        this.small = small;
    }

}