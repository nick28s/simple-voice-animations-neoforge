package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class IntensitySliderWidget extends AbstractSliderButton {

    public IntensitySliderWidget(int x, int y, int width, int height) {
        super(x, y, width, height, CommonComponents.EMPTY, ClientConfig.getPreferences().getIntensity() / Preferences.MAX_INTENSITY);
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.translatable("gui.simplevoiceanimations.settings.intensity", Math.round(getIntensity() * 100F)));
    }

    @Override
    protected void applyValue() {
        ClientConfig.setIntensity(getIntensity());
    }

    private float getIntensity() {
        return Math.round((float) (value * Preferences.MAX_INTENSITY) * 20F) / 20F;
    }

}
