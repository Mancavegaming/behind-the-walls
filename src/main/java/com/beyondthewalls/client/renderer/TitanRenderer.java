package com.beyondthewalls.client.renderer;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.TitanModel;
import com.beyondthewalls.entity.TitanEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TitanRenderer extends MobRenderer<TitanEntity, TitanModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/entity/titan.png");

    public TitanRenderer(EntityRendererProvider.Context context) {
        super(context, new TitanModel(context.bakeLayer(TitanModel.LAYER_LOCATION)), 2.0F);
        this.addLayer(new TitanGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TitanEntity entity) {
        return TEXTURE;
    }
}
