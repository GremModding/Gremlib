package io.siuolplex.gremlib.client.mixtension;

import net.minecraft.client.resources.model.sprite.AtlasManager;

public interface AtlasManagerGetter {
    public default AtlasManager gremlib$getAtlasManager() {
        throw new AssertionError();
    }
}
