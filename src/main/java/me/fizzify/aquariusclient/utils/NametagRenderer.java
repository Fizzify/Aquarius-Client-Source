package me.fizzify.aquariusclient.utils;

import me.fizzify.aquariusclient.utils.font.MainMenuFontRenderer;
import net.minecraft.client.*;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.scoreboard.*;

public class NametagRenderer
{
    /**
     * @author Kaimson the clown
     */
    public static void renderTag(final String tag, final double x, final double y, final double z)
    {
        final Minecraft mc = Minecraft.getMinecraft();
        final Entity entity = mc.getRenderManager().livingPlayer;
        if (entity != null && entity.getDisplayName() != null)
        {
            final FontRenderer fr = mc.fontRendererObj;
            final float f = 1.6f;
            final float f2 = 0.016666668f * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x + 0.0f, (float)y + mc.thePlayer.height + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            final float viewX = mc.getRenderManager().playerViewX;
            final float viewY = mc.getRenderManager().playerViewY;
            GlStateManager.rotate(-viewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(viewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-f2, -f2, f2);
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 9.374999f, 0.0f);
            }
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final int i = fr.getStringWidth(tag) / 2;
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-i - 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(-i - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(i + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(i + 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fr.drawString(tag, -fr.getStringWidth(tag) / 2, 0, 553648127);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            if (!entity.isSneaking()) {
                fr.drawString(tag, -fr.getStringWidth(tag) / 2, 0, -1);
            }
            else {
                fr.drawString(tag, -fr.getStringWidth(tag) / 2, 0, 553648127);
            }
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }

    public static void render(final double x, double y, final double z)
    {
        final Minecraft mc = Minecraft.getMinecraft();
        final Scoreboard scoreboard = mc.thePlayer.getWorldScoreboard();
        final ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
        if (scoreobjective != null && !mc.thePlayer.isSneaking()) {
            final Score score = scoreboard.getValueFromObjective(mc.thePlayer.getName(), scoreobjective);
            renderTag(score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z);
            y += mc.fontRendererObj.FONT_HEIGHT * 1.15f * 0.02666667;
        }
        renderTag((mc.thePlayer.getCustomNameTag() == null || mc.thePlayer.getCustomNameTag().isEmpty()) ? mc.thePlayer.getDisplayName().getFormattedText() : mc.thePlayer.getCustomNameTag(), x, y, z);
    }
}