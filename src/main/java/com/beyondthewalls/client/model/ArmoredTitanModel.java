package com.beyondthewalls.client.model;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.entity.ArmoredTitanEntity;

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

public class ArmoredTitanModel extends HierarchicalModel<ArmoredTitanEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "armored_titan"), "main");

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

    public ArmoredTitanModel(ModelPart root) {
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
        // Bulkier base with armor plate child parts
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partDefinition = mesh.getRoot();

        // Bulkier body
        PartDefinition body = partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-10.0F, -12.0F, -6.0F, 20.0F, 12.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -40.0F, 0.0F));

        // Wider, thicker chest
        PartDefinition chest = body.addOrReplaceChild("chest",
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-16.0F, -32.0F, -8.0F, 32.0F, 32.0F, 16.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -12.0F, 0.0F));

        // Rib overlay (thicker for armored)
        chest.addOrReplaceChild("rib_overlay",
                CubeListBuilder.create()
                        .texOffs(0, 140)
                        .addBox(-14.0F, -22.0F, -1.0F, 28.0F, 18.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, -8.0F));

        // Chest plate armor
        chest.addOrReplaceChild("chest_plate",
                CubeListBuilder.create()
                        .texOffs(96, 140)
                        .addBox(-14.0F, -28.0F, -1.5F, 28.0F, 24.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, -8.5F));

        // Neck
        PartDefinition neck = chest.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(-5.0F, -7.0F, -5.0F, 10.0F, 7.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -32.0F, 0.0F));

        // Head (slightly larger for armored)
        PartDefinition upperHead = neck.addOrReplaceChild("upper_head",
                CubeListBuilder.create()
                        .texOffs(0, 90)
                        .addBox(-8.0F, -11.0F, -7.0F, 16.0F, 11.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -7.0F, 0.0F));

        upperHead.addOrReplaceChild("lower_jaw",
                CubeListBuilder.create()
                        .texOffs(0, 116)
                        .addBox(-7.0F, 0.0F, -11.0F, 14.0F, 4.0F, 9.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Bulkier arms with shoulder pauldrons
        PartDefinition rightArm = chest.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(100, 0)
                        .addBox(-9.0F, -3.0F, -5.0F, 10.0F, 24.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(-16.0F, -30.0F, 0.0F));

        // Right shoulder pauldron
        rightArm.addOrReplaceChild("right_pauldron",
                CubeListBuilder.create()
                        .texOffs(160, 140)
                        .addBox(-10.0F, -5.0F, -6.0F, 12.0F, 8.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightForearm = rightArm.addOrReplaceChild("right_forearm",
                CubeListBuilder.create()
                        .texOffs(100, 34)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 20.0F, 9.0F, CubeDeformation.NONE),
                PartPose.offset(-4.0F, 21.0F, 0.0F));

        rightForearm.addOrReplaceChild("right_hand",
                CubeListBuilder.create()
                        .texOffs(100, 64)
                        .addBox(-4.5F, 0.0F, -3.0F, 9.0F, 7.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition leftArm = chest.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(140, 0)
                        .addBox(-1.0F, -3.0F, -5.0F, 10.0F, 24.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(16.0F, -30.0F, 0.0F));

        // Left shoulder pauldron
        leftArm.addOrReplaceChild("left_pauldron",
                CubeListBuilder.create()
                        .texOffs(208, 140)
                        .addBox(-2.0F, -5.0F, -6.0F, 12.0F, 8.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftForearm = leftArm.addOrReplaceChild("left_forearm",
                CubeListBuilder.create()
                        .texOffs(140, 34)
                        .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 20.0F, 9.0F, CubeDeformation.NONE),
                PartPose.offset(4.0F, 21.0F, 0.0F));

        leftForearm.addOrReplaceChild("left_hand",
                CubeListBuilder.create()
                        .texOffs(140, 64)
                        .addBox(-4.5F, 0.0F, -3.0F, 9.0F, 7.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        // Bulkier legs with shin guards
        PartDefinition rightLeg = partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(200, 0)
                        .addBox(-5.5F, 0.0F, -5.5F, 11.0F, 28.0F, 11.0F, CubeDeformation.NONE),
                PartPose.offset(-6.0F, -40.0F, 0.0F));

        PartDefinition rightLowerLeg = rightLeg.addOrReplaceChild("right_lower_leg",
                CubeListBuilder.create()
                        .texOffs(200, 42)
                        .addBox(-5.0F, 0.0F, -5.0F, 10.0F, 24.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        // Right shin guard
        rightLowerLeg.addOrReplaceChild("right_shin_guard",
                CubeListBuilder.create()
                        .texOffs(256, 140)
                        .addBox(-5.5F, 2.0F, -6.5F, 11.0F, 18.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        rightLowerLeg.addOrReplaceChild("right_foot",
                CubeListBuilder.create()
                        .texOffs(200, 78)
                        .addBox(-5.5F, 0.0F, -8.0F, 11.0F, 4.0F, 16.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leftLeg = partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(250, 0)
                        .addBox(-5.5F, 0.0F, -5.5F, 11.0F, 28.0F, 11.0F, CubeDeformation.NONE),
                PartPose.offset(6.0F, -40.0F, 0.0F));

        PartDefinition leftLowerLeg = leftLeg.addOrReplaceChild("left_lower_leg",
                CubeListBuilder.create()
                        .texOffs(250, 42)
                        .addBox(-5.0F, 0.0F, -5.0F, 10.0F, 24.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        // Left shin guard
        leftLowerLeg.addOrReplaceChild("left_shin_guard",
                CubeListBuilder.create()
                        .texOffs(286, 140)
                        .addBox(-5.5F, 2.0F, -6.5F, 11.0F, 18.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leftLowerLeg.addOrReplaceChild("left_foot",
                CubeListBuilder.create()
                        .texOffs(250, 78)
                        .addBox(-5.5F, 0.0F, -8.0F, 11.0F, 4.0F, 16.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 512, 256);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(ArmoredTitanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        boolean hasTarget = entity.getTarget() != null;
        float partialTick = ageInTicks - entity.tickCount;
        float attackProgress = entity.getAttackAnim(partialTick);

        // Head tracking (slow, heavy)
        this.upperHead.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.7F;
        this.upperHead.xRot = headPitch * Mth.DEG_TO_RAD * 0.7F;
        this.neck.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.3F;

        // Minimal idle sway
        float idleSway = Mth.sin(ageInTicks * 0.03F);
        this.body.zRot += idleSway * 0.01F;
        this.chest.zRot += idleSway * 0.005F;
        this.rightArm.zRot += Mth.sin(ageInTicks * 0.04F) * 0.03F;
        this.leftArm.zRot += -Mth.sin(ageInTicks * 0.04F) * 0.03F;

        // Heavy walk cycle (0.3F frequency)
        float walkFreq = 0.3F;
        float walkSwing = Mth.cos(limbSwing * walkFreq) * limbSwingAmount;

        this.rightLeg.xRot += walkSwing * 0.6F;
        this.leftLeg.xRot += -walkSwing * 0.6F;

        float kneeBase = 0.1F;
        this.rightLowerLeg.xRot += kneeBase + Math.max(0, walkSwing) * 0.3F;
        this.leftLowerLeg.xRot += kneeBase + Math.max(0, -walkSwing) * 0.3F;

        this.rightArm.xRot += -walkSwing * 0.2F;
        this.leftArm.xRot += walkSwing * 0.2F;
        this.rightForearm.xRot += 0.15F + Math.max(0, -walkSwing) * 0.2F;
        this.leftForearm.xRot += 0.15F + Math.max(0, walkSwing) * 0.2F;

        // Amplified body bob
        this.body.y += Mth.cos(limbSwing * walkFreq * 2.0F) * limbSwingAmount * 0.8F;
        this.chest.xRot += limbSwingAmount * 0.03F;

        // Attack swing
        if (attackProgress > 0.0F) {
            float windUp = Math.min(attackProgress * 2.0F, 1.0F);
            float strike = Math.max((attackProgress - 0.5F) * 2.0F, 0.0F);
            this.rightArm.xRot += -windUp * 1.5F + strike * 2.2F;
            this.rightForearm.xRot += -windUp * 0.6F + strike * 0.4F;
            this.chest.xRot += strike * 0.25F;
            this.body.xRot += strike * 0.08F;
        }

        // Jaw
        float breathCycle = Mth.sin(ageInTicks * 0.06F) * 0.04F + 0.04F;
        float jawAngle = breathCycle;
        if (attackProgress > 0.3F) {
            jawAngle += (attackProgress - 0.3F) * 0.8F;
        } else if (hasTarget) {
            jawAngle += 0.1F;
        }
        this.lowerJaw.xRot = jawAngle;
    }
}
