package io.gremstudio.gremlib.impl.client;

import io.gremstudio.gremlib.impl.Gremlib;
import io.gremstudio.gremlib.api.client.mod.GremModClient;
import io.gremstudio.gremlib.api.mod.GremMod;

public class GremlibClient extends GremModClient<Gremlib> {
    public static GremlibClient INSTANCE;

    public GremlibClient() {
        if (GremlibClient.INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't initialize the client mod twice.");
        }
        super(Gremlib.INSTANCE);

    }
}
