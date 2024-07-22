package data.effects;

public class ContinuousDamageEffect extends Effect {

    public float value;
    public final int maxRepeats;
    public int repeats;
    public double waitTime;
    public double time;

    public ContinuousDamageEffect(float value, int repeats, double waitTime) {
        this.value = value;
        this.maxRepeats = repeats;
        this.waitTime = waitTime;
        this.time = waitTime;
    }

}