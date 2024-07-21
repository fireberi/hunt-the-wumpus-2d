package scripts.systems;

import java.util.ArrayList;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.PositionComponent;
import scripts.components.VelocityComponent;
import scripts.components.BoxColliderComponent;
import scripts.components.TilemapComponent;

import scripts.core.Constants;
import scripts.core.State;
import scripts.util.Collision;
import scripts.util.Tiles;

public class MoveSystem implements Runnable {

    private Dominion cherry;
    private State state;
    private float collisionBuffer = 2f;

    public MoveSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(PositionComponent.class, VelocityComponent.class, BoxColliderComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            VelocityComponent vel = e.comp2();
            BoxColliderComponent boxCol = e.comp3();

            if (vel.x == 0 && vel.y == 0) {
                return;
            }

            boxCol.right = false;
            boxCol.left = false;
            boxCol.bottom = false;
            boxCol.top = false;

            if (!Constants.GRID_COLLISION) {
                cherry.findEntitiesWith(PositionComponent.class, BoxColliderComponent.class).stream().forEach(item -> {
                    PositionComponent ipos = item.comp1();
                    BoxColliderComponent iboxCol = item.comp2();

                    if (e.entity() == item.entity()) {
                        System.out.println("skipping");
                        return;
                    }

                    float[] collisionResult = processPreciseCollision(pos, vel, boxCol, ipos, iboxCol);
                    pos.x = collisionResult[0];
                    pos.y = collisionResult[1];
                });
                if (!boxCol.right && !boxCol.left) {
                    pos.x += vel.x;
                }
                if (!boxCol.bottom && !boxCol.top) {
                    pos.y += vel.y;
                }
            }
            else {
                cherry.findEntitiesWith(TilemapComponent.class).stream().forEach(tilemap -> {
                    TilemapComponent map = tilemap.comp();
                    processGridCollision(pos, vel, boxCol, map);
                });
            }
        });
    }

    private float[] processPreciseCollision(PositionComponent pos, VelocityComponent vel, BoxColliderComponent boxCol, PositionComponent ipos, BoxColliderComponent iboxCol) {
        float newX = pos.x;
        float newY = pos.y;

        float nextX = newX + boxCol.x + vel.x;

        boolean xCollision = Collision.AABB(nextX, newY + boxCol.y, boxCol.w, boxCol.h, ipos.x + iboxCol.x, ipos.y + iboxCol.y, iboxCol.w, iboxCol.h);
        boolean xBufferCollision = Collision.AABB(nextX, newY + boxCol.y, boxCol.w, boxCol.h, ipos.x + iboxCol.x, ipos.y + iboxCol.y + collisionBuffer, iboxCol.w, iboxCol.h - (2 * collisionBuffer));

        if (xBufferCollision) {
            if (vel.x > 0) {
                newX = ipos.x + iboxCol.x - (boxCol.x + boxCol.w);
                collideRight(vel, boxCol);
            }
            else if (vel.x < 0) {
                newX = ipos.x + iboxCol.x + iboxCol.w - boxCol.x;
                collideLeft(vel, boxCol);
            }
            else {
                // bump to nearest left or right edge
                if (Math.abs(newX + boxCol.x + boxCol.w - (ipos.x + iboxCol.x)) < Math.abs(newX + boxCol.x - (ipos.x + iboxCol.x + iboxCol.w))) {
                    newX = ipos.x + iboxCol.x - (boxCol.x + boxCol.w);
                    collideRight(vel, boxCol);
                }
                else {
                    newX = ipos.x + iboxCol.x + iboxCol.w - boxCol.x;
                    collideLeft(vel, boxCol);
                }
            }
        }
        else if (xCollision) {
            // bump to nearest bottom or top edge
            if (Math.abs(newY + boxCol.y + boxCol.h - (ipos.y + iboxCol.y)) < Math.abs(newY + boxCol.y - (ipos.y + iboxCol.y + iboxCol.h))) {
                newY = ipos.y + iboxCol.y - (boxCol.y + boxCol.h);
                collideBottom(vel, boxCol);
            }
            else {
                newY = ipos.y + iboxCol.y + iboxCol.h - boxCol.y;
                collideTop(vel, boxCol);
            }
        }

        float nextY = newY + boxCol.y + vel.y;

        boolean yCollision = Collision.AABB(newX + boxCol.x, nextY, boxCol.w, boxCol.h, ipos.x + iboxCol.x, ipos.y + iboxCol.y, iboxCol.w, iboxCol.h);
        boolean yBufferCollision = Collision.AABB(newX + boxCol.x, nextY, boxCol.w, boxCol.h, ipos.x + iboxCol.x + collisionBuffer, ipos.y + iboxCol.y, iboxCol.w - (2 * collisionBuffer), iboxCol.h);

        if (yCollision) {
            if (vel.y > 0) {
                newY = ipos.y + iboxCol.y - (boxCol.y + boxCol.h);
                collideBottom(vel, boxCol);
            }
            else if (vel.y < 0) {
                newY = ipos.y + iboxCol.y + iboxCol.h - boxCol.y;
                collideTop(vel, boxCol);
            }
            else {
                // bump to nearest bottom or top edge
                if (Math.abs(newY + boxCol.y + boxCol.h - (ipos.y + iboxCol.y)) < Math.abs(newY + boxCol.y - (ipos.y + iboxCol.y + iboxCol.h))) {
                    newY = ipos.y + iboxCol.y - (boxCol.y + boxCol.h);
                    collideBottom(vel, boxCol);
                }
                else {
                    newY = ipos.y + iboxCol.y + iboxCol.h - boxCol.y;
                    collideTop(vel, boxCol);
                }
            }
        }
        else if (yCollision) {
            // bump to nearest left or right edge
            if (Math.abs(newX + boxCol.x + boxCol.w - (ipos.x + iboxCol.x)) < Math.abs(newX + boxCol.x - (ipos.x + iboxCol.x + iboxCol.w))) {
                newX = ipos.x + iboxCol.x - (boxCol.x + boxCol.w);
                collideRight(vel, boxCol);
            }
            else {
                newX = ipos.x + iboxCol.x + iboxCol.w - boxCol.x;
                collideLeft(vel, boxCol);
            }
        }

        return new float[] {newX, newY};
    }

    private void processGridCollision(PositionComponent pos, VelocityComponent vel, BoxColliderComponent boxCol, TilemapComponent map) {
        //region solid blocks
        float nextX = pos.x + boxCol.x + vel.x;

        int[] xCollision = Collision.gridAABB(Constants.TILESIZE, map.grid, Tiles.solids, nextX, pos.y + boxCol.y, boxCol.w, boxCol.h);
        int tile = xCollision[0];

        if (tile != 0 && boxCol.active) {
            int tx = xCollision[1] * Constants.TILESIZE;
            int ty = xCollision[2] * Constants.TILESIZE;

            if (vel.x > 0) {
                pos.x = tx - boxCol.w - boxCol.x;
                collideRight(vel, boxCol);
            }
            else if (vel.x < 0) {
                pos.x = tx + Constants.TILESIZE + boxCol.x + boxCol.w;
                collideLeft(vel, boxCol);
            }
            else {
                // bump to nearest right or left edge
                if (Math.abs(pos.x + boxCol.x + boxCol.w - tx) < Math.abs(pos.x + boxCol.x - (tx + Constants.TILESIZE))) {
                    pos.x = tx + boxCol.x;
                    boxCol.right = true;
                    collideRight(vel, boxCol);
                }
                else {
                    pos.x = tx + Constants.TILESIZE + boxCol.x;
                    boxCol.left = true;
                    collideLeft(vel, boxCol);
                }
            }
        }
        else {
            pos.x += vel.x;
        }

        float nextY = pos.y + boxCol.y + vel.y;

        int[] yCollision = Collision.gridAABB(Constants.TILESIZE, map.grid, Tiles.solids, pos.x + boxCol.x, nextY, boxCol.w, boxCol.h);
        tile = yCollision[0];

        if (tile != 0 && boxCol.active) {
            int ty = yCollision[2] * Constants.TILESIZE;

            if (vel.y > 0) {
                pos.y = ty + boxCol.y;
                collideBottom(vel, boxCol);
            }
            else if (vel.y < 0) {
                pos.y = ty + Constants.TILESIZE - boxCol.y;
                collideTop(vel, boxCol);
            }
            else {
                // bump to nearest bottom or top edge
                if (Math.abs(pos.y + boxCol.y + boxCol.h - ty) < Math.abs(pos.y + boxCol.y - (ty + Constants.TILESIZE))) {
                    pos.y = ty + boxCol.y;
                    collideBottom(vel, boxCol);
                }
                else {
                    pos.y = ty + Constants.TILESIZE + boxCol.y;
                    collideTop(vel, boxCol);
                }
            }
        }
        else {
            pos.y += vel.y;
        }
        //endregion
    }

    private void collideRight(VelocityComponent vel, BoxColliderComponent boxCol) {
        vel.x /= 4;
        boxCol.right = true;
    }

    private void collideLeft(VelocityComponent vel, BoxColliderComponent boxCol) {
        vel.x /= 4;
        boxCol.left = true;
    }

    private void collideBottom(VelocityComponent vel, BoxColliderComponent boxCol) {
        vel.y = 0;
        boxCol.bottom = true;
    }

    private void collideTop(VelocityComponent vel, BoxColliderComponent boxCol) {
        vel.y /= 4;
        boxCol.top = true;
    }

}