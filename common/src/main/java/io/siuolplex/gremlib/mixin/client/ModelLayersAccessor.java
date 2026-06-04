package io.siuolplex.gremlib.mixin.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

// Fabric API has a very similar mixin for what is essentially the same purpose. Found https://github.com/FabricMC/fabric-api/blob/26.1.2/fabric-rendering-v1/src/client/java/net/fabricmc/fabric/mixin/client/rendering/ModelLayersAccessor.java
// Licensed under Apache License 2.0. Only real change is method name change. Its simple enough where this notice PROBABLY isnt needed but rather safe than sorry.
// This does need to actually work on Neo so eh.
@Mixin(ModelLayers.class)
public interface ModelLayersAccessor {
    @Accessor("ALL_MODELS")
    static Set<ModelLayerLocation> getAllModels() {
        throw new AssertionError();
    }
}
