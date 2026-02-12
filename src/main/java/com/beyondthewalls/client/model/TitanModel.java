package com.beyondthewalls.client.model;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.entity.TitanEntity;

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

public class TitanModel extends HierarchicalModel<TitanEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "titan"), "main");

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

    public TitanModel(ModelPart root) {
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
        // Titan is 7 blocks (112 model units) tall
        // Ground level in model space: y=+16
        // Parts going UP use negative Y offsets
        // Part heights: foot(4) + lower_leg(24) + upper_leg(28) + waist(10) + chest(30) + neck(6) + head(10) = 112
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partDefinition = mesh.getRoot();

        // === TORSO ===

        // Body (waist/pelvis): pivot at hip level (y=-40)
        // Narrow waist for V-taper silhouette
        PartDefinition body = partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-8.0F, -10.0F, -5.0F, 16.0F, 10.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -40.0F, 0.0F));

        // Chest: wide shoulders, pivot at connection to waist
        PartDefinition chest = body.addOrReplaceChild("chest",
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-14.0F, -30.0F, -7.0F, 28.0F, 30.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -10.0F, 0.0F));

        // Rib overlay: thin detail on front of chest
        chest.addOrReplaceChild("rib_overlay",
                CubeListBuilder.create()
                        .texOffs(0, 140)
                        .addBox(-12.0F, -20.0F, -0.5F, 24.0F, 16.0F, 1.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, -7.5F));

        // === HEAD ===

        // Neck
        PartDefinition neck = chest.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -30.0F, 0.0F));

        // Upper head
        PartDefinition upperHead = neck.addOrReplaceChild("upper_head",
                CubeListBuilder.create()
                        .texOffs(0, 90)
                        .addBox(-7.0F, -10.0F, -6.0F, 14.0F, 10.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -6.0F, 0.0F));

        // Lower jaw: hinges at base of head, swings open
        upperHead.addOrReplaceChild("lower_jaw",
                CubeListBuilder.create()
                        .texOffs(0, 116)
                        .addBox(-6.0F, 0.0F, -10.0F, 12.0F, 4.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // === ARMS ===

        // Right upper arm: pivot at shoulder
        PartDefinition rightArm = chest.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(100, 0)
                        .addBox(-7.0F, -2.0F, -4.0F, 8.0F, 22.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(-14.0F, -28.0F, 0.0F));

        // Right forearm: pivot at elbow
        PartDefinition rightForearm = rightArm.addOrReplaceChild("right_forearm",
                CubeListBuilder.create()
                        .texOffs(100, 34)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 20.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(-3.0F, 20.0F, 0.0F));

        // Right hand
        rightForearm.addOrReplaceChild("right_hand",
                CubeListBuilder.create()
                        .texOffs(100, 64)
                        .addBox(-3.5F, 0.0F, -2.0F, 7.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        // Left upper arm: pivot at shoulder (mirrored)
        PartDefinition leftArm = chest.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(140, 0)
                        .addBox(-1.0F, -2.0F, -4.0F, 8.0F, 22.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(14.0F, -28.0F, 0.0F));

        // Left forearm: pivot at elbow
        PartDefinition leftForearm = leftArm.addOrReplaceChild("left_forearm",
                CubeListBuilder.create()
                        .texOffs(140, 34)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 20.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(3.0F, 20.0F, 0.0F));

        // Left hand
        leftForearm.addOrReplaceChild("left_hand",
                CubeListBuilder.create()
                        .texOffs(140, 64)
                        .addBox(-3.5F, 0.0F, -2.0F, 7.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        // === LEGS ===

        // Right upper leg: pivot at hip
        PartDefinition rightLeg = partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(200, 0)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 28.0F, 9.0F, CubeDeformation.NONE),
                PartPose.offset(-5.0F, -40.0F, 0.0F));

        // Right lower leg: pivot at knee
        PartDefinition rightLowerLeg = rightLeg.addOrReplaceChild("right_lower_leg",
                CubeListBuilder.create()
                        .texOffs(200, 42)
                        .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 24.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        // Right foot: large for stability
        rightLowerLeg.addOrReplaceChild("right_foot",
                CubeListBuilder.create()
                        .texOffs(200, 78)
                        .addBox(-4.5F, 0.0F, -7.0F, 9.0F, 4.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        // Left upper leg: pivot at hip
        PartDefinition leftLeg = partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(250, 0)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 28.0F, 9.0F, CubeDeformation.NONE),
                PartPose.offset(5.0F, -40.0F, 0.0F));

        // Left lower leg: pivot at knee
        PartDefinition leftLowerLeg = leftLeg.addOrReplaceChild("left_lower_leg",
                CubeListBuilder.create()
                        .texOffs(250, 42)
                        .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 24.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        // Left foot
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
    public void setupAnim(TitanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        boolean hasTarget = entity.getTarget() != null;
        float partialTick = ageInTicks - entity.tickCount;
        float attackProgress = entity.getAttackAnim(partialTick);

        // === Layer 1: Head tracking ===
        // Split rotation between neck (20%) and head (80%) for natural look
        this.upperHead.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.8F;
        this.upperHead.xRot = headPitch * Mth.DEG_TO_RAD * 0.8F;
        this.neck.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.2F;
        // Creepy head tilt when targeting a player
        if (hasTarget) {
            this.upperHead.zRot = Mth.sin(ageInTicks * 0.05F) * 0.12F;
        }

        // === Layer 2: Idle sway ===
        float idleSway = Mth.sin(ageInTicks * 0.04F);
        this.body.zRot += idleSway * 0.02F;
        this.chest.zRot += idleSway * 0.01F;
        // Subtle arm dangle
        this.rightArm.zRot += Mth.sin(ageInTicks * 0.06F) * 0.05F;
        this.leftArm.zRot += -Mth.sin(ageInTicks * 0.06F) * 0.05F;
        this.rightArm.xRot += Mth.sin(ageInTicks * 0.05F + 0.5F) * 0.04F;
        this.leftArm.xRot += Mth.sin(ageInTicks * 0.05F + 1.0F) * 0.04F;

        // === Layer 3: Heavy walk cycle ===
        float walkFreq = 0.4F; // Slower than humanoid (0.6662) for heavy footsteps
        float walkSwing = Mth.cos(limbSwing * walkFreq) * limbSwingAmount;

        // Upper legs swing at hips
        this.rightLeg.xRot += walkSwing * 0.8F;
        this.leftLeg.xRot += -walkSwing * 0.8F;

        // Knee bend: slight constant bend + extra during forward step
        float kneeBase = 0.15F;
        this.rightLowerLeg.xRot += kneeBase + Math.max(0, walkSwing) * 0.4F;
        this.leftLowerLeg.xRot += kneeBase + Math.max(0, -walkSwing) * 0.4F;

        // Arms swing opposite to legs
        this.rightArm.xRot += -walkSwing * 0.3F;
        this.leftArm.xRot += walkSwing * 0.3F;

        // Elbow bend increases during arm backswing
        this.rightForearm.xRot += 0.2F + Math.max(0, -walkSwing) * 0.3F;
        this.leftForearm.xRot += 0.2F + Math.max(0, walkSwing) * 0.3F;

        // Body bob (vertical bounce) and forward lean when moving
        this.body.y += Mth.cos(limbSwing * walkFreq * 2.0F) * limbSwingAmount * 0.5F;
        this.chest.xRot += limbSwingAmount * 0.05F;

        // === Layer 4: Attack swing ===
        if (attackProgress > 0.0F) {
            // First half: wind-up (arm raises behind head)
            float windUp = Math.min(attackProgress * 2.0F, 1.0F);
            // Second half: strike (arm slams down)
            float strike = Math.max((attackProgress - 0.5F) * 2.0F, 0.0F);

            // Right arm overhead swing
            this.rightArm.xRot += -windUp * 1.8F + strike * 2.5F;
            this.rightForearm.xRot += -windUp * 0.8F + strike * 0.5F;

            // Body leans forward during strike
            this.chest.xRot += strike * 0.3F;
            this.body.xRot += strike * 0.1F;
        }

        // === Layer 5: Jaw animation ===
        // Breathing cycle: subtle open/close
        float breathCycle = Mth.sin(ageInTicks * 0.08F) * 0.06F + 0.06F;
        float jawAngle = breathCycle;
        if (attackProgress > 0.3F) {
            // Jaw opens wide during attack
            jawAngle += (attackProgress - 0.3F) * 1.0F;
        } else if (hasTarget) {
            // Slightly more open when aggressive
            jawAngle += 0.15F;
        }
        this.lowerJaw.xRot = jawAngle;
    }
}
