package io.siuolplex.gremlib.mod;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Map;
import java.util.function.Consumer;

public interface HasRegistration {
    /**
     * Registration in any GremMod rely on having a registry map. It ties a given resource key to a method that will be called when its time to register that type.
     * @return The registry map
     */
    Map<ResourceKey<?>, Consumer<Registry<?>>> getOrMapRegistries();

    default void fireRegistry(Registry<?> registry) {
        if (getOrMapRegistries().containsKey(registry.key())) {
            getOrMapRegistries().get(registry.key()).accept(registry);
        }
    }
}
