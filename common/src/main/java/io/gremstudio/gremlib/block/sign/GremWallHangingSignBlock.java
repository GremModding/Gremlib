package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class GremWallHangingSignBlock extends WallHangingSignBlock implements GremHangingSign {
    private final Identifier guiTexture;
    private final Identifier texture;

    public GremWallHangingSignBlock(WoodType type, Properties settings, Identifier texture, Identifier guiTexture) {
        super(type, settings);
        this.texture = texture;
        this.guiTexture = guiTexture;
    }

    @Override
    public SpriteId getGuiTexture() {
        return new SpriteId(Sheets.SIGN_SHEET, guiTexture);
    }

    @Override
    public SpriteId getTexture() {
        return new SpriteId(Sheets.SIGN_SHEET, texture);
    }
}
