package io.gremstudio.gremlib.api.mod;

import io.gremstudio.gremlib.api.mod.capabilities.HasSubmods;
import io.gremstudio.gremlib.api.mod.submod.SubGremMod;

import java.util.ArrayList;
import java.util.List;

public class GremModlist {
    private static final List<BaseGremMod> MODS = new ArrayList<>();

    public static void addModsAndSubmods(BaseGremMod mod) {
        MODS.add(mod);
        if (mod instanceof HasSubmods hasSubMod) {
            for (SubGremMod subMod : hasSubMod.getSubmods()) {
                addModsAndSubmods(subMod);
            }
        }
    }

    public static List<BaseGremMod> getModList() {
        return new ArrayList<>(MODS);
    }

}
