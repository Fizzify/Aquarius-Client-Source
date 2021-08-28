package me.fizzify.aquariusclient.module;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.ui.clickgui.ClickGUI;
import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class HUDConfigScreen extends GuiScreen
{

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(69, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "Modules"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        DrawUtils.drawHollowRect(0, 0, this.width - 1 - 1, this.height - 1, new Color(0, 200, 255, 255).getRGB());

        for (Module m : Client.INSTANCE.moduleManager.modules)
        {
            if (m.isEnabled())
            {
                m.renderDummy(mouseX, mouseY);
            }
       }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if (button.id == 69) {
            mc.displayGuiScreen(new ClickGUI());
        }
    }
}
