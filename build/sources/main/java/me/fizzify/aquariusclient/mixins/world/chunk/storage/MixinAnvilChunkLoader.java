package me.fizzify.aquariusclient.mixins.world.chunk.storage;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.RegionFileCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("all")
@Mixin({AnvilChunkLoader.class})
public abstract class MixinAnvilChunkLoader {

    @Shadow
    public File chunkSaveLocation;

    @Shadow
    public abstract Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound p_75822_4_);

    @Shadow
    private Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap<>();

    @Shadow
    private Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.<ChunkCoordIntPair>newSetFromMap(new ConcurrentHashMap());

    /**
     * @author MatthewTGM
     * @reason made chunk loading multithreaded
     */
    @Overwrite
    public Chunk loadChunk(World worldIn, int x, int z) throws IOException, ExecutionException, InterruptedException {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
        AtomicReference<NBTTagCompound> nbttagcompound = new AtomicReference<>((NBTTagCompound) this.chunksToRemove.get(chunkcoordintpair));

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Chunk> chunk1 = (Future<Chunk>) es.submit(() -> {
            if (nbttagcompound.get() == null) {
                DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);

                if (datainputstream == null) {
                    return;
                }

                try {
                    nbttagcompound.set(CompressedStreamTools.read(datainputstream));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        es.shutdown();
        return chunk1.get();
    }

}