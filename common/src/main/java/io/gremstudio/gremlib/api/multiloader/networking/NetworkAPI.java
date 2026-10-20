package io.gremstudio.gremlib.api.multiloader.networking;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkAPI {

    public static class PayloadAPI {
        public static void registerBi(CustomPacketPayload.Type<?> type, StreamCodec<?, ?> codec) {}
        public static void registerClient(CustomPacketPayload.Type<?> type, StreamCodec<?, ?> codec) {}
        public static void registerServer(CustomPacketPayload.Type<?> type, StreamCodec<?, ?> codec) {}

    }

    public static enum NetworkingStage {
        LOGIN, PLAY
    }
}
