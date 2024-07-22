package objects;

import components.TilemapComponent;

public class Level {

    public int[] playerSpawn;
    public float[][] enemySpawns;
    public String nextLevel;
    public TilemapComponent tilemap;

    Level(int[] playerSpawn, float[][] enemySpawns, String nextLevel, int[][] tilemap) {
        this.playerSpawn = playerSpawn;
        this.enemySpawns = enemySpawns;
        this.nextLevel = nextLevel;

        this.tilemap = new TilemapComponent(tilemap);
    }

}