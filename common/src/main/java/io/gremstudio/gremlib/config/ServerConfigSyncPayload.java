package io.gremstudio.gremlib.config;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

// GremConfig is based on https://github.com/khazoda-mods/khazodacore/blob/26.1/common/src/main/java/com/khazoda/core/config/ServerConfigSyncPayload.java
// Explicit permission to use this has been granted.
public record ServerConfigSyncPayload(GremConfigSync sync, Map<String, String> serverValues) implements CustomPacketPayload {
    public ServerConfigSyncPayload {
        Objects.requireNonNull(sync, "sync");
        serverValues = Collections.unmodifiableMap(new LinkedHashMap<>(serverValues));
    }

    // Yes it bothers me that these seem to be able to be Method Referenceable but the mod wont build if you do that
    static ServerConfigSyncPayload read(GremConfigSync sync, RegistryFriendlyByteBuf buffer) {
        return new ServerConfigSyncPayload(sync, buffer.readMap(LinkedHashMap::new,  input -> input.readUtf(), input -> input.readUtf()));
    }

    void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeMap(serverValues,(output, value) -> output.writeUtf(value), (output, value) -> output.writeUtf(value));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return sync.type();
    }
}
