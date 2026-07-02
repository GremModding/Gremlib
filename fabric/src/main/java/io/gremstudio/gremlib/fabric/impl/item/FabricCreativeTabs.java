package io.gremstudio.gremlib.fabric.impl.item;

import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.*;


public class FabricCreativeTabs {
    public static void prepareTabFilling() {
        Map<ResourceKey<CreativeModeTab>, List<CreativeTabAPI.InsertionData>> insertionsByTab = CreativeTabAPI.getInsertionsByTab();
        for (ResourceKey<CreativeModeTab> key : insertionsByTab.keySet()) {
            ItemGroupEvents.modifyEntriesEvent(key).register(listener -> {
                for (CreativeTabAPI.InsertionData insertion : insertionsByTab.get(key)) {
                    switch (insertion.getPoint()) {
                        case START -> listener.prepend(insertion.getNewEntry(), insertion.getVisibility());
                        case BEFORE -> listener.addBefore(insertion.getExistingEntry(), Collections.singletonList(insertion.getNewEntry()), insertion.getVisibility());
                        case AFTER -> listener.addAfter(insertion.getExistingEntry(), Collections.singletonList(insertion.getNewEntry()), insertion.getVisibility());
                        case END -> listener.accept(insertion.getNewEntry(), insertion.getVisibility());
                    }
                }
            });
        }
    }
}
