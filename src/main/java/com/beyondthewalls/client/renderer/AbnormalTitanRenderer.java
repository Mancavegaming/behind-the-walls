package com.beyondthewalls.client.renderer;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.AbnormalTitanModel;
import com.beyondthewalls.entity.AbnormalTitanEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AbnormalTitanRenderer extends MobRenderer<AbnormalTitanEntity, AbnormalTitanModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/entity/abnormal_titan.png");

    public AbnormalTitanRenderer(EntityRendererProvider.Context context) {
        super(context, new AbnormalTitanModel(context.bakeLayer(AbnormalTitanModel.LAYER_LOCATION)), 2.0F);
        this.addLayer(new AbnormalTitanGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AbnormalTitanEntity entity) {
        return TEXTURE;
    }
}
