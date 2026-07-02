package io.gremstudio.gremlib.client.util;

import io.gremstudio.gremlib.mixin.client.ModelLayersAccessor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayersUtil {
    public static ModelLayerLocation register(ModelLayerLocation location) {
        if (!ModelLayersAccessor.getAllModels().add(location)) {
            throw new IllegalStateException("Duplicate registration for " + location);
        } else {
            return location;
        }
    }

    public static ModelLayerLocation register(ResourceLocation model, String layer) {
        return register(new ModelLayerLocation(model, layer));
    }

    public static ModelLayerLocation register(ResourceLocation model) {
        return register(model, "main");
    }
}
