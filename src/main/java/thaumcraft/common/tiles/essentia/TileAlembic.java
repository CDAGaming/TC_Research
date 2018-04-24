package thaumcraft.common.tiles.essentia;

import thaumcraft.common.tiles.*;
import net.minecraft.util.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;

public class TileAlembic extends TileThaumcraft implements IAspectContainer, IEssentiaTransport
{
    public Aspect aspect;
    public Aspect aspectFilter;
    public int amount;
    public int maxAmount;
    public int facing;
    public boolean aboveFurnace;
    EnumFacing fd;
    
    public TileAlembic() {
        this.aspectFilter = null;
        this.amount = 0;
        this.maxAmount = 128;
        this.facing = EnumFacing.DOWN.ordinal();
        this.aboveFurnace = false;
        this.fd = null;
    }
    
    @Override
    public AspectList getAspects() {
        return (this.aspect != null) ? new AspectList().add(this.aspect, this.amount) : new AspectList();
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.func_174877_v().func_177958_n() - 0.1, this.func_174877_v().func_177956_o() - 0.1, this.func_174877_v().func_177952_p() - 0.1, this.func_174877_v().func_177958_n() + 1.1, this.func_174877_v().func_177956_o() + 1.1, this.func_174877_v().func_177952_p() + 1.1);
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.facing = nbttagcompound.func_74771_c("facing");
        this.aspectFilter = Aspect.getAspect(nbttagcompound.func_74779_i("AspectFilter"));
        final String tag = nbttagcompound.func_74779_i("aspect");
        if (tag != null) {
            this.aspect = Aspect.getAspect(tag);
        }
        this.amount = nbttagcompound.func_74765_d("amount");
        this.fd = EnumFacing.field_82609_l[this.facing];
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        if (this.aspect != null) {
            nbttagcompound.func_74778_a("aspect", this.aspect.getTag());
        }
        if (this.aspectFilter != null) {
            nbttagcompound.func_74778_a("AspectFilter", this.aspectFilter.getTag());
        }
        nbttagcompound.func_74777_a("amount", (short)this.amount);
        nbttagcompound.func_74774_a("facing", (byte)this.facing);
        return nbttagcompound;
    }
    
    @Override
    public int addToContainer(final Aspect tt, int am) {
        if (this.aspectFilter != null && tt != this.aspectFilter) {
            return am;
        }
        if ((this.amount < this.maxAmount && tt == this.aspect) || this.amount == 0) {
            this.aspect = tt;
            final int added = Math.min(am, this.maxAmount - this.amount);
            this.amount += added;
            am -= added;
        }
        this.func_70296_d();
        this.syncTile(false);
        return am;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tt, final int am) {
        if (this.amount == 0 || this.aspect == null) {
            this.aspect = null;
            this.amount = 0;
        }
        if (this.aspect != null && this.amount >= am && tt == this.aspect) {
            this.amount -= am;
            if (this.amount <= 0) {
                this.aspect = null;
                this.amount = 0;
            }
            this.func_70296_d();
            this.syncTile(false);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return this.amount > 0 && this.aspect != null && ot.getAmount(this.aspect) > 0;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tt, final int am) {
        return this.amount >= am && tt == this.aspect;
    }
    
    @Override
    public int containerContains(final Aspect tt) {
        return (tt == this.aspect) ? this.amount : 0;
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face != EnumFacing.field_82609_l[this.facing] && face != EnumFacing.DOWN;
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return false;
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return face != EnumFacing.field_82609_l[this.facing] && face != EnumFacing.DOWN;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        return null;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        return 0;
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
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing loc) {
        return 0;
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    protected static boolean processAlembics(final World world, final BlockPos pos, final Aspect aspect) {
        int deep = 1;
        while (true) {
            TileEntity te = world.func_175625_s(pos.func_177981_b(deep));
            if (te != null && te instanceof TileAlembic) {
                final TileAlembic alembic = (TileAlembic)te;
                if (alembic.amount > 0 && alembic.aspect == aspect && alembic.addToContainer(aspect, 1) == 0) {
                    return true;
                }
                ++deep;
            }
            else {
                deep = 1;
                while (true) {
                    te = world.func_175625_s(pos.func_177981_b(deep));
                    if (te == null || !(te instanceof TileAlembic)) {
                        return false;
                    }
                    final TileAlembic alembic = (TileAlembic)te;
                    if ((alembic.aspectFilter == null || alembic.aspectFilter == aspect) && alembic.addToContainer(aspect, 1) == 0) {
                        return true;
                    }
                    ++deep;
                }
            }
        }
    }
}
