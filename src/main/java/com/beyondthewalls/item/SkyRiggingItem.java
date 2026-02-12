package com.beyondthewalls.item;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.GrapplingHookEntity;
import com.beyondthewalls.init.ModItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SkyRiggingItem extends Item {
    public SkyRiggingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        // Search for thrust cartridge in inventory
        int cartridgeSlot = findCartridgeSlot(player);
        if (cartridgeSlot == -1) {
            // No fuel - play fail sound
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            // Consume one thrust cartridge
            if (!player.getAbilities().instabuild) {
                player.getInventory().removeItem(cartridgeSlot, 1);
            }

            // Fire grappling hook projectile
            GrapplingHookEntity hook = new GrapplingHookEntity(level, player);
            hook.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.0F);
            level.addFreshEntity(hook);
        }

        // Play fire sound
        level.playSound(player, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.2F);

        // Apply cooldown
        player.getCooldowns().addCooldown(this, Config.HOOK_COOLDOWN_TICKS.getAsInt());

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private int findCartridgeSlot(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (!invStack.isEmpty() && invStack.is(ModItems.THRUST_CARTRIDGE.get())) {
                return i;
            }
        }
        return -1;
    }
}
