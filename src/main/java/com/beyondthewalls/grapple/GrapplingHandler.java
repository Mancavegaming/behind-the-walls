package com.beyondthewalls.grapple;

import com.beyondthewalls.Config;
import com.beyondthewalls.init.ModAttachments;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class GrapplingHandler {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Server-side only
        if (player.level().isClientSide) {
            return;
        }

        GrapplingState state = player.getData(ModAttachments.GRAPPLING_STATE.get());
        if (!state.isActive()) {
            return;
        }

        state.incrementTicks();

        Vec3 target = state.getTargetPos();
        Vec3 playerPos = player.position().add(0, player.getEyeHeight() * 0.5, 0);
        Vec3 direction = target.subtract(playerPos);
        double distance = direction.length();

        double arrivalDist = Config.GRAPPLE_ARRIVAL_DISTANCE.get();
        int maxTicks = Config.GRAPPLE_MAX_TICKS.getAsInt();

        // Arrival or timeout
        if (distance <= arrivalDist || state.getTicksActive() >= maxTicks) {
            // Preserve momentum + small upward boost
            Vec3 currentVel = player.getDeltaMovement();
            player.setDeltaMovement(currentVel.x, Math.max(currentVel.y, 0.4), currentVel.z);
            player.hurtMarked = true;
            player.fallDistance = 0.0F;
            state.reset();
            return;
        }

        // Pull player toward hook point
        double pullSpeed = Config.GRAPPLE_PULL_SPEED.get();
        Vec3 velocity = direction.normalize().scale(pullSpeed);
        player.setDeltaMovement(velocity);
        player.hurtMarked = true;

        // Reset fall distance during pull (no fall damage while grappling)
        player.fallDistance = 0.0F;
    }
}
