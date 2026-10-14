package io.gremstudio.gremlib.api.mod;

import net.minecraft.resources.Identifier;

public abstract class BaseGremMod {
    public BaseGremMod() {}

    public abstract String getModID();
    public abstract Identifier createId(String path);
}
