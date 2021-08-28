package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.event.EventTarget;
import me.fizzify.aquariusclient.event.impl.TickEvent;
import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import net.minecraft.client.Minecraft;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", "Turns your gamma up!", Category.MISC, 0, 0);
    }

    @Override
    public void draw()
    {
        super.draw();
    }

    @Override
    public int getHeight()
    {
        return 0;
    }

    @Override
    public int getWidth()
    {
        return 0;
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (this.isEnabled()) {
            mc.gameSettings.gammaSetting = 100f;
        } else if (this.isDisabled()) {
            mc.gameSettings.gammaSetting = 0f;
        }
    }
}
