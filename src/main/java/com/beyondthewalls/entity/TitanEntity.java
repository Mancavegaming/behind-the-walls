package com.beyondthewalls.entity;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.goal.TitanBreakWallGoal;
import com.beyondthewalls.init.ModItems;
import com.beyondthewalls.item.UltrahardBladeItem;
import com.beyondthewalls.world.DistrictManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

public class TitanEntity extends Monster {

    private static final EntityDataAccessor<Integer> DATA_DAMAGE_TICKS =
            SynchedEntityData.defineId(TitanEntity.class, EntityDataSerializers.INT);

    private final TitanPartEntity napePart;
    private final TitanPartEntity[] subEntities;

    public TitanEntity(EntityType<? extends TitanEntity> type, Level level) {
        super(type, level);
        this.napePart = new TitanPartEntity(this, "nape", 1.0F, 1.0F);
        this.subEntities = new TitanPartEntity[]{this.napePart};
        this.setId(ENTITY_COUNTER.getAndAdd(this.subEntities.length + 1) + 1);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DAMAGE_TICKS, 0);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.subEntities.length; i++) {
            this.subEntities[i].setId(id + i + 1);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 12.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.STEP_HEIGHT, 1.5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new TitanBreakWallGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.subEntities;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Trigger damage burst particles via synched data
        if (!level().isClientSide) {
            this.entityData.set(DATA_DAMAGE_TICKS, 5);
        }
        // Body hits receive reduced damage
        float multiplier = (float) Config.BODY_DAMAGE_MULTIPLIER.getAsDouble();
        return super.hurt(source, amount * multiplier);
    }

    public boolean hurtFromPart(TitanPartEntity part, DamageSource source, float amount) {
        if ("nape".equals(part.getPartName())) {
            float multiplier = calculateNapeMultiplier(source);

            // Check if attacker is using Ultrahard Blades
            if (source.getEntity() instanceof LivingEntity attacker) {
                if (attacker.getMainHandItem().getItem() instanceof UltrahardBladeItem) {
                    multiplier *= (float) Config.BLADE_NAPE_BONUS.getAsDouble();
                }
            }

            // Trigger damage burst particles
            if (!level().isClientSide) {
                this.entityData.set(DATA_DAMAGE_TICKS, 5);
            }

            return super.hurt(source, amount * multiplier);
        }
        return this.hurt(source, amount);
    }

    private float calculateNapeMultiplier(DamageSource source) {
        Entity attacker = source.getEntity();
        if (attacker == null) {
            return 1.0F;
        }

        // Get direction from titan to attacker
        Vec3 toAttacker = attacker.position().subtract(this.position()).normalize();

        // Get the titan's facing direction (horizontal only)
        Vec3 facing = Vec3.directionFromRotation(0, this.getYRot()).normalize();

        // Dot product: 1 = same direction (attacker in front), -1 = opposite (attacker behind)
        double dot = facing.dot(new Vec3(toAttacker.x, 0, toAttacker.z).normalize());

        if (dot < -0.3) {
            // Behind the titan — full nape bonus
            return (float) Config.NAPE_BONUS_MULTIPLIER.getAsDouble();
        } else if (dot < 0.3) {
            // Side of the titan
            return 0.5F;
        } else {
            // Front of the titan
            return 0.3F;
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Reposition nape hitbox at back of neck
        Vec3 facing = Vec3.directionFromRotation(0, this.getYRot()).normalize();
        double napeX = this.getX() - facing.x * 1.2;
        double napeY = this.getY() + 5.5;
        double napeZ = this.getZ() - facing.z * 1.2;
        this.napePart.setPos(napeX, napeY, napeZ);

        // Decrement damage burst timer on server
        if (!level().isClientSide) {
            int dt = this.entityData.get(DATA_DAMAGE_TICKS);
            if (dt > 0) {
                this.entityData.set(DATA_DAMAGE_TICKS, dt - 1);
            }
        }

        // Steam particles (client-side only)
        if (level().isClientSide) {
            spawnSteamParticles();
        }
    }

    private void spawnSteamParticles() {
        // Skip if no player nearby (performance)
        Player nearest = level().getNearestPlayer(this, 64.0);
        if (nearest == null) {
            return;
        }

        // Ambient steam from shoulders and nape (every 2 ticks)
        if (tickCount % 2 == 0) {
            double shoulderY = this.getY() + 5.0;

            // Left shoulder
            level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + 0.8 + (random.nextDouble() - 0.5) * 0.3,
                    shoulderY + random.nextDouble() * 0.5,
                    this.getZ() + (random.nextDouble() - 0.5) * 0.3,
                    0.0, 0.05, 0.0);

            // Right shoulder
            level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() - 0.8 + (random.nextDouble() - 0.5) * 0.3,
                    shoulderY + random.nextDouble() * 0.5,
                    this.getZ() + (random.nextDouble() - 0.5) * 0.3,
                    0.0, 0.05, 0.0);

            // Nape area
            Vec3 facing = Vec3.directionFromRotation(0, this.getYRot()).normalize();
            level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + facing.x * 0.8,
                    this.getY() + 5.5 + random.nextDouble() * 0.3,
                    this.getZ() + facing.z * 0.8,
                    0.0, 0.07, 0.0);
        }

        // Body steam: sparse cloud particles (every 3 ticks)
        if (tickCount % 3 == 0) {
            double bodyY = this.getY() + 2.5 + random.nextDouble() * 2.5;
            level().addParticle(ParticleTypes.CLOUD,
                    this.getX() + (random.nextDouble() - 0.5) * 1.0,
                    bodyY,
                    this.getZ() + (random.nextDouble() - 0.5) * 1.0,
                    0.0, 0.02, 0.0);
        }

        // Damage burst: 3 particles/tick for 5 ticks = 15 total
        int damageTicks = this.entityData.get(DATA_DAMAGE_TICKS);
        if (damageTicks > 0) {
            for (int i = 0; i < 3; i++) {
                level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        this.getX() + (random.nextDouble() - 0.5) * 2.0,
                        this.getY() + random.nextDouble() * 6.0,
                        this.getZ() + (random.nextDouble() - 0.5) * 2.0,
                        (random.nextDouble() - 0.5) * 0.1,
                        0.1 + random.nextDouble() * 0.1,
                        (random.nextDouble() - 0.5) * 0.1);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Recalculate bounding boxes for sub-entities each tick
        for (TitanPartEntity part : this.subEntities) {
            part.xo = part.getX();
            part.yo = part.getY();
            part.zo = part.getZ();
            part.xOld = part.getX();
            part.yOld = part.getY();
            part.zOld = part.getZ();
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int min = Config.ESSENCE_DROP_MIN.get();
        int max = Config.ESSENCE_DROP_MAX.get();
        int count = min + level.random.nextInt(Math.max(1, max - min + 1));
        this.spawnAtLocation(new ItemStack(ModItems.TITAN_ESSENCE.get(), count));
    }

    public static boolean checkTitanSpawnRules(EntityType<TitanEntity> type, ServerLevelAccessor level,
                                                MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        // Require open sky
        if (!level.canSeeSky(pos)) {
            return false;
        }
        // Require daytime (sky light >= 8)
        if (level.getBrightness(LightLayer.SKY, pos) < 8) {
            return false;
        }
        // Require solid ground below
        if (!level.getBlockState(pos.below()).isSolid()) {
            return false;
        }
        // Block spawns inside districts
        if (level instanceof ServerLevel serverLevel) {
            if (DistrictManager.get(serverLevel).isInsideAnyDistrict(pos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }
}
