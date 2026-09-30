package io.gremstudio.gremlib.fabric.client;

import io.gremstudio.gremlib.client.GremlibClient;
import io.gremstudio.gremlib.client.mod.GremModClient;
import net.fabricmc.api.ClientModInitializer;

public class GremlibClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new GremlibClient();
    }
}
