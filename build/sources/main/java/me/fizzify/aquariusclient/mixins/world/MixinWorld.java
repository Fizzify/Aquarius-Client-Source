package me.fizzify.aquariusclient.mixins.world;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(World.class)
public class MixinWorld {

    /**
     * @author prplz/2pi
     * @reason VoidFlickerFix
     */
    @Overwrite
    public double getHorizon() {
        return 0;
    }
}
