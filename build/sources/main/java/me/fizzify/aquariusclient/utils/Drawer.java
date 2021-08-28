package me.fizzify.aquariusclient.utils;

import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLSync;

import java.awt.*;

public class Drawer {

    public static Drawer instance = new Drawer();

    public static Drawer getInstance() {
        return instance;
    }

    public static void drawRoundedRect(int x0, int y0, int x1, int y1, float radius, int color, float zLevel) {
        int i = 18;
        float f = 90.0F / i;
        float f1 = (color >> 24 & 0xFF) / 255.0F;
        float f2 = (color >> 16 & 0xFF) / 255.0F;
        float f3 = (color >> 8 & 0xFF) / 255.0F;
        float f4 = (color & 0xFF) / 255.0F;
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glBegin(5);
        GL11.glVertex3f(x0 + radius, y0, zLevel);
        GL11.glVertex3f(x0 + radius, y1, zLevel);
        GL11.glVertex3f(x1 - radius, y0, zLevel);
        GL11.glVertex3f(x1 - radius, y1, zLevel);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex3f(x0, y0 + radius, zLevel);
        GL11.glVertex3f(x0 + radius, y0 + radius, zLevel);
        GL11.glVertex3f(x0, y1 - radius, zLevel);
        GL11.glVertex3f(x0 + radius, y1 - radius, zLevel);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex3f(x1, y0 + radius, zLevel);
        GL11.glVertex3f(x1 - radius, y0 + radius, zLevel);
        GL11.glVertex3f(x1, y1 - radius, zLevel);
        GL11.glVertex3f(x1 - radius, y1 - radius, zLevel);
        GL11.glEnd();
        GL11.glBegin(6);
        float f5 = x1 - radius;
        float f6 = y0 + radius;
        GL11.glVertex3f(f5, f6, zLevel);
        for (int j = 0; j <= i; j++) {
            float f7 = j * f;
            GL11.glVertex3f((float)(f5 + radius * Math.cos(Math.toRadians(f7))), (float)(f6 - radius * Math.sin(Math.toRadians(f7))), zLevel);
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f5 = x0 + radius;
        f6 = y0 + radius;
        GL11.glVertex3f(f5, f6, zLevel);
        for (int k = 0; k <= i; k++) {
            float f8 = k * f;
            GL11.glVertex3f((float)(f5 - radius * Math.cos(Math.toRadians(f8))), (float)(f6 - radius * Math.sin(Math.toRadians(f8))), zLevel);
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f5 = x0 + radius;
        f6 = y1 - radius;
        GL11.glVertex3f(f5, f6, zLevel);
        for (int l = 0; l <= i; l++) {
            float f9 = l * f;
            GL11.glVertex3f((float)(f5 - radius * Math.cos(Math.toRadians(f9))), (float)(f6 + radius * Math.sin(Math.toRadians(f9))), zLevel);
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f5 = x1 - radius;
        f6 = y1 - radius;
        GL11.glVertex3f(f5, f6, zLevel);
        for (int i1 = 0; i1 <= i; i1++) {
            float f10 = i1 * f;
            GL11.glVertex3f((float)(f5 + radius * Math.cos(Math.toRadians(f10))), (float)(f6 + radius * Math.sin(Math.toRadians(f10))), zLevel);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
    }



    public static void drawOutline(int x1, int y1,int x2, int y2,int color) {
        DrawUtils.drawHorizontalLine(x1,x2,y1,color);
        DrawUtils.drawHorizontalLine(x1,x2,y2,color);

        DrawUtils.drawVerticalLine(x1,y1,y2,color);
        DrawUtils.drawVerticalLine(x2,y1,y2,color);
    }

    public static void drawBigOutline(int x1, int y1,int x2, int y2,int color) {
        DrawUtils.drawHorizontalLine(x1,x2,y1,color);
        DrawUtils.drawHorizontalLine(x1,x2,y2,color);
        DrawUtils.drawHorizontalLine(x1,x2,y1+1,color);
        DrawUtils.drawHorizontalLine(x1,x2,y2-1,color);

        DrawUtils.drawVerticalLine(x1,y1,y2,color);
        DrawUtils.drawVerticalLine(x2,y1,y2,color);
        DrawUtils.drawVerticalLine(x1 + 1,y1,y2,color);
        DrawUtils.drawVerticalLine(x2 - 1,y1,y2,color);
    }




    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

}