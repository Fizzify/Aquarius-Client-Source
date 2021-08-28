package me.fizzify.aquariusclient.mixins.client.entity;

import me.fizzify.aquariusclient.event.impl.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP {

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void callEvent(CallbackInfo info) {
        EventUpdate event = new EventUpdate();
        event.call();
    }

}
