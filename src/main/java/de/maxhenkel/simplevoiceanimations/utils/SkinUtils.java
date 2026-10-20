package de.maxhenkel.simplevoiceanimations.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;

public final class SkinUtils {

    private SkinUtils() {
    }

    public static PlayerSkin getLocalPlayerSkin() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            return minecraft.player.getSkin();
        }
        return DefaultPlayerSkin.get(minecraft.getUser().getProfileId());
    }

}
