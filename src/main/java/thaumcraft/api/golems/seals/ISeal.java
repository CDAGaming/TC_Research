package thaumcraft.api.golems.seals;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.golems.*;

public interface ISeal
{
    String getKey();
    
    boolean canPlaceAt(final World p0, final BlockPos p1, final EnumFacing p2);
    
    void tickSeal(final World p0, final ISealEntity p1);
    
    void onTaskStarted(final World p0, final IGolemAPI p1, final Task p2);
    
    boolean onTaskCompletion(final World p0, final IGolemAPI p1, final Task p2);
    
    void onTaskSuspension(final World p0, final Task p1);
    
    boolean canGolemPerformTask(final IGolemAPI p0, final Task p1);
    
    void readCustomNBT(final NBTTagCompound p0);
    
    void writeCustomNBT(final NBTTagCompound p0);
    
    ResourceLocation getSealIcon();
    
    void onRemoval(final World p0, final BlockPos p1, final EnumFacing p2);
    
    Object returnContainer(final World p0, final EntityPlayer p1, final BlockPos p2, final EnumFacing p3, final ISealEntity p4);
    
    @SideOnly(Side.CLIENT)
    Object returnGui(final World p0, final EntityPlayer p1, final BlockPos p2, final EnumFacing p3, final ISealEntity p4);
    
    EnumGolemTrait[] getRequiredTags();
    
    EnumGolemTrait[] getForbiddenTags();
}
