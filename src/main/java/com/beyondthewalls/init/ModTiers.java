package com.beyondthewalls.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final Tier ULTRAHARD_STEEL = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            32,    // durability
            6.0F,  // speed
            3.0F,  // attack damage bonus
            14,    // enchantment value
            () -> Ingredient.of(ModItems.BLADE_INSERT.get())
    );
}
