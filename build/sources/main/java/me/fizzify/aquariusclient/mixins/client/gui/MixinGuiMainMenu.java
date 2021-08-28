package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.ui.elements.GuiButtonIcon;
import me.fizzify.aquariusclient.utils.font.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen implements GuiYesNoCallback
{

    /* General */
    private static final ResourceLocation buttonFontTexture = new ResourceLocation("aquariusclient/aqua.ttf");
    private static final FontRenderer buttonFont = new FontRenderer(buttonFontTexture, 20);

    private float x, y, targetX, targetY;

    /**
     * @author Oppyusa
     */
    public void drawBackground(int mouseX, int mouseY)
    {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        GlStateManager.pushMatrix();

        float xDiff = ((mouseX - sr.getScaledWidth() / 2) - x ) / sr.getScaleFactor();
        float yDiff = ((mouseY - sr.getScaledHeight() / 2) - y) / sr.getScaleFactor();

        x += xDiff * 0.3;
        y += yDiff * 0.3;

        GlStateManager.translate(x / 64, y / 64, 0);
        mc.getTextureManager().bindTexture(Client.BACKGROUND);
        drawModalRectWithCustomSizedTexture(-10, -10, 0, 0, sr.getScaledWidth() + 20, sr.getScaledHeight() + 20, sr.getScaledWidth() + 20, sr.getScaledHeight() + 20);
        GlStateManager.bindTexture(0);

        GlStateManager.translate(-x / 100, -y / 100, 0);
        GlStateManager.translate(x / 50, y / 50, 0);

        GlStateManager.popMatrix();
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private Object moveLanguageButton(Object buttonIn)
    {
        final GuiButton guiButton = (GuiButton) buttonIn;
        guiButton.xPosition += 24;
        return guiButton;
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private Object moveOptionsButton(Object buttonIn)
    {
        GuiButton guiButton = (GuiButton) buttonIn;
        guiButton.xPosition += 23;
        guiButton.setWidth(154);
        return guiButton;
    }

    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1))
    private boolean moveQuitButton(List<GuiButton> list, Object e)
    {
        final GuiButton quit = (GuiButton) e;
        return list.add(new GuiButtonIcon(quit.id, quit.xPosition + 78, quit.yPosition, 20, 20, "close.png"));
    }

    /**
     * @reason Overwrite drawScreen() method to implement a custom main menu design.
     * @author Kaimson the clown
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GL11.glColor4f(1f,1f,1f,1f);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(Client.BACKGROUND);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, (float) this.width, (float) this.height, this.width, this.height, (float) this.width, (float) this.height);
        this.mc.getTextureManager().bindTexture(Client.LOGO2);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 27 + 2, 30, 0.0f, 0.0f, 50, 50, 50.0f, 50.0f);
        this.mc.getTextureManager().bindTexture(Client.LOGO);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 118 + 2, 90, 0.0f, 0.0f, 237, 36, 237.0f, 36.0f);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        buttonFont.drawCenteredStringWithShadow("Aquarius Client " + Client.VERSION, 60, this.height - 3 - 3, -1);
        String text = "Not affiliated with Mojang Studios.";
        buttonFont.drawCenteredStringWithShadow(text, width - 100, height - 3 - 3, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /*
    *  Injecting a function when the main menu initializes.
     */
    @Inject(method = "initGui", at = @At("HEAD"))
    private void init (CallbackInfo info)
    {

    }

}
