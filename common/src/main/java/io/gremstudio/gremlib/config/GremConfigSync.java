package io.gremstudio.gremlib.config;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;

// GremConfig is based on https://github.com/khazoda-mods/khazodacore/blob/26.1/common/src/main/java/com/khazoda/core/config/KhazConfigSync.java
// Explicit permission to use this has been granted.
public class GremConfigSync {
    private final ResourceLocation payloadId;
    private final CustomPacketPayload.Type<ServerConfigSyncPayload> type;
    private final StreamCodec<RegistryFriendlyByteBuf, ServerConfigSyncPayload> codec;

    private GremConfigSync(ResourceLocation payloadId) {
        this.payloadId = Objects.requireNonNull(payloadId, "payloadId");
        this.type = new CustomPacketPayload.Type<>(this.payloadId);
        this.codec = CustomPacketPayload.codec(ServerConfigSyncPayload::write, buffer -> ServerConfigSyncPayload.read(this, buffer));
    }

    public static GremConfigSync create(ResourceLocation payloadId) {
        return new GremConfigSync(payloadId);
    }

    public CustomPacketPayload.Type<ServerConfigSyncPayload> type() {
        return type;
    }

    public ResourceLocation payloadId() {
        return payloadId;
    }

    public StreamCodec<RegistryFriendlyByteBuf, ServerConfigSyncPayload> codec() {
        return codec;
    }

    public ServerConfigSyncPayload payload(Map<String, String> serverValues) {
        return new ServerConfigSyncPayload(this, serverValues);
    }
}
