package io.gremstudio.gremlib.api.mod.capabilities;

import io.gremstudio.gremlib.api.client.mod.GremModClient;
import io.gremstudio.gremlib.api.mod.BaseGremMod;

public interface WithClientside {
    public GremModClient<? extends BaseGremMod> initializeClient();
}
