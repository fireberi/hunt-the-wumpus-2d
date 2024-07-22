package scripts.systems;

import java.util.HashMap;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.InputComponent;
import scripts.components.PositionComponent;
import scripts.components.VelocityComponent;
import scripts.components.BoxColliderComponent;
import scripts.components.JumpComponent;
import scripts.components.SpeedComponent;
import scripts.components.InventoryComponent;
import scripts.components.TimerComponent;
import scripts.components.HitboxComponent;
import scripts.components.SpawnerComponent;

import scripts.util.Timer;
import scripts.util.GameMath;

import scripts.core.Input;
import scripts.core.State;

public class EntityControllerSystem implements Runnable {

    Dominion cherry;
    State state;

    public EntityControllerSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(InputComponent.class, VelocityComponent.class, SpeedComponent.class, JumpComponent.class, BoxColliderComponent.class, PositionComponent.class).stream().forEach(e -> {
            HashMap<String, Input> inp = e.comp1().inputs;
            VelocityComponent vel = e.comp2();
            SpeedComponent spd = e.comp3();
            JumpComponent jmp = e.comp4();
            BoxColliderComponent boxCol = e.comp5();
            PositionComponent pos = e.comp6();
            Entity entity = e.entity();

            boolean rightJustPressed = inp.get("right").justPressed();
            boolean rightPressed = inp.get("right").pressed();
            boolean rightJustReleased = inp.get("right").justReleased();

            boolean leftJustPressed = inp.get("left").justPressed();
            boolean leftPressed = inp.get("left").pressed();
            boolean leftJustReleased = inp.get("left").justReleased();

            boolean downJustPressed = inp.get("down").justPressed();
            boolean downPressed = inp.get("down").pressed();
            boolean downJustReleased = inp.get("down").justReleased();

            boolean upJustPressed = inp.get("up").justPressed();
            boolean upPressed = inp.get("up").pressed();
            boolean upJustReleased = inp.get("up").justReleased();

            boolean attackJustPressed = inp.get("attack").justPressed();
            boolean attackPressed = inp.get("attack").pressed();
            boolean attackJustReleased = inp.get("attack").justReleased();

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

            InventoryComponent inv = entity.get(InventoryComponent.class);
            if (inv != null && inv.inventory.size() != 0) {
                Entity weapon = inv.getCurrent();

                if (inv.current == "melee") {
                    TimerComponent tmc = weapon.get(TimerComponent.class);
                    HitboxComponent hit = weapon.get(HitboxComponent.class);

                    hit.active = false;

                    if (attackPressed && !tmc.active()) {
                        tmc.startTimer(0, 0);
                    }

                    if (tmc.timers[0].timeout) {
                        hit.active = true;
                    }
                }
                else if (inv.current == "spawner") {
                    TimerComponent tmc = weapon.get(TimerComponent.class);
                    SpawnerComponent spw = weapon.get(SpawnerComponent.class);

                    if (attackPressed && !tmc.active()) {
                        // run spawn function
                        tmc.startTimer(0, 0);
                    }

                    if (tmc.timers[0].timeout) {
                        spw.spawnerLogic.spawn(cherry, weapon);
                    }
                }
            }
        });
    }

}