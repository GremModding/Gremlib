package io.siuolplex.gremlib.mixin.client;

import io.siuolplex.gremlib.client.mixtension.AtlasManagerGetter;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin implements AtlasManagerGetter {
    @Shadow
    @Final
    private AtlasManager atlasManager;

    // Hi Im EntityRenderDispatcher and I have a vestigial AtlasManager used by Gremlib now.
    @Override
    public AtlasManager gremlib$getAtlasManager() {
        return this.atlasManager;
    }
}
