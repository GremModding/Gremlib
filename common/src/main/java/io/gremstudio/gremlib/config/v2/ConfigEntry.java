package io.gremstudio.gremlib.config.v2;

import com.mojang.serialization.Codec;

// ConfigEntry is based on https://github.com/khazoda-mods/khazodacore/blob/26.1/common/src/main/java/com/khazoda/core/config/KhazConfigHelper.java
// Explicit permission to use this has been granted.

public record ConfigEntry<T>(String entryName, T defaultValue, String comment, Codec<T> adapter, boolean serverSynced) implements ConfigMember {
    public ConfigEntry(String entryName, T defaultValue, String comment, Codec<T> adapter) {
        this(entryName, defaultValue, comment, adapter, true);
    }
}
