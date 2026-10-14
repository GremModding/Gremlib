package io.gremstudio.gremlib.api.mod;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

public abstract class GremMod extends BaseGremMod {
    public GremMod() {
        super();
        GremModlist.addModsAndSubmods(this);
    }

    public abstract Logger getLogger();

    @Override
    public Identifier createId(String path) {
        return Identifier.fromNamespaceAndPath(getModID(), path);
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
