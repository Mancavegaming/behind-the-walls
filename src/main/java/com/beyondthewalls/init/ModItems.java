package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.item.SkyRiggingItem;
import com.beyondthewalls.item.UltrahardBladeItem;
import com.beyondthewalls.item.WallBuilderItem;
import com.beyondthewalls.item.WarHornItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BeyondTheWalls.MODID);

    public static final DeferredItem<SkyRiggingItem> SKY_RIGGING = ITEMS.register("sky_rigging",
            () -> new SkyRiggingItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> THRUST_CARTRIDGE = ITEMS.registerSimpleItem("thrust_cartridge",
            new Item.Properties().stacksTo(16));

    public static final DeferredItem<UltrahardBladeItem> ULTRAHARD_BLADES = ITEMS.register("ultrahard_blades",
            () -> new UltrahardBladeItem(ModTiers.ULTRAHARD_STEEL,
                    new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.ULTRAHARD_STEEL, 3, -2.0F))));

    public static final DeferredItem<Item> BLADE_INSERT = ITEMS.registerSimpleItem("blade_insert",
            new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> BROKEN_BLADE_HANDLES = ITEMS.registerSimpleItem("broken_blade_handles",
            new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> TITAN_ESSENCE = ITEMS.registerSimpleItem("titan_essence",
            new Item.Properties().stacksTo(64));

    public static final DeferredItem<WallBuilderItem> WALL_BUILDER = ITEMS.register("wall_builder",
            () -> new WallBuilderItem(new Item.Properties().durability(128)));

    public static final DeferredItem<WarHornItem> WAR_HORN = ITEMS.register("war_horn",
            () -> new WarHornItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
