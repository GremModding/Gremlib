package io.gremstudio.gremlib.neoforge.api.block;

import io.gremstudio.gremlib.multiloader.block.LoaderBlockAPI;
import io.gremstudio.gremlib.neoforge.impl.BlockEntityHandler;
import io.gremstudio.gremlib.neoforge.impl.StrippableHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class NeoBlockAPI implements LoaderBlockAPI {
    public static NeoBlockAPI INSTANCE = new NeoBlockAPI();

    @Override
    public void addBlockToBE(BlockEntityType<?> be, Block block) {
        BlockEntityHandler.addBlockToBE(be, block);
    }

    @Override
    public void addBlocksToBE(BlockEntityType<?> be, Block... blocks) {
        BlockEntityHandler.addBlocksToBE(be, blocks);
    }

    @Override
    public void addToStrippables(Block input, Block output) {
        StrippableHandler.addStrippable(input, output);
    }
}
