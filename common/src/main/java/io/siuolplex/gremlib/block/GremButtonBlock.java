package io.siuolplex.gremlib.block;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class GremButtonBlock extends ButtonBlock {
    public GremButtonBlock(BlockSetType type, int pressTicks, Properties properties) {
        super(type, pressTicks, properties.noCollision());
    }
}
