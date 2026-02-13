package com.beyondthewalls.client.renderer;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.ArmoredTitanModel;
import com.beyondthewalls.entity.ArmoredTitanEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ArmoredTitanRenderer extends MobRenderer<ArmoredTitanEntity, ArmoredTitanModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/entity/armored_titan.png");

    public ArmoredTitanRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmoredTitanModel(context.bakeLayer(ArmoredTitanModel.LAYER_LOCATION)), 2.5F);
        this.addLayer(new ArmoredTitanGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ArmoredTitanEntity entity) {
        return TEXTURE;
    }
}
