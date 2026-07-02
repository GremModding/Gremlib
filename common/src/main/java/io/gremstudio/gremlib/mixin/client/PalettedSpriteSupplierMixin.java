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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.Optional;

@Mixin(targets = "net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations$PalettedSpriteSupplier")
public class PalettedSpriteSupplierMixin {
    @Shadow
    @Final
    private LazyLoadedImage baseImage;

    @Shadow
    @Final
    private ResourceLocation permutationLocation;

    @WrapOperation(method = "apply(Lnet/minecraft/client/renderer/texture/atlas/SpriteResourceLoader;)Lnet/minecraft/client/renderer/texture/SpriteContents;", at = @At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/metadata/animation/FrameSize;Lcom/mojang/blaze3d/platform/NativeImage;Lnet/minecraft/server/packs/resources/ResourceMetadata;)Lnet/minecraft/client/renderer/texture/SpriteContents;"))
    public SpriteContents gremlib$makeMetadataSupportedSpriteContents(ResourceLocation name, FrameSize originalFramesize, NativeImage image, ResourceMetadata metadata, Operation<SpriteContents> original) {
        ResourceMetadata realMetadata;
        try {
            realMetadata = ((LazyLoadedImageAccessor)(baseImage)).getResource().metadata();
        } catch (IOException exception) {
            Gremlib.INSTANCE.getLogger().error("Unable to parse metadata from {}", permutationLocation, exception);
            realMetadata = metadata;
        }

        Optional<AnimationMetadataSection> animSection = realMetadata.getSection(AnimationMetadataSection.SERIALIZER);

        FrameSize frameSize;
        if (animSection.isPresent()) {
            frameSize = animSection.get().calculateFrameSize(image.getWidth(), image.getHeight());
            if (!Mth.isMultipleOf(image.getWidth(), frameSize.width()) || !Mth.isMultipleOf(image.getHeight(), frameSize.height())) {
                Gremlib.INSTANCE.getLogger().error("Image {} size {},{} is not multiple of frame size {},{}", permutationLocation, image.getWidth(), image.getHeight(), frameSize.width(), frameSize.height());
                image.close();
            }
        } else {
            frameSize = originalFramesize;
        }

        return new SpriteContents(name, frameSize, image, realMetadata);
    }
}
