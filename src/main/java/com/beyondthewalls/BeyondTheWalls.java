package com.beyondthewalls;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.beyondthewalls.grapple.GrapplingHandler;
import com.beyondthewalls.init.ModAttachments;
import com.beyondthewalls.init.ModBlockEntities;
import com.beyondthewalls.init.ModBlocks;
import com.beyondthewalls.init.ModCreativeTabs;
import com.beyondthewalls.init.ModEntities;
import com.beyondthewalls.init.ModItems;
import com.beyondthewalls.init.ModStructures;
import com.beyondthewalls.world.WaveManager;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod(BeyondTheWalls.MODID)
public class BeyondTheWalls {
    public static final String MODID = "beyondthewalls";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BeyondTheWalls(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModStructures.register(modEventBus);

        NeoForge.EVENT_BUS.register(new GrapplingHandler());
        NeoForge.EVENT_BUS.addListener(this::onServerTick);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void onServerTick(ServerTickEvent.Post event) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            if (level.dimension() == Level.OVERWORLD) {
                WaveManager.get(level).tick(level);
            }
        }
    }
}
