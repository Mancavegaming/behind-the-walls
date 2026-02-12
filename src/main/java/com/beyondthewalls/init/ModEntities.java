package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.entity.GrapplingHookEntity;
import com.beyondthewalls.entity.TitanEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, BeyondTheWalls.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<GrapplingHookEntity>> GRAPPLING_HOOK =
            ENTITY_TYPES.register("grappling_hook", () -> EntityType.Builder.<GrapplingHookEntity>of(
                            GrapplingHookEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("grappling_hook"));

    public static final DeferredHolder<EntityType<?>, EntityType<TitanEntity>> TITAN =
            ENTITY_TYPES.register("titan", () -> EntityType.Builder.<TitanEntity>of(
                            TitanEntity::new, MobCategory.MONSTER)
                    .sized(2.5F, 7.0F)
                    .clientTrackingRange(10)
                    .updateInterval(3)
                    .build("titan"));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
