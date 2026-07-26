package io.gremstudio.gremlib.mod;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Map;
import java.util.function.Consumer;

public interface HasRegistration {
    // In 0.1, registries used a "Registry Map" but I feel that obfuscates the actual process a bit much. Now its just get the registry and call what you need.
    void fireRegistry(Registry<?> registry);
}
