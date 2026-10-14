package io.gremstudio.gremlib.api.mod.capabilities;

import net.minecraft.core.Registry;

/**
 Denotes if a GremMod (or GremSubMod) needs to register things in order to work.
 Pretty much every content mod will need to use this, as it allows you to fire registries in a way similar to Neoforge.
 <p>On Fabric, the registration is taken care of right after your mods load with GremModInitalization. </p>
 <p>On Neo, the registration is called by RegistryEvent. </p>
 */
public interface HasRegistration {
    /**
     * Meant to fire registries when needed. Intended for you to check what registry is being fired and using that.
     * <br>For instance, from Flipside:
     * <pre><code>
     *      public void fireRegistry(Registry<?> registry) {
     *          if (registry.key().equals(Registries.BLOCK)) {
     *              FlipsideBlocks.init();
     *          } else if (registry.key().equals(Registries.ITEM)) {
     *              FlipsideItems.init();
     *          }
     *      }
     * </code></pre>
     * @param registry The registry that is currently being fired.
     */
    // In 0.1, registries used a "Registry Map" but I feel that obfuscates the actual process a bit much. Now its just get the registry and call what you need.
    void fireRegistry(Registry<?> registry);
}
