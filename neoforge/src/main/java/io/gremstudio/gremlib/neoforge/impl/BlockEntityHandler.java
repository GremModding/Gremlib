package io.gremstudio.gremlib.neoforge.impl;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.*;

public class BlockEntityHandler {
    static Map<BlockEntityType<?>, Set<Block>> blocksToAddToBEs = new HashMap<>();

    // You COULD just call it directly I guess... If its neoforge that is.
    public static void addBlockToBE(BlockEntityType<?> be, Block block) {
        Set<Block> blocksToAdd = blocksToAddToBEs.getOrDefault(be, new HashSet<>());
        blocksToAdd.add(block);
        blocksToAddToBEs.put(be, blocksToAdd);
    }

    public static void addBlocksToBE(BlockEntityType<?> be, Block... blocks ) {
        Set<Block> blocksToAdd = blocksToAddToBEs.getOrDefault(be, new HashSet<>());
        blocksToAdd.addAll(Arrays.stream(blocks).toList());
        blocksToAddToBEs.put(be, blocksToAdd);
    }

    @SubscribeEvent
    public static void addToBEsEvent(BlockEntityTypeAddBlocksEvent event) {
        for (Map.Entry<BlockEntityType<?>, Set<Block>> entry : blocksToAddToBEs.entrySet()) {
            event.modify(entry.getKey(), entry.getValue().toArray(new Block[0]));
        }
    }
}
