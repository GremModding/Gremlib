package io.gremstudio.gremtest.submods.grem1.registry;

import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import io.gremstudio.gremtest.Gremtest;
import io.gremstudio.gremtest.registry.GremtestTabs;
import io.gremstudio.gremtest.submods.grem1.SubGremtest1;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;
import java.util.function.Function;

public class SubGrem1Items {
    public static Item TEST_ITEM = register("test_item", Item::new, new Item.Properties(), (item) -> {
        CreativeTabAPI.insertEnd(GremtestTabs.GREMTEST_TAB, item::getDefaultInstance);
    });

    public static Item register(String name, Function<Item.Properties, Item> func, Item.Properties properties, Consumer<Item> postRegister) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, SubGremtest1.INSTANCE.createId(name));
        Item item = func.apply(properties.setId(key));
        Registry.register(BuiltInRegistries.ITEM, key, item);
        return item;
    }

    public static void init() {

    }
}
