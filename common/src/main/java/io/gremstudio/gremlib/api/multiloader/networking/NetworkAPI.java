package io.gremstudio.gremlib.api.multiloader.networking;

import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NetworkAPI {
    public static class PayloadAPI {
        private static final List<PayloadRegistration> payloads = new ArrayList<>();

        public static void registerPayload(PayloadRegistration payload) {
            payloads.add(payload);
        }

        public static void registerPayload(CustomPacketPayload.Type<CustomPacketPayload> type, StreamCodec<FriendlyByteBuf, CustomPacketPayload> codec, NetworkingStage stage, @Nullable PayloadHandling<CustomPacketPayload> clientHandling, @Nullable PayloadHandling<CustomPacketPayload> serverHandling) {
            registerPayload(new PayloadRegistration(type, codec, stage, clientHandling, serverHandling));
        }

        public static void registerClientboundPayload(CustomPacketPayload.Type<CustomPacketPayload> type, StreamCodec<FriendlyByteBuf, CustomPacketPayload> codec, NetworkingStage stage, @Nullable PayloadHandling<CustomPacketPayload> clientHandling) {
            registerPayload(new PayloadRegistration(type, codec, stage, clientHandling, null));
        }

        public static void registerServerboundPayload(CustomPacketPayload.Type<CustomPacketPayload> type, StreamCodec<FriendlyByteBuf, CustomPacketPayload> codec, NetworkingStage stage, @Nullable PayloadHandling<CustomPacketPayload> serverHandling) {
            registerPayload(new PayloadRegistration(type, codec, stage, null, serverHandling));
        }

        public static List<PayloadRegistration> getPayloads() {
            return new ArrayList<>(payloads);
        }


        public record PayloadRegistration(
                CustomPacketPayload.Type<CustomPacketPayload> type,
                StreamCodec<FriendlyByteBuf, CustomPacketPayload> codec,
                NetworkingStage stage,
                @Nullable PayloadHandling<CustomPacketPayload> clientHandling,
                @Nullable PayloadHandling<CustomPacketPayload> serverHandling
        ) {
            public static <T extends CustomPacketPayload, B extends FriendlyByteBuf> PayloadRegistration castConstruction(CustomPacketPayload.Type<T> type, StreamCodec<B, T> codec, NetworkingStage stage, @Nullable PayloadHandling<T> clientHandling, @Nullable PayloadHandling<T> serverHandling) {
                return new PayloadRegistration((CustomPacketPayload.Type<CustomPacketPayload>) type, (StreamCodec<FriendlyByteBuf, CustomPacketPayload>) codec, stage, (PayloadHandling<CustomPacketPayload>) clientHandling, (PayloadHandling<CustomPacketPayload>) serverHandling);
            }

            public boolean isServerbound() {
                return serverHandling() != null;
            }

            public boolean isClientbound() {
                return clientHandling() != null;
            }
        }

        public interface PayloadHandling<T extends CustomPacketPayload> {
            void handlePayload(T payload, PayloadContext context);
        }

        public static class PayloadContext {
            Connection connection;
            @Nullable Player player; //Todo: Should be in Play's context when I make that
            Consumer<CustomPacketPayload> responder;
            Consumer<Component> disconnector;

            public PayloadContext(Connection connection, @Nullable Player player, Consumer<CustomPacketPayload> responder, Consumer<Component> disconnector) {
                this.connection = connection;
                this.player = player;
                this.responder = responder;
                this.disconnector = disconnector;
            }

            public Connection getConnection() {
                return connection;
            }

            public @Nullable Player getPlayer() {
                return player;
            }


            public void reply(CustomPacketPayload payload) {
                responder.accept(payload);
            }

            public void disconnect(Component reason) {
                disconnector.accept(reason);
            }

        }
    }



    public enum NetworkingStage {
        CONFIG, PLAY;

        NetworkingStage() {

        }
    }
}
