package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.lwjgl.opengl.GL11;

public class MiniPlayer extends Module {

    public MiniPlayer()
    {
        super("Miniplayer", "Shows your player", Category.HUD, 5, 5);
    }

    @Override
    public void draw()
    {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GuiInventory.drawEntityOnScreen(getX() + 15, getY() + 50, 25, 50, 0, mc.thePlayer);
        super.draw();
    }

    @Override
    public void renderDummy( int mouseX, int mouseY)
    {
        super.renderDummy(mouseX, mouseY);
        GuiInventory.drawEntityOnScreen(getX() + 15, getY() + 50, 25, 50, 0, mc.thePlayer);
    }

    @Override
    public int getHeight()
    {
        return 50;
    }

    @Override
    public int getWidth()
    {
        return 30;
    }
}