package thaumcraft.api.capabilities;

import net.minecraftforge.common.util.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;

public interface IPlayerWarp extends INBTSerializable<NBTTagCompound>
{
    void clear();
    
    int get(@Nonnull final EnumWarpType p0);
    
    void set(@Nonnull final EnumWarpType p0, final int p1);
    
    int add(@Nonnull final EnumWarpType p0, final int p1);
    
    int reduce(@Nonnull final EnumWarpType p0, final int p1);
    
    void sync(final EntityPlayerMP p0);
    
    int getCounter();
    
    void setCounter(final int p0);
    
    public enum EnumWarpType
    {
        PERMANENT, 
        NORMAL, 
        TEMPORARY;
    }
}
