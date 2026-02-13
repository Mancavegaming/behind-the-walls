package com.beyondthewalls.entity;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.goal.AbnormalErraticStrollGoal;
import com.beyondthewalls.entity.goal.AbnormalLungeGoal;
import com.beyondthewalls.init.ModItems;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import com.beyondthewalls.entity.goal.TitanBreakWallGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AbnormalTitanEntity extends TitanEntity {

    public AbnormalTitanEntity(EntityType<? extends TitanEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.STEP_HEIGHT, 2.0);
    }

    @Override
    public int getMaxHeadYRot() {
        return 20; // Fast, erratic head rotation
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AbnormalLungeGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true)); // 1.4x speed
        this.goalSelector.addGoal(2, new TitanBreakWallGoal(this));
        this.goalSelector.addGoal(5, new AbnormalErraticStrollGoal(this, 0.6));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        // Skip TitanEntity's drop, use our own
        int min = Config.ABNORMAL_ESSENCE_DROP_MIN.get();
        int max = Config.ABNORMAL_ESSENCE_DROP_MAX.get();
        int count = min + level.random.nextInt(Math.max(1, max - min + 1));
        this.spawnAtLocation(new ItemStack(ModItems.TITAN_ESSENCE.get(), count));
    }
}
