package io.siuolplex.gremlib.neoforge;

import io.siuolplex.gremlib.Gremlib;
import io.siuolplex.gremlib.mod.GremModInitialization;
import io.siuolplex.gremlib.multiloader.Loader;
import io.siuolplex.gremlib.neoforge.impl.BlockEntityHandler;
import io.siuolplex.gremlib.neoforge.initializers.GremModInitalizationEvent;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(GremlibNeoforge.ID)
public class GremlibNeoforge {
    public static final String ID = "gremlib";
    public static Gremlib gremlib;

    public static final Loader MODLOADER = new GremNeoLoader();
    public final IEventBus modBus;
    private boolean mappedRegistries = false;

    public GremlibNeoforge(IEventBus modBus) {
        this.modBus = modBus;
        modBus.register(this);
        modBus.register(BlockEntityHandler.class);
    }

    @SubscribeEvent
    public void onConstruct(FMLConstructModEvent e) {

        gremlib = new Gremlib(MODLOADER);

        Gremlib.INSTANCE.getLogger().info("onConstruct");
        e.enqueueWork(() -> {
            Gremlib.INSTANCE.getLogger().info("Firing GremModInitalizationEvent");
            ModLoader.postEventWrapContainerInModOrder(new GremModInitalizationEvent());
            Gremlib.INSTANCE.getLogger().info("Done firing");
        });
    }

    @SubscribeEvent
    public void onRegistration(RegisterEvent event) {
        GremModInitialization.fireRegistry(event.getRegistry());
    }
}
