package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import de.maxhenkel.simplevoiceanimations.utils.SkinUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public final class SplitHeightWidget extends AbstractWidget {

    private static final int HEAD_SIZE = 64;

    public SplitHeightWidget(int x, int y) {
        super(x, y, HEAD_SIZE + 16, HEAD_SIZE, Component.translatable("gui.simplevoiceanimations.settings.split_height"));
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int headLeft = getX() + 8;
        graphics.fill(headLeft, getY(), headLeft + HEAD_SIZE, getBottom(), 0xFF202020);
        PlayerFaceRenderer.draw(graphics, SkinUtils.getLocalPlayerSkin(), headLeft, getY(), HEAD_SIZE);
        int barY = getY() + Math.round(getHeight() - ClientConfig.getPreferences().getHeadSplitHeight() / 8F * getHeight());
        graphics.fill(getX(), barY - 1, getRight(), barY + 1, isHovered() ? 0xFFFFFFFF : 0xFFAAAAAA);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        setSplitHeight(mouseY);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        setSplitHeight(mouseY);
        return true;
    }

    private void setSplitHeight(double mouseY) {
        float height = (float) (getBottom() - mouseY) / getHeight() * 8F;
        ClientConfig.setHeadSplitHeight(Mth.clamp(Math.round(height * 2F) / 2F, Preferences.MIN_SPLIT_HEIGHT, Preferences.MAX_SPLIT_HEIGHT));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }

}
