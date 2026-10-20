package io.gremstudio.gremlib.fabric.impl.networking;

import io.gremstudio.gremlib.api.multiloader.networking.NetworkAPI;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;

public class NetworkImpl {
    public static class PayloadImpl {
        public static void registerPayloads() {
            for (NetworkAPI.PayloadAPI.PayloadRegistration payload : NetworkAPI.PayloadAPI.getPayloads()) {
                if (payload.isClientbound()) {
                    registerClientboundPayload(payload);
                }

                if (payload.isServerbound()) {
                    registerServerboundPayload(payload);
                }
            }
        }

        private static void registerClientboundPayload(NetworkAPI.PayloadAPI.PayloadRegistration payload) {
            if (payload.stage().equals(NetworkAPI.NetworkingStage.PLAY)) {
                PayloadTypeRegistry.clientboundPlay().register(payload.type(), payload.codec());
                ClientPlayNetworking.registerGlobalReceiver(payload.type(), (pay, context) -> payload.clientHandling().handlePayload(pay, convertClientPlayContextToGremContext(context)));
            } else if (payload.stage().equals(NetworkAPI.NetworkingStage.CONFIG)) {
                PayloadTypeRegistry.clientboundConfiguration().register(payload.type(), payload.codec());
                ClientConfigurationNetworking.registerGlobalReceiver(payload.type(), (pay, context) -> payload.clientHandling().handlePayload(pay, convertClientConfigContextToGremContext(context)));
            }
        }

        private static void registerServerboundPayload(NetworkAPI.PayloadAPI.PayloadRegistration payload) {
            if (payload.stage().equals(NetworkAPI.NetworkingStage.PLAY)) {
                PayloadTypeRegistry.serverboundPlay().register(payload.type(), payload.codec());
                ServerPlayNetworking.registerGlobalReceiver(payload.type(), (pay, context) -> payload.clientHandling().handlePayload(pay, convertServerPlayContextToGremContext(context)));
            } else if (payload.stage().equals(NetworkAPI.NetworkingStage.CONFIG)) {
                PayloadTypeRegistry.serverboundConfiguration().register(payload.type(), payload.codec());
                ServerConfigurationNetworking.registerGlobalReceiver(payload.type(), (pay, context) -> payload.clientHandling().handlePayload(pay, convertServerConfigContextToGremContext(context)));
            }
        }

        public static NetworkAPI.PayloadAPI.PayloadContext convertClientPlayContextToGremContext(ClientPlayNetworking.Context fabricContext) {
            return new NetworkAPI.PayloadAPI.PayloadContext(fabricContext.packetContext().get(PacketContext.CONNECTION), fabricContext.player(),
            (packet) -> fabricContext.responseSender().sendPacket(packet), (reason) -> fabricContext.responseSender().disconnect(reason));
        }

        public static NetworkAPI.PayloadAPI.PayloadContext convertServerPlayContextToGremContext(ServerPlayNetworking.Context fabricContext) {
            return new NetworkAPI.PayloadAPI.PayloadContext(fabricContext.packetContext().get(PacketContext.CONNECTION), fabricContext.player(),
                    (packet) -> fabricContext.responseSender().sendPacket(packet), (reason) -> fabricContext.responseSender().disconnect(reason));
        }

        public static NetworkAPI.PayloadAPI.PayloadContext convertClientConfigContextToGremContext(ClientConfigurationNetworking.Context fabricContext) {
            return new NetworkAPI.PayloadAPI.PayloadContext(fabricContext.packetContext().get(PacketContext.CONNECTION), null,
                    (packet) -> fabricContext.responseSender().sendPacket(packet), (reason) -> fabricContext.responseSender().disconnect(reason));

        }

        public static NetworkAPI.PayloadAPI.PayloadContext convertServerConfigContextToGremContext(ServerConfigurationNetworking.Context fabricContext) {
            return new NetworkAPI.PayloadAPI.PayloadContext(fabricContext.packetContext().get(PacketContext.CONNECTION), null,
                    (packet) -> fabricContext.responseSender().sendPacket(packet), (reason) -> fabricContext.responseSender().disconnect(reason));

        }
    }
}
