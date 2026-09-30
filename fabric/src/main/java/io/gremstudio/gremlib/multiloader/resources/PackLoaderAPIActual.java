package io.gremstudio.gremlib.multiloader.resources;

import io.gremstudio.gremlib.mod.GremMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.msrandom.multiplatform.annotations.Actual;
import io.gremstudio.gremlib.fabric.impl.resources.PackLoaderImpl;

public class PackLoaderAPIActual {

    @Actual
    public static void addResourcePack(GremMod mod, Identifier packID, Component displayName) {
        PackLoaderImpl.addPackBecauseFabricDoesntCare(mod, packID, displayName);
    }

    @Actual
    public static void addDataPack(GremMod mod, Identifier packID, Component displayName) {
        PackLoaderImpl.addPackBecauseFabricDoesntCare(mod, packID, displayName);
    }



}
