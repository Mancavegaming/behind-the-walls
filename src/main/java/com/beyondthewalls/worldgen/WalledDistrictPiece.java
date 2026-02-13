package com.beyondthewalls.worldgen;

import java.util.HashSet;
import java.util.Set;

import com.beyondthewalls.Config;
import com.beyondthewalls.block.WallGateBlock;
import com.beyondthewalls.blockentity.DistrictHeartBlockEntity;
import com.beyondthewalls.init.ModBlocks;
import com.beyondthewalls.init.ModStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

public class WalledDistrictPiece extends StructurePiece {

    private final int centerX;
    private final int centerY;
    private final int centerZ;

    public WalledDistrictPiece(BlockPos center) {
        super(ModStructures.WALLED_DISTRICT_PIECE.get(), 0, createBoundingBox(center));
        this.centerX = center.getX();
        this.centerY = center.getY();
        this.centerZ = center.getZ();
    }

    public WalledDistrictPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(ModStructures.WALLED_DISTRICT_PIECE.get(), tag);
        this.centerX = tag.getInt("CenterX");
        this.centerY = tag.getInt("CenterY");
        this.centerZ = tag.getInt("CenterZ");
    }

    private static BoundingBox createBoundingBox(BlockPos center) {
        int r = WalledDistrictStructure.RADIUS_MARIA;
        return new BoundingBox(
                center.getX() - r - 2, -64, center.getZ() - r - 2,
                center.getX() + r + 2, 320, center.getZ() + r + 2
        );
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putInt("CenterX", centerX);
        tag.putInt("CenterY", centerY);
        tag.putInt("CenterZ", centerZ);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGen,
                            RandomSource random, BoundingBox chunkBounds, ChunkPos chunkPos, BlockPos pivot) {
        int wallHeight = Config.DISTRICT_STRUCTURE_WALL_HEIGHT.get();
        int foundationDepth = Config.DISTRICT_STRUCTURE_FOUNDATION_DEPTH.get();

        // Generate each wall ring with its corresponding block type
        generateWallRing(level, chunkBounds, WalledDistrictStructure.RADIUS_MARIA,
                ModBlocks.WALL_MARIA.get(), wallHeight, foundationDepth);
        generateWallRing(level, chunkBounds, WalledDistrictStructure.RADIUS_ROSE,
                ModBlocks.WALL_ROSE.get(), wallHeight, foundationDepth);
        generateWallRing(level, chunkBounds, WalledDistrictStructure.RADIUS_SINA,
                ModBlocks.WALL_SINA.get(), wallHeight, foundationDepth);

        // Place district heart at center
        placeDistrictHeart(level, chunkBounds);
    }

    private void generateWallRing(WorldGenLevel level, BoundingBox box, int radius,
                                   Block wallBlock, int wallHeight, int foundationDepth) {
        // Use enough angular steps to avoid gaps: ~2*PI*r * 1.5
        int steps = (int) (2 * Math.PI * radius * 1.5);
        Set<Long> visited = new HashSet<>();

        for (int i = 0; i < steps; i++) {
            double angle = 2 * Math.PI * i / steps;
            int x = centerX + (int) Math.round(radius * Math.cos(angle));
            int z = centerZ + (int) Math.round(radius * Math.sin(angle));

            // De-duplicate positions
            long key = ((long) x << 32) | (z & 0xFFFFFFFFL);
            if (!visited.add(key)) continue;

            // Skip if outside current chunk bounds (XZ check)
            if (x < box.minX() || x > box.maxX() || z < box.minZ() || z > box.maxZ()) continue;

            // Sample terrain height at this column
            int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);

            if (isGatePosition(x, z, radius)) {
                // At the exact center of each gate, place the gate block
                if (isGateCenter(x, z, radius)) {
                    placeGate(level, x, z, groundY, radius, box);
                }
                // Non-center gate positions are left open (no wall placed)
            } else {
                // Place foundation blocks below ground
                BlockState foundationState = ModBlocks.WALL_FOUNDATION.get().defaultBlockState();
                for (int dy = 1; dy <= foundationDepth; dy++) {
                    placeBlockAt(level, foundationState, x, groundY - dy, z, box);
                }

                // Place wall blocks above ground
                BlockState wallState = wallBlock.defaultBlockState();
                for (int dy = 0; dy < wallHeight; dy++) {
                    placeBlockAt(level, wallState, x, groundY + dy, z, box);
                }

                // Place cap (foundation block on top)
                placeBlockAt(level, foundationState, x, groundY + wallHeight, z, box);
            }
        }
    }

    private boolean isGatePosition(int x, int z, int radius) {
        // Gate at each cardinal direction: ±1 block width around the exact cardinal point
        // North gate: (centerX, centerZ - radius)
        if (Math.abs(x - centerX) <= 1 && Math.abs(z - (centerZ - radius)) <= 1) return true;
        // South gate: (centerX, centerZ + radius)
        if (Math.abs(x - centerX) <= 1 && Math.abs(z - (centerZ + radius)) <= 1) return true;
        // East gate: (centerX + radius, centerZ)
        if (Math.abs(x - (centerX + radius)) <= 1 && Math.abs(z - centerZ) <= 1) return true;
        // West gate: (centerX - radius, centerZ)
        if (Math.abs(x - (centerX - radius)) <= 1 && Math.abs(z - centerZ) <= 1) return true;
        return false;
    }

    private boolean isGateCenter(int x, int z, int radius) {
        // Only the exact center column of each gate gets the gate block
        if (x == centerX && z == centerZ - radius) return true; // North
        if (x == centerX && z == centerZ + radius) return true; // South
        if (x == centerX + radius && z == centerZ) return true; // East
        if (x == centerX - radius && z == centerZ) return true; // West
        return false;
    }

    private Direction getGateFacing(int x, int z, int radius) {
        // Gates face outward (away from the district center)
        if (x == centerX && z == centerZ - radius) return Direction.NORTH;
        if (x == centerX && z == centerZ + radius) return Direction.SOUTH;
        if (x == centerX + radius && z == centerZ) return Direction.EAST;
        if (x == centerX - radius && z == centerZ) return Direction.WEST;
        return Direction.NORTH;
    }

    private void placeGate(WorldGenLevel level, int x, int z, int groundY, int radius, BoundingBox box) {
        Direction facing = getGateFacing(x, z, radius);

        BlockState lower = ModBlocks.WALL_GATE.get().defaultBlockState()
                .setValue(WallGateBlock.FACING, facing)
                .setValue(WallGateBlock.OPEN, false)
                .setValue(WallGateBlock.HALF, DoubleBlockHalf.LOWER);
        BlockState upper = ModBlocks.WALL_GATE.get().defaultBlockState()
                .setValue(WallGateBlock.FACING, facing)
                .setValue(WallGateBlock.OPEN, false)
                .setValue(WallGateBlock.HALF, DoubleBlockHalf.UPPER);

        placeBlockAt(level, lower, x, groundY, z, box);
        placeBlockAt(level, upper, x, groundY + 1, z, box);
    }

    private void placeDistrictHeart(WorldGenLevel level, BoundingBox box) {
        int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, centerX, centerZ);
        BlockPos heartPos = new BlockPos(centerX, groundY, centerZ);

        if (!box.isInside(heartPos)) return;

        level.setBlock(heartPos, ModBlocks.DISTRICT_HEART.get().defaultBlockState(), 2);

        // Set the heart to tier 3 — district registration happens via onLoad
        // when the chunk is first loaded into the server
        BlockEntity be = level.getBlockEntity(heartPos);
        if (be instanceof DistrictHeartBlockEntity heart) {
            heart.setTier(3);
        }
    }

    private void placeBlockAt(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
        BlockPos pos = new BlockPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(pos, state, 2);
        }
    }
}
