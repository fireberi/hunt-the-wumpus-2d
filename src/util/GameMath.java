package util;

public final class GameMath {

    public static float approach(float current, float end, float change) {
        if (current < end) {
            return Math.min(current + change, end);
        }
        else if (current > end) {
            return Math.max(current - change, end);
        }
        return current;
    }

    public static float expDecay(float current, float target, float decay, float delta) {
        return target + (current - target) * (float) Math.exp(-decay * delta);
    }

    public static float randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private GameMath() {}

}