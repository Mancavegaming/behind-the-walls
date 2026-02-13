package com.beyondthewalls.event;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.entity.AbnormalTitanEntity;
import com.beyondthewalls.entity.ArmoredTitanEntity;
import com.beyondthewalls.entity.ColossalTitanEntity;
import com.beyondthewalls.entity.TitanEntity;
import com.beyondthewalls.init.ModEntities;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = BeyondTheWalls.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TitanEvents {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TITAN.get(), TitanEntity.createAttributes().build());
        event.put(ModEntities.ABNORMAL_TITAN.get(), AbnormalTitanEntity.createAttributes().build());
        event.put(ModEntities.ARMORED_TITAN.get(), ArmoredTitanEntity.createAttributes().build());
        event.put(ModEntities.COLOSSAL_TITAN.get(), ColossalTitanEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.TITAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TitanEntity::checkTitanSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.AND);

        event.register(ModEntities.ABNORMAL_TITAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TitanEntity::checkTitanSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.AND);

        event.register(ModEntities.ARMORED_TITAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TitanEntity::checkTitanSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.AND);

        // Colossal: always-false spawn rule (never spawns naturally)
        event.register(ModEntities.COLOSSAL_TITAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, spawnType, pos, random) -> false,
                RegisterSpawnPlacementsEvent.Operation.AND);
    }
}
