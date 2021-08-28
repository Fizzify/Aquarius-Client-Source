package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import me.fizzify.aquariusclient.settings.BooleanSetting;
import me.fizzify.aquariusclient.settings.ModeSetting;
import me.fizzify.aquariusclient.utils.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Frames extends Module {

    public Frames() {
        super("FPS", "Shows your FPS!", Category.HUD, 50, 50);
    }

    @Override
    public void draw() {
        final int fps = Minecraft.getDebugFPS();
        Gui.drawRect(getX() - 5, getY() - 5, getX() + getWidth(), getY() + getHeight() + 3, new Color(0, 0, 0, 50).getRGB());
        if (fps <= 10) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() + 1, getY(), -1);
            return;
        }
        if (fps <= 20) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() + 1, getY() , -1);
            return;
        }
        if (fps <= 50) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() + 1, getY(), -1);
            return;
        }
        if (fps <= 99) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() + 1, getY(), -1);
            return;
        }
        if (fps <= 100) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() - 2, getY(), -1);
            return;
        }
        if (fps <= 150) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() - 2, getY(), -1);
            return;
        }
        if (fps <= 999) {
            FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() - 2, getY(), -1);
            return;
        }
        FontUtils.drawString("[" + mc.getDebugFPS() + " " + "FPS" + "]", getX() + 1, getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        Gui.drawRect(getX() - 5, getY() - 5, getX() + getWidth(), getY() + getHeight() + 3, new Color(0, 0, 0, 50).getRGB());
        fr.drawString("[999 FPS]", getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        return fr.getStringWidth("[999 FPS]");
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
