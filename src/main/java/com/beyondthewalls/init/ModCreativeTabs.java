package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondTheWalls.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BTW_TAB =
            CREATIVE_MODE_TABS.register("btw_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.beyondthewalls"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.SKY_RIGGING.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // Combat gear
                        output.accept(ModItems.SKY_RIGGING.get());
                        output.accept(ModItems.THRUST_CARTRIDGE.get());
                        output.accept(ModItems.ULTRAHARD_BLADES.get());
                        output.accept(ModItems.BLADE_INSERT.get());
                        output.accept(ModItems.BROKEN_BLADE_HANDLES.get());
                        // New items
                        output.accept(ModItems.TITAN_ESSENCE.get());
                        output.accept(ModItems.WALL_BUILDER.get());
                        output.accept(ModItems.WAR_HORN.get());
                        // Block items
                        output.accept(ModBlocks.WALL_MARIA_ITEM.get());
                        output.accept(ModBlocks.WALL_ROSE_ITEM.get());
                        output.accept(ModBlocks.WALL_SINA_ITEM.get());
                        output.accept(ModBlocks.WALL_FOUNDATION_ITEM.get());
                        output.accept(ModBlocks.WALL_GATE_ITEM.get());
                        output.accept(ModBlocks.BLACKSMITH_STATION_ITEM.get());
                        output.accept(ModBlocks.GAS_REFINERY_ITEM.get());
                        output.accept(ModBlocks.DISTRICT_HEART_ITEM.get());
                    }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
