package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public final class AnimationSettingsScreen extends Screen {

    private final Screen parent;
    private IntensitySliderWidget intensity;
    private SplitHeightWidget splitHeight;

    public AnimationSettingsScreen(Screen parent) {
        super(Component.translatable("gui.simplevoiceanimations.settings.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        addRenderableWidget(CycleButton.builder(AnimationSettingsScreen::styleName)
                .withValues(ClientConfig.HeadAnimationStyle.values())
                .withInitialValue(ClientConfig.getPreferences().getHeadAnimationStyle())
                .create(width / 2 - 125, 35, 250, 20, Component.translatable("gui.simplevoiceanimations.settings.style"),
                        (button, style) -> ClientConfig.setHeadAnimationStyle(style)));
        intensity = addRenderableWidget(new IntensitySliderWidget(width / 2 - 125, 65, 250, 20));
        splitHeight = addRenderableWidget(new SplitHeightWidget(width / 2 - 40, 100));
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).bounds(width / 2 - 75, height - 35, 150, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 15, 0xFFFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
        splitHeight.visible = ClientConfig.getPreferences().getHeadAnimationStyle().hasSplit();
    }

    @Override
    public void onClose() {
        ClientPreferencesSync.sendLocalPreferences();
        minecraft.setScreen(parent);
    }

    private static Component styleName(ClientConfig.HeadAnimationStyle style) {
        return Component.translatable("gui.simplevoiceanimations.settings.style." + style.name().toLowerCase(Locale.ROOT));
    }

}
