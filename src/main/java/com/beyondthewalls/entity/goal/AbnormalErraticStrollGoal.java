package com.beyondthewalls.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AbnormalErraticStrollGoal extends WaterAvoidingRandomStrollGoal {

    public AbnormalErraticStrollGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        // 30% chance of a doubled-range random position for erratic movement
        if (this.mob.getRandom().nextFloat() < 0.3F) {
            Vec3 pos = DefaultRandomPos.getPos(this.mob, 20, 7);
            if (pos != null) return pos;
        }
        return super.getPosition();
    }
}
