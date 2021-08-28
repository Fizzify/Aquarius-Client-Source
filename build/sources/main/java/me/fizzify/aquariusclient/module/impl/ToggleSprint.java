package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.event.EventTarget;
import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import me.fizzify.aquariusclient.module.ModuleManager;
import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ToggleSprint extends Module
{

    private boolean toggled;
    private int wasPressed;
    private final int keyHoldTicks;
    private static ToggleSprint INSTANCE;

    public ToggleSprint()
    {
        super("ToggleSprint", "Makes sprinting easier!", Category.WORLD, 5, 5);
        this.keyHoldTicks = 7;
        ToggleSprint.INSTANCE = this;
    }

    @EventTarget
    public void updateMovement() {
        if (ModuleManager.getToggleSprint().isEnabled())
        {
            if (this.mc.gameSettings.keyBindSprint.isKeyDown())
            {
                if (this.wasPressed == 0) {
                    if (this.toggled) {
                        this.wasPressed = -1;
                    }
                    else if (this.mc.thePlayer.capabilities.isFlying)
                    {
                        this.wasPressed = this.keyHoldTicks + 1;
                    }
                    else
                    {
                        this.wasPressed = 1;
                    }
                    this.toggled = !this.toggled;
                }
                else if (this.wasPressed > 0)
                {
                    ++this.wasPressed;
                }
            }
            else
            {
                if (this.keyHoldTicks > 0 && this.wasPressed > this.keyHoldTicks)
                {
                    this.toggled = false;
                }
                this.wasPressed = 0;
            }
        }
        else
        {
            this.toggled = false;
        }
        if (this.toggled)
        {
            this.mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void draw()
    {
        if (mc.thePlayer.isSprinting()
                && !mc.thePlayer.isSneaking()
                && !mc.thePlayer.isRiding()
                && toggled) {
            Gui.drawRect(getX() - 4, getY() - 5, getX() + getWidth() + 4, getY() + getHeight() + 3, new Color(0, 0, 0, 50).getRGB());
        } else if (!mc.thePlayer.isSprinting()
                && !(mc.thePlayer.moveForward > 0)) {
            Gui.drawRect(getX() - 4, getY() - 5, getX() + getWidth() - 73, getY() + getHeight() + 3, new Color(0, 0, 0, 50).getRGB());
        }

        if (mc.thePlayer.isSprinting()
                && !mc.thePlayer.isSneaking()
                && !mc.thePlayer.isRiding()
                && toggled)
        {
            fr.drawString("[Sprinting (Key Toggled)]", getX(), getY(), -1);
        } else if (!mc.thePlayer.isSprinting()
                && !(mc.thePlayer.moveForward > 0))
        {
            fr.drawString("[Standing]", getX(), getY(), -1);
        } else if (!mc.thePlayer.isSprinting()
                && !(mc.thePlayer.moveForward > 1)
                && !mc.thePlayer.isSneaking())
        {
            fr.drawString("[Walking]", getX(), getY(), -1);
        } else if (mc.thePlayer.isSprinting()
                && !mc.thePlayer.isSneaking()
                && !mc.thePlayer.isRiding()
                && !toggled) {
            fr.drawString("[Sprinting (Vanilla)]", getX(), getY(), -1);
        } else if (mc.thePlayer.isSprinting()
                && mc.thePlayer.isSneaking()
                && !mc.thePlayer.isRiding())
        {
            fr.drawString("[Sneaking (Key Held)]", getX(), getY(), -1);
        }
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY)
    {
        fr.drawString("[Sprinting (Key Toggled)]", getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }

    @Override
    public int getWidth()
    {
        return fr.getStringWidth("[Sprinting (Key Toggled)]") + 1;
    }

    @Override
    public int getHeight()
    {
        return fr.FONT_HEIGHT + 1;
    }

}
