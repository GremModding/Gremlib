package io.gremstudio.gremlib.client.mixtension;

import net.minecraft.client.resources.model.sprite.AtlasManager;

public interface AtlasManagerGetter {
    default AtlasManager gremlib$getAtlasManager() {
        throw new AssertionError();
    }
}
