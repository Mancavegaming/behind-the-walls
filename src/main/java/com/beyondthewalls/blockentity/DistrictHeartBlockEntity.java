package com.beyondthewalls.blockentity;

import com.beyondthewalls.Config;
import com.beyondthewalls.init.ModBlockEntities;
import com.beyondthewalls.world.DistrictManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DistrictHeartBlockEntity extends BlockEntity {

    private int tier = 1;

    public DistrictHeartBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISTRICT_HEART.get(), pos, state);
    }

    public int getTier() { return tier; }

    public int getRadius() {
        return switch (tier) {
            case 1 -> Config.DISTRICT_RADIUS_T1.get();
            case 2 -> Config.DISTRICT_RADIUS_T2.get();
            case 3 -> Config.DISTRICT_RADIUS_T3.get();
            default -> Config.DISTRICT_RADIUS_T1.get();
        };
    }

    public void setTier(int tier) {
        this.tier = Math.max(1, Math.min(3, tier));
        this.setChanged();
        // Re-register with district manager
        if (level instanceof ServerLevel serverLevel) {
            DistrictManager manager = DistrictManager.get(serverLevel);
            manager.addDistrict(getBlockPos(), getRadius());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Tier", tier);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.tier = tag.getInt("Tier");
        if (this.tier < 1) this.tier = 1;
    }

    public void onLoad() {
        // Re-register with district manager on chunk load
        if (level instanceof ServerLevel serverLevel) {
            DistrictManager manager = DistrictManager.get(serverLevel);
            manager.addDistrict(getBlockPos(), getRadius());
        }
    }
}
