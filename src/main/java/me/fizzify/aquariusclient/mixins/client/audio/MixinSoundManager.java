package me.fizzify.aquariusclient.mixins.client.audio;

import me.fizzify.aquariusclient.event.impl.PlaySoundEvent;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Event target ejections for SoundManager.class.
 *
 * @author Icovid | Icovid#3888
 * @since b0.2
 */
@Mixin(SoundManager.class)
public class MixinSoundManager {

    /**
     * Post {@link PlaySoundEvent} when sound is played on client.
     *
     * @param callbackInfo used to cancel event
     * @param sound sound played
     */
    @Inject(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundHandler;getSound(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/audio/SoundEventAccessorComposite;"), cancellable = true)
    private void playSound(ISound sound, CallbackInfo callbackInfo) {
        PlaySoundEvent playSoundEvent = new PlaySoundEvent(sound);
        playSoundEvent.call();
        if (playSoundEvent.isCancelled()) callbackInfo.cancel();
    }
}
