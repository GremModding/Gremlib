package io.gremstudio.gremtest.neoforge;

import io.gremstudio.gremlib.neoforge.initializers.GremModInitalizationEvent;
import io.gremstudio.gremtest.Gremtest;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

@Mod("gremtest")
public class GremtestNeoforge {
    private static Gremtest gremtest;

    final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    final IEventBus modEventBus = modLoadingContext.getActiveContainer().getEventBus();

    public GremtestNeoforge() {
        modEventBus.register(this);
    }

    public static Gremtest getGremtest() {
        return gremtest;
    }

    @SubscribeEvent
    public void onGremModInitalization(GremModInitalizationEvent event) {
        gremtest = new Gremtest();
    }
}
