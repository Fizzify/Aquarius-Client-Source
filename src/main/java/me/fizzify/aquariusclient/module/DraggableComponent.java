package me.fizzify.aquariusclient.module;

import me.fizzify.aquariusclient.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class DraggableComponent
{

    public ArrayList<Module> modules = new ArrayList<>();
    private int x;
    private int y;
    private int width;
    private int height;
    private int color;
    private int lastX;
    private int lastY;

    private boolean dragging;

    public DraggableComponent(int x, int y, int width, int height, int color)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getxPosition()
    {
        return x;
    }

    public int getyPosition()
    {
        return y;
    }

    public void setxPosition(int x)
    {
        this.x = x;
    }

    public void setyPosition(int y)
    {
        this.y = y;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void draw(int mouseX, int mouseY)
    {
        draggingFix(mouseX, mouseY);
        draggingBorder();
        Gui.drawRect(this.getxPosition(), this.getyPosition(), this.getxPosition() + this.getWidth(), this.getyPosition() + this.getHeight(), this.getColor());
        boolean mouseOverX = (mouseX >= this.getxPosition() && mouseX <= this.getxPosition() + this.getWidth());
        boolean mouseOverY = (mouseY >= this.getyPosition() && mouseY <= this.getyPosition() + this.getHeight());
        if(mouseOverX && mouseOverY)
        {
            if(Mouse.isButtonDown(0))
            {
                if (!this.dragging)
                {
                    this.lastX = x - mouseX;
                    this.lastY = y - mouseY;
                    this.dragging = true;
                }
            }
        }
    }

    private void draggingFix(int mouseX, int mouseY)
    {
        if (this.dragging)
        {
            this.x = mouseX + this.lastX;
            this.y = mouseY + this.lastY;
            if(!Mouse.isButtonDown(0)) this.dragging = false;
        }
    }

    private void draggingBorder()
    {
        int height = Minecraft.getMinecraft().displayHeight;
        int width = Minecraft.getMinecraft().displayWidth;

        for(Module m : modules)
        {
            if (m.isEnabled())
            {
                if(m.x - (m.getWidth() / 2) < 0) {
                    m.x = 15 + (m.getHeight() / 2);
                }
                if(m.y - (m.getHeight() / 2) < 0) {
                    m.y = 0 + (m.getHeight() / 2);
                }
                if(m.y + (m.getHeight() / 2) > height / 2) {
                    m.y = (height / 2) - (m.getHeight() / 2);
                }
                if(m.x + (m.getWidth() / 2) > width / 2) {
                    m.x = (width / 2) - (m.getWidth() / 2);
                }
            }
        }
    }
}