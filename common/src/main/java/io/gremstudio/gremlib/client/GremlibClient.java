package io.gremstudio.gremlib.client;

import io.gremstudio.gremlib.Gremlib;
import io.gremstudio.gremlib.client.mod.GremModClient;
import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.multiloader.Loader;
import net.minecraft.client.renderer.entity.EntityRenderers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GremlibClient extends GremModClient {
    public static GremlibClient INSTANCE;
    private static final List<Supplier<GremModClient>> clientModList = new ArrayList<>();

    public GremlibClient() {
        if (GremlibClient.INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't initialize the client mod twice.");
        }
        super(Gremlib.INSTANCE);

        for (Supplier<GremModClient> clientModSupplier : clientModList) {
            clientModSupplier.get();
        }
    }

    public static void createClientMod(Supplier<GremModClient> supplier) {
        if (Gremlib.LOADER.isClient()) {
            clientModList.add(supplier);
        }
    }
}
