package thaumcraft.api.casters;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public interface ICaster
{
    float getConsumptionModifier(final ItemStack p0, final EntityPlayer p1, final boolean p2);
    
    boolean consumeVis(final ItemStack p0, final EntityPlayer p1, final float p2, final boolean p3, final boolean p4);
    
    Item getFocus(final ItemStack p0);
    
    ItemStack getFocusStack(final ItemStack p0);
    
    void setFocus(final ItemStack p0, final ItemStack p1);
    
    ItemStack getPickedBlock(final ItemStack p0);
}
