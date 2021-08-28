package me.fizzify.aquariusclient.mixins.client.renderer.entity;

import me.fizzify.aquariusclient.event.impl.EventRenderPlayer;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.entity.layers.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderPlayer.class })
public abstract class MixinRenderPlayer extends RendererLivingEntity<AbstractClientPlayer>
{
    public MixinRenderPlayer(final RenderManager renderManagerIn, final ModelBase modelBaseIn, final float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Inject(method = { "<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V" }, at = { @At("RETURN") })
    private void constructor(final RenderManager renderManager, final boolean useSmallArms, final CallbackInfo ci) {
        EventRenderPlayer event = new EventRenderPlayer();
        event.call();
    }
}
