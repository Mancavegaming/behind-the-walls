package com.beyondthewalls.item;

import com.beyondthewalls.Config;
import com.beyondthewalls.block.WallBlock;
import com.beyondthewalls.block.WallFoundationBlock;
import com.beyondthewalls.block.WallTier;
import com.beyondthewalls.init.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;

public class WallBuilderItem extends Item {

    public WallBuilderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (level.isClientSide || player == null) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        BlockState clickedState = level.getBlockState(pos);
        if (!(clickedState.getBlock() instanceof WallFoundationBlock)) {
            return InteractionResult.PASS;
        }

        // Find the best wall blocks in inventory (highest tier first)
        DeferredBlock<WallBlock> wallBlock = findBestWallBlocks(player);
        if (wallBlock == null) {
            player.displayClientMessage(Component.translatable("message.beyondthewalls.no_wall_blocks"), true);
            return InteractionResult.FAIL;
        }

        int height = Config.WALL_BUILD_HEIGHT.get();
        int placed = 0;

        for (int y = 1; y <= height; y++) {
            BlockPos placePos = pos.above(y);
            if (!level.getBlockState(placePos).canBeReplaced()) {
                continue;
            }

            // Check and consume from inventory
            ItemStack wallItemStack = findAndConsumeWallItem(player, wallBlock.get());
            if (wallItemStack == null) break;

            level.setBlock(placePos, wallBlock.get().defaultBlockState(), 3);
            placed++;
        }

        if (placed > 0) {
            level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            context.getItemInHand().hurtAndBreak(1, player, player.getEquipmentSlotForItem(context.getItemInHand()));
            player.displayClientMessage(Component.translatable("message.beyondthewalls.wall_built", placed), true);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private DeferredBlock<WallBlock> findBestWallBlocks(Player player) {
        // Check from highest to lowest tier
        if (hasBlockItem(player, ModBlocks.WALL_SINA.get())) return ModBlocks.WALL_SINA;
        if (hasBlockItem(player, ModBlocks.WALL_ROSE.get())) return ModBlocks.WALL_ROSE;
        if (hasBlockItem(player, ModBlocks.WALL_MARIA.get())) return ModBlocks.WALL_MARIA;
        return null;
    }

    private boolean hasBlockItem(Player player, Block block) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(block.asItem())) {
                return true;
            }
        }
        return false;
    }

    private ItemStack findAndConsumeWallItem(Player player, Block block) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(block.asItem())) {
                stack.shrink(1);
                return stack;
            }
        }
        return null;
    }
}
