package systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import components.EnemyAIComponent;
import components.InputComponent;
import components.PositionComponent;
import components.VelocityComponent;
import components.HitboxComponent;
import components.HurtboxComponent;
import components.BoxColliderComponent;
import components.SpeedComponent;
import components.PlayerControllerComponent;
import components.InventoryComponent;
import components.SpriteComponent;

import data.Tiles;

import core.State;
import core.Constants;
import util.Collision;

public class EnemyAISystem implements Runnable {

    private Dominion cherry;
    private State state;

    public EnemyAISystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(EnemyAIComponent.class, BoxColliderComponent.class).stream().forEach(e -> {
            EnemyAIComponent eAI = e.comp1();
            BoxColliderComponent box = e.comp2();

            // find player
            var player = new Object() {
                boolean found;
                PositionComponent pos;
                HurtboxComponent hrt;
            };;
            cherry.findEntitiesWith(PlayerControllerComponent.class).stream().forEach(p -> {
                player.found = true;
                player.pos = p.entity().get(PositionComponent.class);
                player.hrt = p.entity().get(HurtboxComponent.class);
            });

            if (!player.found) {
                return;
            }

            if (eAI.enemyType == -1) {
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                SpeedComponent spd = e.entity().get(SpeedComponent.class);

                if (box.left) {
                    vel.facingRight = true;
                }
                else if (box.right) {
                    vel.facingRight = false;
                }

                vel.x = spd.maxX * ((vel.facingRight) ? 1 : -1);
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("super worm")) {
                PositionComponent pos = e.entity().get(PositionComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                SpriteComponent spr = e.entity().get(SpriteComponent.class);

                PositionComponent hitPos = e.entity().get(InventoryComponent.class).getCurrent().get(PositionComponent.class);
                HitboxComponent hit = e.entity().get(InventoryComponent.class).getCurrent().get(HitboxComponent.class);

                // super worms will be active when the player is within roughly the same y level and within 16 tiles horizontally
                boolean canSeePlayer = Math.abs((player.pos.y - player.hrt.y - (pos.y - box.y))) < 0.5 && Math.abs(player.pos.x - pos.x) <  Constants.TILESIZE * 16;

                float playerRight = player.pos.x + player.hrt.x + player.hrt.w;
                float playerLeft = player.pos.x + player.hrt.x;
                boolean canAttackPlayer = Collision.AABB(
                    hitPos.x + hit.x,
                    hitPos.y + hit.y,
                    hit.w, hit.h,
                    player.pos.x + player.hrt.x,
                    player.pos.y + player.hrt.y,
                    player.hrt.w, player.hrt.h);

                if (canSeePlayer && !canAttackPlayer) {
                    if (player.pos.x > pos.x) {
                        inp.inputs.get("right").press();
                        inp.inputs.get("left").release();
                    }
                    else {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").press();
                    }
                }
                else if (canAttackPlayer) {
                    inp.inputs.get("right").release();
                    inp.inputs.get("left").release();
                    inp.inputs.get("attack").press();
                }

                // animation
                if (spr != null) {
                    if (spr.image.flip == vel.facingRight) {
                        spr.image.flip = !vel.facingRight;
                    }
                    if (canAttackPlayer) {
                        spr.nextAnim = "attack";
                    }
                    else if ((inp.inputs.get("right").pressed() || inp.inputs.get("left").pressed()) && Math.abs(vel.x) > 0.1) {
                        spr.nextAnim = "run";
                    }
                    else {
                        spr.nextAnim = "idle";
                    }
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("super bat")) {
                PositionComponent pos = e.entity().get(PositionComponent.class);
                HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                SpriteComponent spr = e.entity().get(SpriteComponent.class);

                // super bats will move toward the player when the player is within its radius of 16 tiles
                boolean canSeePlayer = Math.sqrt(Math.pow(player.pos.x - pos.x, 2) + Math.pow(player.pos.y - pos.y, 2)) < Constants.TILESIZE * 16f;

                // super bats inflict damage by touching the player
                if (canSeePlayer) {
                    if (player.pos.x - pos.x > Constants.TILESIZE * 0.2) {
                        inp.inputs.get("right").press();
                        inp.inputs.get("left").release();
                    }
                    else if (player.pos.x - pos.x < -Constants.TILESIZE * 0.2) {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").press();
                    }
                    else {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").release();
                    }
                    if ((player.pos.y + player.hrt.y) - pos.y > Constants.TILESIZE * 0.2 && Math.abs(player.pos.x - pos.x) < Constants.TILESIZE * 2f) {
                        inp.inputs.get("down").press();
                        inp.inputs.get("up").release();
                    }
                    else if ((player.pos.y + player.hrt.y) - pos.y < -Constants.TILESIZE * 0.2 && Math.abs(player.pos.x - pos.x) < Constants.TILESIZE * 2f) {
                        inp.inputs.get("down").release();
                        inp.inputs.get("up").press();
                    }
                    else {
                        inp.inputs.get("down").release();
                        inp.inputs.get("up").release();
                    }
                }
                else {
                    inp.releaseKeys();
                }

                if ((Math.abs(player.pos.x - pos.x) < Constants.TILESIZE * 2) && (Math.abs(player.pos.y - pos.y) < Constants.TILESIZE * 2)) {
                    inp.inputs.get("attack").press();
                }
                else {
                    inp.inputs.get("attack").release();
                }

                // animation
                if (spr != null) {
                    if (spr.image.flip == vel.facingRight) {
                        spr.image.flip = !vel.facingRight;
                    }
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("super spider")) {
                PositionComponent pos = e.entity().get(PositionComponent.class);
                HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                SpriteComponent spr = e.entity().get(SpriteComponent.class);

                // super spiders can see very far, but they cannot attack very far
                boolean canSeePlayer = Math.abs((player.pos.y - player.hrt.y - (pos.y - hrt.y))) < 0.5 && Math.abs(player.pos.x - pos.x) <  Constants.TILESIZE * 24;

                // super spiders stay stationary all the time, but will start spewing webs at the player if they are in range, poisoning the player
                float range = Constants.TILESIZE * 8f;

                float playerRight = player.pos.x + player.hrt.x + player.hrt.w;
                float playerLeft = player.pos.x + player.hrt.x;
                boolean canAttackPlayer = (vel.facingRight &&
                    playerLeft >= pos.x &&
                    playerLeft <= pos.x + range)
                    || (!vel.facingRight &&
                    playerRight <= pos.x &&
                    playerRight >= pos.x - range);

                if (canSeePlayer) {
                    vel.facingRight = player.pos.x > pos.x;

                    if (canAttackPlayer) {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").release();
                        inp.inputs.get("attack").press();
                    }
                }
                else {
                    inp.releaseKeys();
                }

                // animation
                if (spr != null) {
                    if (spr.image.flip == vel.facingRight) {
                        spr.image.flip = !vel.facingRight;
                    }
                    if (canSeePlayer && canAttackPlayer) {
                        spr.nextAnim = "attack";
                        if (false) {
                            spr.frame().time = 0;
                            spr.currentFrame = 0;
                        }
                    }
                    else {
                        spr.nextAnim = "idle";
                    }
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("ghoul")) {
                PositionComponent pos = e.entity().get(PositionComponent.class);
                HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                SpriteComponent spr = e.entity().get(SpriteComponent.class);
                // ghouls will move toward the player when the player is within its radius of 12 tiles
                boolean canSeePlayer = Math.sqrt(Math.pow(player.pos.x - pos.x, 2) + Math.pow(player.pos.y - pos.y, 2)) < Constants.TILESIZE * 12f;

                // ghouls slow down the players attack when the player is within a 4 tile radius
                if (canSeePlayer) {
                    boolean shouldMove = Math.sqrt(Math.pow(player.pos.x - pos.x, 2) + Math.pow(player.pos.y - pos.y, 2)) > Constants.TILESIZE * 8f;

                    if (shouldMove && player.pos.x - pos.x > Constants.TILESIZE * 0.2) {
                        inp.inputs.get("right").press();
                        inp.inputs.get("left").release();
                    }
                    else if (shouldMove && player.pos.x - pos.x < -Constants.TILESIZE * 0.2) {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").press();
                    }
                    else {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").release();
                    }
                    if (shouldMove && (player.pos.y + player.hrt.y) - pos.y > Constants.TILESIZE * 4f) {
                        inp.inputs.get("down").press();
                        inp.inputs.get("up").release();
                    }
                    else if (shouldMove && (player.pos.y + player.hrt.y) - pos.y < -Constants.TILESIZE * 4f) {
                        inp.inputs.get("down").release();
                        inp.inputs.get("up").press();
                    }
                    else {
                        inp.inputs.get("down").release();
                        inp.inputs.get("up").release();
                    }
                }
                else {
                    inp.releaseKeys();
                }

                // animation
                if (spr != null) {
                    if (spr.image.flip == vel.facingRight) {
                        spr.image.flip = !vel.facingRight;
                    }
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("the wumpus")) {}
        });
    }

}