package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;

public abstract class SealFiltered implements ISeal, ISealGui, ISealConfigFilter
{
    NonNullList<ItemStack> filter;
    boolean blacklist;
    
    public SealFiltered() {
        this.filter = (NonNullList<ItemStack>)NonNullList.func_191197_a(this.getFilterSize(), (Object)ItemStack.field_190927_a);
        this.blacklist = true;
    }
    
    @Override
    public void readCustomNBT(final NBTTagCompound nbt) {
        ItemStackHelper.func_191283_b(nbt, (NonNullList)(this.filter = (NonNullList<ItemStack>)NonNullList.func_191197_a(this.getFilterSize(), (Object)ItemStack.field_190927_a)));
        this.blacklist = nbt.func_74767_n("bl");
    }
    
    @Override
    public void writeCustomNBT(final NBTTagCompound nbt) {
        ItemStackHelper.func_191282_a(nbt, (NonNullList)this.filter);
        nbt.func_74757_a("bl", this.blacklist);
    }
    
    @Override
    public Object returnContainer(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ISealEntity seal) {
        return new SealBaseContainer(player.field_71071_by, world, seal);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Object returnGui(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ISealEntity seal) {
        return new SealBaseGUI(player.field_71071_by, world, seal);
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 0 };
    }
    
    @Override
    public int getFilterSize() {
        return 1;
    }
    
    @Override
    public NonNullList<ItemStack> getInv() {
        return this.filter;
    }
    
    @Override
    public ItemStack getFilterSlot(final int i) {
        return (ItemStack)this.filter.get(i);
    }
    
    @Override
    public void setFilterSlot(final int i, final ItemStack stack) {
        this.filter.set(i, (Object)stack.func_77946_l());
    }
    
    @Override
    public boolean isBlacklist() {
        return this.blacklist;
    }
    
    @Override
    public void setBlacklist(final boolean black) {
        this.blacklist = black;
    }
    
    @Override
    public boolean hasStacksizeLimiters() {
        return false;
    }
}
