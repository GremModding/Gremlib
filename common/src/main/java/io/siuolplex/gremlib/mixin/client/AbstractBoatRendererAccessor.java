package io.siuolplex.gremlib.mixin.client;

import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBoatRenderer.class)
public interface AbstractBoatRendererAccessor {
   @Mutable
   @Accessor("texture")
   void gremlib$setTexture(Identifier texture);
}
