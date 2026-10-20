package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ClientPreferencesSync {

    private static final Map<UUID, Preferences> PREFERENCES = new ConcurrentHashMap<>();

    private ClientPreferencesSync() {
    }

    public static void receive(PlayerPreferencesPayload payload) {
        PREFERENCES.put(payload.player(), payload.preferences());
    }

    public static void clear() {
        PREFERENCES.clear();
    }

    public static void sendLocalPreferences() {
        PacketDistributor.sendToServer(new UpdatePreferencesPayload(ClientConfig.getPreferences()));
    }

    public static Preferences getPreferences(UUID playerId) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && playerId.equals(minecraft.player.getUUID())) {
            return ClientConfig.getPreferences();
        }
        Preferences preferences = PREFERENCES.get(playerId);
        return preferences != null ? preferences : ClientConfig.DEFAULT_PREFERENCES;
    }

}
