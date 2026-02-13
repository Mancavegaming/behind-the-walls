package com.beyondthewalls.entity;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.goal.ArmoredBreakWallGoal;
import com.beyondthewalls.init.ModItems;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArmoredTitanEntity extends TitanEntity {

    public ArmoredTitanEntity(EntityType<? extends TitanEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.MOVEMENT_SPEED, 0.12)
                .add(Attributes.ATTACK_DAMAGE, 14.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0) // Immovable
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.STEP_HEIGHT, 1.5);
    }

    @Override
    public int getMaxHeadYRot() {
        return 6; // Very slow, heavy head rotation
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8, true)); // 0.8x speed (slower)
        this.goalSelector.addGoal(2, new ArmoredBreakWallGoal(this)); // Breaks walls 2x faster
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected float getBodyDamageMultiplier() {
        return (float) Config.ARMORED_BODY_DAMAGE_MULTIPLIER.getAsDouble();
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        int min = Config.ARMORED_ESSENCE_DROP_MIN.get();
        int max = Config.ARMORED_ESSENCE_DROP_MAX.get();
        int count = min + level.random.nextInt(Math.max(1, max - min + 1));
        this.spawnAtLocation(new ItemStack(ModItems.TITAN_ESSENCE.get(), count));
    }
}
