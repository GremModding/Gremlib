package io.gremstudio.gremlib.fabric;

import io.gremstudio.gremlib.fabric.api.block.FabricBlockAPI;
import io.gremstudio.gremlib.multiloader.Loader;
import io.gremstudio.gremlib.multiloader.block.LoaderBlockAPI;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class GremFabricLoader implements Loader {
    @Override
    public boolean isDevMode() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public String getLoader() {
        return "fabric";
    }

    @Override
    public Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public boolean isModPresent(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

    @Override
    public boolean isDataGenerating() {
        return System.getProperty("fabric-api.datagen") != null;
    }

    @Override
    public LoaderBlockAPI blocks() {
        return FabricBlockAPI.INSTANCE;
    }
}
