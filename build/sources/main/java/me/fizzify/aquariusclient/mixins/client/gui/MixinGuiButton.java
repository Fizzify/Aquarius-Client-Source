package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton {

    @Shadow
    public int xPosition;
    @Shadow
    public int yPosition;
    @Shadow
    protected int width;
    @Shadow
    protected int height;

    @Shadow
    public boolean visible;
    @Shadow
    public boolean enabled;
    @Shadow
    protected boolean hovered;

    @Shadow
    protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

    @Shadow public String displayString;

    private double hoverFade;
    private long prevDeltaTime;

    /**
     * @reason Overwrites the {@link GuiButton}.drawButton method to make use of our custom design.
     * @author Kaimson the clown
         */
        @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        if (prevDeltaTime == 0) prevDeltaTime = System.currentTimeMillis();

        if (visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            DrawUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, this.enabled ? (this.hovered ? (new Color(0, 10, 10, (int) (100 - (hoverFade / 2))))
                    .getRGB() : (new Color(0, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB()) : (new Color(0, 0, 0, (int) (100 - (hoverFade / 2)))).getRGB());
            DrawUtils.drawRoundedOutline(xPosition, yPosition, xPosition + width, yPosition + height, 2.0F, 2.0F, enabled ? (hovered ? (new Color(0, 166, 255, (int) (100 - (hoverFade / 2))))
                    .getRGB() : (new Color(0, 166, 255, (int) (100 - (hoverFade / 2)))).getRGB()) : (new Color(0, 130, 155, (int) (100 - (hoverFade / 2)))).getRGB());
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2F;
            hoverFade = hovered ? Math.min(100, hoverFade + hoverInc) : Math.max(0, hoverInc - hoverInc);

            if (!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }
            Client.fontRenderer.drawCenteredString(displayString, xPosition + width / 2, yPosition + (height - 17), j);
            prevDeltaTime = System.currentTimeMillis();
        }
    }

}

