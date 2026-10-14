package io.gremstudio.gremlib.impl.mixin;

import io.gremstudio.gremlib.api.world.interaction.context.entity.EntityInteractionContext;
import io.gremstudio.gremlib.api.world.interaction.context.item.ItemEntityInWorldInteractionContext;
import io.gremstudio.gremlib.api.world.interaction.event.entity.EntityWorldInteractions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Debug(export = true)
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements EntityWorldInteractions {
    @Shadow
    public abstract ItemStack getItem();

    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void gremlib$onFrozen(EntityInteractionContext context, int ticksFrozen) {
        super.gremlib$onFrozen(context, ticksFrozen);
        getItem().getItem().gremlib$onFrozen(gremlib$createItemIC(context), ticksFrozen);
    }

    @Override
    public void gremlib$onBurnt(EntityInteractionContext context, int ticks) {
        super.gremlib$onBurnt(context, ticks);
        getItem().getItem().gremlib$onBurnt(gremlib$createItemIC(context), ticks);
    }

    @Override
    public void gremlib$onExplosion(EntityInteractionContext context, Entity cause) {
        super.gremlib$onExplosion(context, cause);
        getItem().getItem().gremlib$onInExplosion(gremlib$createItemIC(context), cause);
    }

    @Override
    public void gremlib$onFluidDipping(EntityInteractionContext context, FluidState fluidState) {
        super.gremlib$onFluidDipping(context, fluidState);
        getItem().getItem().gremlib$onFluidDipping(gremlib$createItemIC(context), fluidState);
    }

    @Override
    public void gremlib$onGeneralDamage(EntityInteractionContext context, DamageSource source) {
        super.gremlib$onGeneralDamage(context, source);
        getItem().getItem().gremlib$onDamage(gremlib$createItemIC(context), source);
    }

    @Override
    public void gremlib$onShocked(EntityInteractionContext context) {
        super.gremlib$onShocked(context);
        getItem().getItem().gremlib$onShocked(gremlib$createItemIC(context));
    }

    @Unique
    private ItemEntityInWorldInteractionContext gremlib$createItemIC(EntityInteractionContext context) {
        return new ItemEntityInWorldInteractionContext(context, (ItemEntity) ((Object)this));
    }
}
