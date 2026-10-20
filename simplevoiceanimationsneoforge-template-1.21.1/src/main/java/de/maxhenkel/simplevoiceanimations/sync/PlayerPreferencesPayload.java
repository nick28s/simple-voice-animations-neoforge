package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record PlayerPreferencesPayload(UUID player, Preferences preferences) implements CustomPacketPayload {

    public static final Type<PlayerPreferencesPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimpleVoiceAnimations.MODID, "player_preferences"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerPreferencesPayload> CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeUUID(payload.player);
                payload.preferences.write(buffer);
            },
            buffer -> new PlayerPreferencesPayload(buffer.readUUID(), Preferences.read(buffer))
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
