package io.gremstudio.gremlib.client.util;

import com.mojang.datafixers.util.Pair;
import io.gremstudio.gremlib.block.sign.GremHangingSign;
import io.gremstudio.gremlib.block.sign.GremSign;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.HashMap;
import java.util.Map;

public class SignHelper {
    private static final Map<WoodType, Pair<SpriteId, SpriteId>> signReplacements = new HashMap<>();

    public static Map<WoodType, Pair<SpriteId, SpriteId>> getSignReplacements() {
        return signReplacements;
    }

    public static void addReplacement(WoodType type, SpriteId signSprite, SpriteId hangingSignSprite) {
        SignHelper.signReplacements.put(type, new Pair<>(signSprite, hangingSignSprite));
    }

    public static void addReplacement(WoodType type, GremSign sign, GremHangingSign hangingSign) {
        addReplacement(type, sign.getTexture(), hangingSign.getTexture());
    }
}
