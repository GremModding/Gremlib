package io.gremstudio.gremlib.api.world.interaction.event.item;


import io.gremstudio.gremlib.api.world.interaction.context.item.ItemInteractionContext;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;

/**
 * These are world interactions WHILE THE ITEM IS AN ITEM ENTITY!
 *
 */
public interface ItemWorldInteractions {
    default void gremlib$onFrozen(ItemInteractionContext context, int ticksFrozen) {}
    default void gremlib$onDamage(ItemInteractionContext context,DamageSource source) {}
    default void gremlib$onFluidDipping(ItemInteractionContext context,FluidState fluidState) {}
    default void gremlib$onBurnt(ItemInteractionContext context,int ticksBurning) {}
    default void gremlib$onInExplosion(ItemInteractionContext context,Entity cause) {}
    default void gremlib$onShocked(ItemInteractionContext context) {}
}
