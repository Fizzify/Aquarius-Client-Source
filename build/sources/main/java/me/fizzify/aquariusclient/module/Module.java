package me.fizzify.aquariusclient.module;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.settings.Setting;
import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Module
{

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr = mc.fontRendererObj;

    public ArrayList<Setting> settings;

    public Category category;
    public String name;
    public String description;
    public boolean enabled;
    public DraggableComponent drag;

    public int x, y;

    public Module(String name, String description, Category category, int x, int y)
    {
        this.name = name;
        this.description = description;
        this.category = category;

        try
        {
            this.x = (int) Client.INSTANCE.configuration.config.get(name.toLowerCase() + " x");
            this.y = (int) Client.INSTANCE.configuration.config.get(name.toLowerCase() + " y");
            this.setEnabled((boolean) Client.INSTANCE.configuration.config.get(name.toLowerCase() + " enabled"));
        } catch (NullPointerException e)
        {
            this.x = x;
            this.y = y;
            this.enabled = false;
        }

        settings = new ArrayList<Setting>();
        drag = new DraggableComponent(this.x, this.y, getWidth(), getHeight(), new Color(0, 0, 0, 0).getRGB());
    }

    public void addSettings(Setting...sets)
    {
        this.settings.add((Setting) Arrays.asList(sets));
    }

    public int getWidth()
    {
        return 50;
    }

    public int getHeight()
    {
        return 50;
    }

    /*
    * Drawing part.
     */
    public void draw()
    {
        // Renders what you put here.
    }

    public void renderDummy(int mouseX, int mouseY)
    {
        boolean hovered = (mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight());
        if (hovered)
        {
           DrawUtils.drawHollowRect(getX() - 2, getY() - 2, getWidth() + 3, getHeight() + 2, new Color(0, 220, 255, 100).getRGB());
        }
        DrawUtils.drawHollowRect(getX() - 2 , getY() - 2 , getWidth() + 3, getHeight() + 2, new Color(150, 150, 150, 50).getRGB());
        drag.draw(mouseX, mouseY);
    }

    public int getX()
    {
        return drag.getxPosition();
    }

    public int getY()
    {
        return drag.getyPosition();
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void toggle()
    {
        this.setEnabled(!this.enabled);
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public boolean isDisabled() {
        return !this.enabled;
    }
}
