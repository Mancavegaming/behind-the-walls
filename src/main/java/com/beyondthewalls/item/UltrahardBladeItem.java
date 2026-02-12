package com.beyondthewalls.item;

import com.beyondthewalls.init.ModItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class UltrahardBladeItem extends SwordItem {

    public UltrahardBladeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);

        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            // Blades are about to break — convert to broken handles
            stack.shrink(1);
            ItemStack brokenHandles = new ItemStack(ModItems.BROKEN_BLADE_HANDLES.get());
            if (!attacker.level().isClientSide && attacker instanceof net.minecraft.world.entity.player.Player player) {
                if (!player.getInventory().add(brokenHandles)) {
                    player.drop(brokenHandles, false);
                }
            }
            attacker.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
        }
    }
}
