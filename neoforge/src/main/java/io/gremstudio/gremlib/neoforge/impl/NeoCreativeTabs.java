package io.gremstudio.gremlib.neoforge.impl;

import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.*;

public class NeoCreativeTabs {
    @SubscribeEvent
    public void prepareTabFilling(BuildCreativeModeTabContentsEvent event) {
        Map<ResourceKey<CreativeModeTab>, List<CreativeTabAPI.InsertionData>> insertionsByTab = CreativeTabAPI.getInsertionsByTab();
        for (ResourceKey<CreativeModeTab> key : insertionsByTab.keySet()) {
            for (CreativeTabAPI.InsertionData insertion : insertionsByTab.get(key)) {
                switch (insertion.getPoint()) {
                    case START -> event.insertFirst(insertion.getNewEntry(), insertion.getVisibility());
                    case BEFORE -> event.insertBefore(insertion.getExistingEntry(), insertion.getNewEntry(), insertion.getVisibility());
                    case AFTER -> event.insertAfter(insertion.getExistingEntry(), insertion.getNewEntry(), insertion.getVisibility());
                    case END -> event.accept(insertion.getNewEntry(), insertion.getVisibility());
                }
            }
        }
    }
}
