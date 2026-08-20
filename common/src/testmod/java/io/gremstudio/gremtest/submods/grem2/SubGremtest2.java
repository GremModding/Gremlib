package io.gremstudio.gremtest.submods.grem2;

import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.mod.HasRegistration;
import io.gremstudio.gremlib.mod.submod.SubGremMod;
import io.gremstudio.gremtest.submods.grem1.registry.SubGrem1Items;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;

public class SubGremtest2 extends SubGremMod {
    public static SubGremtest2 INSTANCE = null;

    public SubGremtest2(GremMod parent) {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a SubGremMod twice over!");
        }

        super(parent);
        INSTANCE = this;
    }

    @Override
    public String getSubmodID() {
        return "subgremtest_2";
    }
}
