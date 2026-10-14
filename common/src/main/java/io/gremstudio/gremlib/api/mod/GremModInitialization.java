package io.gremstudio.gremlib.api.mod;

import io.gremstudio.gremlib.api.mod.capabilities.HasRegistration;
import io.gremstudio.gremlib.api.mod.capabilities.WithClientside;
import net.minecraft.core.Registry;

public class GremModInitialization {
    public static void fireRegistry(Registry<?> registry) {
        for (BaseGremMod mod : GremModlist.getModList()) {
            if (mod instanceof HasRegistration hasReg) {
                hasReg.fireRegistry(registry);
            }
        }
    }

    public static void initalizeClient() {
        for (BaseGremMod mod : GremModlist.getModList()) {
            if (mod instanceof WithClientside withClient) {
                withClient.initializeClient();
            }
        }
    }
}
