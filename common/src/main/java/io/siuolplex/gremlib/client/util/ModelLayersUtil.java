package io.siuolplex.gremlib.client.util;

import io.siuolplex.gremlib.mixin.client.ModelLayersAccessor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.resources.Identifier;

public class ModelLayersUtil {
    public static ModelLayerLocation register(ModelLayerLocation location) {
        if (!ModelLayersAccessor.getAllModels().add(location)) {
            throw new IllegalStateException("Duplicate registration for " + location);
        } else {
            return location;
        }
    }

    public static ModelLayerLocation register(Identifier model, String layer) {
        return register(new ModelLayerLocation(model, layer));
    }

    public static ModelLayerLocation register(Identifier model) {
        return register(model, "main");
    }

    public static ArmorModelSet<ModelLayerLocation> registerArmorSet(Identifier model) {
        return new ArmorModelSet<>(
                register(model, "helmet"),
                register(model, "chestplate"),
                register(model, "leggings"),
                register(model, "boots"));
    }
}
