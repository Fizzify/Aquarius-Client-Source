package me.fizzify.aquariusclient.ui.clickgui.elements;

import java.awt.*;

import me.fizzify.aquariusclient.module.Module;
import me.fizzify.aquariusclient.utils.DrawUtils;
import me.fizzify.aquariusclient.utils.font.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ModuleButton
{
    private static final ResourceLocation buttonFontTexture = new ResourceLocation("aquariusclient/Lato-Light.ttf");
    private static final FontRenderer buttonFont = new FontRenderer(buttonFontTexture, 15);

    private static final ResourceLocation moduleFontTexture = new ResourceLocation("aquariusclient/aqua.ttf");
    private static final FontRenderer moduleFont = new FontRenderer(moduleFontTexture, 13);

    ResourceLocation icon;
    int getScroll;
    public boolean hovered;

    /**
    * @author @SkiesPlusPlus
     */
    private int Scrolled()
    {
        final int i = Mouse.getEventDWheel();
        if (i != 0)
        {

            if (i > 0)
            {
                ++this.getScroll;
            }

            if (i < 1)
            {
                --this.getScroll;
            }

            if (this.getScroll > 50)
            {
                this.getScroll = 50;
            }

            if (this.getScroll < 0)
            {
                this.getScroll = 0;
            }
        }
        return this.getScroll;
    }

    public int x;
    public int y;
    public int w;
    public int h;
    public Module m;

    public ModuleButton(final int x, final int y, final int w, final int h, final Module m, final ResourceLocation resourceLocation)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.m = m;
        this.getScroll = 0;
        this.icon = resourceLocation;
    }

    public void draw(final int mouseX, final int mouseY)
    {
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        DrawUtils.drawRoundedRect(this.x - 20, this.y - this.Scrolled() - 20,this.x + this.w + 20, this.y + this.h - this.Scrolled() + 10, 6, new Color(0, 0, 0, 65).getRGB());
        DrawUtils.drawRoundedOutline(this.x - 20, this.y - this.Scrolled() - 20,this.x + this.w + 20, this.y + this.h - this.Scrolled() + 10, 6, 1, new Color(0, 0, 0, 75).getRGB());
        DrawUtils.drawRoundedRect(this.x - 10, this.y - this.Scrolled(),this.x + this.w + 10, this.y + this.h - this.Scrolled(), 2, this.getColor(mouseX, mouseY));
        DrawUtils.drawRoundedOutline(this.x - 10, this.y - this.Scrolled(),this.x + this.w + 10, this.y + this.h - this.Scrolled(), 3, 1, new Color(255, 255, 255, 35).getRGB());
        moduleFont.drawCenteredStringWithShadow(this.m.name, this.x + 20, this.y - 9 - this.Scrolled(), new Color(255, 255, 255, 200).getRGB());
        buttonFont.drawCenteredStringWithShadow(this.getEnabled(), this.x + 20, this.y + 6 - this.Scrolled(), new Color(255, 255, 255, 200).getRGB());
        GlStateManager.popMatrix();
    }

    private String getEnabled()
    {
        if (this.m.isEnabled())
        {
            return "ENABLED";
        }
        return "DISABLED";
    }

    private int getColor(final int mouseX, final int mouseY)
    {
        this.hovered = (mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + h);
        if (this.m.isEnabled())
        {
            if (this.hovered) {
                return 0xFF007700;
            }
            return 0x77007700;
        }
        if (this.hovered) {
            return 0xFF990000;
        }
        return 0x33880000;
    }

    public void onClick(final int mouseX, final int mouseY, final int button)
    {
        if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h && button == 0)
        {
            if (this.m.isEnabled())
            {
                this.m.setEnabled(false);
            }
            else {
                this.m.setEnabled(true);
            }
            this.playPressSound(Minecraft.getMinecraft().getSoundHandler());
        }
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }
}
