package scripts.components;

import scripts.util.DamageTypes;

public class DamageComponent {

    public DamageTypes.Damage[] effects;
    public float[] values;

    public DamageComponent(DamageTypes.Damage[] effects, float[] values) {
        this.effects = effects;
        this.values = values;
    }

}