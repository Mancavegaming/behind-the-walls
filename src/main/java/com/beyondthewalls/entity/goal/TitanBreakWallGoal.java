package com.beyondthewalls.entity.goal;

import com.beyondthewalls.block.WallBlock;
import com.beyondthewalls.block.WallGateBlock;
import com.beyondthewalls.entity.TitanEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class TitanBreakWallGoal extends Goal {

    private final TitanEntity titan;
    private BlockPos targetWall;
    private int breakProgress;
    protected int breakTime;
    private int searchCooldown;

    public TitanBreakWallGoal(TitanEntity titan) {
        this.titan = titan;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Skip if titan has a player target within 10 blocks
        if (titan.getTarget() != null && titan.distanceTo(titan.getTarget()) < 10.0) {
            return false;
        }

        if (searchCooldown > 0) {
            searchCooldown--;
            return false;
        }
        searchCooldown = 40;

        targetWall = findNearestWall();
        return targetWall != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (targetWall == null) return false;
        // Stop if player gets close
        if (titan.getTarget() != null && titan.distanceTo(titan.getTarget()) < 10.0) {
            return false;
        }
        BlockState state = titan.level().getBlockState(targetWall);
        return isWallBlock(state.getBlock());
    }

    @Override
    public void start() {
        breakProgress = 0;
        BlockState state = titan.level().getBlockState(targetWall);
        Block block = state.getBlock();
        if (block instanceof WallBlock wallBlock) {
            breakTime = wallBlock.getTier().getTitanBreakTicks();
        } else {
            breakTime = 60; // Default for gate blocks
        }
    }

    @Override
    public void tick() {
        if (targetWall == null) return;

        // Move toward wall
        double dist = titan.distanceToSqr(targetWall.getX() + 0.5, targetWall.getY(), targetWall.getZ() + 0.5);
        if (dist > 4.0) {
            titan.getNavigation().moveTo(targetWall.getX() + 0.5, targetWall.getY(), targetWall.getZ() + 0.5, 1.0);
            return;
        }

        titan.getNavigation().stop();
        titan.getLookControl().setLookAt(targetWall.getX() + 0.5, targetWall.getY() + 0.5, targetWall.getZ() + 0.5);

        breakProgress++;

        // Show block crack animation
        Level level = titan.level();
        int destroyStage = (int)((float) breakProgress / breakTime * 10.0F);
        level.destroyBlockProgress(titan.getId(), targetWall, destroyStage);

        if (breakProgress >= breakTime) {
            // Destroy the wall and adjacent 3x3 area
            destroyWallArea(level, targetWall);
            targetWall = null;
        }
    }

    @Override
    public void stop() {
        if (targetWall != null) {
            titan.level().destroyBlockProgress(titan.getId(), targetWall, -1);
        }
        targetWall = null;
        breakProgress = 0;
    }

    private void destroyWallArea(Level level, BlockPos center) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (isWallBlock(state.getBlock())) {
                        level.destroyBlock(pos, true);
                    }
                }
            }
        }
    }

    private BlockPos findNearestWall() {
        BlockPos titanPos = titan.blockPosition();
        int range = 16;
        BlockPos nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for (int x = -range; x <= range; x++) {
            for (int y = -4; y <= 10; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = titanPos.offset(x, y, z);
                    BlockState state = titan.level().getBlockState(pos);
                    if (isWallBlock(state.getBlock())) {
                        double dist = titanPos.distSqr(pos);
                        if (dist < nearestDist) {
                            nearestDist = dist;
                            nearest = pos;
                        }
                    }
                }
            }
        }
        return nearest;
    }

    private boolean isWallBlock(Block block) {
        return block instanceof WallBlock || block instanceof WallGateBlock;
    }
}
