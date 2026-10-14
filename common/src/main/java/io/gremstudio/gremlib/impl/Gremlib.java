package io.gremstudio.gremlib.impl;

import io.gremstudio.gremlib.impl.client.GremlibClient;
import io.gremstudio.gremlib.api.config.v2.GremModConfig;
import io.gremstudio.gremlib.api.mod.GremMod;
import io.gremstudio.gremlib.api.mod.capabilities.WithClientside;
import io.gremstudio.gremlib.api.multiloader.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gremlib extends GremMod implements WithClientside {
    public static Loader LOADER = null;
    private final Logger LOGGER = LoggerFactory.getLogger("Gremlib");
    public static Gremlib INSTANCE = null;
    public static GremModConfig CONFIG = null;

    public Gremlib(Loader loader) {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a GremMod twice over!");
        }

        LOADER = loader;
        super();
        INSTANCE = this;
        CONFIG = new GremModConfig("1.0.0");
    }

    @Override
    public String getModID() {
        return "gremlib";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public GremlibClient initializeClient() {
        return new GremlibClient();
    }
}
