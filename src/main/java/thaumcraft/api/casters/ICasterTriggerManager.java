package thaumcraft.api.casters;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public interface ICasterTriggerManager
{
    boolean performTrigger(final World p0, final ItemStack p1, final EntityPlayer p2, final BlockPos p3, final EnumFacing p4, final int p5);
}
