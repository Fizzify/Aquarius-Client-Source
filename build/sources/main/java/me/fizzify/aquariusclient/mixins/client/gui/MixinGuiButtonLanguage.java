package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.*;

import java.awt.*;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;

@Mixin(GuiButtonLanguage.class)
public abstract class MixinGuiButtonLanguage extends GuiButton {
    private final ResourceLocation language = new ResourceLocation("aquariusclient/icons/language.png");

    public MixinGuiButtonLanguage(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    private double hoverFade;
    private long prevDeltaTime;

    /**
     * @author Kaimson the Clown
     */
    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        if (prevDeltaTime == 0) prevDeltaTime = System.currentTimeMillis();

        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2F;
            hoverFade = hovered ? Math.min(100, hoverFade + hoverInc) : Math.max(0, hoverInc - hoverInc);
            DrawUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, this.enabled ? (this.hovered ? (new Color(0, 10, 10, (int) (100 - (hoverFade / 2))))
                    .getRGB() : (new Color(0, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB()) : (new Color(0, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB());
            DrawUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, 2.0F, this.enabled ? (this.hovered ? (new Color(17, 255, 0, (int) (100 - (hoverFade / 2))))
                    .getRGB() : (new Color(17, 255, 0, (int) (100 - (hoverFade / 2)))).getRGB()) : (new Color(17, 255, 0, (int) (100 - (hoverFade / 2)))).getRGB());
            mc.getTextureManager().bindTexture(this.language);
            GlStateManager.enableBlend();
            int b = 12;

            if (this.hovered) {
                DrawUtils.setGlColor(new Color(255, 255, 200, 60).getRGB());
                Gui.drawScaledCustomSizeModalRect(this.xPosition + (this.width - b) / 2, this.yPosition + (this.height - b) / 2, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
            }

            Gui.drawScaledCustomSizeModalRect(this.xPosition + (this.width - b) / 2, this.yPosition + (this.height - b) / 2, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
            prevDeltaTime = System.currentTimeMillis();
        }
    }
}
