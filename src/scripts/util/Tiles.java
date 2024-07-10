package scripts.util;

import java.util.HashMap;
import java.util.stream.IntStream;

public final class Tiles {

    //region decorations
    public static final int EMPTY = 0;
    public static final int __ = EMPTY;

    public static final int ROCK1 = 1;
    public static final int R1 = ROCK1;

    public static final int ROCK2 = 2;
    public static final int R2 = ROCK2;

    public static final int ROCK3 = 3;
    public static final int R3 = ROCK3;

    public static final int ROCK4 = 4;
    public static final int R4 = ROCK4;
    //endregion

    //region solids
    public static final int BORDER = 5;
    public static final int BD = BORDER;

    public static final int GROUND = 6;
    public static final int GR = GROUND;

    public static final int WALL = 7;
    public static final int WL = WALL;
    //endregion

    //region interactables
    public static final int LAVA = 8;
    public static final int LV = LAVA;

    public static final int SPIKE = 9;
    public static final int SK = SPIKE;

    public static final int NEXT_LEVEL = 10;
    public static final int NL = NEXT_LEVEL;
    //endregion

    public static final HashMap<String, Integer> enemyTypes = new HashMap<String, Integer>();

    public static final int[] list;
    public static final int[] decorations;
    public static final int[] solids;
    public static final int[] interactables;

    static {
        // auto generate list, decorations, solids and interactables arrays

        int initialised_blocks = 0;
        int decorations_count = 5;
        int solid_count = 3;
        int interactables_count = 3;
        int block_count = decorations_count + solid_count + interactables_count;

        list = new int[block_count];
        decorations = new int[decorations_count];
        solids = new int[solid_count];
        interactables = new int[interactables_count];

        for (int i = 0; i < block_count; i++) {
            list[i] = i;
        }

        for (int i = 0; i < decorations_count; i++) {
            decorations[i] = i + initialised_blocks;
        }
        initialised_blocks += decorations_count;

        for (int i = 0; i < solid_count; i++) {
            solids[i] = i + initialised_blocks;
        }
        initialised_blocks += solid_count;

        for (int i = 0; i < interactables_count; i++) {
            interactables[i] = i + initialised_blocks;
        }
        initialised_blocks += interactables_count;



        // define enemy types
        enemyTypes.put("super worm", 0);
        enemyTypes.put("super spider", 1);
        enemyTypes.put("super bat", 2);
        enemyTypes.put("ghoul", 3);
        enemyTypes.put("the wumpus", 4);
    }

    public static boolean isSolid(int tile) {
        return IntStream.of(solids).anyMatch(x -> x == tile);
    }

    public static boolean isDecoration(int tile) {
        return IntStream.of(decorations).anyMatch(x -> x == tile);
    }

    public static boolean isInteractable(int tile) {
        return IntStream.of(interactables).anyMatch(x -> x == tile);
    }

    private Tiles() {}

}