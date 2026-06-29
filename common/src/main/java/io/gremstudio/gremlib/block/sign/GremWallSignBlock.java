package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

// Code is based on Terraform
// Original found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/block/TerraformSignBlock.java
public class GremWallSignBlock extends WallSignBlock implements GremSign {
    private final Identifier texture;

    public GremWallSignBlock(WoodType type, Properties settings, Identifier texture) {
        super(type, settings.noOcclusion().noCollision());
        this.texture = texture;
    }

    @Override
    public SpriteId getTexture() {
        return new SpriteId(Identifier.withDefaultNamespace("textures/atlas/signs.png"), texture);
    }
}
