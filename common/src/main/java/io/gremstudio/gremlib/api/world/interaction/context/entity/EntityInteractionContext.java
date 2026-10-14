package io.gremstudio.gremlib.api.world.interaction.context.entity;

import io.gremstudio.gremlib.api.world.interaction.context.AbstractWorldInteractionContext;
import net.minecraft.core.Position;

public class EntityInteractionContext extends AbstractWorldInteractionContext {
    public EntityInteractionContext(Position pos) {
        super(pos);
    }
}
