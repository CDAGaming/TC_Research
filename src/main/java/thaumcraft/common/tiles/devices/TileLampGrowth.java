package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.util.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class TileLampGrowth extends TileThaumcraft implements IEssentiaTransport, ITickable
{
    private boolean reserve;
    public int charges;
    public int maxCharges;
    int lx;
    int ly;
    int lz;
    Block lid;
    int lmd;
    ArrayList<BlockPos> checklist;
    int drawDelay;
    
    public TileLampGrowth() {
        this.reserve = false;
        this.charges = -1;
        this.maxCharges = 20;
        this.lx = 0;
        this.ly = 0;
        this.lz = 0;
        this.lid = Blocks.field_150350_a;
        this.lmd = 0;
        this.checklist = new ArrayList<BlockPos>();
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
            if (this.charges <= 0) {
                if (this.reserve) {
                    this.charges = this.maxCharges;
                    this.reserve = false;
                    this.func_70296_d();
                    this.syncTile(true);
                }
                else if (this.drawEssentia()) {
                    this.charges = this.maxCharges;
                    this.func_70296_d();
                    this.syncTile(true);
                }
                if (this.charges <= 0) {
                    if (BlockStateUtils.isEnabled(this.func_145832_p())) {
                        this.field_145850_b.func_180501_a(this.field_174879_c, this.field_145850_b.func_180495_p(this.func_174877_v()).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false), 3);
                    }
                }
                else if (!this.gettingPower() && !BlockStateUtils.isEnabled(this.func_145832_p())) {
                    this.field_145850_b.func_180501_a(this.field_174879_c, this.field_145850_b.func_180495_p(this.func_174877_v()).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true), 3);
                }
            }
            if (!this.reserve && this.drawEssentia()) {
                this.reserve = true;
            }
            if (this.charges == 0) {
                this.charges = -1;
                this.syncTile(true);
            }
            if (!this.gettingPower() && this.charges > 0) {
                this.updatePlant();
            }
        }
    }
    
    boolean isPlant(final BlockPos bp) {
        final IBlockState b = this.field_145850_b.func_180495_p(bp);
        final boolean flag = b.func_177230_c() instanceof IGrowable;
        final Material mat = b.func_185904_a();
        return (flag || mat == Material.field_151570_A || mat == Material.field_151585_k) && mat != Material.field_151577_b;
    }
    
    private void updatePlant() {
        final IBlockState bs = this.field_145850_b.func_180495_p(new BlockPos(this.lx, this.ly, this.lz));
        if (this.lid != bs.func_177230_c() || this.lmd != bs.func_177230_c().func_176201_c(bs)) {
            final EntityPlayer p = this.field_145850_b.func_184137_a((double)this.lx, (double)this.ly, (double)this.lz, 32.0, false);
            if (p != null) {
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockMist(new BlockPos(this.lx, this.ly, this.lz), 4259648), new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.getDimension(), (double)this.lx, (double)this.ly, (double)this.lz, 32.0));
            }
            this.lid = bs.func_177230_c();
            this.lmd = bs.func_177230_c().func_176201_c(bs);
        }
        final int distance = 6;
        if (this.checklist.size() == 0) {
            for (int a = -distance; a <= distance; ++a) {
                for (int b = -distance; b <= distance; ++b) {
                    this.checklist.add(this.func_174877_v().func_177982_a(a, distance, b));
                }
            }
            Collections.shuffle(this.checklist, this.field_145850_b.field_73012_v);
        }
        final int x = this.checklist.get(0).func_177958_n();
        int y = this.checklist.get(0).func_177956_o();
        final int z = this.checklist.get(0).func_177952_p();
        this.checklist.remove(0);
        while (y >= this.field_174879_c.func_177956_o() - distance) {
            final BlockPos bp = new BlockPos(x, y, z);
            if (!this.field_145850_b.func_175623_d(bp) && this.isPlant(bp) && this.func_145835_a(x + 0.5, y + 0.5, z + 0.5) < distance * distance && !CropUtils.isGrownCrop(this.field_145850_b, bp) && CropUtils.doesLampGrow(this.field_145850_b, bp)) {
                --this.charges;
                this.lx = x;
                this.ly = y;
                this.lz = z;
                final IBlockState bs2 = this.field_145850_b.func_180495_p(bp);
                this.lid = bs2.func_177230_c();
                this.lmd = bs2.func_177230_c().func_176201_c(bs2);
                this.field_145850_b.func_175684_a(bp, this.lid, 1);
                return;
            }
            --y;
        }
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.reserve = nbttagcompound.func_74767_n("reserve");
        this.charges = nbttagcompound.func_74762_e("charges");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74757_a("reserve", this.reserve);
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
            if (ic.getSuctionAmount(BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d()) < this.getSuctionAmount(BlockStateUtils.getFacing(this.func_145832_p())) && ic.takeEssentia(Aspect.PLANT, 1, BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d()) == 1) {
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
        return Aspect.PLANT;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing face) {
        return (face == BlockStateUtils.getFacing(this.func_145832_p()) && (!this.reserve || this.charges <= 0)) ? 128 : 0;
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
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing loc) {
        return 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing loc) {
        return 0;
    }
}
