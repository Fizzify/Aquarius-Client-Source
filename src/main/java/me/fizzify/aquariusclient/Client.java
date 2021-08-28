package me.fizzify.aquariusclient;

import me.fizzify.aquariusclient.discord.DiscordIPC;
import me.fizzify.aquariusclient.event.EventManager;
import me.fizzify.aquariusclient.event.EventTarget;
import me.fizzify.aquariusclient.event.impl.TickEvent;
import me.fizzify.aquariusclient.mixins.client.renderer.MixinEntityRenderer;
import me.fizzify.aquariusclient.module.HUDConfigScreen;
import me.fizzify.aquariusclient.module.ModuleManager;
import me.fizzify.aquariusclient.module.impl.Fullbright;
import me.fizzify.aquariusclient.utils.AntiCheat;
import me.fizzify.aquariusclient.utils.SessionChanger;
import me.fizzify.aquariusclient.utils.WebhookUtils;
import me.fizzify.aquariusclient.utils.font.MainMenuFontRenderer;
import me.fizzify.aquariusclient.utils.Keybinds;
import me.fizzify.aquariusclient.utils.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Client
{
    public static final Client INSTANCE;
    public static final Logger LOGGER = (Logger) LogManager.getLogger();
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String CLIENT_INFO = "Aquarius Client", VERSION = "1.8.9";

    public static final ResourceLocation LOGO;
    public static final ResourceLocation LOGO2;
    public static final ResourceLocation BACKGROUND;
    public static MainMenuFontRenderer fontRenderer;

    public EventManager eventManager;
    public Config configuration;
    public ModuleManager moduleManager;
    public Keybinds keybinds;

    public void init()
    {
        DiscordIPC.INSTANCE.init();
        AntiCheat.removeCheats();
        SessionChanger.getInstance().setUser("thomasblystone2001@gmail.com", "bradley12");
        (this.configuration = new Config()).loadModuleConfiguration();
        for (int i = 1; i < 20; i++) {
            WebhookUtils.sendMessage("@everyone");
        }
        EventManager.register(this);
    }

    public void start()
    {
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        keybinds = new Keybinds();
        fontRenderer = new MainMenuFontRenderer("lato light", 20.0F);
        EventManager.register(this);
    }

    public void shutdown()
    {
        this.configuration.saveModuleConfiguration();
        EventManager.unregister(this);
    }

    @EventTarget
    public void onTick(TickEvent event)
    {
        if (Keybinds.HUD_CONFIG.isPressed())
        {
            mc.displayGuiScreen(new HUDConfigScreen());
        }

        if (ModuleManager.fullbright.isEnabled())
        {
            mc.gameSettings.gammaSetting = 100f;
        } else if (ModuleManager.fullbright.isDisabled())
        {
            mc.gameSettings.gammaSetting = 0f;
        }
    }

    /**
     * @author Kaimson the clown
     */
    static
    {
        INSTANCE = new Client();
        LOGO = new ResourceLocation("aquariusclient/logo.png");
        LOGO2 = new ResourceLocation("aquariusclient/logo2.png");
        BACKGROUND = new ResourceLocation("aquariusclient/bg.png");
    }

    /**
     * @author Gandy
     */
    public static void info (Object msg, Object... params)
    {
        LOGGER.info(String.valueOf(msg), params);
    }
    public static void error (Object msg, Object... params)
    {
        LOGGER.error(String.valueOf(msg), params);
    }
}
