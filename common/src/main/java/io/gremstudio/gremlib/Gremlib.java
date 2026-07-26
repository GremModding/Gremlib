package io.gremstudio.gremlib;

import io.gremstudio.gremlib.config.v2.GremModConfig;
import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.mod.HasConfig;
import io.gremstudio.gremlib.multiloader.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gremlib extends GremMod implements HasConfig {
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
}
