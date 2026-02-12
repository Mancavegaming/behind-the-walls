package com.beyondthewalls.world;

import com.beyondthewalls.BeyondTheWalls;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class DistrictManager extends SavedData {

    private static final String DATA_NAME = BeyondTheWalls.MODID + "_districts";

    private final Map<BlockPos, Integer> districts = new HashMap<>();

    public DistrictManager() {
    }

    public static DistrictManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(DistrictManager::new, DistrictManager::load),
                DATA_NAME);
    }

    public void addDistrict(BlockPos pos, int radius) {
        districts.put(pos, radius);
        setDirty();
    }

    public void removeDistrict(BlockPos pos) {
        districts.remove(pos);
        setDirty();
    }

    public boolean isInsideAnyDistrict(BlockPos pos) {
        for (Map.Entry<BlockPos, Integer> entry : districts.entrySet()) {
            BlockPos center = entry.getKey();
            int radius = entry.getValue();
            double distSq = center.distSqr(pos);
            if (distSq <= (double) radius * radius) {
                return true;
            }
        }
        return false;
    }

    public BlockPos getNearestDistrictCenter(BlockPos pos) {
        BlockPos nearest = null;
        double nearestDist = Double.MAX_VALUE;
        for (BlockPos center : districts.keySet()) {
            double dist = center.distSqr(pos);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearest = center;
            }
        }
        return nearest;
    }

    public Map<BlockPos, Integer> getDistricts() {
        return districts;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (Map.Entry<BlockPos, Integer> entry : districts.entrySet()) {
            CompoundTag districtTag = new CompoundTag();
            districtTag.putInt("X", entry.getKey().getX());
            districtTag.putInt("Y", entry.getKey().getY());
            districtTag.putInt("Z", entry.getKey().getZ());
            districtTag.putInt("Radius", entry.getValue());
            list.add(districtTag);
        }
        tag.put("Districts", list);
        return tag;
    }

    public static DistrictManager load(CompoundTag tag, HolderLookup.Provider registries) {
        DistrictManager manager = new DistrictManager();
        ListTag list = tag.getList("Districts", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag districtTag = list.getCompound(i);
            BlockPos pos = new BlockPos(districtTag.getInt("X"), districtTag.getInt("Y"), districtTag.getInt("Z"));
            int radius = districtTag.getInt("Radius");
            manager.districts.put(pos, radius);
        }
        return manager;
    }
}
