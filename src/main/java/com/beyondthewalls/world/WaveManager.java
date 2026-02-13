package com.beyondthewalls.world;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.Config;
import com.beyondthewalls.init.ModEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;

public class WaveManager extends SavedData {

    private static final String DATA_NAME = BeyondTheWalls.MODID + "_waves";

    private int waveNumber = 0;
    private int countdown;
    private boolean active = false;

    public WaveManager() {
        this.countdown = Config.WAVE_INTERVAL_BASE.get();
    }

    public static WaveManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(WaveManager::new, WaveManager::load),
                DATA_NAME);
    }

    public void tick(ServerLevel level) {
        DistrictManager districts = DistrictManager.get(level);
        if (districts.getDistricts().isEmpty()) {
            return; // No districts, no waves
        }

        if (!active) {
            countdown--;
            if (countdown <= 0) {
                triggerWave(level);
            } else if (countdown == 600) { // 30 second warning
                for (ServerPlayer player : level.players()) {
                    player.displayClientMessage(Component.translatable("message.beyondthewalls.wave_warning"), false);
                }
            }
        }
    }

    public void triggerWave(ServerLevel level) {
        waveNumber++;
        int titanCount = Config.WAVE_BASE_TITANS.get() + waveNumber * Config.WAVE_SCALING.get();

        DistrictManager districts = DistrictManager.get(level);

        // Announce wave
        for (ServerPlayer player : level.players()) {
            player.displayClientMessage(Component.translatable("message.beyondthewalls.wave_start", waveNumber, titanCount), false);
            level.playSound(null, player.blockPosition(), SoundEvents.RAID_HORN.value(),
                    SoundSource.HOSTILE, 2.0F, 0.8F);
        }

        // Spawn titans around each district
        for (Map.Entry<BlockPos, Integer> entry : districts.getDistricts().entrySet()) {
            BlockPos center = entry.getKey();
            int radius = entry.getValue();

            // Spawn random titan types for the wave count
            for (int i = 0; i < titanCount; i++) {
                EntityType<?> titanType = selectTitanType(level);
                spawnTitanAtDistrict(level, titanType, center, radius);
            }

            // Spawn Colossal if eligible (fixed addition, 1 per district)
            if (shouldSpawnColossal()) {
                spawnTitanAtDistrict(level, ModEntities.COLOSSAL_TITAN.get(), center, radius);
            }
        }

        // Calculate next wave interval (shrinks with each wave)
        int interval = Config.WAVE_INTERVAL_BASE.get() - waveNumber * 1200;
        interval = Math.max(interval, Config.WAVE_INTERVAL_MIN.get());
        this.countdown = interval;
        this.active = false;
        setDirty();
    }

    public void triggerWaveWithColossal(ServerLevel level) {
        triggerWave(level);
        // Always spawn 1 Colossal at the first district (War Horn bonus)
        DistrictManager districts = DistrictManager.get(level);
        for (Map.Entry<BlockPos, Integer> entry : districts.getDistricts().entrySet()) {
            spawnTitanAtDistrict(level, ModEntities.COLOSSAL_TITAN.get(), entry.getKey(), entry.getValue());
            break; // Only 1 colossal for war horn
        }
    }

    private EntityType<?> selectTitanType(ServerLevel level) {
        // Weighted random selection based on wave number thresholds
        int normalWeight = 10;
        int abnormalWeight = waveNumber >= Config.WAVE_ABNORMAL_START.get() ? 4 : 0;
        int armoredWeight = waveNumber >= Config.WAVE_ARMORED_START.get() ? 2 : 0;

        int totalWeight = normalWeight + abnormalWeight + armoredWeight;
        int roll = level.random.nextInt(totalWeight);

        if (roll < normalWeight) {
            return ModEntities.TITAN.get();
        } else if (roll < normalWeight + abnormalWeight) {
            return ModEntities.ABNORMAL_TITAN.get();
        } else {
            return ModEntities.ARMORED_TITAN.get();
        }
    }

    private boolean shouldSpawnColossal() {
        int colossalStart = Config.WAVE_COLOSSAL_START.get();
        int colossalInterval = Config.WAVE_COLOSSAL_INTERVAL.get();

        if (waveNumber < colossalStart) return false;
        return (waveNumber - colossalStart) % colossalInterval == 0;
    }

    private void spawnTitanAtDistrict(ServerLevel level, EntityType<?> titanType, BlockPos center, int radius) {
        double angle = level.random.nextDouble() * Math.PI * 2;
        int spawnDist = radius + 10 + level.random.nextInt(20);
        int x = center.getX() + (int)(Math.cos(angle) * spawnDist);
        int z = center.getZ() + (int)(Math.sin(angle) * spawnDist);
        int y = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

        BlockPos spawnPos = new BlockPos(x, y, z);

        var titan = titanType.create(level, null, spawnPos, MobSpawnType.EVENT, true, false);
        if (titan != null) {
            level.addFreshEntity(titan);
        }
    }

    public int getWaveNumber() { return waveNumber; }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("WaveNumber", waveNumber);
        tag.putInt("Countdown", countdown);
        tag.putBoolean("Active", active);
        return tag;
    }

    public static WaveManager load(CompoundTag tag, HolderLookup.Provider registries) {
        WaveManager manager = new WaveManager();
        manager.waveNumber = tag.getInt("WaveNumber");
        manager.countdown = tag.getInt("Countdown");
        manager.active = tag.getBoolean("Active");
        return manager;
    }
}
