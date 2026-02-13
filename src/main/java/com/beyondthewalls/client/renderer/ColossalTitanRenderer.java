package com.beyondthewalls.client.renderer;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.ColossalTitanModel;
import com.beyondthewalls.entity.ColossalTitanEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ColossalTitanRenderer extends MobRenderer<ColossalTitanEntity, ColossalTitanModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/entity/colossal_titan.png");

    public ColossalTitanRenderer(EntityRendererProvider.Context context) {
        super(context, new ColossalTitanModel(context.bakeLayer(ColossalTitanModel.LAYER_LOCATION)), 4.0F);
        this.addLayer(new ColossalTitanGlowLayer(this));
    }

    @Override
    protected void scale(ColossalTitanEntity entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(3.5F, 3.5F, 3.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(ColossalTitanEntity entity) {
        return TEXTURE;
    }
}
