package io.gremstudio.gremlib.mixin.client;

import io.gremstudio.gremlib.Gremlib;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ModelManager.class)
public class AtlasManagerMixin {
    @Shadow
    @Final
    @Mutable
    private static Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void gremlib$addAtlases(CallbackInfo ci) {
        HashMap<ResourceLocation, ResourceLocation> map = new HashMap<>(VANILLA_ATLASES);
        map.put(Gremlib.INSTANCE.createId("textures/atlas/boats.png"), Gremlib.INSTANCE.createId("boats"));
        VANILLA_ATLASES = map;
    }
}
