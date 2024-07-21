package scripts.systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.EnemyAIComponent;
import scripts.components.InputComponent;
import scripts.components.PositionComponent;
import scripts.components.VelocityComponent;
import scripts.components.HitboxComponent;
import scripts.components.HurtboxComponent;
import scripts.components.BoxColliderComponent;
import scripts.components.SpeedComponent;
import scripts.components.PlayerControllerComponent;
import scripts.components.InventoryComponent;
import scripts.components.SpriteComponent;

import scripts.core.State;
import scripts.core.Constants;
import scripts.util.Tiles;

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
                HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                HitboxComponent hit = e.entity().get(InventoryComponent.class).getCurrent().get(HitboxComponent.class);
                SpriteComponent spr = e.entity().get(SpriteComponent.class);

                // super worms will be active when the player is within roughly the same y level and within 16 tiles horizontally
                eAI.active = Math.abs((player.pos.y - player.hrt.y - (pos.y - hrt.y))) < 0.5 && Math.abs(player.pos.x - pos.x) <  Constants.TILESIZE * 16;

                float playerRight = player.pos.x + player.hrt.x + player.hrt.w;
                float playerLeft = player.pos.x + player.hrt.x;
                boolean canAttackPlayer = (vel.facingRight &&
                    playerLeft >= pos.x &&
                    playerLeft <= pos.x + hit.w)
                    || (!vel.facingRight &&
                    playerRight <= pos.x &&
                    playerRight >= pos.x - hit.w);

                if (eAI.active) {
                    if (!canAttackPlayer) {
                        if (player.pos.x > pos.x) {
                            inp.inputs.get("right").press();
                            inp.inputs.get("left").release();
                        }
                        else {
                            inp.inputs.get("right").release();
                            inp.inputs.get("left").press();
                        }
                    }
                    else {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").release();
                        inp.inputs.get("attack").press();
                    }
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
                // super bats will move toward the player when the player is within its radius of 24 tiles
                eAI.active = Math.sqrt(Math.pow(player.pos.x - pos.x, 2) + Math.pow(player.pos.y - pos.y, 2)) < Constants.TILESIZE * 24f;

                if (!eAI.active) {
                    inp.releaseKeys();
                    return;
                }

                // super bats inflict damage by touching the player
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

                if ((Math.abs(player.pos.x - pos.x) < Constants.TILESIZE * 2) && (Math.abs(player.pos.y - pos.y) < Constants.TILESIZE * 2)) {
                    inp.inputs.get("attack").press();
                }
                else {
                    inp.inputs.get("attack").release();
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("super spider")) {
                PositionComponent pos = e.entity().get(PositionComponent.class);
                HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                // super spiders will be active when the player is within roughly the same y level and within 24 tiles horizontally
                eAI.active = Math.abs((player.pos.y - player.hrt.y - (pos.y - hrt.y))) < 0.5 && Math.abs(player.pos.x - pos.x) <  Constants.TILESIZE * 24;

                if (!eAI.active) {
                    inp.releaseKeys();
                    return;
                }

                // super spiders will move in range of the player, about 8 tiles, and start spewing webs at the player, slowing the player down and dealing damage
                float range = Constants.TILESIZE * 8f;

                float playerRight = player.pos.x + player.hrt.x + player.hrt.w;
                float playerLeft = player.pos.x + player.hrt.x;
                boolean canAttackPlayer = (vel.facingRight &&
                    playerLeft >= pos.x &&
                    playerLeft <= pos.x + range)
                    || (!vel.facingRight &&
                    playerRight <= pos.x &&
                    playerRight >= pos.x - range);

                if (!canAttackPlayer) {
                    if (player.pos.x > pos.x) {
                        inp.inputs.get("right").press();
                        inp.inputs.get("left").release();
                    }
                    else {
                        inp.inputs.get("right").release();
                        inp.inputs.get("left").press();
                    }
                    inp.inputs.get("attack").release();
                }
                else {
                    inp.inputs.get("right").release();
                    inp.inputs.get("left").release();
                    inp.inputs.get("attack").press();
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("ghoul")) {
                PositionComponent pos = e.entity().get(PositionComponent.class);
                HurtboxComponent hrt = e.entity().get(HurtboxComponent.class);
                InputComponent inp = e.entity().get(InputComponent.class);
                VelocityComponent vel = e.entity().get(VelocityComponent.class);
                // ghouls will move toward the player when the player is within its radius of 24 tiles
                eAI.active = Math.sqrt(Math.pow(player.pos.x - pos.x, 2) + Math.pow(player.pos.y - pos.y, 2)) < Constants.TILESIZE * 24f;

                if (!eAI.active) {
                    inp.releaseKeys();
                    return;
                }

                // ghouls slow down the players attack when the player is within a 4 tile radius
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
                if (shouldMove && (player.pos.y + player.hrt.y) - pos.y > Constants.TILESIZE * 0.2 && Math.abs(player.pos.x - pos.x) < Constants.TILESIZE * 2f) {
                    inp.inputs.get("down").press();
                    inp.inputs.get("up").release();
                }
                else if (shouldMove && (player.pos.y + player.hrt.y) - pos.y < -Constants.TILESIZE * 0.2 && Math.abs(player.pos.x - pos.x) < Constants.TILESIZE * 2f) {
                    inp.inputs.get("down").release();
                    inp.inputs.get("up").press();
                }
                else {
                    inp.inputs.get("down").release();
                    inp.inputs.get("up").release();
                }
            }
            else if (eAI.enemyType == Tiles.enemyTypes.get("the wumpus")) {}
        });
    }

}