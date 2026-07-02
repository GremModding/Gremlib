package io.gremstudio.gremlib.mod;

import io.gremstudio.gremlib.mod.submod.SubGremMod;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.List;

public abstract class GremMod {
    public GremMod() {
        GremModInitialization.MODS.add(this);
    }

    // Main purpose is SIE's proposed modular system.
    public List<SubGremMod> getSubmods() {
        return List.of();
    }

    public abstract String getModID();

    public abstract Logger getLogger();

    public ResourceLocation createId(String path) {
        return ResourceLocation.fromNamespaceAndPath(getModID(), path);
    }

    public static class GremModReinitError extends Error {

        public GremModReinitError() {
            super();
        }

        public GremModReinitError(String message) {
            super(message);
        }
    }
}
