package io.gremstudio.gremlib.world.interaction.item;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;

/**
 * These are world interactions WHILE THE ITEM IS AN ITEM ENTITY!
 *
 */
public interface ItemWorldInteractions {
    default void gremlib$onSelfFrozen(int ticksFrozen) {}
    default void gremlib$onSelfDamage(DamageSource source) {}
    default void gremlib$onSelfFluidDipping(FluidState fluidState) {}
    default void gremlib$onSelfBurnt(int ticksBurning) {}
    default void gremlib$onSelfInExplosion(Entity cause) {}
    default void gremlib$onSelfShocked() {}
}
