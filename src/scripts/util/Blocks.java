package scripts.util;

import java.util.stream.IntStream;

public final class Blocks {

    public static final int EMPTY = 0;
    public static final int BORDER = 1;
    public static final int WALL = 2;
    public static final int LAVA = 3;
    public static final int SPIKE = 4;
    public static final int ROCK1 = 5;
    public static final int ROCK2 = 6;
    public static final int ROCK3 = 7;
    public static final int ROCK4 = 8;

    public static final int[] list = new int[] {
        EMPTY,
        BORDER,
        WALL,
        LAVA,
        SPIKE,
        ROCK1,
        ROCK2,
        ROCK3,
        ROCK4,
    };

    public static final int[] decorations = new int[] {
        EMPTY,
        ROCK1,
        ROCK2,
        ROCK3,
        ROCK4,
    };

    public static final int[] solids = new int[] {
        BORDER,
        WALL,
    };

    public static final int[] interactables = new int[] {
        LAVA,
        SPIKE,
    };

    public static boolean isSolid(int tile) {
        return IntStream.of(solids).anyMatch(x -> x == tile);
    }

    public static boolean isDecoration(int tile) {
        return IntStream.of(decorations).anyMatch(x -> x == tile);
    }

    public static boolean isInteractable(int tile) {
        return IntStream.of(interactables).anyMatch(x -> x == tile);
    }

    private Blocks() {}

}