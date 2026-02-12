package com.beyondthewalls.item;

import com.beyondthewalls.world.WaveManager;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WarHornItem extends Item {

    private static final int COOLDOWN_TICKS = 6000; // 5 minutes

    public WarHornItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (level instanceof ServerLevel serverLevel) {
            WaveManager waveManager = WaveManager.get(serverLevel);
            waveManager.triggerWave(serverLevel);

            level.playSound(null, player.blockPosition(), SoundEvents.GOAT_HORN_SOUND_VARIANTS.getFirst().value(),
                    SoundSource.PLAYERS, 2.0F, 0.8F);

            player.displayClientMessage(Component.translatable("message.beyondthewalls.war_horn_used"), true);
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }

        return InteractionResultHolder.success(stack);
    }
}
