package io.gremstudio.gremlib.fabric.api.block;

import io.gremstudio.gremlib.multiloader.block.LoaderBlockAPI;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricBlockAPI implements LoaderBlockAPI {
    public static FabricBlockAPI INSTANCE = new FabricBlockAPI();

    // Fabric thank you for the elegant solution that Neo should be doing.
    @Override
    public void addBlockToBE(BlockEntityType<?> be, Block block) {
        be.addSupportedBlock(block);
    }

    @Override
    public void addToStrippables(Block input, Block output) {
        StrippableBlockRegistry.register(input, output);
    }
}
