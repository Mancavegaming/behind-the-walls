package com.beyondthewalls;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue GRAPPLE_PULL_SPEED = BUILDER
            .comment("Speed at which the player is pulled toward the hook point (blocks/tick)")
            .defineInRange("grapplePullSpeed", 1.8, 0.1, 10.0);

    public static final ModConfigSpec.IntValue GRAPPLE_MAX_TICKS = BUILDER
            .comment("Maximum duration of a grapple pull in ticks")
            .defineInRange("grappleMaxTicks", 30, 5, 200);

    public static final ModConfigSpec.DoubleValue GRAPPLE_ARRIVAL_DISTANCE = BUILDER
            .comment("Distance from hook point at which the player is considered arrived")
            .defineInRange("grappleArrivalDistance", 2.0, 0.5, 10.0);

    public static final ModConfigSpec.DoubleValue HOOK_GRAVITY = BUILDER
            .comment("Gravity applied to the grappling hook projectile")
            .defineInRange("hookGravity", 0.01, 0.0, 1.0);

    public static final ModConfigSpec.IntValue HOOK_COOLDOWN_TICKS = BUILDER
            .comment("Cooldown between Sky Rigging uses in ticks")
            .defineInRange("hookCooldownTicks", 10, 0, 100);

    static final ModConfigSpec SPEC = BUILDER.build();
}
