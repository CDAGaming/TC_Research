package thaumcraft.api.research;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import thaumcraft.api.*;

public class ScanItem implements IScanThing
{
    String research;
    ItemStack stack;
    
    public ScanItem(final String research, final ItemStack stack) {
        this.research = research;
        this.stack = stack;
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        if (obj == null) {
            return false;
        }
        ItemStack is = null;
        if (obj instanceof ItemStack) {
            is = (ItemStack)obj;
        }
        if (obj instanceof EntityItem && ((EntityItem)obj).func_92059_d() != null) {
            is = ((EntityItem)obj).func_92059_d();
        }
        return is != null && !is.func_190926_b() && ThaumcraftApiHelper.areItemStacksEqualForCrafting(is, this.stack);
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return this.research;
    }
}
