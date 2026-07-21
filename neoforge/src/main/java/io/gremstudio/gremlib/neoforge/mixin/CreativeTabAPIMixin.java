package io.gremstudio.gremlib.neoforge.mixin;

import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
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
        return tabFunc.apply(CreativeModeTab.builder());
    }
}
