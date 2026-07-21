package io.gremstudio.gremlib.fabric.mixin;

import io.gremstudio.gremlib.fabric.impl.item.FabricCreativeTabs;
import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Function;

@Mixin(CreativeTabAPI.class)
public class CreativeTabAPIMixin {
    /**
     * @author Siuolplex
     * @reason Mixin Implementation
     */
    @Overwrite
    public static CreativeModeTab createTab(Function<CreativeModeTab.Builder, CreativeModeTab> tabFunc) {
        return tabFunc.apply(FabricCreativeModeTab.builder());
    }
}
