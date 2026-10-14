package io.gremstudio.gremtest.submods.grem2;

import io.gremstudio.gremlib.api.mod.GremMod;
import io.gremstudio.gremlib.api.mod.submod.SubGremMod;

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
    public String getModID() {
        return "subgremtest_2";
    }
}
