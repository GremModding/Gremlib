package io.gremstudio.gremlib.multiloader.resources;

import io.gremstudio.gremlib.mod.GremMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.msrandom.multiplatform.annotations.Expect;

/**
 * Adds additional resource/datapacks into the game from the /resourcepacks/ folder in resources.
 * <br> Due to a limitation from Fabric, you cannot choose another place to put the packs.
 * <br> Additionally, since Fabric and Neo register in different ways, it is highly recommended that you separate any
 * matching assets and data and manually link them from here. Most likely this will not be an issue, but if it is then
 * open something on Github.
 */
public class PackLoaderAPI {
    // GremMod mod, Identifier id, Component component

    /*
    id = Identifier.fromNamespaceAndPath(id.namespace(), "resourcepacks/" + id.path())
    event.addPackFinders(
                id,
                PackType.CLIENT_RESOURCES,
                component,
                PackSource.DEFAULT,
                false,
                Pack.Position.TOP)
     */

    /*
    ResourceLoader.registerBuiltinPack(
                id,
                FabricLoader.getInstance().getModContainer(mod.getID()).get(),
                component,
                PackActivationType.NORMAL)
     */

    @Expect
    public static void addResourcePack(GremMod mod, Identifier packID, Component displayName);

    @Expect
    public static void addDataPack(GremMod mod, Identifier packID, Component displayName);
}
