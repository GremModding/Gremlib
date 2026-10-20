package io.gremstudio.gremlib.neoforge.impl.networking;

import io.gremstudio.gremlib.api.multiloader.networking.NetworkAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;

public class NetworkImpl {
    public static class PayloadImpl {
        // On the mod event bus
        @SubscribeEvent
        public static void register(RegisterPayloadHandlersEvent event) {
            List<NetworkAPI.PayloadAPI.PayloadRegistration> payloads = NetworkAPI.PayloadAPI.getPayloads();

            PayloadRegistrar payloadRegistrar = event.registrar("1");
            for (NetworkAPI.PayloadAPI.PayloadRegistration payload : payloads) {
                if (payload.stage().equals(NetworkAPI.NetworkingStage.PLAY)) {
                    if (payload.isClientbound()) {
                        payloadRegistrar.playToClient(payload.type(), payload.codec(), (pay, context) -> {
                            payload.clientHandling().handlePayload(pay, convertToGremContext(context));
                        });
                    }

                    if (payload.isServerbound()) {
                        payloadRegistrar.playToServer(payload.type(), payload.codec(), (pay, context) -> {
                            payload.serverHandling().handlePayload(pay, convertToGremContext(context));
                        });
                    }
                } else if (payload.stage().equals(NetworkAPI.NetworkingStage.CONFIG)) {
                    if (payload.isClientbound()) {
                        payloadRegistrar.configurationToClient(payload.type(), payload.codec(), (pay, context) -> {
                            payload.clientHandling().handlePayload(pay, convertToGremContext(context));
                        });
                    }

                    if (payload.isServerbound()) {
                        payloadRegistrar.configurationToServer(payload.type(), payload.codec(), (pay, context) -> {
                            payload.serverHandling().handlePayload(pay, convertToGremContext(context));
                        });
                    }
                }
            }
        }

        public static NetworkAPI.PayloadAPI.PayloadContext convertToGremContext(IPayloadContext neoContext) {
            Player player = null;
            try {
                player = neoContext.player();
            } catch (UnsupportedOperationException exception) {
                // Can reasonably assume is a config event. Ignore the exception.
            }

            return new NetworkAPI.PayloadAPI.PayloadContext(neoContext.connection(), player, neoContext::reply, neoContext::disconnect);
        }
    }
}
