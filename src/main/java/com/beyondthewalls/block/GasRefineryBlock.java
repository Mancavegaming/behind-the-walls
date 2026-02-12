package com.beyondthewalls.block;

import com.beyondthewalls.blockentity.GasRefineryBlockEntity;
import com.beyondthewalls.init.ModBlockEntities;
import com.beyondthewalls.init.ModItems;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class GasRefineryBlock extends BaseEntityBlock {

    public static final MapCodec<GasRefineryBlock> CODEC = simpleCodec(p -> new GasRefineryBlock());

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public GasRefineryBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GasRefineryBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return createTickerHelper(type, ModBlockEntities.GAS_REFINERY.get(), GasRefineryBlockEntity::serverTick);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                               Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.sidedSuccess(true);
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof GasRefineryBlockEntity refinery)) {
            return ItemInteractionResult.FAIL;
        }

        // Add coal as fuel
        if (stack.is(Items.COAL)) {
            if (refinery.addFuel(stack.getCount())) {
                stack.setCount(0);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 0.8F);
                player.displayClientMessage(Component.translatable("message.beyondthewalls.fuel_added",
                        refinery.getFuel()), true);
                return ItemInteractionResult.SUCCESS;
            }
        }

        // Collect cartridges with empty hand
        if (stack.isEmpty()) {
            int ready = refinery.collectCartridges();
            if (ready > 0) {
                ItemStack cartridges = new ItemStack(ModItems.THRUST_CARTRIDGE.get(), ready);
                if (!player.getInventory().add(cartridges)) {
                    player.drop(cartridges, false);
                }
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.2F);
                player.displayClientMessage(Component.translatable("message.beyondthewalls.cartridges_collected", ready), true);
                return ItemInteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.translatable("message.beyondthewalls.refinery_status",
                        refinery.getFuel(), refinery.getProgress()), true);
                return ItemInteractionResult.CONSUME;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
