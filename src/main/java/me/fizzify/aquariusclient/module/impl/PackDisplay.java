package me.fizzify.aquariusclient.module.impl;

import com.google.common.collect.Lists;
import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

public class PackDisplay extends Module {

    private ResourceLocation thumbnail;
    private final Minecraft mc = Minecraft.getMinecraft();
    IResourcePack pack = this.getCurrentPack();
    private DefaultResourcePack mcDefaultResourcePack;

    public PackDisplay() {
        super("Pack Display", "Shows your current pack!", Category.HUD, 200, 200);
    }

    // YOU TOLD ME TO BLOCK FUNKY
    @Override
    public void draw() {
        for (final ResourcePackRepository.Entry entry : Lists.reverse(this.mc.getResourcePackRepository().getRepositoryEntries())) {
            Gui.drawRect(getX(), getY(), getHeight() + getX(), getWidth() + getY(), new Color(0, 0, 0, 50).getRGB());
            GlStateManager.color(1f, 1f, 1f, 1f);
            entry.bindTexturePackIcon(this.mc.getTextureManager());
            Gui.drawModalRectWithCustomSizedTexture(getX() - 35, getY() - 10, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
            fr.drawStringWithShadow("" + pack.getPackName(), getX(), getY(), -1);
        }
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY)
    {
        for (final ResourcePackRepository.Entry entry : Lists.reverse(this.mc.getResourcePackRepository().getRepositoryEntries())) {
            Gui.drawRect(getX(), getY(), getHeight() + getX(), getWidth() + getY(), new Color(0, 0, 0, 50).getRGB());
            GlStateManager.color(1f, 1f, 1f, 1f);
            entry.bindTexturePackIcon(this.mc.getTextureManager());
            Gui.drawModalRectWithCustomSizedTexture(getX() - 35, getY() - 10, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
            fr.drawStringWithShadow("" + pack.getPackName(), getX(), getY(), -1);
        }
        super.renderDummy(mouseX, mouseY);
    }

    public int getWidth() {
        return fr.getStringWidth("[Ping: 000]");
    }


    public int getHeight() {
        return fr.FONT_HEIGHT;
    }

    private IResourcePack getCurrentPack() {
        List list = this.mc.getResourcePackRepository().getRepositoryEntries();
        Object pack = null;

        return (IResourcePack) (list.size() > 0 ? ((ResourcePackRepository.Entry) list.get(0)).getResourcePack() : this.mcDefaultResourcePack);
    }

    public void loadTexture() {
        DynamicTexture dt;

        try {
            dt = new DynamicTexture(this.getCurrentPack().getPackImage());
        } catch (Exception exception) {
            dt = TextureUtil.missingTexture;
        }

        this.thumbnail = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", dt);
    }
}