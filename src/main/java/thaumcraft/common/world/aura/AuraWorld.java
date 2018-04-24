package thaumcraft.common.world.aura;

import java.util.concurrent.*;
import thaumcraft.common.lib.utils.*;

public class AuraWorld
{
    int dim;
    ConcurrentHashMap<PosXY, AuraChunk> auraChunks;
    
    public AuraWorld(final int dim) {
        this.auraChunks = new ConcurrentHashMap<PosXY, AuraChunk>();
        this.dim = dim;
    }
    
    public ConcurrentHashMap<PosXY, AuraChunk> getAuraChunks() {
        return this.auraChunks;
    }
    
    public void setAuraChunks(final ConcurrentHashMap<PosXY, AuraChunk> auraChunks) {
        this.auraChunks = auraChunks;
    }
    
    public AuraChunk getAuraChunkAt(final int x, final int y) {
        return this.getAuraChunkAt(new PosXY(x, y));
    }
    
    public AuraChunk getAuraChunkAt(final PosXY loc) {
        return this.auraChunks.get(loc);
    }
}
