package io.gremstudio.gremlib.api.world.interaction.context;

import net.minecraft.core.Position;

// I am a bit worried about memory usage with contexts. But eh, hopefully its all good.
public abstract class AbstractWorldInteractionContext {
    private Position pos;

    public AbstractWorldInteractionContext(Position pos) {
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }
}
