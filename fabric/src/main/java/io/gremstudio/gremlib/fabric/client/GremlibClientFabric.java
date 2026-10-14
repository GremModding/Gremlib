package io.gremstudio.gremlib.fabric.client;

import io.gremstudio.gremlib.api.mod.GremModInitialization;
import io.gremstudio.gremlib.fabric.impl.client.EntityRendererImpl;
import io.gremstudio.gremlib.impl.client.GremlibClient;
import net.fabricmc.api.ClientModInitializer;

public class GremlibClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GremModInitialization.initalizeClient();
        EntityRendererImpl.registerLayers();
    }
}
