package io.gremstudio.gremlib.neoforge.impl.resources;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.ArrayList;
import java.util.List;

public class PackLoaderImpl {
    public record PackData(Identifier packID, Component displayName, boolean isData) {}

    private static final List<PackData> packDataList = new ArrayList<>();

    @SubscribeEvent
    public void addPacks(AddPackFindersEvent event) {
        for (PackData packData : packDataList) {
            event.addPackFinders(
                    packData.packID,
                    (packData.isData) ? PackType.SERVER_DATA : PackType.CLIENT_RESOURCES,
                    packData.displayName,
                    PackSource.DEFAULT,
                    false,
                    Pack.Position.TOP);
        }
    }

    public static void addPackData(PackData data) {
        packDataList.add(data);
    }
}
