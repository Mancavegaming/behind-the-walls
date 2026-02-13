package com.beyondthewalls.client.model;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.entity.AbnormalTitanEntity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AbnormalTitanModel extends HierarchicalModel<AbnormalTitanEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "abnormal_titan"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart chest;
    private final ModelPart neck;
    private final ModelPart upperHead;
    private final ModelPart lowerJaw;
    private final ModelPart rightArm;
    private final ModelPart rightForearm;
    private final ModelPart leftArm;
    private final ModelPart leftForearm;
    private final ModelPart rightLeg;
    private final ModelPart rightLowerLeg;
    private final ModelPart leftLeg;
    private final ModelPart leftLowerLeg;

    public AbnormalTitanModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.chest = this.body.getChild("chest");
        this.neck = this.chest.getChild("neck");
        this.upperHead = this.neck.getChild("upper_head");
        this.lowerJaw = this.upperHead.getChild("lower_jaw");
        this.rightArm = this.chest.getChild("right_arm");
        this.rightForearm = this.rightArm.getChild("right_forearm");
        this.leftArm = this.chest.getChild("left_arm");
        this.leftForearm = this.leftArm.getChild("left_forearm");
        this.rightLeg = root.getChild("right_leg");
        this.rightLowerLeg = this.rightLeg.getChild("right_lower_leg");
        this.leftLeg = root.getChild("left_leg");
        this.leftLowerLeg = this.leftLeg.getChild("left_lower_leg");
    }

    public static LayerDefinition createBodyLayer() {
        // Lankier proportions: thinner torso, longer arms/legs, smaller head, no rib overlay
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partDefinition = mesh.getRoot();

        // Thinner body/waist
        PartDefinition body = partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, -10.0F, -4.0F, 12.0F, 10.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -40.0F, 0.0F));

        // Thinner chest
        PartDefinition chest = body.addOrReplaceChild("chest",
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-12.0F, -28.0F, -6.0F, 24.0F, 28.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -10.0F, 0.0F));

        // No rib overlay for abnormal

        // Neck
        PartDefinition neck = chest.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -28.0F, 0.0F));

        // Smaller head
        PartDefinition upperHead = neck.addOrReplaceChild("upper_head",
                CubeListBuilder.create()
                        .texOffs(0, 90)
                        .addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -6.0F, 0.0F));

        upperHead.addOrReplaceChild("lower_jaw",
                CubeListBuilder.create()
                        .texOffs(0, 116)
                        .addBox(-4.0F, 0.0F, -8.0F, 8.0F, 3.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Longer arms
        PartDefinition rightArm = chest.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(100, 0)
                        .addBox(-6.0F, -2.0F, -3.0F, 6.0F, 26.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(-12.0F, -26.0F, 0.0F));

        PartDefinition rightForearm = rightArm.addOrReplaceChild("right_forearm",
                CubeListBuilder.create()
                        .texOffs(100, 34)
                        .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 24.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(-3.0F, 24.0F, 0.0F));

        rightForearm.addOrReplaceChild("right_hand",
                CubeListBuilder.create()
                        .texOffs(100, 64)
                        .addBox(-3.0F, 0.0F, -2.0F, 6.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leftArm = chest.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(140, 0)
                        .addBox(0.0F, -2.0F, -3.0F, 6.0F, 26.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(12.0F, -26.0F, 0.0F));

        PartDefinition leftForearm = leftArm.addOrReplaceChild("left_forearm",
                CubeListBuilder.create()
                        .texOffs(140, 34)
                        .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 24.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(3.0F, 24.0F, 0.0F));

        leftForearm.addOrReplaceChild("left_hand",
                CubeListBuilder.create()
                        .texOffs(140, 64)
                        .addBox(-3.0F, 0.0F, -2.0F, 6.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        // Longer legs
        PartDefinition rightLeg = partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(200, 0)
                        .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 30.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(-4.0F, -40.0F, 0.0F));

        PartDefinition rightLowerLeg = rightLeg.addOrReplaceChild("right_lower_leg",
                CubeListBuilder.create()
                        .texOffs(200, 42)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 26.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 30.0F, 0.0F));

        rightLowerLeg.addOrReplaceChild("right_foot",
                CubeListBuilder.create()
                        .texOffs(200, 78)
                        .addBox(-4.0F, 0.0F, -7.0F, 8.0F, 4.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition leftLeg = partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(250, 0)
                        .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 30.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(4.0F, -40.0F, 0.0F));

        PartDefinition leftLowerLeg = leftLeg.addOrReplaceChild("left_lower_leg",
                CubeListBuilder.create()
                        .texOffs(250, 42)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 26.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 30.0F, 0.0F));

        leftLowerLeg.addOrReplaceChild("left_foot",
                CubeListBuilder.create()
                        .texOffs(250, 78)
                        .addBox(-4.0F, 0.0F, -7.0F, 8.0F, 4.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 26.0F, 0.0F));

        return LayerDefinition.create(mesh, 512, 256);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(AbnormalTitanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        boolean hasTarget = entity.getTarget() != null;
        float partialTick = ageInTicks - entity.tickCount;
        float attackProgress = entity.getAttackAnim(partialTick);

        // Head tracking with random twitches
        this.upperHead.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.8F;
        this.upperHead.xRot = headPitch * Mth.DEG_TO_RAD * 0.8F;
        this.neck.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.2F;

        // Erratic head twitches
        if (ageInTicks % 20 < 3) {
            this.upperHead.zRot = Mth.sin(ageInTicks * 2.0F) * 0.3F;
            this.upperHead.yRot += Mth.cos(ageInTicks * 1.5F) * 0.2F;
        }
        if (hasTarget) {
            this.upperHead.zRot += Mth.sin(ageInTicks * 0.1F) * 0.15F;
        }

        // Faster idle sway
        float idleSway = Mth.sin(ageInTicks * 0.06F);
        this.body.zRot += idleSway * 0.03F;
        this.chest.zRot += idleSway * 0.02F;
        this.rightArm.zRot += Mth.sin(ageInTicks * 0.08F) * 0.08F;
        this.leftArm.zRot += -Mth.sin(ageInTicks * 0.08F) * 0.08F;

        // Faster walk cycle (0.6F frequency) with wider arm swings
        float walkFreq = 0.6F;
        float walkSwing = Mth.cos(limbSwing * walkFreq) * limbSwingAmount;

        this.rightLeg.xRot += walkSwing * 1.0F;
        this.leftLeg.xRot += -walkSwing * 1.0F;

        float kneeBase = 0.15F;
        this.rightLowerLeg.xRot += kneeBase + Math.max(0, walkSwing) * 0.5F;
        this.leftLowerLeg.xRot += kneeBase + Math.max(0, -walkSwing) * 0.5F;

        // Wider arm swings
        this.rightArm.xRot += -walkSwing * 0.6F;
        this.leftArm.xRot += walkSwing * 0.6F;
        this.rightForearm.xRot += 0.2F + Math.max(0, -walkSwing) * 0.4F;
        this.leftForearm.xRot += 0.2F + Math.max(0, walkSwing) * 0.4F;

        this.body.y += Mth.cos(limbSwing * walkFreq * 2.0F) * limbSwingAmount * 0.5F;
        this.chest.xRot += limbSwingAmount * 0.05F;

        // Attack swing
        if (attackProgress > 0.0F) {
            float windUp = Math.min(attackProgress * 2.0F, 1.0F);
            float strike = Math.max((attackProgress - 0.5F) * 2.0F, 0.0F);
            this.rightArm.xRot += -windUp * 1.8F + strike * 2.5F;
            this.rightForearm.xRot += -windUp * 0.8F + strike * 0.5F;
            this.chest.xRot += strike * 0.3F;
            this.body.xRot += strike * 0.1F;
        }

        // Jaw animation
        float breathCycle = Mth.sin(ageInTicks * 0.1F) * 0.08F + 0.08F;
        float jawAngle = breathCycle;
        if (attackProgress > 0.3F) {
            jawAngle += (attackProgress - 0.3F) * 1.2F;
        } else if (hasTarget) {
            jawAngle += 0.2F;
        }
        this.lowerJaw.xRot = jawAngle;
    }
}
