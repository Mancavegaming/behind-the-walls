package com.beyondthewalls;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.beyondthewalls.grapple.GrapplingHandler;
import com.beyondthewalls.init.ModAttachments;
import com.beyondthewalls.init.ModCreativeTabs;
import com.beyondthewalls.init.ModEntities;
import com.beyondthewalls.init.ModItems;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(BeyondTheWalls.MODID)
public class BeyondTheWalls {
    public static final String MODID = "beyondthewalls";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BeyondTheWalls(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(new GrapplingHandler());

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
