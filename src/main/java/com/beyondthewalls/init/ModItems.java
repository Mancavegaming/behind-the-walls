package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.item.SkyRiggingItem;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BeyondTheWalls.MODID);

    public static final DeferredItem<SkyRiggingItem> SKY_RIGGING = ITEMS.register("sky_rigging",
            () -> new SkyRiggingItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> THRUST_CARTRIDGE = ITEMS.registerSimpleItem("thrust_cartridge",
            new Item.Properties().stacksTo(16));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
