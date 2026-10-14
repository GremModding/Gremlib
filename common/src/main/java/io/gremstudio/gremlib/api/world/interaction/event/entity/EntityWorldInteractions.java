package io.gremstudio.gremlib.api.world.interaction.event.entity;

import io.gremstudio.gremlib.api.world.interaction.context.entity.EntityInteractionContext;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public interface EntityWorldInteractions {
    // Block Interactions
    default void gremlib$onFrozen(EntityInteractionContext context, int ticksFrozen) {}

    // Damage Interactions
    default void gremlib$onFallDamage(EntityInteractionContext context, BlockState landedOnBlock, float damageMult) {}
    default void gremlib$onPVPDamage(EntityInteractionContext context, Entity attacker) {}
    default void gremlib$onGeneralDamage(EntityInteractionContext context, DamageSource source) {}

    // Fluid Interactions
    default void gremlib$onFluidDipping(EntityInteractionContext context, FluidState fluidState) {}

    // Inter-Entity Interactions

    // Misc Interactions
    default void gremlib$onBurnt(EntityInteractionContext context, int ticks) {}
    default void gremlib$onExplosion(EntityInteractionContext context, Entity cause) {}
    default void gremlib$onShocked(EntityInteractionContext context) {}
}
