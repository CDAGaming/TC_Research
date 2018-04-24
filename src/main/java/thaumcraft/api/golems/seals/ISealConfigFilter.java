package thaumcraft.api.golems.seals;

import net.minecraft.util.*;
import net.minecraft.item.*;

public interface ISealConfigFilter
{
    NonNullList<ItemStack> getInv();
    
    int getFilterSize();
    
    ItemStack getFilterSlot(final int p0);
    
    void setFilterSlot(final int p0, final ItemStack p1);
    
    boolean isBlacklist();
    
    void setBlacklist(final boolean p0);
    
    boolean hasStacksizeLimiters();
}
