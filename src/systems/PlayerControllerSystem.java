package systems;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import components.PlayerControllerComponent;
import components.InputComponent;
import components.PositionComponent;
import components.VelocityComponent;
import components.SpeedComponent;
import components.JumpComponent;
import components.BoxColliderComponent;
import components.InventoryComponent;
import components.HitboxComponent;
import components.TimerComponent;
import components.SpriteComponent;

// temp
import components.HealthComponent;

import objects.Objects;

import util.Timer;
import util.GameMath;
import util.Collision;

import core.Constants;
import core.State;

public class PlayerControllerSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public PlayerControllerSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(PlayerControllerComponent.class, VelocityComponent.class, SpeedComponent.class, JumpComponent.class, BoxColliderComponent.class, PositionComponent.class).stream().forEach(e -> {
            Entity entity = e.entity();
            PlayerControllerComponent pcc = e.comp1();
            VelocityComponent vel = e.comp2();
            SpeedComponent spd = e.comp3();
            JumpComponent jmp = e.comp4();
            BoxColliderComponent boxCol = e.comp5();
            PositionComponent pos = e.comp6();
            InputComponent inp = entity.get(InputComponent.class);

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
            if (altJustPressed) {
                inp.inputs.get("cycle").press();
            }
            if (altJustReleased) {
                inp.inputs.get("cycle").release();
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

            // animation check
            InventoryComponent inv = entity.get(InventoryComponent.class);
            String itemType = "";

            if (inv.inventory.size() != 0) {
                Entity weapon = inv.getCurrentItem();
                itemType = inv.getCurrentItemType();

                if (itemType == "melee") {
                    TimerComponent tmc = weapon.get(TimerComponent.class);
                    if (tmc.timers[0].active || tmc.timers[1].active) {
                        nextAnim = "melee";
                    }
                }
                else if (itemType == "spawner") {
                    TimerComponent tmc = weapon.get(TimerComponent.class);
                    VelocityComponent wpnVel = weapon.get(VelocityComponent.class);
                    if (tmc.active()) {
                        nextAnim = "shoot";
                    }
                    // set bow shoot direction
                    float upwardsShotVelocity = -0.8f;
                    float flatShotVelocity = -0.1f;
                    if (cancelJustPressed) {
                        if (wpnVel.y == upwardsShotVelocity) {
                            wpnVel.y = flatShotVelocity;
                        }
                        else if (wpnVel.y == flatShotVelocity) {
                            wpnVel.y = upwardsShotVelocity;
                        }
                    }
                    if (wpnVel.y < upwardsShotVelocity) {
                        wpnVel.y = flatShotVelocity;
                    }
                    else if (wpnVel.y > flatShotVelocity) {
                        wpnVel.y = upwardsShotVelocity;
                    }
                }
            }

            // set animation
            SpriteComponent spr = entity.get(SpriteComponent.class);
            SpriteComponent swordSpr = null;
            SpriteComponent bowSpr = null;
            if (inv.inventory.size() != 0) {
                swordSpr = entity.get(InventoryComponent.class).getItemWithType("melee").get(SpriteComponent.class);
                bowSpr = entity.get(InventoryComponent.class).getItemWithType("spawner").get(SpriteComponent.class);
            }
            if (spr != null) {
                if (nextAnim != "melee" && nextAnim != "shoot") {
                    if (spr.image.flip != flip) {
                        spr.image.flip = flip;
                    }
                }
                spr.nextAnim = nextAnim;
            }
            if (swordSpr != null) {
                if (itemType == "spawner" && swordSpr.image.active) {
                    swordSpr.image.active = false;
                }
                else if (itemType == "melee" && !swordSpr.image.active) {
                    swordSpr.image.active = true;
                }
                if (nextAnim != "melee") {
                    if (swordSpr.image.flip != flip) {
                        swordSpr.image.flip = flip;
                    }
                }
                if (nextAnim != "shoot") {
                    swordSpr.nextAnim = nextAnim;
                }
            }
            if (bowSpr != null) {
                if (itemType == "melee" && bowSpr.image.active) {
                    bowSpr.image.active = false;
                }
                else if (itemType == "spawner" && !bowSpr.image.active) {
                    bowSpr.image.active = true;
                }
                if (nextAnim != "shoot") {
                    if (bowSpr.image.flip != flip) {
                        bowSpr.image.flip = flip;
                    }
                }
                if (nextAnim != "melee") {
                    bowSpr.nextAnim = nextAnim;
                }
            }
        });
    }

}