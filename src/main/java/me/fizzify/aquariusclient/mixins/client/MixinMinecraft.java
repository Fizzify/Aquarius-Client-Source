package me.fizzify.aquariusclient.mixins.client;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.discord.DiscordIPC;
import me.fizzify.aquariusclient.event.impl.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldSettings;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow private int leftClickCounter;

    @ModifyArg(method = { "createDisplay" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", remap = false))
    private String setDisplayTitle(final String title)
    {
        return "Aquarius Client | 1.8.9";
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    private void init(CallbackInfo info)
    {
        Client.INSTANCE.init();
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void start(CallbackInfo info)
    {
        Client.INSTANCE.start();
    }

    @Inject(method = {"shutdownMinecraftApplet"}, at = {@At("HEAD")})
    private void shutdownMinecraftApplet(final CallbackInfo ci)
    {
        Client.INSTANCE.shutdown();
    }

    @Inject(method = "launchIntegratedServer", at = @At("RETURN"))
    private void singlePlayerRP(String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo info)
    {
       //DiscordIPC.INSTANCE.update("Singleplayer MixinWorld", "In Game");
    }

    @Inject(method = "runTick", at = @At("RETURN"))
    private void runTick(CallbackInfo info)
    {
        new TickEvent().call();
    }

    /**
     * @author Kaimson the Clown
     */
    @Overwrite
    public int getLimitFramerate()
    {
        return (Minecraft.getMinecraft().theWorld == null && Minecraft.getMinecraft().currentScreen != null) ? 60 : Minecraft.getMinecraft().gameSettings.limitFramerate;
    }


    /**
     * @author Ghast.io
     * @reason HitDelayFix
     */
    @Inject(method = "clickMouse", at = @At("HEAD"))
    private void clickMouseAfter(CallbackInfo ci) {
        leftClickCounter = 0;
    }

}
