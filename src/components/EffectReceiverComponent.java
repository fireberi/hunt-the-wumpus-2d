package components;

import java.util.ArrayList;

import data.effects.Effect;

public class EffectReceiverComponent {

    public ArrayList<Effect> effects = new ArrayList<Effect>();

    public EffectReceiverComponent() {}

    public void add(Effect[] newEffects) {
        for (Effect f : newEffects) {
            effects.add(f);
        }
    }

}