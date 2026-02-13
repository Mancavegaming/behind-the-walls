package com.beyondthewalls.worldgen;

import java.util.Optional;

import com.beyondthewalls.init.ModStructures;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class WalledDistrictStructure extends Structure {

    public static final int RADIUS_MARIA = 128;
    public static final int RADIUS_ROSE = 80;
    public static final int RADIUS_SINA = 40;

    public static final MapCodec<WalledDistrictStructure> CODEC = simpleCodec(WalledDistrictStructure::new);

    public WalledDistrictStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int centerX = context.chunkPos().getMiddleBlockX();
        int centerZ = context.chunkPos().getMiddleBlockZ();

        int y = context.chunkGenerator().getFirstOccupiedHeight(
                centerX, centerZ,
                Heightmap.Types.WORLD_SURFACE_WG,
                context.heightAccessor(),
                context.randomState()
        );

        BlockPos structurePos = new BlockPos(centerX, y, centerZ);

        return Optional.of(new GenerationStub(structurePos, piecesBuilder -> {
            piecesBuilder.addPiece(new WalledDistrictPiece(structurePos));
        }));
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.WALLED_DISTRICT.get();
    }
}
