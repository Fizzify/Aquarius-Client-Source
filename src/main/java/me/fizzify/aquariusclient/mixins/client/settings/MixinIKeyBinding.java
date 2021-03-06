package me.fizzify.aquariusclient.mixins.client.settings;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface MixinIKeyBinding {
    @Accessor void setPressed(boolean pressed);
}
