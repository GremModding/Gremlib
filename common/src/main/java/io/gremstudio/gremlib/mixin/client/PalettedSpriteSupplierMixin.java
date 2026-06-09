package io.gremstudio.gremlib.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.platform.NativeImage;
import io.gremstudio.gremlib.Gremlib;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Mixin(targets = "net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations$PalettedSpriteSupplier")
public class PalettedSpriteSupplierMixin {
    @Shadow
    @Final
    private LazyLoadedImage baseImage;

    @Shadow
    @Final
    private Identifier permutationLocation;

    @Inject(method = "get", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/atlas/sources/LazyLoadedImage;get()Lcom/mojang/blaze3d/platform/NativeImage;"))
    public void gremlib$readMetadata(SpriteResourceLoader loader, CallbackInfoReturnable<SpriteContents> cir, @Share("animationInfo") LocalRef<Optional<AnimationMetadataSection>> animationInfoRef, @Share("textureInfo") LocalRef<Optional<TextureMetadataSection>> textureInfoRef) {
        try {
            ResourceMetadata metadata = ((LazyLoadedImageAccessor)(baseImage)).getResource().metadata();
            animationInfoRef.set(metadata.getSection(AnimationMetadataSection.TYPE));
            textureInfoRef.set(metadata.getSection(TextureMetadataSection.TYPE));
        } catch (IOException exception) {
            Gremlib.INSTANCE.getLogger().error("Unable to parse metadata from {}", permutationLocation, exception);
        }
    }

    @WrapOperation(method = "get", at = @At(value = "NEW", target = "(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/resources/metadata/animation/FrameSize;Lcom/mojang/blaze3d/platform/NativeImage;)Lnet/minecraft/client/renderer/texture/SpriteContents;"))
    public SpriteContents gremlib$makeMetadataSupportedSpriteContents(Identifier name, FrameSize originalFramesize, NativeImage image, Operation<SpriteContents> original, @Share("animationInfo") LocalRef<Optional<AnimationMetadataSection>> animationInfoRef, @Share("textureInfo") LocalRef<Optional<TextureMetadataSection>> textureInfoRef) {
        FrameSize frameSize;
        if (animationInfoRef.get().isPresent()) {
            frameSize = animationInfoRef.get().get().calculateFrameSize(image.getWidth(), image.getHeight());
            if (!Mth.isMultipleOf(image.getWidth(), frameSize.width()) || !Mth.isMultipleOf(image.getHeight(), frameSize.height())) {
                Gremlib.INSTANCE.getLogger().error("Image {} size {},{} is not multiple of frame size {},{}", permutationLocation, image.getWidth(), image.getHeight(), frameSize.width(), frameSize.height());
                image.close();
            }
        } else {
            frameSize = originalFramesize;
        }

        Optional<AnimationMetadataSection> anim = (animationInfoRef.get().isPresent()) ? animationInfoRef.get() : Optional.empty();
        Optional<TextureMetadataSection> texture = (textureInfoRef.get().isPresent()) ? textureInfoRef.get() : Optional.empty();

        return new SpriteContents(name, frameSize, image, anim, List.of(), texture);
    }
}
