package components;

import data.DamageTypes.Damage;

public class DamageComponent {

    public Damage[] effects;
    public float[] values;

    public DamageComponent(Damage[] effects, float[] values) {
        this.effects = effects;
        this.values = values;
    }

}