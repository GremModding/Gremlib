package io.gremstudio.gremlib.api.world.interaction.context.be;

import io.gremstudio.gremlib.api.world.interaction.context.AbstractWorldInteractionContext;
import net.minecraft.core.Position;

public class BlockInteractionContext extends AbstractWorldInteractionContext {
    public BlockInteractionContext(Position pos) {
        super(pos);
    }
}
