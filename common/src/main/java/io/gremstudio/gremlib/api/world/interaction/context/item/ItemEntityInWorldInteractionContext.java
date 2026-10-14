package io.gremstudio.gremlib.api.world.interaction.context.item;

import io.gremstudio.gremlib.api.world.interaction.context.entity.EntityInteractionContext;
import net.minecraft.core.Position;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;

// Helps determine to an item that the event happened as an ItemEntity.
public class ItemEntityInWorldInteractionContext extends ItemInteractionContext {
    private ItemEntity self;

    public ItemEntityInWorldInteractionContext(Position pos, ItemEntity self) {
        super(pos);
        this.self = self;
    }

    public ItemEntityInWorldInteractionContext(EntityInteractionContext context, ItemEntity self) {
        this(context.getPos(), self);
    }

    public ItemEntity getEntity() {
        return self;
    }
}
