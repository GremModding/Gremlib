package io.gremstudio.gremlib;

import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.multiloader.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Gremlib extends GremMod {
    public static Loader LOADER = null;
    private final Logger LOGGER = LoggerFactory.getLogger("Gremlib");
    public static Gremlib INSTANCE = null;

    public Gremlib(Loader loader) {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a GremMod twice over!");
        }

        LOADER = loader;
        super();
        INSTANCE = this;
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
