package thaumcraft.common.tiles.essentia;

import net.minecraft.nbt.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import net.minecraft.tileentity.*;

public class TileJarFillable extends TileJar implements IAspectSource, IEssentiaTransport
{
    public static final int CAPACITY = 250;
    public Aspect aspect;
    public Aspect aspectFilter;
    public int amount;
    public int facing;
    public boolean blocked;
    int count;
    
    public TileJarFillable() {
        this.aspect = null;
        this.aspectFilter = null;
        this.amount = 0;
        this.facing = 2;
        this.blocked = false;
        this.count = 0;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.aspect = Aspect.getAspect(nbttagcompound.func_74779_i("Aspect"));
        this.aspectFilter = Aspect.getAspect(nbttagcompound.func_74779_i("AspectFilter"));
        this.amount = nbttagcompound.func_74765_d("Amount");
        this.facing = nbttagcompound.func_74771_c("facing");
        this.blocked = nbttagcompound.func_74767_n("blocked");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        if (this.aspect != null) {
            nbttagcompound.func_74778_a("Aspect", this.aspect.getTag());
        }
        if (this.aspectFilter != null) {
            nbttagcompound.func_74778_a("AspectFilter", this.aspectFilter.getTag());
        }
        nbttagcompound.func_74777_a("Amount", (short)this.amount);
        nbttagcompound.func_74774_a("facing", (byte)this.facing);
        nbttagcompound.func_74757_a("blocked", this.blocked);
        return nbttagcompound;
    }
    
    public AspectList getAspects() {
        final AspectList al = new AspectList();
        if (this.aspect != null && this.amount > 0) {
            al.add(this.aspect, this.amount);
        }
        return al;
    }
    
    public void setAspects(final AspectList aspects) {
        if (aspects != null && aspects.size() > 0) {
            this.aspect = aspects.getAspectsSortedByAmount()[0];
            this.amount = aspects.getAmount(aspects.getAspectsSortedByAmount()[0]);
        }
    }
    
    public int addToContainer(final Aspect tt, int am) {
        if (am == 0) {
            return am;
        }
        if ((this.amount < 250 && tt == this.aspect) || this.amount == 0) {
            this.aspect = tt;
            final int added = Math.min(am, 250 - this.amount);
            this.amount += added;
            am -= added;
        }
        this.syncTile(false);
        this.func_70296_d();
        return am;
    }
    
    public boolean takeFromContainer(final Aspect tt, final int am) {
        if (this.amount >= am && tt == this.aspect) {
            this.amount -= am;
            if (this.amount <= 0) {
                this.aspect = null;
                this.amount = 0;
            }
            this.syncTile(false);
            this.func_70296_d();
            return true;
        }
        return false;
    }
    
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    public boolean doesContainerContainAmount(final Aspect tag, final int amt) {
        return this.amount >= amt && tag == this.aspect;
    }
    
    public boolean doesContainerContain(final AspectList ot) {
        for (final Aspect tt : ot.getAspects()) {
            if (this.amount > 0 && tt == this.aspect) {
                return true;
            }
        }
        return false;
    }
    
    public int containerContains(final Aspect tag) {
        if (tag == this.aspect) {
            return this.amount;
        }
        return 0;
    }
    
    public boolean doesContainerAccept(final Aspect tag) {
        return this.aspectFilter == null || tag.equals(this.aspectFilter);
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face == EnumFacing.UP;
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return face == EnumFacing.UP;
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return face == EnumFacing.UP;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
    }
    
    @Override
    public int getMinimumSuction() {
        return (this.aspectFilter != null) ? 64 : 32;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        return (this.aspectFilter != null) ? this.aspectFilter : this.aspect;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        if (this.amount >= 250) {
            return 0;
        }
        if (this.aspectFilter != null) {
            return 64;
        }
        return 32;
    }
    
    @Override
    public Aspect getEssentiaType(final EnumFacing loc) {
        return this.aspect;
    }
    
    @Override
    public int getEssentiaAmount(final EnumFacing loc) {
        return this.amount;
    }
    
    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return (this.canOutputTo(face) && this.takeFromContainer(aspect, amount)) ? amount : 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return this.canInputFrom(face) ? (amount - this.addToContainer(aspect, amount)) : 0;
    }
    
    @Override
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K && ++this.count % 5 == 0 && this.amount < 250) {
            this.fillJar();
        }
    }
    
    void fillJar() {
        final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, EnumFacing.UP);
        if (te != null) {
            final IEssentiaTransport ic = (IEssentiaTransport)te;
            if (!ic.canOutputTo(EnumFacing.DOWN)) {
                return;
            }
            Aspect ta = null;
            if (this.aspectFilter != null) {
                ta = this.aspectFilter;
            }
            else if (this.aspect != null && this.amount > 0) {
                ta = this.aspect;
            }
            else if (ic.getEssentiaAmount(EnumFacing.DOWN) > 0 && ic.getSuctionAmount(EnumFacing.DOWN) < this.getSuctionAmount(EnumFacing.UP) && this.getSuctionAmount(EnumFacing.UP) >= ic.getMinimumSuction()) {
                ta = ic.getEssentiaType(EnumFacing.DOWN);
            }
            if (ta != null && ic.getSuctionAmount(EnumFacing.DOWN) < this.getSuctionAmount(EnumFacing.UP)) {
                this.addToContainer(ta, ic.takeEssentia(ta, 1, EnumFacing.DOWN));
            }
        }
    }
    
    @Override
    public boolean isBlocked() {
        return this.blocked;
    }
}
