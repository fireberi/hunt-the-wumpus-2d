package scripts.objects;

import scripts.components.TilemapComponent;

import scripts.util.Tiles;

public final class LevelData {

    public static final int __ = Tiles.__;
    public static final int R1 = Tiles.R1;
    public static final int R2 = Tiles.R2;
    public static final int R3 = Tiles.R3;
    public static final int R4 = Tiles.R4;

    public static final int BD = Tiles.BD;
    public static final int GR = Tiles.GR;
    public static final int WL = Tiles.WL;

    public static final int LV = Tiles.LV;
    public static final int SK = Tiles.SK;

    public static final int NL = Tiles.NL;

    // reference (useless)
    public static final int PS = 0; // player spawn
    public static final int ES = 0; // enemy spawn

    //region level 1
    public static int[] level1PlayerSpawn = new int[] {5, 11};
    public static int[][] level1EnemySpawns = new int[][] {
        {Tiles.enemyTypes.get("super worm").intValue(), 65, 11},
    };
    public static String level1NextLevel = "Level2";

    public static TilemapComponent level1 = new TilemapComponent(new int[][] {
        {BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,NL,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,WL,__,__,__,__,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,WL,__,__,__,__,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,WL,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,WL,__,__,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,BD},
        {BD,__,__,__,__,PS,__,__,__,NL,__,__,__,__,__,__,__,__,__,__,__,__,WL,WL,WL,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,WL,SK,SK,SK,WL,__,__,__,__,__,__,__,__,__,__,__,__,__,__,ES,__,__,__,__,__,__,__,__,WL,__,__,WL,__,__,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,WL,BD},
        {BD,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,GR,BD},
        {BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD},
    });
    //endregion

    //region level 2
    public static int[] level2PlayerSpawn = new int[] {3, 11};
    public static int[][] level2EnemySpawns = new int[0][];
    public static String level2NextLevel = "";

    public static TilemapComponent level2 = new TilemapComponent(new int[][] {
        {BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,__,__,PS,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,__,BD},
        {BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD},
        {BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD,BD},
    });
    //endregion

    private LevelData() {}

}