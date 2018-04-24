package thaumcraft.api.items;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;

public interface IArchitect
{
    RayTraceResult getArchitectMOP(final ItemStack p0, final World p1, final EntityLivingBase p2);
    
    boolean useBlockHighlight(final ItemStack p0);
    
    ArrayList<BlockPos> getArchitectBlocks(final ItemStack p0, final World p1, final BlockPos p2, final EnumFacing p3, final EntityPlayer p4);
    
    boolean showAxis(final ItemStack p0, final World p1, final EntityPlayer p2, final EnumFacing p3, final EnumAxis p4);
    
    public enum EnumAxis
    {
        X, 
        Y, 
        Z;
    }
}
