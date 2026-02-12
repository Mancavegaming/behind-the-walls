package com.beyondthewalls.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallBlock extends Block {

    private final WallTier tier;

    public WallBlock(WallTier tier) {
        super(BlockBehaviour.Properties.of()
                .strength(tier.getHardness(), tier.getBlastResistance())
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
        this.tier = tier;
    }

    public WallTier getTier() {
        return tier;
    }
}
