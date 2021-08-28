package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import me.fizzify.aquariusclient.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Keystrokes extends Module {

    public static Minecraft mc = Minecraft.getMinecraft();
    protected long pressTime;

    public Keystrokes() {
        super("Keystrokes", "Shows what you press!", Category.HUD, 500, 100);
    }

    public static enum KeystrokesMode {

        WASD(Key.W, Key.A, Key.S, Key.D),
        WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
        WASD_JUMP(Key.W, Key.A, Key.S, Key.D, Key.Jump1),
        WASD_JUMP_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, Key.Jump2);

        private final Key[] keys;
        private int width, height;

        private KeystrokesMode(Key... keyIn) {
            this.keys = keyIn;

            for(Key key : keys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public Key[] getKeys() {
            return keys;
        }
    }

    public static class Key {

        private static final Key W = new Key("W", mc.gameSettings.keyBindForward, 21, 1, 18, 18);
        private static final Key A = new Key("A", mc.gameSettings.keyBindLeft, 1, 21, 18, 18);
        private static final Key S = new Key("S", mc.gameSettings.keyBindBack, 21, 21, 18, 18);
        private static final Key D = new Key("D", mc.gameSettings.keyBindRight, 41, 21, 18, 18);

        private static final Key LMB = new Key("LMB", mc.gameSettings.keyBindAttack, 1, 41, 28, 18);
        private static final Key RMB = new Key("RMB", mc.gameSettings.keyBindUseItem, 31, 41, 28, 18);

        private static final Key Jump1 = new Key("§m-----", mc.gameSettings.keyBindJump, 1, 30, 58, 10);
        private static final Key Jump2 = new Key("§m-----", mc.gameSettings.keyBindJump, 1, 61, 58, 7);

        private final String name;
        private final KeyBinding keyBind;
        private final int x, y, w, h;

        public Key(String name, KeyBinding keyBind, int x, int y, int w, int h) {
            this.name = name;
            this.keyBind = keyBind;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public boolean isDown() {
            return keyBind.isKeyDown();
        }

        public int getHeight() {
            return h;
        }

        public int getWidth() {
            return w;
        }

        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    private KeystrokesMode mode = KeystrokesMode.WASD_JUMP_MOUSE;

    public Color getColor()
    {
        for (Key key : mode.getKeys()) {
            if (key.isDown()) {
                System.currentTimeMillis();
                return new Color(0, 0, 0, 50);
            }
        }
        return new Color(255, 255, 255, 50);
    }

    @Override
    public int getWidth() {
        return 58;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();

        for(Key key : mode.getKeys()) {

            int textWidth = fr.getStringWidth(key.getName());

            Gui.drawRect(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 40).getRGB() : new Color(0, 0, 0, 50).getRGB());
            DrawUtils.drawRectOutline(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(0, 0, 0, 150).getRGB() : new Color(255, 255, 255, 140).getRGB());
            fr.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2, getY() + key.getY() + key.getHeight() / 2 - 4, key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
        }

        GL11.glPopMatrix();
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GL11.glPushMatrix();

        for(Key key : mode.getKeys()) {

            int textWidth = fr.getStringWidth(key.getName());

            Gui.drawRect(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 40).getRGB() : new Color(0, 0, 0, 50).getRGB());
            fr.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2, getY() + key.getY() + key.getHeight() / 2 - 4, key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
        }

        GL11.glPopMatrix();
        super.renderDummy(mouseX, mouseY);
    }
}
