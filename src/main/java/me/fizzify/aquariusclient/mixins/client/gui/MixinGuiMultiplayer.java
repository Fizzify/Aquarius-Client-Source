package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.discord.DiscordIPC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiMultiplayer;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer
{
    @Deprecated
    @Inject(method = "initGui", at=@At("HEAD"))
    private void multiplayerListRP (CallbackInfo info)
    {
      //DiscordIPC.INSTANCE.update("Idle", "Server List");
    }
}
