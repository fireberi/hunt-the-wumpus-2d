package systems;

import java.util.ArrayList;
import java.util.ListIterator;

import dev.dominion.ecs.api.Dominion;

import components.HealthComponent;
import components.DamageComponent;
import components.HitboxComponent;
import components.HurtboxComponent;

import data.DamageTypes.Damage;

import core.State;

public class HealthDamageSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public HealthDamageSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(HealthComponent.class).stream().forEach(e -> {
            HealthComponent hth = e.comp();
            ArrayList<Damage> effects = hth.effects;
            ArrayList<Float> values = hth.values;

            int i = 0;
            boolean[] markDeletes = new boolean[effects.size()];

            for (Damage d : effects) {
                if (d == Damage.INSTANT) {
                    hth.health = Math.max(0f, hth.health - values.get(i));
                    markDeletes[i] = true;
                }

                if (hth.health <= 0) {
                    HitboxComponent hit = e.entity().get(HitboxComponent.class);
                    HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                    if (hit != null) {
                        hit.markDelete = true;
                    }
                    else if (hrt != null) {
                        hrt.markDelete = true;
                    }
                }

                i++;
            }

            // reset effects and values
            int deleted = 0;
            for (i = 0; i < markDeletes.length; i++) {
                if (markDeletes[i]) {
                    effects.remove(i - deleted);
                    values.remove(i - deleted);
                    deleted++;
                }
            }
        });
    }

}