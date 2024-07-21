package scripts.systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.PlayerControllerComponent;
import scripts.components.InputComponent;
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
            InputComponent inp = e.entity().get(InputComponent.class);

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

            if (rightJustPressed) {
                inp.inputs.get("right").press();
            }
            if (rightJustReleased) {
                inp.inputs.get("right").release();
            }
            if (leftJustPressed) {
                inp.inputs.get("left").press();
            }
            if (leftJustReleased) {
                inp.inputs.get("left").release();
            }
            if (downJustPressed) {
                inp.inputs.get("down").press();
            }
            if (downJustReleased) {
                inp.inputs.get("down").release();
            }
            if (upJustPressed) {
                inp.inputs.get("up").press();
            }
            if (upJustReleased) {
                inp.inputs.get("up").release();
            }
            if (confirmJustPressed) {
                inp.inputs.get("attack").press();
            }
            if (confirmJustReleased) {
                inp.inputs.get("attack").release();
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

            InventoryComponent inv = e.entity().get(InventoryComponent.class);
            if (inv.inventory.size() != 0) {
                Entity weapon = inv.getCurrent();
                if (inv.current == "melee") {
                    // animation check
                    TimerComponent tmc = weapon.get(TimerComponent.class);
                    if (tmc.timers[0].active || tmc.timers[1].active) {
                        nextAnim = "melee";
                    }
                }
            }

            // set animation
            SpriteComponent spr = e.entity().get(SpriteComponent.class);
            SpriteComponent swordSpr = null;
            if (inv.inventory.size() != 0) {
                swordSpr = e.entity().get(InventoryComponent.class).inventory.get("melee").item.get(SpriteComponent.class);
            }
            if (spr != null) {
                if (nextAnim != "melee") {
                    if (spr.image.flip != flip) {
                        spr.image.flip = flip;
                    }
                }
                spr.nextAnim = nextAnim;
            }
            if (swordSpr != null) {
                if (nextAnim != "melee") {
                    if (swordSpr.image.flip != flip) {
                        swordSpr.image.flip = flip;
                    }
                }
                swordSpr.nextAnim = nextAnim;
                if (swordSpr.nextAnim == "melee") {
                    swordSpr.nextAnim = "attack";
                }
            }
        });
    }

}