package io.gremstudio.gremlib.fabric.impl.resources;

import io.gremstudio.gremlib.mod.GremMod;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class PackLoaderImpl {
    // Rather annoying that Fabric doesnt really care if its a resource or datapack. -2.
    public static void addPackBecauseFabricDoesntCare(GremMod mod, Identifier packID, Component displayName) {
        ResourceLoader.registerBuiltinPack(
                packID,
                FabricLoader.getInstance().getModContainer(mod.getModID()).get(),
                displayName,
                PackActivationType.NORMAL);
    }
}
