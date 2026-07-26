package io.gremstudio.gremlib.neoforge.mixin;

import io.gremstudio.gremlib.multiloader.MixinImplementationError;
import io.gremstudio.gremlib.multiloader.block.BlockAPI;
import io.gremstudio.gremlib.neoforge.impl.BlockEntityHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;
import java.util.Map;

@Mixin(BlockAPI.class)
public class BlockAPIMixin {
    /**
     * @author Siuolplex
     * @reason Mixin Implementation
     */
    @Overwrite
    public void addBlockToBE(BlockEntityType<?> be, Block block) {
        BlockEntityHandler.addBlockToBE(be, block);
    }

    /**
     * @author Siuolplex
     * @reason Mixin Implementation, technically this one isn't required but I thought it might as well be done.
     */
    @Overwrite
    public void addBlocksToBe(BlockEntityType<?> be, Block... blocks) {
        BlockEntityHandler.addBlocksToBE(be, blocks);
    }


    /**
     * @author Siuolplex
     * @reason Mixin Implementation
     */
    @Overwrite
    public void addToStrippables(Block input, Block output) {
        Map<Block, Block> strippables = new HashMap<>( AxeItemAccessor.getStrippables());
        strippables.put(input, output);
        AxeItemAccessor.setStrippables(strippables);
    }
}
