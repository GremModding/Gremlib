package io.gremstudio.gremtest.registry;

import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import io.gremstudio.gremtest.Gremtest;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GremtestTabs {
    private static Map<ResourceKey<CreativeModeTab>, Function<CreativeModeTab.Builder, CreativeModeTab>> tabs = new HashMap<>();

    public static final ResourceKey<CreativeModeTab> GREMTEST_TAB = prepareTabAndCreateKey(ResourceKey.create(Registries.CREATIVE_MODE_TAB, Gremtest.INSTANCE.createId("gremtest")),
            builder -> builder
                    .title(Component.translatable("gremtest.tab"))
                    .icon(() -> GremtestItems.TEST_ITEM.getDefaultInstance())
                    .build());

    private static ResourceKey<CreativeModeTab> prepareTabAndCreateKey(ResourceKey<CreativeModeTab> tabKey, Function<CreativeModeTab.Builder, CreativeModeTab> tabFunc) {
        tabs.put(tabKey, tabFunc);
        return tabKey;
    }

    public static void registerTabs() {
        for (Map.Entry<ResourceKey<CreativeModeTab>, Function<CreativeModeTab.Builder, CreativeModeTab>> tabEntry : tabs.entrySet()) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, tabEntry.getKey(), CreativeTabAPI.createTab(tabEntry.getValue()));
        }
    }
}
