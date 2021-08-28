package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.*;

import java.awt.*;

@Mixin(targets = "net/minecraft/client/gui/GuiScreenOptionsSounds$Button")
public class MixinGuiScreenOptionsSoundsButton extends GuiButton {
    @Shadow
    public float field_146156_o;

    public MixinGuiScreenOptionsSoundsButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    @Inject(method = "mouseDragged(Lnet/minecraft/client/Minecraft;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"), cancellable = true)
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY, CallbackInfo ci) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        DrawUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + (int) (this.field_146156_o * (this.width - 8)) + 8, this.yPosition + 20, 2.0f, new Color(0, 166, 255, 100).getRGB());
        DrawUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + (int) (this.field_146156_o * (this.width - 8)) + 8, this.yPosition + 20, 2.0f, 2.0f, new Color(0, 166, 255, 50).getRGB());
        DrawUtils.drawRoundedOutline(this.xPosition + (int) (this.field_146156_o * (this.width - 8)), this.yPosition, this.xPosition + (int) (this.field_146156_o * (this.width - 8)) + 8, this.yPosition + 20, 2.0f, 2.0f, new Color(0, 166, 255, 100).getRGB());
        ci.cancel();
    }
}