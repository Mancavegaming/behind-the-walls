package com.beyondthewalls.entity;

import com.beyondthewalls.Config;
import com.beyondthewalls.grapple.GrapplingState;
import com.beyondthewalls.init.ModAttachments;
import com.beyondthewalls.init.ModEntities;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class GrapplingHookEntity extends ThrowableProjectile {
    private static final int MAX_LIFETIME = 60; // 3 seconds

    public GrapplingHookEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public GrapplingHookEntity(Level level, Player player) {
        super(ModEntities.GRAPPLING_HOOK.get(), player, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected double getDefaultGravity() {
        return Config.HOOK_GRAVITY.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide && getOwner() instanceof Player player) {
            Vec3 hitPos = result.getLocation();
            GrapplingState state = player.getData(ModAttachments.GRAPPLING_STATE.get());
            state.activate(hitPos);
        }
        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        // Don't interact with entities - just pass through
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= MAX_LIFETIME) {
            discard();
        }
    }
}
