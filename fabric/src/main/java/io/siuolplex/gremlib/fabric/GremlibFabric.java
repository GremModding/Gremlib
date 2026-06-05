package io.siuolplex.gremlib.fabric;

import io.siuolplex.gremlib.Gremlib;
import io.siuolplex.gremlib.fabric.initializers.GremModInitializer;
import io.siuolplex.gremlib.mod.GremModInitialization;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;
import java.util.Map;

public class GremlibFabric implements ModInitializer {

    @Override
    @SuppressWarnings("unchecked")
    public void onInitialize() {
        new Gremlib(new GremFabricLoader());
        FabricLoader.getInstance().invokeEntrypoints(GremModInitializer.ENTRYPOINT_ID, GremModInitializer.class, GremModInitializer::onGremModInitalization);


        Map<ResourceKey<Registry<?>>, Registry<?>> registryMap = new HashMap<>();

        for (Map.Entry<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> entry : BuiltInRegistries.REGISTRY.entrySet()) {
            registryMap.put((ResourceKey<Registry<?>>) entry.getKey(), entry.getValue());
        }

        // Registries are fascinating, given the fact that there is a chain of dependencies of each registry.
        // Personally, I always thought "Oh yeah all you need is Blocks and Items", but I believe you need to do Entities in between the two.
        GremModInitialization.fireRegistry(registryMap.remove(Registries.BLOCK));
        GremModInitialization.fireRegistry(registryMap.remove(Registries.ENTITY_TYPE));
        GremModInitialization.fireRegistry(registryMap.remove(Registries.ITEM));

        registryMap.forEach((registryResourceKey, registry) -> GremModInitialization.fireRegistry(registry));
    }
}
