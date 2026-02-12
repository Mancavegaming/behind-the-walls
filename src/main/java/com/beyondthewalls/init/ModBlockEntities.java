package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.blockentity.DistrictHeartBlockEntity;
import com.beyondthewalls.blockentity.GasRefineryBlockEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BeyondTheWalls.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GasRefineryBlockEntity>> GAS_REFINERY =
            BLOCK_ENTITIES.register("gas_refinery",
                    () -> BlockEntityType.Builder.of(GasRefineryBlockEntity::new, ModBlocks.GAS_REFINERY.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DistrictHeartBlockEntity>> DISTRICT_HEART =
            BLOCK_ENTITIES.register("district_heart",
                    () -> BlockEntityType.Builder.of(DistrictHeartBlockEntity::new, ModBlocks.DISTRICT_HEART.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
