package io.gremstudio.gremtest.registry;

import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import io.gremstudio.gremtest.Gremtest;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;
import java.util.function.Function;

public class GremtestItems {
    public static Item TEST_ITEM = register("test_item", Item::new, new Item.Properties(), (item) -> {
        CreativeTabAPI.insertEnd(GremtestTabs.GREMTEST_TAB, item::getDefaultInstance);
    });

    public static Item register(String name, Function<Item.Properties, Item> func, Item.Properties properties, Consumer<Item> postRegister) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Gremtest.INSTANCE.createId(name));
        Item item = func.apply(properties.setId(key));
        Registry.register(BuiltInRegistries.ITEM, key, item);
        return item;
    }

    public static void init() {

    }
}
