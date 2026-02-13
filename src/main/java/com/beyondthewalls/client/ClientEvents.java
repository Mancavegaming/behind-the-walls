package com.beyondthewalls.client;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.AbnormalTitanModel;
import com.beyondthewalls.client.model.ArmoredTitanModel;
import com.beyondthewalls.client.model.ColossalTitanModel;
import com.beyondthewalls.client.model.TitanModel;
import com.beyondthewalls.client.renderer.AbnormalTitanRenderer;
import com.beyondthewalls.client.renderer.ArmoredTitanRenderer;
import com.beyondthewalls.client.renderer.ColossalTitanRenderer;
import com.beyondthewalls.client.renderer.TitanRenderer;
import com.beyondthewalls.init.ModEntities;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = BeyondTheWalls.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK.get(), GrapplingHookRenderer::new);
        event.registerEntityRenderer(ModEntities.TITAN.get(), TitanRenderer::new);
        event.registerEntityRenderer(ModEntities.ABNORMAL_TITAN.get(), AbnormalTitanRenderer::new);
        event.registerEntityRenderer(ModEntities.ARMORED_TITAN.get(), ArmoredTitanRenderer::new);
        event.registerEntityRenderer(ModEntities.COLOSSAL_TITAN.get(), ColossalTitanRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TitanModel.LAYER_LOCATION, TitanModel::createBodyLayer);
        event.registerLayerDefinition(AbnormalTitanModel.LAYER_LOCATION, AbnormalTitanModel::createBodyLayer);
        event.registerLayerDefinition(ArmoredTitanModel.LAYER_LOCATION, ArmoredTitanModel::createBodyLayer);
        event.registerLayerDefinition(ColossalTitanModel.LAYER_LOCATION, ColossalTitanModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.HOTBAR,
                ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "gas_meter"),
                new GasMeterOverlay()
        );
    }
}
