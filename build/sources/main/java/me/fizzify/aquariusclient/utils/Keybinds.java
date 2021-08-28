package me.fizzify.aquariusclient.utils;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;

public class Keybinds {
    public static final KeyBinding HUD_CONFIG = new KeyBinding("HUD Config", Keyboard.KEY_RSHIFT, "Aquarius Client");
    public static final KeyBinding MODMENU = new KeyBinding("Click Gui", Keyboard.KEY_Y, "Aquarius Client");
    public static final KeyBinding FREELOOK = new KeyBinding("Freelook", Keyboard.KEY_LMENU, "Aquarius Client");
}
