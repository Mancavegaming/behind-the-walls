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
                        output.accept(ModItems.SKY_RIGGING.get());
                        output.accept(ModItems.THRUST_CARTRIDGE.get());
                    }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
