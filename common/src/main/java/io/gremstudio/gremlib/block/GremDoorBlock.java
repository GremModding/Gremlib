package io.gremstudio.gremlib.block;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class GremDoorBlock extends DoorBlock {
    public GremDoorBlock(BlockSetType type, Properties properties) {
        super(type, properties.noOcclusion());
    }
}
