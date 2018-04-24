package thaumcraft.api.golems.seals;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;

public interface ISealEntity
{
    void tickSealEntity(final World p0);
    
    ISeal getSeal();
    
    SealPos getSealPos();
    
    byte getPriority();
    
    void setPriority(final byte p0);
    
    void readNBT(final NBTTagCompound p0);
    
    NBTTagCompound writeNBT();
    
    void syncToClient(final World p0);
    
    BlockPos getArea();
    
    void setArea(final BlockPos p0);
    
    boolean isLocked();
    
    void setLocked(final boolean p0);
    
    boolean isRedstoneSensitive();
    
    void setRedstoneSensitive(final boolean p0);
    
    String getOwner();
    
    void setOwner(final String p0);
    
    byte getColor();
    
    void setColor(final byte p0);
    
    boolean isStoppedByRedstone(final World p0);
}
