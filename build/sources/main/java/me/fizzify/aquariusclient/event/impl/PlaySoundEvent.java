package me.fizzify.aquariusclient.event.impl;

import me.fizzify.aquariusclient.event.EventCancelable;
import net.minecraft.client.audio.ISound;

/**
 * Fired sound is played on client.
 *
 * @author Icovid | Icovid#3888
 * @since b0.2*
 */
public class PlaySoundEvent extends EventCancelable {

    public final ISound iSound;

    /** @param sound sound played */
    public PlaySoundEvent(ISound sound) {
        this.iSound = sound;
    }
}
