package com.beyondthewalls.entity.goal;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.ColossalTitanEntity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class SteamBurstGoal extends Goal {

    private final ColossalTitanEntity titan;
    private int cooldown;

    public SteamBurstGoal(ColossalTitanEntity titan) {
        this.titan = titan;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        LivingEntity target = titan.getTarget();
        if (target == null) return false;
        double radius = Config.COLOSSAL_STEAM_RADIUS.getAsDouble();
        return titan.distanceTo(target) <= radius;
    }

    @Override
    public boolean canContinueToUse() {
        return false; // Single burst, no continuation
    }

    @Override
    public void start() {
        cooldown = Config.COLOSSAL_STEAM_COOLDOWN.get();
        double radius = Config.COLOSSAL_STEAM_RADIUS.getAsDouble();
        float damage = (float) Config.COLOSSAL_STEAM_DAMAGE.getAsDouble();

        // Set steam burst ticks for client animation
        titan.setSteamBurstTicks(20);

        // AOE damage and push
        AABB area = titan.getBoundingBox().inflate(radius);
        List<Entity> entities = titan.level().getEntities(titan, area);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living) {
                double dist = titan.distanceTo(living);
                if (dist <= radius) {
                    // Damage
                    living.hurt(titan.damageSources().mobAttack(titan), damage);

                    // Push: 1.5 horizontal + 0.4 upward
                    Vec3 push = living.position().subtract(titan.position()).normalize();
                    living.setDeltaMovement(
                            push.x * 1.5,
                            0.4,
                            push.z * 1.5
                    );
                    living.hasImpulse = true;
                    living.hurtMarked = true;
                }
            }
        }

        // Spawn particle burst ring on server (sends to clients)
        if (titan.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 60; i++) {
                double angle = (Math.PI * 2.0 / 60) * i;
                double px = titan.getX() + Math.cos(angle) * radius;
                double pz = titan.getZ() + Math.sin(angle) * radius;
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        px, titan.getY() + 1.0, pz,
                        3, 0.5, 1.0, 0.5, 0.05);
            }
            // Central burst
            serverLevel.sendParticles(ParticleTypes.EXPLOSION,
                    titan.getX(), titan.getY() + 5.0, titan.getZ(),
                    8, 3.0, 3.0, 3.0, 0.1);
        }
    }
}
