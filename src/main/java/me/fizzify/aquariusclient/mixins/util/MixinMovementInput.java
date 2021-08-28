package me.fizzify.aquariusclient.mixins.util;

import me.fizzify.aquariusclient.module.ModuleManager;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ MovementInputFromOptions.class })
public class MixinMovementInput extends MovementInput
{
    @Inject(method = { "updatePlayerMoveState" }, at = { @At("TAIL") })
    private void updatePlayerMoveState(final CallbackInfo ci) {
        ModuleManager.getToggleSprint().updateMovement();
    }
}
