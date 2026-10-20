package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

public final class Preferences {

    public static final float MIN_SPLIT_HEIGHT = 0.5F;
    public static final float MAX_SPLIT_HEIGHT = 7.5F;
    public static final float DEFAULT_SPLIT_HEIGHT = 2F;
    public static final float MIN_INTENSITY = 0F;
    public static final float MAX_INTENSITY = 2F;
    public static final float DEFAULT_INTENSITY = 1F;

    private ClientConfig.HeadAnimationStyle headAnimationStyle;
    private float headSplitHeight;
    private float intensity;

    public Preferences(ClientConfig.HeadAnimationStyle headAnimationStyle, float headSplitHeight, float intensity) {
        this.headAnimationStyle = headAnimationStyle;
        this.headSplitHeight = headSplitHeight;
        this.intensity = intensity;
    }

    public ClientConfig.HeadAnimationStyle getHeadAnimationStyle() {
        return headAnimationStyle;
    }

    public float getHeadSplitHeight() {
        return headSplitHeight;
    }

    public float getIntensity() {
        return intensity;
    }

    public static Preferences read(FriendlyByteBuf buffer) {
        return new Preferences(
                buffer.readEnum(ClientConfig.HeadAnimationStyle.class),
                clamp(buffer.readFloat(), MIN_SPLIT_HEIGHT, MAX_SPLIT_HEIGHT, MIN_SPLIT_HEIGHT),
                clamp(buffer.readFloat(), MIN_INTENSITY, MAX_INTENSITY, DEFAULT_INTENSITY)
        );
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeEnum(headAnimationStyle);
        buffer.writeFloat(headSplitHeight);
        buffer.writeFloat(intensity);
    }

    private static float clamp(float value, float min, float max, float fallback) {
        return Float.isFinite(value) ? Mth.clamp(value, min, max) : fallback;
    }

}
