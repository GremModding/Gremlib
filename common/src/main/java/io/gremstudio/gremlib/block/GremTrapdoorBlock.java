package io.gremstudio.gremlib.block;

import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class GremTrapdoorBlock extends TrapDoorBlock {
    public GremTrapdoorBlock(BlockSetType type, Properties properties) {
        super(type, properties.noOcclusion());
    }
}
