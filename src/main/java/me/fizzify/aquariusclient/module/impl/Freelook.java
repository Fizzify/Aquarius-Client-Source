package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.event.EventTarget;
import me.fizzify.aquariusclient.event.impl.TickEvent;
import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import me.fizzify.aquariusclient.module.ModuleManager;
import me.fizzify.aquariusclient.utils.Keybinds;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

public class Freelook extends Module
{

    private float cameraYaw = 0F;
    private float cameraPitch = 0F;
    private int previousPerspective = 0;
    private boolean perspectiveToggled = false;
    private boolean pressed = false;

    public Freelook() {
        super("Freelook", "360 Perspective while holding the keybind.", Category.WORLD, 0, 0);
    }

    @EventTarget
    public void keyboardEvent(TickEvent e) {
        if (ModuleManager.getFreelook().isEnabled()) return;
        if (!Keybinds.FREELOOK.isKeyDown() && pressed) {
            pressed = false;
            perspectiveToggled = false;
            Minecraft.getMinecraft().gameSettings.thirdPersonView = previousPerspective;
        }

        if (Keybinds.FREELOOK.isKeyDown() && !pressed) {
            pressed = true;
            perspectiveToggled = !perspectiveToggled;

            cameraYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
            cameraPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

            if (perspectiveToggled) {
                previousPerspective = Minecraft.getMinecraft().gameSettings.thirdPersonView;
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
            } else {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = previousPerspective;
            }
        }
    }

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : Minecraft.getMinecraft().thePlayer.rotationPitch;
    }

    public void setCameraYaw(float yaw) {
        cameraYaw = yaw;
    }

    public void setCameraPitch(float pitch) {
        cameraPitch = pitch;
    }

    public boolean isToggled() {
        return perspectiveToggled;
    }

    public boolean overrideMouse() {
        if (Minecraft.getMinecraft().inGameHasFocus && Display.isActive()) {

            if (!perspectiveToggled) {
                return true;
            }

            Minecraft.getMinecraft().mouseHelper.mouseXYChange();
            float f1 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = (float) Minecraft.getMinecraft().mouseHelper.deltaX * f2;
            float f4 = (float) Minecraft.getMinecraft().mouseHelper.deltaY * f2;

            cameraYaw += f3 * 0.15F;
            cameraPitch += f4 * 0.15F;

            if (cameraPitch > 90) cameraPitch = 90;
            if (cameraPitch < -90) cameraPitch = -90;
        }
        return false;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

}
