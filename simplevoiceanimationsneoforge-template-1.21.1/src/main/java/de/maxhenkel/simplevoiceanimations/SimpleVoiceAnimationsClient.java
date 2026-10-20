package de.maxhenkel.simplevoiceanimations;

import net.neoforged.api.distmarker.Dist;
import de.maxhenkel.simplevoiceanimations.gui.AnimationSettingsScreen;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import net.neoforged.fml.common.Mod;

@Mod(value = SimpleVoiceAnimations.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SimpleVoiceAnimations.MODID, value = Dist.CLIENT)
public final class SimpleVoiceAnimationsClient {

    private static final KeyMapping SETTINGS = new KeyMapping("key.simplevoiceanimations.settings", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, "key.categories.misc");

    public SimpleVoiceAnimationsClient() {
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(SETTINGS);
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        while (SETTINGS.consumeClick()) {
            if (net.minecraft.client.Minecraft.getInstance().screen == null) {
                net.minecraft.client.Minecraft.getInstance().setScreen(new AnimationSettingsScreen(null));
            }
        }
    }

    @SubscribeEvent
    public static void loggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        ClientPreferencesSync.clear();
        ClientPreferencesSync.sendLocalPreferences();
    }

    @SubscribeEvent
    public static void loggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        ClientPreferencesSync.clear();
    }

}
