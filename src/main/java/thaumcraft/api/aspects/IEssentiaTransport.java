package thaumcraft.api.aspects;

import net.minecraft.util.*;

public interface IEssentiaTransport
{
    boolean isConnectable(final EnumFacing p0);
    
    boolean canInputFrom(final EnumFacing p0);
    
    boolean canOutputTo(final EnumFacing p0);
    
    void setSuction(final Aspect p0, final int p1);
    
    Aspect getSuctionType(final EnumFacing p0);
    
    int getSuctionAmount(final EnumFacing p0);
    
    int takeEssentia(final Aspect p0, final int p1, final EnumFacing p2);
    
    int addEssentia(final Aspect p0, final int p1, final EnumFacing p2);
    
    Aspect getEssentiaType(final EnumFacing p0);
    
    int getEssentiaAmount(final EnumFacing p0);
    
    int getMinimumSuction();
}
