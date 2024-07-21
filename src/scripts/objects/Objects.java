package scripts.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javafx.scene.paint.Color;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;

import scripts.components.*;

import scripts.core.Input;
import scripts.util.DamageTypes.Damage;
import scripts.util.Frame;
import scripts.util.GameMath;
import scripts.util.Timer;
import scripts.util.Tiles;

public final class Objects {

    //region actors
    public static Entity createSimpleCharacterActor(Dominion cherry, float x, float y, boolean gravity) {
        return cherry.createEntity(
            new PlayerControllerComponent(),
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

    public static Entity createTestCharacterActor(Dominion cherry, float x, float y, boolean gravity, HashMap<String, InventoryItem> inventory) {
        return cherry.createEntity(
            new PlayerControllerComponent(),
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
            new InventoryComponent("melee", inventory),
            new FocusComponent(true, 0f, 0f),
            new SpriteComponent(new ImageComponent("hunter", 48, 32),
                "idle", true,
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

    public static Entity createCharacterActor(Dominion cherry, float x, float y, boolean gravity, HashMap<String, InventoryItem> inventory) {
        return cherry.createEntity(
            new PlayerControllerComponent(),
            new InputComponent(new HashMap<String, Input>(Map.ofEntries(
                Map.entry("right", new Input()),
                Map.entry("left", new Input()),
                Map.entry("down", new Input()),
                Map.entry("up", new Input()),
                Map.entry("attack", new Input())
            ))),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 0.55f, 2.5f),
            // new VelocityComponent(0f, 0f, true, false),
            // new SpeedComponent(0.05f, 0.074f, 2f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.15f, 0.75f),
            new BoxColliderComponent(8f, 14f),
            new HurtboxComponent(true, 8f, 14f, new boolean[] {true, false}),
            new HealthComponent(200f),
            new InventoryComponent("melee", inventory),
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
            ),
            new GraphicsListComponent(new GraphicsComponent[] {
                // new GraphicsComponent(8f, 14f, Color.rgb(255, 255, 255), true),
                // new GraphicsComponent(8f, 14f, "hurtbox", false),
            }),
            new RenderLayerComponent((byte) 1)
        );
    }

    public static Entity createSwordActor(Dominion cherry, float x, float y, boolean facingRight) {
        return cherry.createEntity(
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, false),
            new HitboxComponent("melee", false, 14f, 8f, new boolean[] {false, true}, new HitboxLogic() {
                @Override
                public void update(Entity hitbox, Entity hurtbox, boolean entered, boolean justEntered, boolean justExited) {
                    HitboxComponent hit = hitbox.get(HitboxComponent.class);
                    VelocityComponent vel = hitbox.get(VelocityComponent.class);
                    HurtboxComponent hrt = hurtbox.get(HurtboxComponent.class);
                    VelocityComponent hrtVel = hurtbox.get(VelocityComponent.class);
                    HealthComponent hrtHth = hurtbox.get(HealthComponent.class);

                    if (justEntered) {
                        DamageComponent dmc = hitbox.get(DamageComponent.class);
                        hrtHth.add(dmc.effects, dmc.values);

                        // knockback
                        if (vel != null) {
                            if (vel.facingRight) {
                                hrtVel.x += 1.5;
                            }
                            else {
                                hrtVel.x -= 1.5;
                            }
                            hrtVel.y -= 1;
                        }
                    }
                }
                @Override
                public void clean(Dominion cherry, Entity sword) {}
            }),
            new DamageComponent(new Damage[] {Damage.INSTANT}, new float[] {20f}),
            new TimerComponent(false, new Timer[] {new Timer(0.16), new Timer(0.15), new Timer(0.19)}),
            new SpriteComponent(new ImageComponent("rusty", 32, 32), 1, "idle", true,
                new String[] {"idle", "air", "run", "attack"},
                new boolean[] {true, false, true, false},
                new double[] {0.75f, 10f, 0.14f, 0.08f},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 16f, -14f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -14f, -11f),
                    },
                    {
                        new Frame(0f, 16f, 16f, 16f, -18f, -17f),
                    },
                    {
                        new Frame(0f, 0f, 16f, 16f, -18f, -13f),
                        new Frame(0f, 0f, 16f, 16f, -13f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -10f, -12f),
                        new Frame(0f, 0f, 16f, 16f, -13f, -12f),
                    },
                    {
                        new Frame(16f, 0f, 16f, 16f, -17f, -18f),
                        new Frame(0f, 16f, 16f, 16f, -14f, -13f),
                        new Frame(16f, 16f, 16f, 16f, -7f, -11f),
                    }
                }
            ),
            new GraphicsListComponent(new GraphicsComponent[] {
                // new GraphicsComponent(14f, 8f, "hitbox", false),
            }),
            new RenderLayerComponent((byte) 2)
        );
    }

    public static Entity createArrowActor(Dominion cherry, float x, float y, boolean facingRight) {
        float direction = facingRight ? 2.5f : -2.5f;
        return cherry.createEntity(
            new PositionComponent(x, y),
            new VelocityComponent(direction, -0.2f, true, true),
            new SpeedComponent(0f, 0f, 0f, 2.5f),
            new GravityComponent(0.008f),
            new BoxColliderComponent(4f, 4f),
            new HitboxComponent("arrow", true, 4f, 4f, new boolean[] {false, true}, new HitboxLogic() {
                @Override
                public void update(Entity hitbox, Entity hurtbox, boolean entered, boolean justEntered, boolean justExited) {}
                @Override
                public void clean(Dominion cherry, Entity arrow) {
                    HitboxComponent hit = arrow.get(HitboxComponent.class);
                    BoxColliderComponent boxCol = arrow.get(BoxColliderComponent.class);
                    if (boxCol.right || boxCol.left || boxCol.bottom || boxCol.top) {
                        hit.markDelete = true;
                    }
                }
            }),
            new DamageComponent(new Damage[] {Damage.INSTANT}, new float[] {30f}),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(4f, 4f, Color.rgb(0, 207, 255), true),
                new GraphicsComponent(4f, 4f, "hitbox", false),
            }),
            new RenderLayerComponent((byte) 2)
        );
    }

    public static Entity createSimpleEnemyActor(Dominion cherry, float x, float y, boolean gravity) {
        return cherry.createEntity(
            new EnemyAIComponent(-1),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 0.15f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.15f, 0.75f),
            new BoxColliderComponent(7f, 10f),
            new HurtboxComponent(true, 7f, 10f, new boolean[] {false, true}),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(7f, 10f, Color.rgb(159, 31, 47), true),
                new GraphicsComponent(7f, 10f, "hurtbox", false),
            }),
            new HealthComponent(50f),
            new RenderLayerComponent((byte) 1)
        );
    }

    public static Entity createEnemyActor(Dominion cherry, float x, float y, int enemyType) {
        float boxW = 7f;
        float boxH = 10f;
        float health = 50f;
        HashMap<String, InventoryItem> inventory = new HashMap<String, InventoryItem>();
        String currentInventory = "";
        boolean gravity = true;
        SpriteComponent spr = new SpriteComponent(new ImageComponent("", 0, 0),
            1, "", false,
            new String[0],
            new boolean[0],
            new double[0],
            new Frame[0][]
        );

        if (enemyType == Tiles.enemyTypes.get("super worm").intValue()) {
            boxW = 14f;
            boxH = 4f;
            health = 50f;
            inventory.put("melee", Objects.createSuperWormAttackItem(cherry, x, y));
            currentInventory = "melee";
            spr = new SpriteComponent(new ImageComponent("super_worm", 32, 16),
                1, "idle", true,
                new String[] {"idle", "run", "attack"},
                new boolean[] {true, true, false},
                new double[] {(float) GameMath.randInt(70, 120) / 100, (float) GameMath.randInt(15, 40) / 100, 0.16f},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 8f, -8f, -6f),
                        new Frame(16f, 0f, 16f, 8f, -8f, -6f),
                    },
                    {
                        new Frame(0f, 8f, 16f, 8f, -8f, -6f),
                        new Frame(16f, 8f, 16f, 8f, -8f, -6f),
                    },
                    {
                        new Frame(16f, 0f, 16f, 8f, -8f, -6f),
                        new Frame(0f, 0f, 16f, 8f, -8f, -6f),
                    },
                }
            );
        }
        else if (enemyType == Tiles.enemyTypes.get("super bat").intValue()) {
            boxW = 8f;
            boxH = 8f;
            health = 50f;
            gravity = false;
            inventory.put("melee", Objects.createSuperBatAttackItem(cherry, x, y));
            currentInventory = "melee";
            spr = new SpriteComponent(new ImageComponent("super_bat", 32, 8),
                1, "flap", true,
                new String[] {"flap"},
                new boolean[] {true},
                new double[] {(float) GameMath.randInt(10, 20) / 100},
                new Frame[][] {
                    {
                        new Frame(0f, 0f, 16f, 8f, -8f, -4f),
                        new Frame(16f, 0f, 16f, 8f, -8f, -4f),
                    },
                }
            );
        }
        else if (enemyType == Tiles.enemyTypes.get("super spider").intValue()) {
            boxW = 16f;
            boxH = 12f;
            health = 120f;
        }
        else if (enemyType == Tiles.enemyTypes.get("ghoul").intValue()) {
            boxW = 8f;
            boxH = 8f;
            health = 80f;
            gravity = false;
        }
        else if (enemyType == Tiles.enemyTypes.get("the wumpus").intValue()) {
            boxW = 20f;
            boxH = 20f;
            health = 500f;
        }
        else {
            System.out.println("enemyType argument is illegal");
        }

        return cherry.createEntity(
            new EnemyAIComponent(enemyType),
            new InputComponent(new HashMap<String, Input>(Map.ofEntries(
                Map.entry("right", new Input()),
                Map.entry("left", new Input()),
                Map.entry("down", new Input()),
                Map.entry("up", new Input()),
                Map.entry("attack", new Input())
            ))),
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, gravity),
            new SpeedComponent(0.05f, 0.074f, 0.15f, 2.5f),
            new GravityComponent(),
            new JumpComponent(1.15f, 0.75f),
            new BoxColliderComponent(boxW, boxH),
            new InventoryComponent(currentInventory, inventory),
            new HurtboxComponent(true, boxW, boxH, new boolean[] {false, true}),
            new HealthComponent(health),
            spr,
            new GraphicsListComponent(new GraphicsComponent[] {
                // new GraphicsComponent(boxW, boxH, Color.rgb(159, 31, 47), true),
                // new GraphicsComponent(boxW, boxH, "hurtbox", false),
            }),
            new RenderLayerComponent((byte) 1)
        );
    }

    private static Entity createSuperWormAttackActor(Dominion cherry, float x, float y) {
        return cherry.createEntity(
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, false),
            new HitboxComponent("headbutt", false, 10f, 6f, new boolean[] {true, false}, new HitboxLogic() {
                @Override
                public void update(Entity hitbox, Entity hurtbox, boolean entered, boolean justEntered, boolean justExited) {
                    HitboxComponent hit = hitbox.get(HitboxComponent.class);
                    VelocityComponent vel = hitbox.get(VelocityComponent.class);
                    HurtboxComponent hrt = hurtbox.get(HurtboxComponent.class);
                    VelocityComponent hrtVel = hurtbox.get(VelocityComponent.class);
                    HealthComponent hrtHth = hurtbox.get(HealthComponent.class);

                    if (justEntered) {
                        DamageComponent dmc = hitbox.get(DamageComponent.class);
                        hrtHth.add(dmc.effects, dmc.values);

                        // knockback
                        if (vel != null) {
                            if (vel.facingRight) {
                                hrtVel.x += 1;
                            }
                            else {
                                hrtVel.x -= 1;
                            }
                            hrtVel.y -= 1;
                        }
                    }
                }
                @Override
                public void clean(Dominion cherry, Entity sword) {}
            }),
            new DamageComponent(new Damage[] {Damage.INSTANT}, new float[] {10f}),
            new TimerComponent(false, new Timer[] {new Timer(0.16), new Timer(0.1), new Timer(0.74)}),
            new GraphicsListComponent(new GraphicsComponent[] {
                // new GraphicsComponent(10f, 6f, "hitbox", false),
            }),
            new RenderLayerComponent((byte) 2)
        );
    }

    private static Entity createSuperBatAttackActor(Dominion cherry, float x, float y) {
        return cherry.createEntity(
            new PositionComponent(x, y),
            new VelocityComponent(0f, 0f, true, false),
            new HitboxComponent("melee", false, 10f, 10f, new boolean[] {true, false}, new HitboxLogic() {
                @Override
                public void update(Entity hitbox, Entity hurtbox, boolean entered, boolean justEntered, boolean justExited) {
                    HitboxComponent hit = hitbox.get(HitboxComponent.class);
                    VelocityComponent vel = hitbox.get(VelocityComponent.class);
                    HurtboxComponent hrt = hurtbox.get(HurtboxComponent.class);
                    VelocityComponent hrtVel = hurtbox.get(VelocityComponent.class);
                    HealthComponent hrtHth = hurtbox.get(HealthComponent.class);

                    if (justEntered) {
                        DamageComponent dmc = hitbox.get(DamageComponent.class);
                        hrtHth.add(dmc.effects, dmc.values);

                        // knockback
                        if (vel != null) {
                            if (vel.facingRight) {
                                hrtVel.x += 1;
                            }
                            else {
                                hrtVel.x -= 1;
                            }
                            hrtVel.y -= 1;
                        }
                    }
                }
                @Override
                public void clean(Dominion cherry, Entity sword) {}
            }),
            new DamageComponent(new Damage[] {Damage.INSTANT}, new float[] {20f}),
            new TimerComponent(false, new Timer[] {new Timer(0.16), new Timer(0.1), new Timer(0.94)}),
            new GraphicsListComponent(new GraphicsComponent[] {
                new GraphicsComponent(10f, 10f, "hitbox", false),
            }),
            new RenderLayerComponent((byte) 2)
        );
    }
    //endregion

    //region inventory items
    public static InventoryItem createSwordItem(Dominion cherry) {
        return new InventoryItem(
            Objects.createSwordActor(cherry, 0f, 0f, true),
            new InventoryLogic() {
                @Override
                public void update(Entity item, Entity owner) {
                    PositionComponent pos = item.get(PositionComponent.class);
                    VelocityComponent vel = item.get(VelocityComponent.class);
                    HitboxComponent hit = item.get(HitboxComponent.class);
                    SpriteComponent spr = item.get(SpriteComponent.class);
                    PositionComponent opos = owner.get(PositionComponent.class);
                    VelocityComponent ovel = owner.get(VelocityComponent.class);
                    float direction;
                    if (spr == null) {
                        direction = ovel.facingRight ? hit.w / 2 + 2 : -hit.w / 2 - 2;
                    }
                    else {
                        direction = !spr.image.flip ? hit.w / 2 + 2 : -hit.w / 2 - 2;
                    }
                    pos.x = opos.x + direction;
                    pos.y = opos.y + 3f;
                    vel.x = ovel.x;
                    vel.y = ovel.y;
                    vel.facingRight = ovel.facingRight;
                }
            }
        );
    }

    public static InventoryItem createSuperWormAttackItem(Dominion cherry, float x, float y) {
        return new InventoryItem(
            Objects.createSuperWormAttackActor(cherry, x, y),
            new InventoryLogic() {
                @Override
                public void update(Entity item, Entity owner) {
                    PositionComponent pos = item.get(PositionComponent.class);
                    VelocityComponent vel = item.get(VelocityComponent.class);
                    HitboxComponent hit = item.get(HitboxComponent.class);
                    SpriteComponent spr = item.get(SpriteComponent.class);
                    PositionComponent opos = owner.get(PositionComponent.class);
                    VelocityComponent ovel = owner.get(VelocityComponent.class);
                    float direction;
                    if (spr == null) {
                        direction = ovel.facingRight ? hit.w / 2 : -hit.w / 2;
                    }
                    else {
                        direction = !spr.image.flip ? hit.w / 2 : -hit.w / 2;
                    }
                    pos.x = opos.x + direction;
                    pos.y = opos.y;
                    vel.x = ovel.x;
                    vel.y = ovel.y;
                    vel.facingRight = ovel.facingRight;
                }
            }
        );
    }

    public static InventoryItem createSuperBatAttackItem(Dominion cherry, float x, float y) {
        return new InventoryItem(
            Objects.createSuperBatAttackActor(cherry, x, y),
            new InventoryLogic() {
                @Override
                public void update(Entity item, Entity owner) {
                    PositionComponent pos = item.get(PositionComponent.class);
                    VelocityComponent vel = item.get(VelocityComponent.class);
                    PositionComponent opos = owner.get(PositionComponent.class);
                    VelocityComponent ovel = owner.get(VelocityComponent.class);
                    pos.x = opos.x;
                    pos.y = opos.y;
                    vel.x = ovel.x;
                    vel.y = ovel.y;
                    vel.facingRight = ovel.facingRight;
                }
            }
        );
    }
    //endregion

    private Objects() {}

}