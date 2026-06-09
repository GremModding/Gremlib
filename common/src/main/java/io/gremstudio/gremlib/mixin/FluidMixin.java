package io.gremstudio.gremlib.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidState.class)
public class FluidMixin {
    @Inject(method = "entityInside", at = @At("TAIL"))
    public void gremlib$entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, CallbackInfo ci) {
        entity.gremlib$onFluidDipping((FluidState)(Object)this);
    }
}
