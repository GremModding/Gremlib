package io.siuolplex.gremlib.neoforge;

import io.siuolplex.gremlib.multiloader.Loader;
import io.siuolplex.gremlib.multiloader.block.LoaderBlockAPI;
import io.siuolplex.gremlib.neoforge.api.block.NeoBlockAPI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

import java.nio.file.Path;

public final class GremNeoLoader implements Loader {
    @Override
    public boolean isDevMode() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public boolean isClient() {
        return FMLLoader.getCurrent().getDist() != Dist.DEDICATED_SERVER;
    }

    @Override
    public String getLoader() {
        return "Neoforge";
    }

    @Override
    public Path getGameDir() {
        return FMLLoader.getCurrent().getGameDir();
    }

    @Override
    public boolean isModPresent(String mod) {
        return ModList.get().isLoaded(mod);
    }

    @Override
    public boolean isDataGenerating() {
            return DatagenModLoader.isRunningDataGen();
        }

    @Override
    public LoaderBlockAPI blocks() {
        return NeoBlockAPI.INSTANCE;
    }
}
