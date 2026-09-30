package io.gremstudio.gremlib.multiloader.block;

import io.gremstudio.gremlib.multiloader.MixinImplementationError;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.msrandom.multiplatform.annotations.Actual;

public class BlockAPIActual {
    @Actual
    public static void addBlockToBE(BlockEntityType<?> be, Block block) {
        be.addValidBlock(block);
    }

    @Actual
    public static void addToStrippables(Block input, Block output) {
        StrippableBlockRegistry.register(input, output);
    }
}
