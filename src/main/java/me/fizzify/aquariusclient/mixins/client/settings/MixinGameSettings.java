package me.fizzify.aquariusclient.mixins.client.settings;

import me.fizzify.aquariusclient.utils.Keybinds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {

    @Shadow
    public KeyBinding[] keyBindings;

    // injected into return at constructor
    @Inject(method="<init>", at = @At("RETURN"))
    private void addClientKeybinds (CallbackInfo info) {
        KeyBinding[] buffer = new KeyBinding[this.keyBindings.length + 3];
        System.arraycopy(this.keyBindings, 0, buffer, 0, this.keyBindings.length);

        buffer[this.keyBindings.length] = Keybinds.HUD_CONFIG;
        buffer[this.keyBindings.length+1] = Keybinds.MODMENU;
        buffer[this.keyBindings.length+2] = Keybinds.FREELOOK;
        this.keyBindings = buffer;
    }
}
