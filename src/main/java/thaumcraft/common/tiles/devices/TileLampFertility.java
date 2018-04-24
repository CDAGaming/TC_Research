package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.nbt.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class TileLampFertility extends TileThaumcraft implements IEssentiaTransport, ITickable
{
    public int charges;
    int count;
    int drawDelay;
    
    public TileLampFertility() {
        this.charges = 0;
        this.count = 0;
        this.drawDelay = 0;
    }
    
    @Override
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
            this.field_145850_b.func_180500_c(EnumSkyBlock.BLOCK, this.func_174877_v());
        }
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            if (this.charges < 10) {
                if (this.drawEssentia()) {
                    ++this.charges;
                    this.func_70296_d();
                    this.syncTile(true);
                }
                if (this.charges <= 1) {
                    if (BlockStateUtils.isEnabled(this.func_145832_p())) {
                        this.field_145850_b.func_180501_a(this.field_174879_c, this.field_145850_b.func_180495_p(this.func_174877_v()).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false), 3);
                    }
                }
                else if (!this.gettingPower() && !BlockStateUtils.isEnabled(this.func_145832_p())) {
                    this.field_145850_b.func_180501_a(this.field_174879_c, this.field_145850_b.func_180495_p(this.func_174877_v()).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true), 3);
                }
            }
            if (!this.gettingPower() && this.charges > 1 && this.count++ % 300 == 0) {
                this.updateAnimals();
            }
        }
    }
    
    private void updateAnimals() {
        final int distance = 7;
        final List<EntityAnimal> var5 = (List<EntityAnimal>)this.field_145850_b.func_72872_a((Class)EntityAnimal.class, new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1)).func_72314_b((double)distance, (double)distance, (double)distance));
    Label_0314:
        for (final EntityLivingBase var8 : var5) {
            final EntityAnimal var7 = (EntityAnimal)var8;
            if (var7.func_70874_b() == 0) {
                if (var7.func_70880_s()) {
                    continue;
                }
                final ArrayList<EntityAnimal> sa = new ArrayList<EntityAnimal>();
                for (final EntityLivingBase var9 : var5) {
                    if (var9.getClass().equals(var8.getClass())) {
                        sa.add((EntityAnimal)var9);
                    }
                }
                if (sa != null && sa.size() > 9) {
                    continue;
                }
                final Iterator var10 = sa.iterator();
                EntityAnimal partner = null;
                while (var10.hasNext()) {
                    final EntityAnimal var11 = var10.next();
                    if (var11.func_70874_b() == 0) {
                        if (var11.func_70880_s()) {
                            continue;
                        }
                        if (partner != null) {
                            this.charges -= 5;
                            var11.func_146082_f((EntityPlayer)null);
                            partner.func_146082_f((EntityPlayer)null);
                            break Label_0314;
                        }
                        partner = var11;
                    }
                }
            }
        }
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.charges = nbttagcompound.func_74762_e("charges");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74768_a("charges", this.charges);
        return nbttagcompound;
    }
    
    boolean drawEssentia() {
        if (++this.drawDelay % 5 != 0) {
            return false;
        }
        final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.func_174877_v(), BlockStateUtils.getFacing(this.func_145832_p()));
        if (te != null) {
            final IEssentiaTransport ic = (IEssentiaTransport)te;
            if (!ic.canOutputTo(BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d())) {
                return false;
            }
            if (ic.getSuctionAmount(BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d()) < this.getSuctionAmount(BlockStateUtils.getFacing(this.func_145832_p())) && ic.takeEssentia(Aspect.DESIRE, 1, BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d()) == 1) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face == BlockStateUtils.getFacing(this.func_145832_p());
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return face == BlockStateUtils.getFacing(this.func_145832_p());
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return false;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing face) {
        return Aspect.DESIRE;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing face) {
        return (face == BlockStateUtils.getFacing(this.func_145832_p())) ? (128 - this.charges * 10) : 0;
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
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing facing) {
        return 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing facing) {
        return 0;
    }
}
