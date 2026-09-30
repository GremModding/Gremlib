package io.gremstudio.gremlib.multiloader.resources;

import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.neoforge.impl.resources.PackLoaderImpl;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.msrandom.multiplatform.annotations.Actual;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.ArrayList;
import java.util.List;

public class PackLoaderAPIActual {
    @Actual
    public static void addResourcePack(GremMod mod, Identifier packID, Component displayName) {
        packID = Identifier.fromNamespaceAndPath(packID.getNamespace(), "resourcepacks/" + packID.getPath());
        PackLoaderImpl.addPackData(new PackLoaderImpl.PackData(packID, displayName, false));
    }

    @Actual
    public static void addDataPack(GremMod mod, Identifier packID, Component displayName) {
        packID = Identifier.fromNamespaceAndPath(packID.getNamespace(), "resourcepacks/" + packID.getPath());
        PackLoaderImpl.addPackData(new PackLoaderImpl.PackData(packID, displayName, true));
    }
}
