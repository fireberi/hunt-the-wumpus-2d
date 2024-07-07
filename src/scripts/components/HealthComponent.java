package scripts.components;

import java.util.ArrayList;

import scripts.util.DamageTypes.Damage;

public class HealthComponent {

    public float health;
    public ArrayList<Damage> effects = new ArrayList<Damage>();
    public ArrayList<Float> values = new ArrayList<Float>();

    public HealthComponent(float health) {
        this.health = health;
    }

    public void add(Damage[] newEffects, float[] newValues) {
        for (Damage d : newEffects) {
            effects.add(d);
        }
        for (float v : newValues) {
            values.add(v);
        }
    }

}