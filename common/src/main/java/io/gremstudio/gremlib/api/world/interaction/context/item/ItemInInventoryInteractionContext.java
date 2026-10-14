package io.gremstudio.gremlib.api.world.interaction.context.item;

import net.minecraft.core.Position;
import net.minecraft.world.inventory.Slot;

/* In theory, this could be made to work with items in any type of inventory. Block entity, entity, etc.
    Im not sure if that will be the case in practice, but you know.
 */
public class ItemInInventoryInteractionContext extends ItemInteractionContext {
    public ItemInInventoryInteractionContext(Position pos, Slot slot) {
        super(pos);
    }
}
