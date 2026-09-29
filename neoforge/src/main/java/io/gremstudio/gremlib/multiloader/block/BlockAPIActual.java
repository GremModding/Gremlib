package io.gremstudio.gremlib.multiloader.block;

import io.gremstudio.gremlib.neoforge.impl.BlockEntityHandler;
import io.gremstudio.gremlib.neoforge.mixin.AxeItemAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.msrandom.multiplatform.annotations.Actual;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;
import java.util.Map;

public class BlockAPIActual {
    @Actual
    public void addBlockToBE(BlockEntityType<?> be, Block block) {
        BlockEntityHandler.addBlockToBE(be, block);
    }

    @Actual
    public void addToStrippables(Block input, Block output) {
        Map<Block, Block> strippables = new HashMap<>( AxeItemAccessor.getStrippables());
        strippables.put(input, output);
        AxeItemAccessor.setStrippables(strippables);
    }
}
