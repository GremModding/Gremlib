package io.gremstudio.gremlib.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import io.gremstudio.gremlib.world.interaction.entity.EntityWorldInteractions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityWorldInteractions {

    // This almost certainly needs to be accessed through another interface injector but eh.
    @Unique
    int gremlib$ticksIHaveBeenOnFire = 0;

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract int getTicksFrozen();

    @Shadow
    public abstract boolean isOnFire();

    @Inject(method = "tick", at = @At("TAIL"))
    public void gremlib$onTick(CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            if (this.getTicksFrozen() > 0) {
                this.gremlib$onFrozen(getTicksFrozen());
            }
            if (this.isOnFire()) {
                gremlib$ticksIHaveBeenOnFire++;
                this.gremlib$onBurnt();
            } else {
                gremlib$ticksIHaveBeenOnFire = 0;
            }
        }
    }

    // Funnily enough, I was looking at the mixin extras wiki to get an idea on how to target this area and they just straight up have what Im looking for as an example, neat!
    @Definition(id = "putShort", method = "Lnet/minecraft/world/level/storage/ValueOutput;putShort(Ljava/lang/String;S)V")
    @Expression("?.putShort('Fire', ?)")
    @Inject(method = "saveWithoutId", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void gremlib$saveInteractionData(ValueOutput output, CallbackInfo ci) {
        output.putShort("gremlib:ticks_been_burning", (short) gremlib$ticksIHaveBeenOnFire);
    }

    @Definition(id = "getShortOr", method = "Lnet/minecraft/world/level/storage/ValueInput;getShortOr(Ljava/lang/String;S)I")
    @Expression("?.getShortOr('Fire', ?)")
    @Inject(method = "load", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void gremlib$loadInteractionData(ValueInput input, CallbackInfo ci) {
        gremlib$ticksIHaveBeenOnFire = input.getShortOr("gremlib:ticks_been_burning", (short) 0);
    }

    @Override
    public void gremlib$onBurnt() {
        EntityWorldInteractions.super.gremlib$onBurnt();

    }
}
