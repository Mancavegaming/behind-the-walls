package com.beyondthewalls.client.renderer;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.client.model.ColossalTitanModel;
import com.beyondthewalls.entity.ColossalTitanEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ColossalTitanGlowLayer extends RenderLayer<ColossalTitanEntity, ColossalTitanModel> {
    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/entity/colossal_titan_glow.png");

    public ColossalTitanGlowLayer(RenderLayerParent<ColossalTitanEntity, ColossalTitanModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       ColossalTitanEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(GLOW_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
    }
}
