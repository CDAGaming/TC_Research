package thaumcraft.common.world.aura;

import net.minecraft.util.math.*;
import java.lang.ref.*;
import net.minecraft.world.chunk.*;

public class AuraChunk
{
    ChunkPos loc;
    short base;
    float vis;
    float flux;
    WeakReference<Chunk> chunkRef;
    
    public AuraChunk(final ChunkPos loc) {
        this.loc = loc;
    }
    
    public AuraChunk(final Chunk chunk, final short base, final float vis, final float flux) {
        if (chunk != null) {
            this.loc = chunk.func_76632_l();
            this.chunkRef = new WeakReference<Chunk>(chunk);
        }
        this.base = base;
        this.vis = vis;
        this.flux = flux;
    }
    
    public boolean isModified() {
        return this.chunkRef != null && this.chunkRef.get() != null && this.chunkRef.get().func_76601_a(false);
    }
    
    public short getBase() {
        return this.base;
    }
    
    public void setBase(final short base) {
        this.base = base;
    }
    
    public float getVis() {
        return this.vis;
    }
    
    public void setVis(final float vis) {
        this.vis = Math.min(32766.0f, Math.max(0.0f, vis));
    }
    
    public float getFlux() {
        return this.flux;
    }
    
    public void setFlux(final float flux) {
        this.flux = Math.min(32766.0f, Math.max(0.0f, flux));
    }
    
    public ChunkPos getLoc() {
        return this.loc;
    }
    
    public void setLoc(final ChunkPos loc) {
        this.loc = loc;
    }
}
