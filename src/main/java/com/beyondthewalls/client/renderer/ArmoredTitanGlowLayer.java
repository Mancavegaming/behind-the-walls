package com.beyondthewalls.client.renderer;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.ArmoredTitanModel;
import com.beyondthewalls.entity.ArmoredTitanEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ArmoredTitanGlowLayer extends RenderLayer<ArmoredTitanEntity, ArmoredTitanModel> {
    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/entity/armored_titan_glow.png");

    public ArmoredTitanGlowLayer(RenderLayerParent<ArmoredTitanEntity, ArmoredTitanModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       ArmoredTitanEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(GLOW_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
    }
}
