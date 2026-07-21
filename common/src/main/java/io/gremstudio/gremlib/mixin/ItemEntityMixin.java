package io.gremstudio.gremlib.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getItem();

    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    public void gremlib$onFrozen(int ticksFrozen) {
        getItem().getItem().gremlib$onSelfFrozen(ticksFrozen);
    }

    @Override
    public void gremlib$onBurnt(int ticks) {
        getItem().getItem().gremlib$onSelfBurnt(ticks);
    }

    @Override
    public void gremlib$onExplosion(Entity cause) {
        getItem().getItem().gremlib$onSelfInExplosion(cause);
    }

    @Override
    public void gremlib$onFluidDipping(FluidState fluidState) {
        getItem().getItem().gremlib$onSelfFluidDipping(fluidState);
    }

    @Override
    public void gremlib$onGeneralDamage(DamageSource source) {
        getItem().getItem().gremlib$onSelfDamage(source);
    }

    @Override
    public void gremlib$onShocked() {
        getItem().getItem().gremlib$onSelfShocked();
    }
}
