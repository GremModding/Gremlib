package io.gremstudio.gremlib.mixin.client;

import com.mojang.datafixers.util.Pair;
import io.gremstudio.gremlib.client.util.SignHelper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Sheets.class)
public class SheetsMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void gremlib$addSigns(CallbackInfo ci) {
        Map<WoodType, Pair<Material, Material>> signReplacements = SignHelper.getSignReplacements();
        for (Map.Entry<WoodType, Pair<Material, Material>> entry : signReplacements.entrySet()) {
            Sheets.SIGN_MATERIALS.replace(entry.getKey(), entry.getValue().getFirst());
            Sheets.HANGING_SIGN_MATERIALS.replace(entry.getKey(), entry.getValue().getSecond());
        }
    }
}
