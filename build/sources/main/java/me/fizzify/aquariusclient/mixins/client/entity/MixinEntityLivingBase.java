package me.fizzify.aquariusclient.mixins.client.entity;

import me.fizzify.aquariusclient.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    private Utils utils = new Utils(
            (EntityLivingBase) (Object) this);

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    /**
     * MouseDelayFix Fixes bug MC-67665
     *
     * @author prplz
     */
    @Inject(method = "getLook", at = @At("HEAD"), cancellable = true)
    private void getLook(float partialTicks, CallbackInfoReturnable<Vec3> ci) {
        utils.getLook(ci, super.getLook(partialTicks));
    }

}
