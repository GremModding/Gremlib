package io.gremstudio.gremlib.mixin.client;

import com.google.common.collect.ImmutableMap;
import io.gremstudio.gremlib.client.util.LayerDefinitionRegistry;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {
    @ModifyVariable(method = "createRoots", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"))
    private static ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> addDefinitions(ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> result) {
        LayerDefinitionRegistry.getLayers().entrySet().forEach(result::put);
        return result;
    }
}
