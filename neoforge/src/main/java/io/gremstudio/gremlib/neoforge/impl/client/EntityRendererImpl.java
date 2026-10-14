package io.gremstudio.gremlib.neoforge.impl.client;

import io.gremstudio.gremlib.api.multiloader.client.EntityRendererAPI;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class EntityRendererImpl {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (EntityRendererAPI.LayerInfo layer : EntityRendererAPI.getLayers()) {
            event.registerLayerDefinition(layer.location(), layer.definition());
        }
    }
}
