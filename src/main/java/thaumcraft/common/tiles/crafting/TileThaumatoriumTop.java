package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;

public class TileThaumatoriumTop extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ISidedInventory, ITickable
{
    public TileThaumatorium thaumatorium;
    
    public TileThaumatoriumTop() {
        this.thaumatorium = null;
    }
    
    public void func_73660_a() {
        if (this.thaumatorium == null) {
            final TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177977_b());
            if (tile != null && tile instanceof TileThaumatorium) {
                this.thaumatorium = (TileThaumatorium)tile;
            }
        }
    }
    
    @Override
    public int addToContainer(final Aspect tt, final int am) {
        if (this.thaumatorium == null) {
            return am;
        }
        return this.thaumatorium.addToContainer(tt, am);
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tt, final int am) {
        return this.thaumatorium != null && this.thaumatorium.takeFromContainer(tt, am);
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tt, final int am) {
        return this.thaumatorium != null && this.thaumatorium.doesContainerContainAmount(tt, am);
    }
    
    @Override
    public int containerContains(final Aspect tt) {
        if (this.thaumatorium == null) {
            return 0;
        }
        return this.thaumatorium.containerContains(tt);
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return this.thaumatorium != null && this.thaumatorium.isConnectable(face);
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return this.thaumatorium != null && this.thaumatorium.canInputFrom(face);
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return false;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
        if (this.thaumatorium == null) {
            return;
        }
        this.thaumatorium.setSuction(aspect, amount);
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        if (this.thaumatorium == null) {
            return null;
        }
        return this.thaumatorium.getSuctionType(loc);
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        if (this.thaumatorium == null) {
            return 0;
        }
        return this.thaumatorium.getSuctionAmount(loc);
    }
    
    @Override
    public Aspect getEssentiaType(final EnumFacing loc) {
        return null;
    }
    
    @Override
    public int getEssentiaAmount(final EnumFacing loc) {
        return 0;
    }
    
    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        if (this.thaumatorium == null) {
            return 0;
        }
        return this.thaumatorium.takeEssentia(aspect, amount, face);
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        if (this.thaumatorium == null) {
            return 0;
        }
        return this.thaumatorium.addEssentia(aspect, amount, face);
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    @Override
    public AspectList getAspects() {
        if (this.thaumatorium == null) {
            return null;
        }
        return this.thaumatorium.essentia;
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
        if (this.thaumatorium == null) {
            return;
        }
        this.thaumatorium.setAspects(aspects);
    }
    
    public int func_70302_i_() {
        return 1;
    }
    
    public ItemStack func_70301_a(final int par1) {
        if (this.thaumatorium == null) {
            return null;
        }
        return this.thaumatorium.func_70301_a(par1);
    }
    
    public ItemStack func_70298_a(final int par1, final int par2) {
        if (this.thaumatorium == null) {
            return null;
        }
        return this.thaumatorium.func_70298_a(par1, par2);
    }
    
    public ItemStack func_70304_b(final int par1) {
        if (this.thaumatorium == null) {
            return null;
        }
        return this.thaumatorium.func_70304_b(par1);
    }
    
    public void func_70299_a(final int par1, final ItemStack stack2) {
        if (this.thaumatorium == null) {
            return;
        }
        this.thaumatorium.func_70299_a(par1, stack2);
    }
    
    public int func_70297_j_() {
        return 64;
    }
    
    public boolean func_70300_a(final EntityPlayer par1EntityPlayer) {
        return this.field_145850_b.func_175625_s(this.field_174879_c) == this && par1EntityPlayer.func_174831_c(this.field_174879_c) <= 64.0;
    }
    
    public boolean func_94041_b(final int par1, final ItemStack stack2) {
        return true;
    }
    
    public int[] func_180463_a(final EnumFacing side) {
        return new int[] { 0 };
    }
    
    public boolean func_180462_a(final int index, final ItemStack itemStackIn, final EnumFacing direction) {
        return true;
    }
    
    public boolean func_180461_b(final int index, final ItemStack stack, final EnumFacing direction) {
        return true;
    }
    
    public void func_174889_b(final EntityPlayer player) {
    }
    
    public void func_174886_c(final EntityPlayer player) {
    }
    
    public int func_174887_a_(final int id) {
        return 0;
    }
    
    public void func_174885_b(final int id, final int value) {
    }
    
    public int func_174890_g() {
        return 0;
    }
    
    public void func_174888_l() {
        this.thaumatorium.func_174888_l();
    }
    
    public String func_70005_c_() {
        return null;
    }
    
    public boolean func_145818_k_() {
        return false;
    }
    
    public ITextComponent func_145748_c_() {
        return null;
    }
    
    public boolean func_191420_l() {
        return this.thaumatorium.func_191420_l();
    }
}
