package systems;

import java.util.ArrayList;
import java.util.ListIterator;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import components.*;

import data.effects.*;

import core.State;

public class EffectsSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public EffectsSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(EffectReceiverComponent.class).stream().forEach(e -> {
            Entity entity = e.entity();
            EffectReceiverComponent fxr = e.comp();
            HealthComponent hth = entity.get(HealthComponent.class);
            SpeedComponent spd = entity.get(SpeedComponent.class);

            ArrayList<Effect> effects = fxr.effects;

            int i = 0;
            boolean[] markDeletes = new boolean[effects.size()];

            for (Effect f : effects) {
                if (InstantDamageEffect.class.isInstance(f) && hth != null) {
                    InstantDamageEffect dmg = (InstantDamageEffect) f;
                    hth.health = Math.max(0f, hth.health - dmg.value);
                    markDeletes[i] = true;
                }
                else if (ContinuousDamageEffect.class.isInstance(f) && hth != null) {
                    ContinuousDamageEffect cdmg = (ContinuousDamageEffect) f;

                    cdmg.time += state.delta;

                    if (cdmg.time >= cdmg.waitTime) {
                        cdmg.time = 0;
                        cdmg.repeats += 1;
                        hth.health = Math.max(0f, hth.health - cdmg.value);
                        System.out.println(hth.health);
                    }

                    if (cdmg.repeats >= cdmg.maxRepeats) {
                        markDeletes[i] = true;
                    }
                }
                else if (SlowMovementEffect.class.isInstance(f) && spd != null) {
                    SlowMovementEffect slw = (SlowMovementEffect) f;

                    spd.xMultiplier = slw.value;
                    markDeletes[i] = true;
                }

                i++;
            }

            // reset effects
            int deleted = 0;
            for (i = 0; i < markDeletes.length; i++) {
                if (markDeletes[i]) {
                    effects.remove(i - deleted);
                    deleted++;
                }
            }
        });
    }

}