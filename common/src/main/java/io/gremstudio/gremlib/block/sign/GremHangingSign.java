package io.gremstudio.gremlib.block.sign;

import net.minecraft.client.resources.model.Material;

public interface GremHangingSign extends GremSign {
    Material getGuiTexture();
    boolean isSprited();
}
