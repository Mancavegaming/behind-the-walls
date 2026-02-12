package com.beyondthewalls.client;

import com.beyondthewalls.entity.GrapplingHookEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix4f;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("beyondthewalls", "textures/entity/grappling_hook.png");

    public GrapplingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(GrapplingHookEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GrapplingHookEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Entity owner = entity.getOwner();
        if (owner == null) return;

        poseStack.pushPose();

        Vec3 hookPos = entity.position();
        Vec3 ownerPos = owner.getPosition(partialTick).add(0, owner.getEyeHeight() * 0.7, 0);
        Vec3 diff = ownerPos.subtract(hookPos);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.lineStrip());
        Matrix4f matrix = poseStack.last().pose();

        // Wire line from hook to player
        consumer.addVertex(matrix, 0, 0, 0)
                .setColor(80, 80, 80, 255)
                .setNormal(0, 1, 0);
        consumer.addVertex(matrix, (float) diff.x, (float) diff.y, (float) diff.z)
                .setColor(80, 80, 80, 255)
                .setNormal(0, 1, 0);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
