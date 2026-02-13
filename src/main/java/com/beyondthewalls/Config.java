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

    // Wall settings
    public static final ModConfigSpec.IntValue WALL_BUILD_HEIGHT = BUILDER
            .comment("Number of wall blocks placed by the Wall Builder tool")
            .defineInRange("wallBuildHeight", 5, 1, 20);

    // District settings
    public static final ModConfigSpec.IntValue DISTRICT_RADIUS_T1 = BUILDER
            .comment("Protection radius for Tier 1 District Heart")
            .defineInRange("districtRadiusT1", 32, 8, 128);

    public static final ModConfigSpec.IntValue DISTRICT_RADIUS_T2 = BUILDER
            .comment("Protection radius for Tier 2 District Heart")
            .defineInRange("districtRadiusT2", 48, 8, 192);

    public static final ModConfigSpec.IntValue DISTRICT_RADIUS_T3 = BUILDER
            .comment("Protection radius for Tier 3 District Heart")
            .defineInRange("districtRadiusT3", 64, 8, 256);

    // Wave settings
    public static final ModConfigSpec.IntValue WAVE_INTERVAL_BASE = BUILDER
            .comment("Base interval between titan waves in ticks (24000 = 1 MC day)")
            .defineInRange("waveIntervalBase", 24000, 1200, 96000);

    public static final ModConfigSpec.IntValue WAVE_INTERVAL_MIN = BUILDER
            .comment("Minimum interval between titan waves in ticks")
            .defineInRange("waveIntervalMin", 6000, 600, 48000);

    public static final ModConfigSpec.IntValue WAVE_BASE_TITANS = BUILDER
            .comment("Base number of titans per wave")
            .defineInRange("waveBaseTitans", 3, 1, 50);

    public static final ModConfigSpec.IntValue WAVE_SCALING = BUILDER
            .comment("Additional titans per wave number")
            .defineInRange("waveScaling", 2, 0, 20);

    // Essence drops
    public static final ModConfigSpec.IntValue ESSENCE_DROP_MIN = BUILDER
            .comment("Minimum Titan Essence dropped on death")
            .defineInRange("essenceDropMin", 2, 0, 64);

    public static final ModConfigSpec.IntValue ESSENCE_DROP_MAX = BUILDER
            .comment("Maximum Titan Essence dropped on death")
            .defineInRange("essenceDropMax", 4, 0, 64);

    // Abnormal Titan settings
    public static final ModConfigSpec.DoubleValue ABNORMAL_TITAN_HEALTH = BUILDER
            .comment("Health of an Abnormal Titan")
            .defineInRange("abnormalTitanHealth", 60.0, 10.0, 1000.0);

    public static final ModConfigSpec.DoubleValue ABNORMAL_TITAN_SPEED = BUILDER
            .comment("Movement speed of an Abnormal Titan")
            .defineInRange("abnormalTitanSpeed", 0.28, 0.05, 1.0);

    public static final ModConfigSpec.DoubleValue ABNORMAL_TITAN_DAMAGE = BUILDER
            .comment("Attack damage of an Abnormal Titan")
            .defineInRange("abnormalTitanDamage", 10.0, 1.0, 100.0);

    public static final ModConfigSpec.IntValue ABNORMAL_ESSENCE_DROP_MIN = BUILDER
            .comment("Minimum Titan Essence dropped by Abnormal Titan on death")
            .defineInRange("abnormalEssenceDropMin", 2, 0, 64);

    public static final ModConfigSpec.IntValue ABNORMAL_ESSENCE_DROP_MAX = BUILDER
            .comment("Maximum Titan Essence dropped by Abnormal Titan on death")
            .defineInRange("abnormalEssenceDropMax", 4, 0, 64);

    // Armored Titan settings
    public static final ModConfigSpec.DoubleValue ARMORED_TITAN_HEALTH = BUILDER
            .comment("Health of an Armored Titan")
            .defineInRange("armoredTitanHealth", 200.0, 10.0, 2000.0);

    public static final ModConfigSpec.DoubleValue ARMORED_TITAN_SPEED = BUILDER
            .comment("Movement speed of an Armored Titan")
            .defineInRange("armoredTitanSpeed", 0.12, 0.05, 1.0);

    public static final ModConfigSpec.DoubleValue ARMORED_TITAN_DAMAGE = BUILDER
            .comment("Attack damage of an Armored Titan")
            .defineInRange("armoredTitanDamage", 14.0, 1.0, 100.0);

    public static final ModConfigSpec.DoubleValue ARMORED_BODY_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier for body hits on Armored Titans (very resistant)")
            .defineInRange("armoredBodyDamageMultiplier", 0.02, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue ARMORED_WALL_BREAK_SPEED = BUILDER
            .comment("Wall break speed multiplier for Armored Titans (lower = faster)")
            .defineInRange("armoredWallBreakSpeed", 0.5, 0.1, 2.0);

    public static final ModConfigSpec.IntValue ARMORED_ESSENCE_DROP_MIN = BUILDER
            .comment("Minimum Titan Essence dropped by Armored Titan on death")
            .defineInRange("armoredEssenceDropMin", 4, 0, 64);

    public static final ModConfigSpec.IntValue ARMORED_ESSENCE_DROP_MAX = BUILDER
            .comment("Maximum Titan Essence dropped by Armored Titan on death")
            .defineInRange("armoredEssenceDropMax", 8, 0, 64);

    // Colossal Titan settings
    public static final ModConfigSpec.DoubleValue COLOSSAL_TITAN_HEALTH = BUILDER
            .comment("Health of a Colossal Titan")
            .defineInRange("colossalTitanHealth", 500.0, 50.0, 5000.0);

    public static final ModConfigSpec.DoubleValue COLOSSAL_TITAN_SPEED = BUILDER
            .comment("Movement speed of a Colossal Titan")
            .defineInRange("colossalTitanSpeed", 0.08, 0.01, 1.0);

    public static final ModConfigSpec.DoubleValue COLOSSAL_TITAN_DAMAGE = BUILDER
            .comment("Attack damage of a Colossal Titan")
            .defineInRange("colossalTitanDamage", 20.0, 1.0, 200.0);

    public static final ModConfigSpec.IntValue COLOSSAL_STEAM_COOLDOWN = BUILDER
            .comment("Cooldown in ticks between Colossal Titan steam bursts")
            .defineInRange("colossalSteamCooldown", 200, 20, 1000);

    public static final ModConfigSpec.DoubleValue COLOSSAL_STEAM_DAMAGE = BUILDER
            .comment("Damage dealt by Colossal Titan steam burst")
            .defineInRange("colossalSteamDamage", 8.0, 1.0, 100.0);

    public static final ModConfigSpec.DoubleValue COLOSSAL_STEAM_RADIUS = BUILDER
            .comment("Radius of Colossal Titan steam burst in blocks")
            .defineInRange("colossalSteamRadius", 8.0, 2.0, 32.0);

    public static final ModConfigSpec.IntValue COLOSSAL_ESSENCE_DROP_MIN = BUILDER
            .comment("Minimum Titan Essence dropped by Colossal Titan on death")
            .defineInRange("colossalEssenceDropMin", 8, 0, 64);

    public static final ModConfigSpec.IntValue COLOSSAL_ESSENCE_DROP_MAX = BUILDER
            .comment("Maximum Titan Essence dropped by Colossal Titan on death")
            .defineInRange("colossalEssenceDropMax", 16, 0, 64);

    // Wave variant thresholds
    public static final ModConfigSpec.IntValue WAVE_ABNORMAL_START = BUILDER
            .comment("Wave number at which Abnormal Titans start appearing")
            .defineInRange("waveAbnormalStart", 1, 1, 100);

    public static final ModConfigSpec.IntValue WAVE_ARMORED_START = BUILDER
            .comment("Wave number at which Armored Titans start appearing")
            .defineInRange("waveArmoredStart", 3, 1, 100);

    public static final ModConfigSpec.IntValue WAVE_COLOSSAL_START = BUILDER
            .comment("Wave number at which Colossal Titans start appearing")
            .defineInRange("waveColossalStart", 5, 1, 100);

    public static final ModConfigSpec.IntValue WAVE_COLOSSAL_INTERVAL = BUILDER
            .comment("Colossal Titan spawns every Nth wave starting from waveColossalStart")
            .defineInRange("waveColossalInterval", 3, 1, 20);

    // Structure generation settings
    public static final ModConfigSpec.IntValue DISTRICT_STRUCTURE_WALL_HEIGHT = BUILDER
            .comment("Height of walls in generated walled district structures (blocks above ground)")
            .defineInRange("districtStructureWallHeight", 5, 1, 20);

    public static final ModConfigSpec.IntValue DISTRICT_STRUCTURE_FOUNDATION_DEPTH = BUILDER
            .comment("Depth of foundation below ground for generated walled district structures")
            .defineInRange("districtStructureFoundationDepth", 3, 1, 10);

    // Refinery settings
    public static final ModConfigSpec.IntValue REFINERY_SPEED = BUILDER
            .comment("Ticks per refinery cycle (coal to cartridges)")
            .defineInRange("refinerySpeed", 200, 20, 2000);

    public static final ModConfigSpec.IntValue REFINERY_OUTPUT = BUILDER
            .comment("Number of thrust cartridges produced per coal")
            .defineInRange("refineryOutput", 2, 1, 16);

    static final ModConfigSpec SPEC = BUILDER.build();
}
