package components;

import components.helpers.RespawnLogic;

public class HealthComponent {

    public float health;
    public RespawnLogic respawnLogic;

    public HealthComponent(float health) {
        this.health = health;
        this.respawnLogic = null;
    }

    public HealthComponent(float health, RespawnLogic respawnLogic) {
        this.health = health;
        this.respawnLogic = respawnLogic;
    }

}