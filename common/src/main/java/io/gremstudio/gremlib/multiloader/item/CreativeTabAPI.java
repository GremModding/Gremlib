package io.gremstudio.gremlib.multiloader.item;

import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CreativeTabAPI {
    static Map<ResourceKey<CreativeModeTab>, List<InsertionData>> insertionsByTab = new HashMap<>();

    public static void insert(InsertionData data) {
        List<InsertionData> insertions = insertionsByTab.getOrDefault(data.getTab(), new ArrayList<>());
        insertions.add(data);
        insertionsByTab.put(data.getTab(), insertions);
    }

    public static void insertStart(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> newEntry, CreativeModeTab.TabVisibility visibility) {
        insert(new InsertionData(tab, newEntry, InsertionPoint.START, visibility));
    }

    public static void insertBefore(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> existingEntry, Supplier<ItemStack> newEntry, CreativeModeTab.TabVisibility visibility) {
        insert(new InsertionData(tab, existingEntry, newEntry, InsertionPoint.BEFORE, visibility));
    }

    public static void insertAfter(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> existingEntry, Supplier<ItemStack> newEntry, CreativeModeTab.TabVisibility visibility) {
        insert(new InsertionData(tab, existingEntry, newEntry, InsertionPoint.AFTER, visibility));
    }

    public static void insertEnd(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> newEntry, CreativeModeTab.TabVisibility visibility) {
        insert(new InsertionData(tab, newEntry, InsertionPoint.END, visibility));
    }

    public static void insertStart(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> newEntry) {
        insert(new InsertionData(tab, newEntry, InsertionPoint.START));
    }

    public static void insertBefore(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> existingEntry, Supplier<ItemStack> newEntry) {
        insert(new InsertionData(tab, existingEntry, newEntry, InsertionPoint.BEFORE));
    }

    public static void insertAfter(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> existingEntry, Supplier<ItemStack> newEntry) {
        insert(new InsertionData(tab, existingEntry, newEntry, InsertionPoint.AFTER));
    }

    public static void insertEnd(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> newEntry) {
        insert(new InsertionData(tab, newEntry, InsertionPoint.END));
    }

    public static Map<ResourceKey<CreativeModeTab>, List<InsertionData>> getInsertionsByTab() {
        return Map.copyOf(insertionsByTab);
    }

    public enum InsertionPoint {
        START(false), BEFORE(true), AFTER(true), END(false);

        private final boolean requiresExisting;

        InsertionPoint(boolean requiresExisting) {
            this.requiresExisting = requiresExisting;
        }

        public boolean requiresExisting() {
            return requiresExisting;
        }
    }

    public static class InsertionData {
        ResourceKey<CreativeModeTab> tab;
        Supplier<ItemStack> existingEntry;
        Supplier<ItemStack> newEntry;
        InsertionPoint point;
        CreativeModeTab.TabVisibility visibility;

        /**
         * Represents a creative tab addition.
         * @param tab Creative Tab.
         * @param existingEntry Current thing to append to, may be null IF appending to start/end.
         * @param newEntry New thing to add.
         * @param point Where to append?
         * @param visibility Where is this visible?
         */
        public InsertionData(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> existingEntry, Supplier<ItemStack> newEntry, InsertionPoint point, CreativeModeTab.TabVisibility visibility) {
            if (point.requiresExisting() && existingEntry == null) {
                throw new InsertionError("No existing entry for insertion");
            }

            this.tab = tab;
            this.existingEntry = existingEntry;
            this.newEntry = newEntry;
            this.point = point;
            this.visibility = visibility;
        }

        /**
         * Represents a creative tab addition.
         * @param tab Creative Tab.
         * @param newEntry New thing to add.
         * @param point ONLY IS START OR END. Where to append?
         * @param visibility Where is this variable?
         */
        public InsertionData(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> newEntry, InsertionPoint point, CreativeModeTab.TabVisibility visibility) {
            this(tab, null, newEntry, point, visibility);
        }

        /**
         * Represents a creative tab addition.
         * @param tab Creative Tab.
         * @param existingEntry Current thing to append to, may be null IF appending to start/end.
         * @param newEntry New thing to add.
         * @param point Where to append?
         */
        public InsertionData(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> existingEntry, Supplier<ItemStack> newEntry, InsertionPoint point) {
            this(tab, existingEntry, newEntry, point, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        /**
         * Represents a creative tab addition.
         * @param tab Creative Tab.
         * @param newEntry New thing to add.
         * @param point ONLY IS START OR END. Where to append?
         */
        public InsertionData(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> newEntry, InsertionPoint point) {
            this(tab, null, newEntry, point, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }


        public ItemStack getExistingEntry() {
            return existingEntry.get();
        }

        public ItemStack getNewEntry() {
            return newEntry.get();
        }

        public InsertionPoint getPoint() {
            return point;
        }

        public ResourceKey<CreativeModeTab> getTab() {
            return tab;
        }

        public CreativeModeTab.TabVisibility getVisibility() {
            return visibility;
        }



        public static class InsertionError extends Error {
            public InsertionError() {
                super();
            }

            public InsertionError(Throwable cause) {
                super(cause);
            }

            public InsertionError(String message) {
                super(message);
            }

            public InsertionError(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }
}
