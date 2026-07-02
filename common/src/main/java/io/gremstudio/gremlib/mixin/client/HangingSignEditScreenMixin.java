package io.gremstudio.gremlib.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.gremstudio.gremlib.block.sign.GremHangingSign;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// Code is based on Terraform
// Found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/mixin/MixinHangingSignEditScreen.java
@Mixin(HangingSignEditScreen.class)
public class HangingSignEditScreenMixin {

    @Final
    @Mutable
    @Shadow private ResourceLocation texture;

    @Unique
    boolean gremlib$palettedSign = false;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gremlib$initSignTextureId(SignBlockEntity sign, boolean isFrontText, boolean shouldFilter, CallbackInfo ci) {
        if (sign.getBlockState().getBlock() instanceof GremHangingSign hangingSign) {
            ResourceLocation guiTexture = hangingSign.getGuiTexture().texture();
            this.texture = ResourceLocation.fromNamespaceAndPath(guiTexture.getNamespace(), guiTexture.getPath());
            if (hangingSign.isSprited()) {
                gremlib$palettedSign = true;
            }
        }
    }


    @WrapOperation(method = "renderSignBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void gremlib$addSignSupport(GuiGraphics instance, ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
        if (gremlib$palettedSign) {
            instance.blitSprite(texture, textureWidth, textureHeight, 0, 0, x, y, width, height);
        } else {
            original.call(instance, texture, x, y, u, v, width, height, textureWidth, textureHeight);
        }
    }
}
