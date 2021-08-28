package me.fizzify.aquariusclient.utils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Utils {

    private EntityLivingBase parent;

    public Utils(EntityLivingBase parent) {
        this.parent = parent;
    }

    public void getLook(CallbackInfoReturnable<Vec3> ci, Vec3 look) {
        EntityLivingBase base = parent;
        if (base instanceof EntityPlayerSP) ci.setReturnValue(look);
    }

}
