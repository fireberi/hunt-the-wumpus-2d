package scripts.systems;

import java.util.ArrayList;
import java.util.ListIterator;

import dev.dominion.ecs.api.Dominion;

import scripts.components.HealthComponent;
import scripts.components.DamageComponent;
import scripts.components.HitboxComponent;
import scripts.components.HurtboxComponent;

import scripts.util.DamageTypes.Damage;
import scripts.core.State;

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
            ListIterator<Damage> eIter = effects.listIterator();
            i = 0;
            while (eIter.hasNext()) {
                if (markDeletes[i]) {
                    effects.remove(i);
                }
                i++;
            }
            ListIterator<Float> vIter = values.listIterator();
            i = 0;
            while (vIter.hasNext()) {
                if (markDeletes[i]) {
                    values.remove(i);
                }
                i++;
            }
        });
    }

}