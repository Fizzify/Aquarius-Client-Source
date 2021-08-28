package me.fizzify.aquariusclient.mixins.client.renderer.entity;

import me.fizzify.aquariusclient.module.ModuleManager;
import me.fizzify.aquariusclient.module.impl.itemphysics.ClientPhysic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;

@Mixin(RenderEntityItem.class)
public abstract class MixinRenderEntityItem
{
    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void renderItemPhysic(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info)
    {
        if (ModuleManager.itemPhysics.isEnabled())
        {
            ClientPhysic.doRender(entity, x, y, z);
            info.cancel();
        }
    }
}
