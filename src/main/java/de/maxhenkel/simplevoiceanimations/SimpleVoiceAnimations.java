package de.maxhenkel.simplevoiceanimations;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.sync.PlayerPreferencesPayload;
import de.maxhenkel.simplevoiceanimations.sync.UpdatePreferencesPayload;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod(SimpleVoiceAnimations.MODID)
public final class SimpleVoiceAnimations {

    public static final String MODID = "simplevoiceanimations";
    private final Map<UUID, PlayerPreferencesPayload> preferences = new ConcurrentHashMap<>();

    public SimpleVoiceAnimations(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        modEventBus.addListener(this::registerPayloads);
        NeoForge.EVENT_BUS.register(this);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .playToClient(PlayerPreferencesPayload.TYPE, PlayerPreferencesPayload.CODEC,
                        (payload, context) -> context.enqueueWork(() -> ClientPreferencesSync.receive(payload)));
        event.registrar("1")
                .playToServer(UpdatePreferencesPayload.TYPE, UpdatePreferencesPayload.CODEC,
                        (payload, context) -> context.enqueueWork(() -> {
                            if (context.player() instanceof net.minecraft.server.level.ServerPlayer sender) {
                                PlayerPreferencesPayload update = new PlayerPreferencesPayload(sender.getUUID(), payload.preferences());
                                preferences.put(sender.getUUID(), update);
                                net.neoforged.neoforge.network.PacketDistributor.sendToAllPlayers(update);
                            }
                        }));
    }

    @net.neoforged.bus.api.SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
            preferences.values().forEach(update -> PacketDistributor.sendToPlayer(player, update));
        }
    }

}
