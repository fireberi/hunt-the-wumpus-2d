package data;

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

    public static final int WALL_TOP_LEFT = 6;
    public static final int W0 = WALL_TOP_LEFT;

    public static final int WALL_TOP = 7;
    public static final int W1 = WALL_TOP;

    public static final int WALL_TOP_RIGHT = 8;
    public static final int W2 = WALL_TOP_RIGHT;

    public static final int WALL_LEFT = 9;
    public static final int W3 = WALL_LEFT;

    public static final int WALL_MIDDLE = 10;
    public static final int W4 = WALL_MIDDLE;

    public static final int WALL_RIGHT = 11;
    public static final int W5 = WALL_RIGHT;

    public static final int WALL_BOTTOM_LEFT = 12;
    public static final int W6 = WALL_BOTTOM_LEFT;

    public static final int WALL_BOTTOM = 13;
    public static final int W7 = WALL_BOTTOM;

    public static final int WALL_BOTTOM_RIGHT = 14;
    public static final int W8 = WALL_BOTTOM_RIGHT;

    public static final int WALL_INNER_TOP_LEFT = 15;
    public static final int WA = WALL_INNER_TOP_LEFT;

    public static final int WALL_INNER_TOP_RIGHT = 16;
    public static final int WB = WALL_INNER_TOP_RIGHT;

    public static final int WALL_INNER_BOTTOM_RIGHT = 17;
    public static final int WC = WALL_INNER_BOTTOM_RIGHT;

    public static final int WALL_INNER_BOTTOM_LEFT = 18;
    public static final int WD = WALL_INNER_BOTTOM_LEFT;
    //endregion

    //region interactables
    public static final int LAVA_TOP = 19;
    public static final int LT = LAVA_TOP;

    public static final int LAVA_BODY = 20;
    public static final int LB = LAVA_BODY;

    public static final int SPIKE = 21;
    public static final int SK = SPIKE;

    public static final int NEXT_LEVEL_TOP_LEFT = 22;
    public static final int NA = NEXT_LEVEL_TOP_LEFT;

    public static final int NEXT_LEVEL_TOP_RIGHT = 23;
    public static final int NB = NEXT_LEVEL_TOP_RIGHT;

    public static final int NEXT_LEVEL_BOTTOM_LEFT = 24;
    public static final int NC = NEXT_LEVEL_BOTTOM_LEFT;

    public static final int NEXT_LEVEL_BOTTOM_RIGHT = 25;
    public static final int ND = NEXT_LEVEL_BOTTOM_RIGHT;
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
        int solid_count = 14;
        int interactables_count = 7;
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
        enemyTypes.put("super bat", 1);
        enemyTypes.put("super spider", 2);
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