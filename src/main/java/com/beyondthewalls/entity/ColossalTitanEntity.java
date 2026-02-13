package com.beyondthewalls.entity;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.goal.SteamBurstGoal;
import com.beyondthewalls.init.ModItems;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.phys.Vec3;

public class ColossalTitanEntity extends TitanEntity {

    private static final EntityDataAccessor<Integer> DATA_STEAM_BURST_TICKS =
            SynchedEntityData.defineId(ColossalTitanEntity.class, EntityDataSerializers.INT);

    public ColossalTitanEntity(EntityType<? extends TitanEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STEAM_BURST_TICKS, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 500.0)
                .add(Attributes.MOVEMENT_SPEED, 0.08)
                .add(Attributes.ATTACK_DAMAGE, 20.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.STEP_HEIGHT, 2.5);
    }

    @Override
    public int getMaxHeadYRot() {
        return 5; // Ponderous
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SteamBurstGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8, true)); // Slow attack
        this.goalSelector.addGoal(2, new com.beyondthewalls.entity.goal.TitanBreakWallGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.4));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        // Reposition nape for colossal scale: y+19.25, 4.2 blocks behind
        Vec3 facing = Vec3.directionFromRotation(0, this.getYRot()).normalize();
        double napeX = this.getX() - facing.x * 4.2;
        double napeY = this.getY() + 19.25;
        double napeZ = this.getZ() - facing.z * 4.2;
        // Access napePart through getParts()
        if (this.getParts() != null && this.getParts().length > 0) {
            this.getParts()[0].setPos(napeX, napeY, napeZ);
        }

        // Decrement steam burst ticks
        if (!level().isClientSide) {
            int st = this.entityData.get(DATA_STEAM_BURST_TICKS);
            if (st > 0) {
                this.entityData.set(DATA_STEAM_BURST_TICKS, st - 1);
            }
        }
    }

    public int getSteamBurstTicks() {
        return this.entityData.get(DATA_STEAM_BURST_TICKS);
    }

    public void setSteamBurstTicks(int ticks) {
        this.entityData.set(DATA_STEAM_BURST_TICKS, ticks);
    }

    @Override
    protected void spawnSteamParticles() {
        super.spawnSteamParticles();

        // Additional intense steam for colossal scale
        if (tickCount % 2 == 0) {
            for (int i = 0; i < 3; i++) {
                level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        this.getX() + (random.nextDouble() - 0.5) * 6.0,
                        this.getY() + 10.0 + random.nextDouble() * 10.0,
                        this.getZ() + (random.nextDouble() - 0.5) * 6.0,
                        0.0, 0.1, 0.0);
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        int min = Config.COLOSSAL_ESSENCE_DROP_MIN.get();
        int max = Config.COLOSSAL_ESSENCE_DROP_MAX.get();
        int count = min + level.random.nextInt(Math.max(1, max - min + 1));
        this.spawnAtLocation(new ItemStack(ModItems.TITAN_ESSENCE.get(), count));
    }
}
