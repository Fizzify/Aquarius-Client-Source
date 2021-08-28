package me.fizzify.aquariusclient.mixins.client.renderer.entity.layers;

import me.fizzify.aquariusclient.module.ModuleManager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase>
{

    /**
     * @author Fizzify
     */
    @Overwrite
    public boolean shouldCombineTextures()
    {
        return ModuleManager.oldAnimations.isEnabled();
    }

}
