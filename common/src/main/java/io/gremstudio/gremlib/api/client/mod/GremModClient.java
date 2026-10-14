package io.gremstudio.gremlib.api.client.mod;

import io.gremstudio.gremlib.api.mod.BaseGremMod;

//You know, it IS kind of like a SubGremMod but designed for clients...
public abstract class GremModClient<M extends BaseGremMod> {
    M parent;

    public GremModClient(M parent) {
        this.parent = parent;
    }

    public M getParent() {
        return parent;
    }
}
