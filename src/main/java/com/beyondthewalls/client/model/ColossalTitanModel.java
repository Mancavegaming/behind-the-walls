package com.beyondthewalls.client.model;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.entity.ColossalTitanEntity;

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

public class ColossalTitanModel extends HierarchicalModel<ColossalTitanEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "colossal_titan"), "main");

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

    public ColossalTitanModel(ModelPart root) {
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
        // Same base skeleton as TitanModel but with muscle overlay (CubeDeformation inflation)
        // and thicker rib overlay. Scale is handled by the renderer (3.5x).
        CubeDeformation muscleInflation = new CubeDeformation(1.5F);

        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partDefinition = mesh.getRoot();

        // Body with muscle overlay
        PartDefinition body = partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-8.0F, -10.0F, -5.0F, 16.0F, 10.0F, 10.0F, CubeDeformation.NONE)
                        .texOffs(52, 0)
                        .addBox(-8.0F, -10.0F, -5.0F, 16.0F, 10.0F, 10.0F, muscleInflation),
                PartPose.offset(0.0F, -40.0F, 0.0F));

        // Chest with muscle overlay
        PartDefinition chest = body.addOrReplaceChild("chest",
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-14.0F, -30.0F, -7.0F, 28.0F, 30.0F, 14.0F, CubeDeformation.NONE)
                        .texOffs(0, 168)
                        .addBox(-14.0F, -30.0F, -7.0F, 28.0F, 30.0F, 14.0F, muscleInflation),
                PartPose.offset(0.0F, -10.0F, 0.0F));

        // Thicker rib overlay
        chest.addOrReplaceChild("rib_overlay",
                CubeListBuilder.create()
                        .texOffs(0, 140)
                        .addBox(-13.0F, -22.0F, -1.0F, 26.0F, 18.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, -7.5F));

        // Neck
        PartDefinition neck = chest.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -30.0F, 0.0F));

        // Head
        PartDefinition upperHead = neck.addOrReplaceChild("upper_head",
                CubeListBuilder.create()
                        .texOffs(0, 90)
                        .addBox(-7.0F, -10.0F, -6.0F, 14.0F, 10.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -6.0F, 0.0F));

        upperHead.addOrReplaceChild("lower_jaw",
                CubeListBuilder.create()
                        .texOffs(0, 116)
                        .addBox(-6.0F, 0.0F, -10.0F, 12.0F, 4.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Arms with muscle inflation
        PartDefinition rightArm = chest.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(100, 0)
                        .addBox(-7.0F, -2.0F, -4.0F, 8.0F, 22.0F, 8.0F, CubeDeformation.NONE)
                        .texOffs(100, 100)
                        .addBox(-7.0F, -2.0F, -4.0F, 8.0F, 22.0F, 8.0F, muscleInflation),
                PartPose.offset(-14.0F, -28.0F, 0.0F));

        PartDefinition rightForearm = rightArm.addOrReplaceChild("right_forearm",
                CubeListBuilder.create()
                        .texOffs(100, 34)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 20.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(-3.0F, 20.0F, 0.0F));

        rightForearm.addOrReplaceChild("right_hand",
                CubeListBuilder.create()
                        .texOffs(100, 64)
                        .addBox(-3.5F, 0.0F, -2.0F, 7.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition leftArm = chest.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(140, 0)
                        .addBox(-1.0F, -2.0F, -4.0F, 8.0F, 22.0F, 8.0F, CubeDeformation.NONE)
                        .texOffs(140, 100)
                        .addBox(-1.0F, -2.0F, -4.0F, 8.0F, 22.0F, 8.0F, muscleInflation),
                PartPose.offset(14.0F, -28.0F, 0.0F));

        PartDefinition leftForearm = leftArm.addOrReplaceChild("left_forearm",
                CubeListBuilder.create()
                        .texOffs(140, 34)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 20.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(3.0F, 20.0F, 0.0F));

        leftForearm.addOrReplaceChild("left_hand",
                CubeListBuilder.create()
                        .texOffs(140, 64)
                        .addBox(-3.5F, 0.0F, -2.0F, 7.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        // Legs with muscle inflation
        PartDefinition rightLeg = partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(200, 0)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 28.0F, 9.0F, CubeDeformation.NONE)
                        .texOffs(200, 100)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 28.0F, 9.0F, muscleInflation),
                PartPose.offset(-5.0F, -40.0F, 0.0F));

        PartDefinition rightLowerLeg = rightLeg.addOrReplaceChild("right_lower_leg",
                CubeListBuilder.create()
                        .texOffs(200, 42)
                        .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 24.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        rightLowerLeg.addOrReplaceChild("right_foot",
                CubeListBuilder.create()
                        .texOffs(200, 78)
                        .addBox(-4.5F, 0.0F, -7.0F, 9.0F, 4.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leftLeg = partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(250, 0)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 28.0F, 9.0F, CubeDeformation.NONE)
                        .texOffs(250, 100)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 28.0F, 9.0F, muscleInflation),
                PartPose.offset(5.0F, -40.0F, 0.0F));

        PartDefinition leftLowerLeg = leftLeg.addOrReplaceChild("left_lower_leg",
                CubeListBuilder.create()
                        .texOffs(250, 42)
                        .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 24.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        leftLowerLeg.addOrReplaceChild("left_foot",
                CubeListBuilder.create()
                        .texOffs(250, 78)
                        .addBox(-4.5F, 0.0F, -7.0F, 9.0F, 4.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 512, 256);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(ColossalTitanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        boolean hasTarget = entity.getTarget() != null;
        float partialTick = ageInTicks - entity.tickCount;
        float attackProgress = entity.getAttackAnim(partialTick);
        int steamBurstTicks = entity.getSteamBurstTicks();

        // Head tracking (slow, ponderous)
        this.upperHead.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.6F;
        this.upperHead.xRot = headPitch * Mth.DEG_TO_RAD * 0.6F;
        this.neck.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.4F;

        // Minimal idle sway
        float idleSway = Mth.sin(ageInTicks * 0.02F);
        this.body.zRot += idleSway * 0.01F;
        this.rightArm.xRot += Mth.sin(ageInTicks * 0.03F) * 0.02F;
        this.leftArm.xRot += Mth.sin(ageInTicks * 0.03F + 0.5F) * 0.02F;

        // Very slow walk cycle (0.25F)
        float walkFreq = 0.25F;
        float walkSwing = Mth.cos(limbSwing * walkFreq) * limbSwingAmount;

        this.rightLeg.xRot += walkSwing * 0.5F;
        this.leftLeg.xRot += -walkSwing * 0.5F;

        float kneeBase = 0.1F;
        this.rightLowerLeg.xRot += kneeBase + Math.max(0, walkSwing) * 0.3F;
        this.leftLowerLeg.xRot += kneeBase + Math.max(0, -walkSwing) * 0.3F;

        this.rightArm.xRot += -walkSwing * 0.15F;
        this.leftArm.xRot += walkSwing * 0.15F;
        this.rightForearm.xRot += 0.1F + Math.max(0, -walkSwing) * 0.15F;
        this.leftForearm.xRot += 0.1F + Math.max(0, walkSwing) * 0.15F;

        this.body.y += Mth.cos(limbSwing * walkFreq * 2.0F) * limbSwingAmount * 0.3F;
        this.chest.xRot += limbSwingAmount * 0.03F;

        // Steam burst pose: arms spread, chest back, jaw open
        if (steamBurstTicks > 0) {
            float burstProgress = steamBurstTicks / 20.0F;
            this.rightArm.zRot += -burstProgress * 1.2F;
            this.leftArm.zRot += burstProgress * 1.2F;
            this.rightArm.xRot += -burstProgress * 0.3F;
            this.leftArm.xRot += -burstProgress * 0.3F;
            this.chest.xRot += -burstProgress * 0.2F;
            this.lowerJaw.xRot = burstProgress * 0.8F;
            return; // Skip normal attack/jaw during steam burst
        }

        // Attack swing
        if (attackProgress > 0.0F) {
            float windUp = Math.min(attackProgress * 2.0F, 1.0F);
            float strike = Math.max((attackProgress - 0.5F) * 2.0F, 0.0F);
            this.rightArm.xRot += -windUp * 1.5F + strike * 2.0F;
            this.rightForearm.xRot += -windUp * 0.6F + strike * 0.4F;
            this.chest.xRot += strike * 0.2F;
        }

        // Jaw
        float breathCycle = Mth.sin(ageInTicks * 0.06F) * 0.05F + 0.05F;
        float jawAngle = breathCycle;
        if (attackProgress > 0.3F) {
            jawAngle += (attackProgress - 0.3F) * 0.8F;
        } else if (hasTarget) {
            jawAngle += 0.1F;
        }
        this.lowerJaw.xRot = jawAngle;
    }
}
