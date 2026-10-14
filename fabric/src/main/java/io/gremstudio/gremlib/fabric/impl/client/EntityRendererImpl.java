package io.gremstudio.gremlib.fabric.impl.client;

import io.gremstudio.gremlib.api.multiloader.client.EntityRendererAPI;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public class EntityRendererImpl {
    public static void registerLayers() {
        for (EntityRendererAPI.LayerInfo info : EntityRendererAPI.getLayers()) {
            ModelLayerRegistry.registerModelLayer(info.location(), info.definition()::get);
        }
    }
}
