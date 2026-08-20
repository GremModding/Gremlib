package io.gremstudio.gremtest;

import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.mod.HasConfig;
import io.gremstudio.gremlib.mod.HasRegistration;
import io.gremstudio.gremlib.mod.submod.SubGremMod;
import io.gremstudio.gremtest.registry.GremtestItems;
import io.gremstudio.gremtest.registry.GremtestTabs;
import io.gremstudio.gremtest.submods.grem1.SubGremtest1;
import io.gremstudio.gremtest.submods.grem2.SubGremtest2;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Gremtest extends GremMod implements HasRegistration, HasConfig {
    private final Logger LOGGER = LoggerFactory.getLogger("Gremlib");
    public static Gremtest INSTANCE = null;
    private final List<SubGremMod> subGremMods = new ArrayList<>();

    public Gremtest() {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a GremMod twice over!");
        }

        super();
        INSTANCE = this;
        getLogger().info("Gremtest Initialized!");

        subGremMods.add(new SubGremtest1(this));
        subGremMods.add(new SubGremtest2(this));
    }

    @Override
    public String getModID() {
        return "gremtest";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public List<SubGremMod> getSubmods() {
        return subGremMods;
    }

    @Override
    public void fireRegistry(Registry<?> registry) {
        if (registry.key().equals(Registries.ITEM)) {
            GremtestItems.init();
        } else if (registry.key().equals(Registries.CREATIVE_MODE_TAB)) {
            GremtestTabs.registerTabs();
        }
    }
}
