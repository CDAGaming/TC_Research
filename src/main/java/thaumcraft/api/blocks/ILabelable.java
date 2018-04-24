package thaumcraft.api.blocks;

import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public interface ILabelable
{
    boolean applyLabel(final EntityPlayer p0, final BlockPos p1, final EnumFacing p2, final ItemStack p3);
}
