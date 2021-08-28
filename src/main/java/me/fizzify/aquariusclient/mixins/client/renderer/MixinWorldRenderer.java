package me.fizzify.aquariusclient.mixins.client.renderer;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.vertex.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

import org.spongepowered.asm.mixin.injection.*;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer
{
    @Shadow
    private VertexFormatElement vertexFormatElement;
    @Shadow
    private VertexFormat vertexFormat;
    @Shadow
    private int vertexFormatIndex;

    @Inject(method = "nextVertexFormatIndex", at = @At("HEAD"), cancellable = true)
    private void nextVertexFormatIndex(final CallbackInfo ci)
    {
            ci.cancel();
            final List<VertexFormatElement> elements = this.vertexFormat.getElements();
            do {
                if (++this.vertexFormatIndex >= elements.size())
                {
                    this.vertexFormatIndex -= elements.size();
                } this.vertexFormatElement = elements.get(this.vertexFormatIndex);
            } while (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING);
    }
}
