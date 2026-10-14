package io.gremstudio.gremlib.api.mod.capabilities;

import io.gremstudio.gremlib.api.mod.submod.SubGremMod;

import java.util.List;

public interface HasSubmods {
    public List<SubGremMod> getSubmods();
}
