package io.gremstudio.gremlib.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class GremPressurePlateBlock extends PressurePlateBlock {
    public GremPressurePlateBlock(BlockSetType type, Properties properties) {
        super(type, properties.noCollision());
    }
}
