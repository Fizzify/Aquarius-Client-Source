package me.fizzify.aquariusclient.mixins.client.multiplayer;

import me.fizzify.aquariusclient.discord.DiscordIPC;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.multiplayer.GuiConnecting;

@Mixin(GuiConnecting.class)
public abstract class MixinGuiConnecting
{
    Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "connect", at = @At("RETURN"))
    private void updateServerRP (final String ip, final int port, CallbackInfo info)
    {
        //DiscordIPC.INSTANCE.update("Playing " + mc.getCurrentServerData().serverIP, "In Game");
    }

}
