package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.resources.model.sprite.SpriteId;

public interface GremHangingSign extends GremSign {
    SpriteId getGuiTexture();
    boolean isSprited();
}
