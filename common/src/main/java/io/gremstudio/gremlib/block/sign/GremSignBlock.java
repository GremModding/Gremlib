package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

// Code is based on Terraform
// Original found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/block/TerraformSignBlock.java
public class GremSignBlock extends StandingSignBlock implements GremSign {
    private final ResourceLocation texture;

    public GremSignBlock(WoodType type, Properties settings, ResourceLocation texture) {
        super(type, settings.noOcclusion().noCollission());
        this.texture = texture;
    }

    @Override
    public Material getTexture() {
        return new Material(ResourceLocation.withDefaultNamespace("textures/atlas/signs.png"), texture);
    }
}
