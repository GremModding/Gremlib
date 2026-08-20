package io.gremstudio.gremtest.submods.grem1;

import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.mod.HasConfig;
import io.gremstudio.gremlib.mod.HasRegistration;
import io.gremstudio.gremlib.mod.submod.SubGremMod;
import io.gremstudio.gremtest.submods.grem1.registry.SubGrem1Items;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;

public class SubGremtest1 extends SubGremMod implements HasRegistration, HasConfig {
    public static SubGremtest1 INSTANCE = null;

    public SubGremtest1(GremMod parent) {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a SubGremMod twice over!");
        }

        super(parent);

        INSTANCE = this;
    }

    @Override
    public String getSubmodID() {
        return "subgremtest_1";
    }

    @Override
    public void fireRegistry(Registry<?> registry) {
        if (registry.key().equals(Registries.ITEM)) {
            SubGrem1Items.init();
        }
    }
}
