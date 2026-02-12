package com.beyondthewalls.blockentity;

import com.beyondthewalls.Config;
import com.beyondthewalls.init.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GasRefineryBlockEntity extends BlockEntity {

    private int fuel;
    private int progress;
    private int readyCartridges;

    public GasRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GAS_REFINERY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GasRefineryBlockEntity be) {
        if (be.fuel > 0) {
            be.progress++;
            if (be.progress >= Config.REFINERY_SPEED.get()) {
                be.progress = 0;
                be.fuel--;
                be.readyCartridges += Config.REFINERY_OUTPUT.get();
                be.setChanged();
            }
        }
    }

    public boolean addFuel(int amount) {
        this.fuel += amount;
        this.setChanged();
        return true;
    }

    public int collectCartridges() {
        int ready = this.readyCartridges;
        this.readyCartridges = 0;
        this.setChanged();
        return ready;
    }

    public int getFuel() { return fuel; }
    public int getProgress() { return progress; }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Fuel", fuel);
        tag.putInt("Progress", progress);
        tag.putInt("ReadyCartridges", readyCartridges);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.fuel = tag.getInt("Fuel");
        this.progress = tag.getInt("Progress");
        this.readyCartridges = tag.getInt("ReadyCartridges");
    }
}
