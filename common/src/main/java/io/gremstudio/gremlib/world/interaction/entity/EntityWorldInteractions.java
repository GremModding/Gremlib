package io.gremstudio.gremlib.world.interaction.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

// Might be some overlap, but I think its fair.
public interface EntityWorldInteractions {
    // Block Interactions
    default void gremlib$onFrozen(int ticksFrozen) {}

    // Damage Interactions
    default void gremlib$onFallDamage(BlockState landedOnBlock, float damageMult) {}
    default void gremlib$onPVPDamage(Entity attacker) {}
    default void gremlib$onGeneralDamage(DamageSource source) {}

    // Fluid Interactions
    default void gremlib$onFluidDipping(FluidState fluidState) {}

    // Inter-Entity Interactions

    // Misc Interactions
    default void gremlib$onBurnt() {}
    default void gremlib$onExplosion(Explosion explosion) {}
    default void gremlib$onShocked() {}
    default void gremlib$onShulkerRecieved() {}


}
