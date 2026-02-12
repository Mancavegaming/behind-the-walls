package com.beyondthewalls.grapple;

import net.minecraft.world.phys.Vec3;

public class GrapplingState {
    private boolean active;
    private Vec3 targetPos;
    private int ticksActive;

    public GrapplingState() {
        this.active = false;
        this.targetPos = Vec3.ZERO;
        this.ticksActive = 0;
    }

    public void activate(Vec3 target) {
        this.active = true;
        this.targetPos = target;
        this.ticksActive = 0;
    }

    public void reset() {
        this.active = false;
        this.targetPos = Vec3.ZERO;
        this.ticksActive = 0;
    }

    public boolean isActive() {
        return active;
    }

    public Vec3 getTargetPos() {
        return targetPos;
    }

    public int getTicksActive() {
        return ticksActive;
    }

    public void incrementTicks() {
        ticksActive++;
    }
}
