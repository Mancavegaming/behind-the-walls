package com.beyondthewalls.init;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.worldgen.WalledDistrictPiece;
import com.beyondthewalls.worldgen.WalledDistrictStructure;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, BeyondTheWalls.MODID);

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PIECE, BeyondTheWalls.MODID);

    public static final DeferredHolder<StructureType<?>, StructureType<WalledDistrictStructure>> WALLED_DISTRICT =
            STRUCTURE_TYPES.register("walled_district", () -> () -> WalledDistrictStructure.CODEC);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> WALLED_DISTRICT_PIECE =
            STRUCTURE_PIECE_TYPES.register("walled_district_piece", () -> WalledDistrictPiece::new);

    public static void register(IEventBus modEventBus) {
        STRUCTURE_TYPES.register(modEventBus);
        STRUCTURE_PIECE_TYPES.register(modEventBus);
    }
}
