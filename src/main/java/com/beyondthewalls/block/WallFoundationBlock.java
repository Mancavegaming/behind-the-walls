package com.beyondthewalls.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallFoundationBlock extends Block {

    public WallFoundationBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(20.0F, 40.0F)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
    }
}
