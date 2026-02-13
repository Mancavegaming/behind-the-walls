package com.beyondthewalls.entity.goal;

import com.beyondthewalls.entity.TitanEntity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class AbnormalLungeGoal extends Goal {

    private static final ResourceLocation LUNGE_SPEED_ID = ResourceLocation.fromNamespaceAndPath("beyondthewalls", "abnormal_lunge_speed");

    private final TitanEntity titan;
    private int lungeTicks;
    private int cooldown;

    public AbnormalLungeGoal(TitanEntity titan) {
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
        double dist = titan.distanceTo(target);
        return dist > 6.0 && dist < 20.0;
    }

    @Override
    public boolean canContinueToUse() {
        return lungeTicks > 0 && titan.getTarget() != null;
    }

    @Override
    public void start() {
        lungeTicks = 15;
        cooldown = 60;

        // Apply sprint speed boost
        AttributeInstance speedAttr = titan.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            speedAttr.removeModifier(LUNGE_SPEED_ID);
            speedAttr.addTransientModifier(new AttributeModifier(
                    LUNGE_SPEED_ID, 0.6, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    public void tick() {
        lungeTicks--;
        LivingEntity target = titan.getTarget();
        if (target == null) return;

        // Sprint toward target
        titan.getNavigation().moveTo(target, 1.5);

        // Leap on the first tick
        if (lungeTicks == 14) {
            Vec3 direction = target.position().subtract(titan.position()).normalize();
            titan.setDeltaMovement(
                    direction.x * 0.8,
                    0.5,
                    direction.z * 0.8
            );
            titan.hasImpulse = true;
        }
    }

    @Override
    public void stop() {
        lungeTicks = 0;
        AttributeInstance speedAttr = titan.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            speedAttr.removeModifier(LUNGE_SPEED_ID);
        }
    }
}
