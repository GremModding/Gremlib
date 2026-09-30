package io.gremstudio.gremlib.client.mod;

import io.gremstudio.gremlib.mod.GremMod;

public abstract class GremModClient {
    public final GremMod mod;

    // Maybe consider making this more agnostic to if its a mod or submod?
    public GremModClient(GremMod mod) {
        this.mod = mod;
    }
}
