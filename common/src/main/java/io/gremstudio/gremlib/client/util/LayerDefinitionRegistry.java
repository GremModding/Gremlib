package io.gremstudio.gremlib.client.util;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.HashMap;
import java.util.Map;

public class LayerDefinitionRegistry {
    private static Map<ModelLayerLocation, LayerDefinition> layers = new HashMap<>();

    public static Map<ModelLayerLocation, LayerDefinition> getLayers() {
        return layers;
    }

    public static void addLayer(ModelLayerLocation location, LayerDefinition def) {
        layers.put(location, def);
    }
}
