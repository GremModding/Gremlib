package io.gremstudio.gremlib.mixin;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public interface WoodTypeMixin {
    @Invoker("register")
    static WoodType registerWoodType(WoodType type) {
        throw new AssertionError();
    }
}
