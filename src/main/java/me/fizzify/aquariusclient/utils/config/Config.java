package me.fizzify.aquariusclient.utils.config;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.module.Module;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Config {

    public File configurationFolder = new File("AquariusClient");
    public File modulesFolder = new File("AquariusClient/Modules");

    public Configuration config, configToSave = ConfigurationAPI.newConfiguration(new File("AquariusClient/Modules/ModuleConfiguration.json"));

    public void saveModuleConfiguration()
    {

        if (!configurationFolder.exists())
        {
            configurationFolder.mkdirs();
        }

        if (!modulesFolder.exists())
        {
            modulesFolder.mkdirs();
        }

        System.out.println("Saving Module Configuration");

        for (Module m : Client.INSTANCE.moduleManager.modules)
        {
            configToSave.set(m.name.toLowerCase() + " x", m.getX());
            configToSave.set(m.name.toLowerCase() + " y", m.getY());
            configToSave.set(m.name.toLowerCase() + " enabled", m.isEnabled());
        }

        try {

            this.configToSave.save();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadModuleConfiguration() {
        try
        {
            this.config = ConfigurationAPI.loadExistingConfiguration(new File("AquariusClient/Modules/ModuleConfiguration.json"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
