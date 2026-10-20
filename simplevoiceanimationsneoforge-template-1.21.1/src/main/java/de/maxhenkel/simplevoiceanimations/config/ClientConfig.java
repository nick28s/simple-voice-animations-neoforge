package de.maxhenkel.simplevoiceanimations.config;

import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {

    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.EnumValue<HeadAnimationStyle> HEAD_ANIMATION_STYLE;
    private static final ModConfigSpec.DoubleValue HEAD_SPLIT_HEIGHT;
    private static final ModConfigSpec.DoubleValue INTENSITY;
    private static final ModConfigSpec.BooleanValue FALLBACK_TO_OWN_ANIMATIONS;

    public static final Preferences DEFAULT_PREFERENCES = new Preferences(
            HeadAnimationStyle.SPLIT_HINGE,
            Preferences.DEFAULT_SPLIT_HEIGHT,
            Preferences.DEFAULT_INTENSITY
    );

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        HEAD_ANIMATION_STYLE = builder
                .comment("The head animation style used while speaking")
                .defineEnum("head_animation_style", DEFAULT_PREFERENCES.getHeadAnimationStyle());
        HEAD_SPLIT_HEIGHT = builder
                .comment("The height of the head split in pixels, measured from the bottom")
                .defineInRange("head_split_height", (double) Preferences.DEFAULT_SPLIT_HEIGHT, (double) Preferences.MIN_SPLIT_HEIGHT, (double) Preferences.MAX_SPLIT_HEIGHT);
        INTENSITY = builder
                .comment("How strongly the head animation reacts to the voice")
                .defineInRange("intensity", (double) Preferences.DEFAULT_INTENSITY, (double) Preferences.MIN_INTENSITY, (double) Preferences.MAX_INTENSITY);
        FALLBACK_TO_OWN_ANIMATIONS = builder
                .comment("Use your local settings for players when the server cannot synchronize settings")
                .define("fallback_to_own_animations", true);
        SPEC = builder.build();
    }

    private ClientConfig() {
    }

    public static Preferences getPreferences() {
        return new Preferences(HEAD_ANIMATION_STYLE.get(), (float) HEAD_SPLIT_HEIGHT.get().doubleValue(), (float) INTENSITY.get().doubleValue());
    }

    public static boolean isFallbackToOwnAnimations() {
        return FALLBACK_TO_OWN_ANIMATIONS.get();
    }

    public static void setHeadAnimationStyle(HeadAnimationStyle style) {
        HEAD_ANIMATION_STYLE.set(style);
    }

    public static void setHeadSplitHeight(float height) {
        HEAD_SPLIT_HEIGHT.set((double) height);
    }

    public static void setIntensity(float intensity) {
        INTENSITY.set((double) intensity);
    }

    public enum HeadAnimationStyle {
        NONE(false),
        SQUISH(false),
        SCALE_Y(false),
        SCALE_FULL(false),
        SPLIT_HINGE(true),
        SPLIT_VERTICAL(true),
        NOD(false);

        private final boolean split;

        HeadAnimationStyle(boolean split) {
            this.split = split;
        }

        public boolean hasSplit() {
            return split;
        }
    }

}
