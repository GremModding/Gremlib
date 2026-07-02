package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class GremWallHangingSignBlock extends WallHangingSignBlock implements GremHangingSign {
    private final ResourceLocation guiTexture;
    private final ResourceLocation texture;

    public GremWallHangingSignBlock(WoodType type, Properties settings, ResourceLocation texture, ResourceLocation guiTexture) {
        super(type, settings);
        this.texture = texture;
        this.guiTexture = guiTexture;
    }

    @Override
    public Material getGuiTexture() {
        return new Material(ResourceLocation.withDefaultNamespace("textures/atlas/gui.png"), guiTexture);
    }

    @Override
    public boolean isSprited() {
        return false;
    }

    @Override
    public Material getTexture() {
        return new Material(ResourceLocation.withDefaultNamespace("textures/atlas/signs.png"), texture);
    }
}
