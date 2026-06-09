package io.gremstudio.gremlib.multiloader.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Boilerplate block api stuff thats shared amongst the loaders.
 */
public interface LoaderBlockAPI {
    /**
     * Adds a block to a block entity.
     * @param block The block being added
     * @param be The block entity to add it to.
     */
    void addBlockToBE(BlockEntityType<?> be, Block block);

    /**
     * Multi-block variant of addBlockToBE()
     */
    default void addBlocksToBE(BlockEntityType<?> be, Block... blocks) {
        for (Block block : blocks) {
            addBlockToBE(be, block);
        }
    }

    /**
     * Adds to the Strippable map in AxeItem.
     *
     * Yes I know there's a datamap for this on Neoforge, no I am not going to use it do you know how annoying it is to maintain a datamap alongside just a regular in-code solution?
     */
    void addToStrippables(Block input, Block output);

}
