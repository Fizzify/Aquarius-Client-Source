package me.fizzify.aquariusclient.mixins.client.resources;

import me.fizzify.aquariusclient.Client;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ AbstractResourcePack.class })
public abstract class MixinAbstractResourcePack
{


    /**
     * @author prplz
     * @reason Scale the pack image down to reduce memory usage
     */
    @Overwrite
    public BufferedImage getPackImage() throws IOException {
        final BufferedImage image = TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
        final BufferedImage scaledImage = new BufferedImage(64, 64, 2);
        final Graphics graphics = scaledImage.getGraphics();
        graphics.drawImage(image, 0, 0, 64, 64, null);
        graphics.dispose();
        return scaledImage;
    }

    @Shadow
    protected abstract InputStream getInputStreamByName(final String p0);
}
