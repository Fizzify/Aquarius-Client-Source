package me.fizzify.aquariusclient.module;

import me.fizzify.aquariusclient.module.impl.*;
import me.fizzify.aquariusclient.module.impl.itemphysics.ItemPhysics;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Set;

public class ModuleManager
{

    public ArrayList<Module> modules = new ArrayList<>();

    public static Frames frames;
    public static ClicksPerSecond clicksPerSecond;
    public static ToggleSprint toggleSprint;
    public static OldAnimations oldAnimations;
    public static Keystrokes keystrokes;
    public static MiniPlayer miniPlayer;
    public static ArmorStatus armorStatus;
    public static ItemPhysics itemPhysics;
    public static Fullbright fullbright;
    public static Freelook freelook;
    public static PackDisplay packDisplay;

    public ModuleManager()
    {
        // Calls the method so it can register the modules on startup.
        this.registerModules();
    }

    /**
     * Registers the modules.
     */
    public void registerModules() {
        modules.add(frames = new Frames());
        modules.add(clicksPerSecond = new ClicksPerSecond());
        modules.add(toggleSprint = new ToggleSprint());
        modules.add(oldAnimations = new OldAnimations());
        modules.add(keystrokes = new Keystrokes());
        modules.add(miniPlayer = new MiniPlayer());
        modules.add(armorStatus = new ArmorStatus());
        modules.add(itemPhysics = new ItemPhysics());
        modules.add(fullbright = new Fullbright());
        modules.add(freelook = new Freelook());
        modules.add(packDisplay = new PackDisplay());
    }

    public void renderModules()
    {
        for(Module m : modules)
        {
            if(m.isEnabled())
            {
                m.draw();
            }
        }
    }

    public static Freelook getFreelook() {
        return freelook;
    }

    public static ToggleSprint getToggleSprint() {
        return toggleSprint;
    }
}
