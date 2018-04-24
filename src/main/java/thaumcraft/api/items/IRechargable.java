package thaumcraft.api.items;

import net.minecraft.item.*;
import net.minecraft.entity.*;

public interface IRechargable
{
    int getMaxCharge(final ItemStack p0, final EntityLivingBase p1);
    
    EnumChargeDisplay showInHud(final ItemStack p0, final EntityLivingBase p1);
    
    public enum EnumChargeDisplay
    {
        NEVER, 
        NORMAL, 
        PERIODIC;
    }
}
