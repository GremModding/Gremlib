package io.gremstudio.gremlib.api.multiloader.resources;

import io.gremstudio.gremlib.api.mod.GremMod;
import io.gremstudio.gremlib.neoforge.impl.resources.PackLoaderImpl;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.msrandom.multiplatform.annotations.Actual;

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
