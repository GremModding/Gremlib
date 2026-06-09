package io.gremstudio.gremlib.mod;

import io.gremstudio.gremlib.mod.submod.SubGremMod;
import net.minecraft.core.Registry;

import java.util.ArrayList;
import java.util.List;

public class GremModInitialization {
    public static final List<GremMod> MODS = new ArrayList<>();

    public static void fireRegistry(Registry<?> registry) {
        for (GremMod mod : MODS) {
            if (mod instanceof HasRegistration) {
                ((HasRegistration) mod).fireRegistry(registry);
            }

            if (!mod.getSubmods().isEmpty()) {
                for (SubGremMod subMod : mod.getSubmods()) {
                    if (subMod instanceof HasRegistration) {
                        ((HasRegistration) subMod).fireRegistry(registry);
                    }
                }
            }
        }
    }
}
