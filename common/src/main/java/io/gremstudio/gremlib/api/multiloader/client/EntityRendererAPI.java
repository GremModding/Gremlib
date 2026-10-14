package io.gremstudio.gremlib.api.multiloader.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class EntityRendererAPI {
    private static final List<LayerInfo> layerDefinitions = new ArrayList<>();

    public static void addLayer(ModelLayerLocation location, Supplier<LayerDefinition> layer) {
        addLayer(new LayerInfo(location, layer));
    }

    public static void addLayer(LayerInfo info) {
        layerDefinitions.add(info);
    }

    public static void addLayers(LayerInfo... infos) {
        addLayers(Arrays.asList(infos));
    }

    public static void addLayers(Collection<LayerInfo> infos) {
        layerDefinitions.addAll(infos);
    }

    public static List<LayerInfo> getLayers() {
        return new ArrayList<>(layerDefinitions);
    }

    public record LayerInfo(ModelLayerLocation location, Supplier<LayerDefinition> definition) {

    }
}
