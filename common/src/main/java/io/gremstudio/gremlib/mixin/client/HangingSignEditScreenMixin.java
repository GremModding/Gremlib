package io.gremstudio.gremlib.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.gremstudio.gremlib.block.sign.GremHangingSign;
import io.gremstudio.gremlib.client.UsesPalettes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.resources.Identifier;
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
    @Shadow private Identifier texture;

    @Unique
    boolean gremlib$palettedSign = false;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void gremlib$initSignTextureId(SignBlockEntity sign, boolean isFrontText, boolean shouldFilter, CallbackInfo ci) {
        if (sign.getBlockState().getBlock() instanceof GremHangingSign hangingSign) {
            Identifier guiTexture = hangingSign.getGuiTexture().texture();
            this.texture = Identifier.fromNamespaceAndPath(guiTexture.getNamespace(), guiTexture.getPath());
        }

        if (sign.getBlockState().getBlock() instanceof UsesPalettes) {
            gremlib$palettedSign = true;
        }
    }

    @WrapOperation(method = "extractSignBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"))
    private void gremlib$addSignSupport(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
        if (gremlib$palettedSign) {
            instance.blitSprite(renderPipeline, texture, 16, 16, 0, 0, x, y, width, height);
        } else {
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, textureWidth, textureHeight);
        }
    }
}
