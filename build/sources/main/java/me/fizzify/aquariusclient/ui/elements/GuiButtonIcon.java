package me.fizzify.aquariusclient.ui.elements;

import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class GuiButtonIcon extends GuiButton
{
    private final ResourceLocation ICON;

    private double hoverFade;
    private long prevDeltaTime;

    public GuiButtonIcon(final int buttonId, final int x, final int y, final int width, final int height, final String iconName) {
        super(buttonId, x, y, width, height, "");
        this.ICON = new ResourceLocation("aquariusclient/icons/" + iconName);
    }

    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {

        if (prevDeltaTime == 0) prevDeltaTime = System.currentTimeMillis();

        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2F;
            hoverFade = hovered ? Math.min(100, hoverFade + hoverInc) : Math.max(0, hoverInc - hoverInc);
            DrawUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, this.enabled ? (this.hovered ? (new Color(0, 10, 10, (int) (100 - (hoverFade / 2))))
                    .getRGB() : (new Color(0, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB()) : (new Color(0, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB());
            DrawUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, 2.0F, this.enabled ? (this.hovered ? (new Color(255, 0, 0, (int) (100 - (hoverFade / 2))))
                    .getRGB() : (new Color(255, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB()) : (new Color(155, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB());
            mc.getTextureManager().bindTexture(this.ICON);
            final int b = 10;
            GlStateManager.enableBlend();
            if (this.hovered) {
                DrawUtils.setGlColor(new Color(255, 255, 160, 60).getRGB());
                Gui.drawScaledCustomSizeModalRect(this.xPosition + (this.width - b) / 2, this.yPosition + (this.height - b) / 2, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
            }
            Gui.drawScaledCustomSizeModalRect(this.xPosition + (this.width - b) / 2, this.yPosition + (this.height - b) / 2, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
            prevDeltaTime = System.currentTimeMillis();
        }
    }
}
