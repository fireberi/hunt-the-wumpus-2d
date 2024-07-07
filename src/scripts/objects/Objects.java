package scripts.objects;

import java.util.HashMap;
import java.util.ArrayList;

import javafx.scene.paint.Color;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.*;

import scripts.util.Timer;
import scripts.util.Frame;
import scripts.util.DamageTypes.Damage;

public final class Objects {

    //region actors
    public static Entity createSimpleCharacterActor(Dominion cherry, float x, float y, boolean gravity) {
        return cherry.createEntity(
            new PlayerControllerComponent(false),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 1.25f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.75f, 0.5f),
            new BoxColliderComponent(7f, 10f),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(7f, 10f, Color.rgb(255, 255, 255), true)
            })
        );
    }

    public static Entity createTestCharacterActor(Dominion cherry, float x, float y, boolean gravity, HashMap<String, Entity> inventory) {
        return cherry.createEntity(
            new PlayerControllerComponent(inventory.size() == 0 ? false : true),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 1.25f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.75f, 0.5f),
            new BoxColliderComponent(7f, 10f),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(7f, 10f, Color.rgb(255, 255, 255), true)
            }),
            new HealthComponent(200f),
            new InventoryComponent(inventory),
            new FocusComponent(true, 0f, 0f),
            new SpriteComponent(new ImageComponent("hunter", 48, 32), "idle", true,
                new String[] {"idle", "air", "run"},
                new boolean[] {true, false, true, false},
                new double[] {0.75f, 0f, 0.15f},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -8f, -11f),
                        new Frame(16f, 0f, 16f, 16f, -8f, -11f),
                    },
                    {
                        new Frame(32f, 0f, 16f, 16f, -8f, -11f),
                    },
                    {
                        new Frame(0f, 16f, 16f, 16f, -8f, -11f),
                        new Frame(16f, 16f, 16f, 16f, -8f, -11f),
                        new Frame(32f, 16f, 16f, 16f, -8f, -11f),
                        new Frame(16f, 16f, 16f, 16f, -8f, -11f),
                    }
                }
            )
        );
    }

    public static Entity createCharacterActor(Dominion cherry, float x, float y, boolean gravity, HashMap<String, Entity> inventory) {
        return cherry.createEntity(
            new PlayerControllerComponent(inventory.size() == 0 ? false : true),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 0.55f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.15f, 0.75f),
            new BoxColliderComponent(8f, 14f),
            new GraphicsListComponent(new GraphicsComponent[] {
                // new GraphicsComponent(8f, 14f, Color.rgb(255, 255, 255), true)
            }),
            new HealthComponent(200f),
            new InventoryComponent(inventory),
            new FocusComponent(true, 0f, -8f),
            new SpriteComponent(new ImageComponent("hunter", 48, 32), 0, "idle", true,
                new String[] {"idle", "air", "run", "melee"},
                new boolean[] {true, false, true, false},
                new double[] {0.75f, 0.1f, 0.14f, 0.08f},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 0f, 16f, 16f, -7f, -9f),
                    },
                    {
                        new Frame(32f, 0f, 16f, 16f, -7f, -9f),
                    },
                    {
                        new Frame(0f, 16f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 16f, 16f, 16f, -7f, -9f),
                        new Frame(32f, 16f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 16f, 16f, 16f, -7f, -9f),
                    },
                    {
                        new Frame(32f, 0f, 16f, 16f, -7f, -9f),
                        new Frame(16f, 16f, 16f, 16f, -7f, -9f),
                        new Frame(32f, 16f, 16f, 16f, -7f, -9f),
                    }
                }
            )
        );
    }

    public static Entity createSwordActor(Dominion cherry, float x, float y, boolean facingRight) {
        return cherry.createEntity(
            new PositionComponent(x, y),
            new HitboxComponent("sword", false, 14f, 8f, new HitboxLogic() {
                @Override
                public void update(Dominion cherry, Entity sword) {
                    PositionComponent pos = sword.get(PositionComponent.class);
                    HitboxComponent hit = sword.get(HitboxComponent.class);
                    cherry.findEntitiesWith(PlayerControllerComponent.class, PositionComponent.class, VelocityComponent.class).stream().forEach(player -> {
                        PositionComponent ppos = player.comp2();
                        VelocityComponent pvel = player.comp3();
                        float direction = pvel.facingRight ? hit.w / 2 + 2 : -hit.w / 2 - 2;
                        pos.x = ppos.x + direction;
                        pos.y = ppos.y;
                    });
                }
                @Override
                public void clean(Dominion cherry, Entity sword) {}
            }),
            new HitboxGraphicsListComponent(new GraphicsComponent[] {
                // new GraphicsComponent(14f, 8f, "hitbox", false),
            }),
            new DamageComponent(new Damage[] {Damage.INSTANT}, new float[] {30f}),
            new TimerComponent(false, new Timer[] {new Timer(0.16), new Timer(0.15), new Timer(0.19)}),
            new SpriteComponent(new ImageComponent("rusty", 32, 32), 1, "idle", true,
                new String[] {"idle", "air", "run", "attack"},
                new boolean[] {true, false, true, false},
                new double[] {0.75f, 10f, 0.14f, 0.08f},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -14f, -9),
                        new Frame(0f, 0f, 16f, 16f, -14f, -8),
                    },
                    {
                        new Frame(0f, 16f, 16f, 16f, -18f, -14),
                    },
                    {
                        new Frame(0f, 0f, 16f, 16f, -18f, -10),
                        new Frame(0f, 0f, 16f, 16f, -13f, -9),
                        new Frame(0f, 0f, 16f, 16f, -10f, -9),
                        new Frame(0f, 0f, 16f, 16f, -13f, -9),
                    },
                    {
                        new Frame(16f, 0f, 16f, 16f, -17f, -15),
                        new Frame(0f, 16f, 16f, 16f, -14f, -10),
                        new Frame(16f, 16f, 16f, 16f, -7, -8),
                    }
                }
            )
        );
    }

    public static Entity createArrowActor(Dominion cherry, float x, float y, boolean facingRight) {
        float direction = facingRight ? 2.5f : -2.5f;
        return cherry.createEntity(
            new PositionComponent(x, y),
            new VelocityComponent(direction, -0.3f, true, true),
            new SpeedComponent(0.1f, 0.005f, 2.0f, 2.5f),
            new GravityComponent(0.01f),
            new BoxColliderComponent(4f, 4f),
            new HitboxComponent("arrow", true, 4f, 4f, new HitboxLogic() {
                @Override
                public void update(Dominion cherry, Entity arrow) {}
                @Override
                public void clean(Dominion cherry, Entity arrow) {
                    HitboxComponent hit = arrow.get(HitboxComponent.class);
                    BoxColliderComponent boxCol = arrow.get(BoxColliderComponent.class);
                    if (boxCol.right || boxCol.left || boxCol.bottom || boxCol.top) {
                        hit.markDelete = true;
                    }
                }
            }),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(4f, 4f, Color.rgb(0, 207, 255), true),
            }),
            new HitboxGraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(4f, 4f, "hitbox", false),
            }),
            new DamageComponent(new Damage[] {Damage.INSTANT}, new float[] {20f})
        );
    }

    public static Entity createEnemyActor(Dominion cherry, float x, float y, boolean gravity) {
        return cherry.createEntity(
            new EnemyAIComponent("left-right"),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 0.15f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.15f, 0.75f),
            new BoxColliderComponent(7f, 10f),
            new HurtboxComponent(true, 7f, 10f, new HurtboxLogic() {
                @Override
                public void update(Entity receiver, Entity attacker, boolean entered, boolean justEntered, boolean justExited) {
                    HitboxComponent aHit = attacker.get(HitboxComponent.class);
                    HurtboxComponent rHrt = receiver.get(HurtboxComponent.class);
                    HealthComponent rHp = receiver.get(HealthComponent.class);
                    if (aHit.type == "arrow" || aHit.type == "sword") {
                        if (justEntered) {
                            DamageComponent dmc = attacker.get(DamageComponent.class);
                            rHp.add(dmc.effects, dmc.values);
                        }
                    }
                }
            }),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(7f, 10f, Color.rgb(159, 31, 47), true),
            }),
            new HurtboxGraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(7f, 10f, "hurtbox", false),
            }),
            new HealthComponent(10f)
        );
    }
    //endregion

    private Objects() {}

}