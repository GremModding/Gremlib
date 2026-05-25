package io.siuolplex.gremlib.fabric;

import io.siuolplex.gremlib.Gremlib;
import io.siuolplex.gremlib.fabric.initializers.GremModInitializer;
import io.siuolplex.gremlib.mod.GremModInitialization;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

import java.util.Map;

public class GremlibFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        new Gremlib(new GremFabricLoader());
        FabricLoader.getInstance().invokeEntrypoints(GremModInitializer.ENTRYPOINT_ID, GremModInitializer.class, GremModInitializer::onGremModInitalization);

        for (Map.Entry<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> registry : BuiltInRegistries.REGISTRY.entrySet()) {
            GremModInitialization.fireRegistry(registry.getValue());
        }
    }
}
