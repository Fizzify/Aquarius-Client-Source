package me.fizzify.aquariusclient.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    protected void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {}
}
