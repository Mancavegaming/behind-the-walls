package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.block.*;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BeyondTheWalls.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(BeyondTheWalls.MODID);

    // Wall blocks
    public static final DeferredBlock<WallBlock> WALL_MARIA = BLOCKS.register("wall_maria",
            () -> new WallBlock(WallTier.MARIA));
    public static final DeferredBlock<WallBlock> WALL_ROSE = BLOCKS.register("wall_rose",
            () -> new WallBlock(WallTier.ROSE));
    public static final DeferredBlock<WallBlock> WALL_SINA = BLOCKS.register("wall_sina",
            () -> new WallBlock(WallTier.SINA));

    // Foundation and gate
    public static final DeferredBlock<WallFoundationBlock> WALL_FOUNDATION = BLOCKS.register("wall_foundation",
            WallFoundationBlock::new);
    public static final DeferredBlock<WallGateBlock> WALL_GATE = BLOCKS.register("wall_gate",
            WallGateBlock::new);

    // Functional blocks
    public static final DeferredBlock<BlacksmithStationBlock> BLACKSMITH_STATION = BLOCKS.register("blacksmith_station",
            BlacksmithStationBlock::new);
    public static final DeferredBlock<GasRefineryBlock> GAS_REFINERY = BLOCKS.register("gas_refinery",
            GasRefineryBlock::new);
    public static final DeferredBlock<DistrictHeartBlock> DISTRICT_HEART = BLOCKS.register("district_heart",
            DistrictHeartBlock::new);

    // Block items
    public static final DeferredItem<BlockItem> WALL_MARIA_ITEM = BLOCK_ITEMS.register("wall_maria",
            () -> new BlockItem(WALL_MARIA.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> WALL_ROSE_ITEM = BLOCK_ITEMS.register("wall_rose",
            () -> new BlockItem(WALL_ROSE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> WALL_SINA_ITEM = BLOCK_ITEMS.register("wall_sina",
            () -> new BlockItem(WALL_SINA.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> WALL_FOUNDATION_ITEM = BLOCK_ITEMS.register("wall_foundation",
            () -> new BlockItem(WALL_FOUNDATION.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> WALL_GATE_ITEM = BLOCK_ITEMS.register("wall_gate",
            () -> new BlockItem(WALL_GATE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> BLACKSMITH_STATION_ITEM = BLOCK_ITEMS.register("blacksmith_station",
            () -> new BlockItem(BLACKSMITH_STATION.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> GAS_REFINERY_ITEM = BLOCK_ITEMS.register("gas_refinery",
            () -> new BlockItem(GAS_REFINERY.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> DISTRICT_HEART_ITEM = BLOCK_ITEMS.register("district_heart",
            () -> new BlockItem(DISTRICT_HEART.get(), new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }
}
