package io.siuolplex.gremlib.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import io.siuolplex.gremlib.client.UsesPalettes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBoatRenderer.class)
public abstract class AbstractBoatRendererMixin extends EntityRenderer<AbstractBoat, BoatRenderState> {
    @Shadow
    protected abstract EntityModel<BoatRenderState> model();

    @Shadow
    @Final
    protected Identifier texture;

    protected AbstractBoatRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @WrapOperation(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/BoatRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", 
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    public void gremlib$useAtlas(SubmitNodeCollector instance, Model<?> model, Object state, PoseStack poseStack, Identifier texture,
                                 int lightCoords, int overlayCoords, int outlineColor, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
                                 Operation<Void> original) {
        if (this instanceof UsesPalettes) {
            AtlasManager manager = entityRenderDispatcher.gremlib$getAtlasManager();
            instance.submitModel(this.model(), (BoatRenderState) state, poseStack, lightCoords, overlayCoords, -1, new SpriteId(Identifier.fromNamespaceAndPath("minecraft", "textures/atlas/blocks.png"), texture), manager, outlineColor, crumblingOverlay);
        } else {
            original.call(instance, model, state, poseStack, texture, lightCoords, overlayCoords, outlineColor, crumblingOverlay);
        }
    }
}
