package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.utils.AquariusGuiScreenResourcePacks;
import me.fizzify.aquariusclient.ui.elements.CustomGuiTextField;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.ResourcePackListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author KodingKing
 */
@Mixin(GuiScreenResourcePacks.class)
public class MixinGuiScreenResourcePacks extends GuiScreen {

    @Shadow private GuiResourcePackAvailable availableResourcePacksList;
    @Shadow private GuiResourcePackSelected selectedResourcePacksList;
    @Shadow private List<ResourcePackListEntry> availableResourcePacks;

    private final AquariusGuiScreenResourcePacks aquariusGuiResourcePack = new AquariusGuiScreenResourcePacks(
            (GuiScreenResourcePacks) (Object) this);

    private CustomGuiTextField textField;

    @Inject(method = "initGui", at = @At("HEAD"))
    private void initTextField(CallbackInfo ci) {
        String s1 = textField == null ? "" : textField.getText();
        textField = new CustomGuiTextField(3, fontRendererObj, width / 2 - 4 - 200, height - 24, 200, 20);
        textField.setText(s1);
    }

    /**
     * @reason Change buttons size
     * @author SiroQ
     */
    @Inject(method = "initGui", at = @At("RETURN"))
    private void initGui(CallbackInfo callbackInfo) {
        aquariusGuiResourcePack.initGui(buttonList);
    }

    /**
     * @reason Change text location
     * @author SiroQ
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        aquariusGuiResourcePack.drawScreen(availableResourcePacksList, selectedResourcePacksList, mouseX, mouseY, partialTicks, fontRendererObj, width);
        super.drawScreen(mouseX, mouseY, partialTicks);
        textField.drawTextBox();
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        textField.mouseClicked(mouseX, mouseY, mouseButton);
        if (textField != null) textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (textField.isFocused()) textField.textboxKeyTyped(typedChar, keyCode);
        availableResourcePacksList = aquariusGuiResourcePack.updateList(textField, availableResourcePacksList, availableResourcePacks, mc, width, height);
    }
}
