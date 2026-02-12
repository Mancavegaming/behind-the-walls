package com.beyondthewalls.block;

import com.beyondthewalls.blockentity.DistrictHeartBlockEntity;
import com.beyondthewalls.init.ModBlockEntities;
import com.beyondthewalls.world.DistrictManager;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DistrictHeartBlock extends BaseEntityBlock {

    public static final MapCodec<DistrictHeartBlock> CODEC = simpleCodec(p -> new DistrictHeartBlock());

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public DistrictHeartBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(50.0F, 1200.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .lightLevel(s -> 7));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DistrictHeartBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level instanceof ServerLevel serverLevel) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof DistrictHeartBlockEntity heart) {
                DistrictManager manager = DistrictManager.get(serverLevel);
                manager.addDistrict(pos, heart.getRadius());
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level instanceof ServerLevel serverLevel) {
                DistrictManager manager = DistrictManager.get(serverLevel);
                manager.removeDistrict(pos);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                               Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.sidedSuccess(true);
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof DistrictHeartBlockEntity heart) {
            player.displayClientMessage(Component.translatable("message.beyondthewalls.district_info",
                    heart.getTier(), heart.getRadius()), true);
        }
        return ItemInteractionResult.SUCCESS;
    }
}
