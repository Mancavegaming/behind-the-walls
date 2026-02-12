package com.beyondthewalls.block;

import com.beyondthewalls.init.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlacksmithStationBlock extends Block {

    public BlacksmithStationBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(5.0F, 6.0F)
                .sound(SoundType.ANVIL)
                .requiresCorrectToolForDrops());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                               Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.sidedSuccess(true);
        }

        // Check if player has broken blade handles in hand and 2 blade inserts in inventory
        if (stack.is(ModItems.BROKEN_BLADE_HANDLES.get())) {
            int insertCount = 0;
            int insertSlot = -1;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack invStack = player.getInventory().getItem(i);
                if (invStack.is(ModItems.BLADE_INSERT.get())) {
                    insertCount += invStack.getCount();
                    if (insertSlot == -1) insertSlot = i;
                }
            }

            if (insertCount >= 2) {
                // Consume materials
                stack.shrink(1);
                int toRemove = 2;
                for (int i = 0; i < player.getInventory().getContainerSize() && toRemove > 0; i++) {
                    ItemStack invStack = player.getInventory().getItem(i);
                    if (invStack.is(ModItems.BLADE_INSERT.get())) {
                        int take = Math.min(toRemove, invStack.getCount());
                        invStack.shrink(take);
                        toRemove -= take;
                    }
                }

                // Give ultrahard blades
                ItemStack blades = new ItemStack(ModItems.ULTRAHARD_BLADES.get());
                if (!player.getInventory().add(blades)) {
                    player.drop(blades, false);
                }

                level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.displayClientMessage(Component.translatable("message.beyondthewalls.blades_repaired"), true);
                return ItemInteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.translatable("message.beyondthewalls.need_inserts"), true);
                return ItemInteractionResult.FAIL;
            }
        }

        player.displayClientMessage(Component.translatable("message.beyondthewalls.need_broken_handles"), true);
        return ItemInteractionResult.CONSUME;
    }
}
