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

    // Titan settings
    public static final ModConfigSpec.DoubleValue TITAN_HEALTH = BUILDER
            .comment("Base health of a Titan")
            .defineInRange("titanHealth", 100.0, 10.0, 1000.0);

    public static final ModConfigSpec.DoubleValue TITAN_DAMAGE = BUILDER
            .comment("Base attack damage of a Titan")
            .defineInRange("titanDamage", 12.0, 1.0, 100.0);

    public static final ModConfigSpec.IntValue TITAN_SPAWN_WEIGHT = BUILDER
            .comment("Spawn weight for Titans in valid biomes")
            .defineInRange("titanSpawnWeight", 5, 0, 100);

    public static final ModConfigSpec.DoubleValue NAPE_BONUS_MULTIPLIER = BUILDER
            .comment("Damage multiplier for hitting the Titan's nape from behind")
            .defineInRange("napeBonusMultiplier", 1.5, 0.1, 10.0);

    public static final ModConfigSpec.DoubleValue BLADE_NAPE_BONUS = BUILDER
            .comment("Extra damage multiplier when hitting nape with Ultrahard Blades")
            .defineInRange("bladeNapeBonus", 2.0, 0.1, 10.0);

    public static final ModConfigSpec.DoubleValue BODY_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier for body hits on Titans (non-nape)")
            .defineInRange("bodyDamageMultiplier", 0.1, 0.0, 1.0);

    static final ModConfigSpec SPEC = BUILDER.build();
}
