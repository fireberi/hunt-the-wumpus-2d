package scripts.components;

public class EnemyAIComponent {

    public String mode;

    public EnemyAIComponent(String mode) {
        // maybe make mode an enum?
        this.mode = mode.toLowerCase();
    }

}