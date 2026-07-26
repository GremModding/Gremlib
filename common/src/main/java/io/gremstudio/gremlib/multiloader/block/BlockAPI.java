package io.gremstudio.gremlib.multiloader.block;

import io.gremstudio.gremlib.multiloader.MixinImplementationError;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockAPI {
    /**
     * Adds a block to a block entity.
     *
     * @param block The block being added.
     * @param be The block entity to add it to.
     */
    public void addBlockToBE(BlockEntityType<?> be, Block block) {
        throw new MixinImplementationError("IMPLEMENTED IN MIXIN");
    }

    /**
     * Multiple block variant of addBlockToBE().
     */
    public void addBlocksToBE(BlockEntityType<?> be, Block... blocks) {
        for (Block block : blocks) {
            addBlockToBE(be, block);
        }
    }

    /**
     * This adds a block to the strippable map. Pretty much just an input and output deal.
     */

    // Yes I know there's a datamap for this on Neoforge, no I am not going to use it do you know how annoying it is to maintain a datamap alongside just a regular in-code solution?
    public void addToStrippables(Block input, Block output) {
        throw new MixinImplementationError("IMPLEMENTED IN MIXIN");
    }

}
