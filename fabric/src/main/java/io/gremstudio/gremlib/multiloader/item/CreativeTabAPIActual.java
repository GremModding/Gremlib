package io.gremstudio.gremlib.multiloader.item;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.function.Function;

public class CreativeTabAPIActual {
    @Actual
    public static CreativeModeTab createTab(Function<CreativeModeTab.Builder, CreativeModeTab> tabFunc) {
        return tabFunc.apply(FabricCreativeModeTab.builder());
    }
}
