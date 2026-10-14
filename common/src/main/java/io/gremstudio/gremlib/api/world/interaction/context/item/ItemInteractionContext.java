package io.gremstudio.gremlib.api.world.interaction.context.item;

import io.gremstudio.gremlib.api.world.interaction.context.AbstractWorldInteractionContext;
import net.minecraft.core.Position;


public abstract class ItemInteractionContext extends AbstractWorldInteractionContext {
    public ItemInteractionContext(Position pos) {
        super(pos);
    }
}
