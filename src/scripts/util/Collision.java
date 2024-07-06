package scripts.util;

import java.util.stream.IntStream;

public final class Collision {

    public static boolean AABB(float ax, float ay, float aw, float ah, float bx, float by, float bw, float bh) {
        return ax < bx + bw && ax + aw > bx && ay < by + bh && ay + ah > by;
    }

    public static int[] gridAABB(int TILESIZE, int[][] grid, int[] tiles, float x, float y, float w, float h) {
        int right = (int) Math.min(Math.max(0, Math.ceil((x + w) / TILESIZE)), grid[0].length);
        int left = (int) Math.min(Math.max(0, Math.floor(x / TILESIZE)), grid[0].length);
        int bottom = (int) Math.min(Math.max(0, Math.ceil((y + h) / TILESIZE)), grid.length);
        int top = (int) Math.min(Math.max(0, Math.floor(y / TILESIZE)), grid.length);

        for (int ty = top; ty < bottom; ty++) {
            for (int tx = left; tx < right; tx++) {
                int tile = grid[ty][tx];
                if (IntStream.of(tiles).anyMatch(t -> t == tile)) {
                    return new int[] {tile, tx, ty};
                }
            }
        }
        return null;
    }

    private Collision() {}

}