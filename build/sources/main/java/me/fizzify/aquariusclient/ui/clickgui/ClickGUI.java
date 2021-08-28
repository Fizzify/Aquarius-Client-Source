package me.fizzify.aquariusclient.ui.clickgui;

import me.fizzify.aquariusclient.module.Module;
import me.fizzify.aquariusclient.module.ModuleManager;
import me.fizzify.aquariusclient.ui.clickgui.elements.ModuleButton;
import me.fizzify.aquariusclient.ui.elements.GuiColorPicker;
import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.io.*;

@SuppressWarnings("all")
public class ClickGUI extends GuiScreen
{
    private int yOffset = 0;
    private int scaleFactor = 1200;
    private int xOffset = 0;
    private double opacityOffset = 1;
    private GuiColorPicker colorPicker;
    ArrayList<ModuleButton> modButtons;
    Module m;

    public ClickGUI() {
        this.modButtons = new ArrayList<ModuleButton>();
    }

    /*
     * Being called when the GUI starts
     */
    public void initGui()
    {
        super.initGui();
        this.modButtons.add(new ModuleButton(163, 140, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.frames, new ResourceLocation("aquariusclient/icons/fps.png")));
        this.modButtons.add(new ModuleButton(253, 140, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.clicksPerSecond, new ResourceLocation("aquariusclient/icons/fps.png")));
        this.modButtons.add(new ModuleButton(343, 140, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.toggleSprint, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(433, 140, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.keystrokes, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(163, 200, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.oldAnimations, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(253, 200, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.miniPlayer, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(343, 200, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.armorStatus, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(433, 200, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.itemPhysics, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(163, 260, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.packDisplay, new ResourceLocation("aquariusclient/icons/reach.png")));
        this.modButtons.add(new ModuleButton(253, 260, 42, this.mc.fontRendererObj.FONT_HEIGHT + 5, ModuleManager.fullbright, new ResourceLocation("aquariusclient/icons/reach.png")));
        //this.buttonList.add(colorPicker = new GuiColorPicker(0, this.width / 2 - 101, this.height / 2 + 25, 202, 20));
        // this.buttonList.add(new GuiButtonIcon(1, 100, 260, 20, 20, "close.png"));
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        DrawUtils.drawRoundedRect(130, 105, this.width - 130, this.height - 90, 6, 0x33000000);
        DrawUtils.drawRoundedOutline(130, 105, this.width - 130, this.height - 90, 6, 2, 0x33000000);
        WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        GlStateManager.color(0, 0, 0, (float) (107 * this.opacityOffset) / 255.0F);
        this.drawBaseRectangles(worldrenderer);
        GlStateManager.color(0, 0, 0, (float) (48 * this.opacityOffset) / 255.0F);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);

        GlStateManager.color(0, 0, 0, (float) (107 * this.opacityOffset) / 255.0F);
        this.drawBaseCorners(worldrenderer);
        GlStateManager.color(0, 0, 0, (float) (48 * this.opacityOffset) / 255.0F);
        this.drawCategoryCorners(worldrenderer);

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glLineWidth(2.0F);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        this.glScissor(50, 105, this.width - 100, this.height - 195);
        for(ModuleButton m : modButtons) {
            m.draw(mouseX, mouseY);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    /*
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (final ModuleButton m : this.modButtons)
        {
            m.onClick(mouseX, mouseY, mouseButton);
        }
    }

    private void drawBaseRectangles(@NotNull WorldRenderer worldrenderer) {
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor) - xOffset, (this.height * 936 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset, (this.height * 936 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 684 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 684 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset, (this.height * 260 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor) - xOffset, (this.height * 260 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset, (this.height * 331 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 184 / this.scaleFactor) - xOffset, (this.height * 331 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 184 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor) - xOffset, (this.height * 331 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset, (this.height * 331 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 192 / this.scaleFactor) - xOffset, (this.height * 331 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor) - xOffset, (this.height * 331 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 192 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset, (this.height * 260 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor) - xOffset, (this.height * 260 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 690 / this.scaleFactor) - xOffset, (this.height * 923 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 690 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset, (this.height * 273 / this.scaleFactor) + yOffset, 0.0D).endVertex();
        Tessellator.getInstance().draw();
    }

    private void drawCategoryCorners(@NotNull WorldRenderer worldrenderer) {
        int yRadius = ((this.height * 304 / this.scaleFactor) + yOffset) - ((this.height * 290 / this.scaleFactor) + yOffset);
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 628 / this.scaleFactor + xOffset), (this.height * 304 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (0 / 360.0 * 100); i <= (int) (90 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 628 / this.scaleFactor) - xOffset) - ((this.width / 2) - (this.height * 640 / this.scaleFactor + xOffset));
            worldrenderer.pos((this.width / 2) - (this.height * 628 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 304 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 248 / this.scaleFactor - xOffset), (this.height * 304 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (0 / 360.0 * 100); i <= (int) (90 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) + (this.height * 248 / this.scaleFactor) - xOffset) - ((this.width / 2) + (this.height * 236 / this.scaleFactor - xOffset));
            worldrenderer.pos((this.width / 2) + (this.height * 248 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 304 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 240 / this.scaleFactor + xOffset), (this.height * 304 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (270 / 360.0 * 100); i <= (int) (360 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 226 / this.scaleFactor - xOffset)) -  ((this.width / 2) - (this.height * 240 / this.scaleFactor) + xOffset);
            worldrenderer.pos((this.width / 2) - (this.height * 240 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 304 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 633 / this.scaleFactor - xOffset), (this.height * 304 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (270 / 360.0 * 100); i <= (int) (360 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) + (this.height * 645 / this.scaleFactor) - xOffset) - ((this.width / 2) + (this.height * 633 / this.scaleFactor - xOffset));
            worldrenderer.pos((this.width / 2) + (this.height * 633 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 304 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        yRadius = ((this.height * 341 / this.scaleFactor) + yOffset) - ((this.height * 327 / this.scaleFactor) + yOffset);
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 628 / this.scaleFactor + xOffset), (this.height * 327 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (90 / 360.0 * 100); i <= (int) (180 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 628 / this.scaleFactor) - xOffset) - ((this.width / 2) - (this.height * 640 / this.scaleFactor + xOffset));
            worldrenderer.pos((this.width / 2) - (this.height * 628 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 327 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 240 / this.scaleFactor + xOffset), (this.height * 327 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (180 / 360.0 * 100); i <= (int) (270 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 226 / this.scaleFactor - xOffset)) -  ((this.width / 2) - (this.height * 240 / this.scaleFactor) + xOffset);
            worldrenderer.pos((this.width / 2) - (this.height * 240 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 327 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 248 / this.scaleFactor - xOffset), (this.height * 327 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (90 / 360.0 * 100); i <= (int) (180 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) + (this.height * 248 / this.scaleFactor) - xOffset) - ((this.width / 2) + (this.height * 236 / this.scaleFactor - xOffset));
            worldrenderer.pos((this.width / 2) + (this.height * 248 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 327 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 633 / this.scaleFactor - xOffset), (this.height * 327 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (180 / 360.0 * 100); i <= (int) (270 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) + (this.height * 645 / this.scaleFactor) - xOffset) - ((this.width / 2) + (this.height * 633 / this.scaleFactor - xOffset));
            worldrenderer.pos((this.width / 2) + (this.height * 633 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 327 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
    }

    private void drawBaseCorners(@NotNull WorldRenderer worldrenderer) {
        int yRadius = ((this.height * 273 / this.scaleFactor) + yOffset) - ((this.height * 260 / this.scaleFactor) + yOffset);
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor + xOffset), (this.height * 273 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (0 / 360.0 * 100); i <= (int) (90 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 672 / this.scaleFactor + xOffset)) - ((this.width / 2) - (this.height * 684 / this.scaleFactor) - xOffset);
            worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 273 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor + xOffset), (this.height * 273 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (270 / 360.0 * 100); i <= (int) (360 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 184 / this.scaleFactor + xOffset)) - ((this.width / 2) - (this.height * 197 / this.scaleFactor) - xOffset);
            worldrenderer.pos((this.width / 2) - (this.height * 197 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 273 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor - xOffset), (this.height * 273 / this.scaleFactor) + yOffset , 0).endVertex();
        for (int i = (int) (0 / 360.0 * 100); i <= (int) (90 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) + (this.height * 205 / this.scaleFactor - xOffset)) - ((this.width / 2) + (this.height * 192 / this.scaleFactor) - xOffset);
            worldrenderer.pos((this.width / 2) + (this.height * 205 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 273 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor - xOffset), (this.height * 273 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (270 / 360.0 * 100); i <= (int) (360 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius =  ((this.width / 2) + (this.height * 690 / this.scaleFactor) - xOffset) - ((this.width / 2) + (this.height * 677 / this.scaleFactor - xOffset));
            worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 273 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        yRadius = ((this.height * 936 / this.scaleFactor) + yOffset) - ((this.height * 923 / this.scaleFactor) + yOffset);
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor + xOffset), (this.height * 923 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (90 / 360.0 * 100); i <= (int) (180 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) - (this.height * 672 / this.scaleFactor + xOffset)) - ((this.width / 2) - (this.height * 684 / this.scaleFactor) - xOffset);
            worldrenderer.pos((this.width / 2) - (this.height * 672 / this.scaleFactor + xOffset) + Math.sin(angle) * xRadius, (this.height * 923 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor - xOffset), (this.height * 923 / this.scaleFactor) + yOffset , 0.0D).endVertex();
        for (int i = (int) (180 / 360.0 * 100); i <= (int) (270 / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            int xRadius = ((this.width / 2) + (this.height * 690 / this.scaleFactor - xOffset)) - ((this.width / 2) + (this.height * 677 / this.scaleFactor) - xOffset);
            worldrenderer.pos((this.width / 2) + (this.height * 677 / this.scaleFactor - xOffset) + Math.sin(angle) * xRadius, (this.height * 923 / this.scaleFactor) + yOffset + Math.cos(angle) * yRadius, 0.0D).endVertex();
        } Tessellator.getInstance().draw();
    }

    private void glScissor(double x, double y, double width, double height)
    {

        y += height;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Minecraft mc = Minecraft.getMinecraft();

        GL11.glScissor((int) ((x * mc.displayWidth) / scaledResolution.getScaledWidth()),
                (int) (((scaledResolution.getScaledHeight() - y) * mc.displayHeight) / scaledResolution.getScaledHeight()),
                (int) (width * mc.displayWidth / scaledResolution.getScaledWidth()),
                (int) (height * mc.displayHeight / scaledResolution.getScaledHeight()));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0) {
            colorPicker.nextColor();
        }
        super.actionPerformed(button);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h)
    {
        this.buttonList.clear();
        super.onResize(mcIn, w, h);
    }
}
