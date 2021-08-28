package me.fizzify.aquariusclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.EntityRenderer;

@Mixin(EntityRenderer.class)
public interface MixinIEntityRenderer {
    @Accessor void setCloudFog(boolean isFog);
}
