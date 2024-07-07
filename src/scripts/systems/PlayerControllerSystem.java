package scripts.systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.PlayerControllerComponent;
import scripts.components.PositionComponent;
import scripts.components.VelocityComponent;
import scripts.components.SpeedComponent;
import scripts.components.JumpComponent;
import scripts.components.BoxColliderComponent;
import scripts.components.InventoryComponent;
import scripts.components.HitboxComponent;
import scripts.components.TimerComponent;
import scripts.components.SpriteComponent;

import scripts.objects.Objects;

import scripts.util.Timer;
import scripts.util.GameMath;
import scripts.util.Collision;

import scripts.core.Constants;
import scripts.core.State;

public class PlayerControllerSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public PlayerControllerSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(PlayerControllerComponent.class, VelocityComponent.class, SpeedComponent.class, JumpComponent.class, BoxColliderComponent.class, PositionComponent.class).stream().forEach(e -> {
            PlayerControllerComponent pcc = e.comp1();
            VelocityComponent vel = e.comp2();
            SpeedComponent spd = e.comp3();
            JumpComponent jmp = e.comp4();
            BoxColliderComponent boxCol = e.comp5();
            PositionComponent pos = e.comp6();

            boolean rightJustPressed = pcc.inputs.get("right").justPressed();
            boolean rightPressed = pcc.inputs.get("right").pressed();
            boolean rightJustReleased = pcc.inputs.get("right").justReleased();

            boolean leftJustPressed = pcc.inputs.get("left").justPressed();
            boolean leftPressed = pcc.inputs.get("left").pressed();
            boolean leftJustReleased = pcc.inputs.get("left").justReleased();

            boolean downJustPressed = pcc.inputs.get("down").justPressed();
            boolean downPressed = pcc.inputs.get("down").pressed();
            boolean downJustReleased = pcc.inputs.get("down").justReleased();

            boolean upJustPressed = pcc.inputs.get("up").justPressed();
            boolean upPressed = pcc.inputs.get("up").pressed();
            boolean upJustReleased = pcc.inputs.get("up").justReleased();

            boolean confirmJustPressed = pcc.inputs.get("confirm").justPressed();
            boolean confirmPressed = pcc.inputs.get("confirm").pressed();
            boolean confirmJustReleased = pcc.inputs.get("confirm").justReleased();

            boolean cancelJustPressed = pcc.inputs.get("cancel").justPressed();
            boolean cancelPressed = pcc.inputs.get("cancel").pressed();
            boolean cancelJustReleased = pcc.inputs.get("cancel").justReleased();

            boolean altJustPressed = pcc.inputs.get("alt").justPressed();
            boolean altPressed = pcc.inputs.get("alt").pressed();
            boolean altJustReleased = pcc.inputs.get("alt").justReleased();

            if (altJustPressed) {}

            // determine direction
            if (rightJustPressed && !leftJustPressed) {
                vel.facingRight = true;
            }
            else if (leftJustPressed && !rightJustPressed) {
                vel.facingRight = false;
            }

            if (rightJustReleased && leftPressed) {
                vel.facingRight = false;
            }
            else if (leftJustReleased && rightPressed) {
                vel.facingRight = true;
            }

            // move in direction
            if (vel.facingRight && rightPressed) {
                if (vel.x < -spd.maxX / 2) {
                    vel.x = GameMath.approach(vel.x, spd.maxX, 3 * spd.ax);
                }
                else {
                    vel.x = GameMath.approach(vel.x, spd.maxX, spd.ax);
                }
            }
            else if (!vel.facingRight && leftPressed) {
                if (vel.x > spd.maxX / 2) {
                    vel.x = GameMath.approach(vel.x, -spd.maxX, 3 * spd.ax);
                }
                else {
                    vel.x = GameMath.approach(vel.x, -spd.maxX, spd.ax);
                }
            }
            else if (vel.x != 0) {
                vel.x = GameMath.approach(vel.x, 0, spd.dx);
            }

            // if gravity, up becomes jump and down is not an input
            if (vel.gravity) {
                if (upJustPressed) {
                    jmp.jump = true;
                }
                else if (upJustReleased) {
                    jmp.jump = false;
                }

                if (boxCol.bottom && jmp.jump) {
                    vel.y = -jmp.force;
                    boxCol.bottom = false;
                    jmp.jump = false;
                }
                else if (upJustReleased && vel.y < -jmp.small) {
                    vel.y = -jmp.small;
                }
            }
            // otherwise up becomes up and down becomes down
            else {
                if (downJustPressed && !upJustPressed) {
                    vel.facingDown = true;
                }
                else if (upJustPressed && !downJustPressed) {
                    vel.facingDown = false;
                }

                if (downJustReleased && upPressed) {
                    vel.facingDown = false;
                }
                else if (upJustReleased && downPressed) {
                    vel.facingDown = true;
                }

                if (vel.facingDown && downPressed) {
                vel.y = GameMath.approach(vel.y, spd.maxX, spd.ax);
                }
                else if (!vel.facingDown && upPressed) {
                    vel.y = GameMath.approach(vel.y, -spd.maxX, spd.ax);
                }
                else if (vel.y != 0) {
                    vel.y = GameMath.approach(vel.y, 0, spd.dx);
                }
            }

            // animation checks
            boolean flip = !vel.facingRight;
            String nextAnim = "";
            if (boxCol.bottom) {
                if ((vel.facingRight && rightPressed && vel.x >= 0.1) || (!vel.facingRight && leftPressed && vel.x <= 0.1)) {
                    nextAnim = "run";
                }
                else {
                    nextAnim = "idle";
                }
            }
            else {
                nextAnim = "air";
            }

            if (pcc.hasInventory) {
                InventoryComponent inv = e.entity().get(InventoryComponent.class);
                Entity sword = inv.inventory.get("sword");
                TimerComponent swordTmc = sword.get(TimerComponent.class);
                HitboxComponent swordHit = sword.get(HitboxComponent.class);

                swordHit.active = false;

                if (confirmJustPressed && !swordTmc.active()) {
                    swordTmc.startTimer(0, 0);
                }
                // spawn an arrow
                if (cancelJustPressed) {
                    Objects.createArrowActor(cherry, pos.x, pos.y, vel.facingRight);
                }

                if (swordTmc.timers[0].timeout) {
                    swordHit.active = true;
                }

                // animation check
                if (swordTmc.timers[0].active || swordTmc.timers[1].active) {
                    nextAnim = "melee";
                }
            }

            // set animation
            SpriteComponent spr = e.entity().get(SpriteComponent.class);
            SpriteComponent swordSpr = null;
            if (pcc.hasInventory) {
                swordSpr = e.entity().get(InventoryComponent.class).inventory.get("sword").get(SpriteComponent.class);
            }
            if (spr != null) {
                if (spr.image.flip != flip) {
                    spr.image.flip = flip;
                }
                spr.nextAnim = nextAnim;
            }
            if (swordSpr != null) {
                if (swordSpr.image.flip != flip) {
                    swordSpr.image.flip = flip;
                }
                swordSpr.nextAnim = nextAnim;
                if (swordSpr.nextAnim == "melee") {
                    swordSpr.nextAnim = "attack";
                }
            }
        });
    }

}