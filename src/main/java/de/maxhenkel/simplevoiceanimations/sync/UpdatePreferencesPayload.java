package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record UpdatePreferencesPayload(Preferences preferences) implements CustomPacketPayload {

    public static final Type<UpdatePreferencesPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimpleVoiceAnimations.MODID, "update_preferences"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdatePreferencesPayload> CODEC = StreamCodec.of(
            (buffer, payload) -> payload.preferences.write(buffer),
            buffer -> new UpdatePreferencesPayload(Preferences.read(buffer))
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
