package thaumcraft.common.tiles.essentia;

import net.minecraft.nbt.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import java.util.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;

public class TileTubeBuffer extends TileTube implements IAspectContainer
{
    public AspectList aspects;
    public final int MAXAMOUNT = 10;
    public byte[] chokedSides;
    int count;
    int bellows;
    
    public TileTubeBuffer() {
        this.aspects = new AspectList();
        this.chokedSides = new byte[] { 0, 0, 0, 0, 0, 0 };
        this.count = 0;
        this.bellows = -1;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.aspects.readFromNBT(nbttagcompound);
        final byte[] sides = nbttagcompound.func_74770_j("open");
        if (sides != null && sides.length == 6) {
            for (int a = 0; a < 6; ++a) {
                this.openSides[a] = (sides[a] == 1);
            }
        }
        this.chokedSides = nbttagcompound.func_74770_j("choke");
        if (this.chokedSides == null || this.chokedSides.length < 6) {
            this.chokedSides = new byte[] { 0, 0, 0, 0, 0, 0 };
        }
        this.facing = EnumFacing.field_82609_l[nbttagcompound.func_74762_e("side")];
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        this.aspects.writeToNBT(nbttagcompound);
        final byte[] sides = new byte[6];
        for (int a = 0; a < 6; ++a) {
            sides[a] = (byte)(this.openSides[a] ? 1 : 0);
        }
        nbttagcompound.func_74773_a("open", sides);
        nbttagcompound.func_74773_a("choke", this.chokedSides);
        nbttagcompound.func_74768_a("side", this.facing.ordinal());
        return nbttagcompound;
    }
    
    @Override
    public AspectList getAspects() {
        return this.aspects;
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
    }
    
    @Override
    public int addToContainer(final Aspect tt, final int am) {
        if (am != 1) {
            return am;
        }
        if (this.aspects.visSize() < 10) {
            this.aspects.add(tt, am);
            this.func_70296_d();
            this.syncTile(false);
            return 0;
        }
        return am;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tt, final int am) {
        if (this.aspects.getAmount(tt) >= am) {
            this.aspects.remove(tt, am);
            this.func_70296_d();
            this.syncTile(false);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amt) {
        return this.aspects.getAmount(tag) >= amt;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    @Override
    public int containerContains(final Aspect tag) {
        return this.aspects.getAmount(tag);
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return this.openSides[face.ordinal()];
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return this.openSides[face.ordinal()];
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return this.openSides[face.ordinal()];
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        return null;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        return (this.chokedSides[loc.ordinal()] == 2) ? 0 : ((this.bellows <= 0 || this.chokedSides[loc.ordinal()] == 1) ? 1 : (this.bellows * 32));
    }
    
    @Override
    public Aspect getEssentiaType(final EnumFacing loc) {
        return (this.aspects.size() > 0) ? this.aspects.getAspects()[this.field_145850_b.field_73012_v.nextInt(this.aspects.getAspects().length)] : null;
    }
    
    @Override
    public int getEssentiaAmount(final EnumFacing loc) {
        return this.aspects.visSize();
    }
    
    @Override
    public int takeEssentia(final Aspect aspect, int amount, final EnumFacing face) {
        if (!this.canOutputTo(face)) {
            return 0;
        }
        TileEntity te = null;
        IEssentiaTransport ic = null;
        int suction = 0;
        te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, face);
        if (te != null) {
            ic = (IEssentiaTransport)te;
            suction = ic.getSuctionAmount(face.func_176734_d());
        }
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            if (this.canOutputTo(dir)) {
                if (dir != face) {
                    te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, dir);
                    if (te != null) {
                        ic = (IEssentiaTransport)te;
                        final int sa = ic.getSuctionAmount(dir.func_176734_d());
                        final Aspect su = ic.getSuctionType(dir.func_176734_d());
                        if ((su == aspect || su == null) && suction < sa && this.getSuctionAmount(dir) < sa) {
                            return 0;
                        }
                    }
                }
            }
        }
        if (amount > this.aspects.getAmount(aspect)) {
            amount = this.aspects.getAmount(aspect);
        }
        return this.takeFromContainer(aspect, amount) ? amount : 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return this.canInputFrom(face) ? (amount - this.addToContainer(aspect, amount)) : 0;
    }
    
    @Override
    public void func_73660_a() {
        ++this.count;
        if (this.bellows < 0 || this.count % 20 == 0) {
            this.getBellows();
        }
        if (!this.field_145850_b.field_72995_K && this.count % 5 == 0) {
            final int visSize = this.aspects.visSize();
            this.getClass();
            if (visSize < 10) {
                this.fillBuffer();
            }
        }
    }
    
    void fillBuffer() {
        TileEntity te = null;
        IEssentiaTransport ic = null;
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, dir);
            if (te != null) {
                ic = (IEssentiaTransport)te;
                if (ic.getEssentiaAmount(dir.func_176734_d()) > 0 && ic.getSuctionAmount(dir.func_176734_d()) < this.getSuctionAmount(dir) && this.getSuctionAmount(dir) >= ic.getMinimumSuction()) {
                    final Aspect ta = ic.getEssentiaType(dir.func_176734_d());
                    this.addToContainer(ta, ic.takeEssentia(ta, 1, dir.func_176734_d()));
                    return;
                }
            }
        }
    }
    
    public void getBellows() {
        this.bellows = TileBellows.getBellows(this.field_145850_b, this.field_174879_c, EnumFacing.field_82609_l);
    }
    
    @Override
    public boolean onCasterRightClick(final World world, final ItemStack wandstack, final EntityPlayer player, final BlockPos bp, final EnumFacing side, final EnumHand hand) {
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, this.field_174879_c);
        if (hit == null) {
            return false;
        }
        if (hit.subHit >= 0 && hit.subHit < 6) {
            player.func_184609_a(hand);
            if (player.func_70093_af()) {
                player.field_70170_p.func_184134_a(bp.func_177958_n() + 0.5, bp.func_177956_o() + 0.5, bp.func_177952_p() + 0.5, SoundsTC.squeek, SoundCategory.BLOCKS, 0.6f, 2.0f + world.field_73012_v.nextFloat() * 0.2f, false);
                if (!world.field_72995_K) {
                    final byte[] chokedSides = this.chokedSides;
                    final int subHit = hit.subHit;
                    ++chokedSides[subHit];
                    if (this.chokedSides[hit.subHit] > 2) {
                        this.chokedSides[hit.subHit] = 0;
                    }
                    this.func_70296_d();
                    this.syncTile(true);
                }
            }
            else {
                player.field_70170_p.func_184134_a(bp.func_177958_n() + 0.5, bp.func_177956_o() + 0.5, bp.func_177952_p() + 0.5, SoundsTC.tool, SoundCategory.BLOCKS, 0.5f, 0.9f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f, false);
                this.openSides[hit.subHit] = !this.openSides[hit.subHit];
                final EnumFacing dir = EnumFacing.field_82609_l[hit.subHit];
                final TileEntity tile = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
                if (tile != null && tile instanceof TileTube) {
                    ((TileTube)tile).openSides[dir.func_176734_d().ordinal()] = this.openSides[hit.subHit];
                    ((TileTube)tile).syncTile(true);
                    tile.func_70296_d();
                }
                this.func_70296_d();
                this.syncTile(true);
            }
        }
        return false;
    }
    
    @Override
    public boolean canConnectSide(final EnumFacing side) {
        final TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(side));
        return tile != null && tile instanceof IEssentiaTransport;
    }
    
    @Override
    public void addTraceableCuboids(final List<IndexedCuboid6> cuboids) {
        final float min = 0.375f;
        final float max = 0.625f;
        if (this.canConnectSide(EnumFacing.DOWN)) {
            cuboids.add(new IndexedCuboid6(0, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + max)));
        }
        if (this.canConnectSide(EnumFacing.UP)) {
            cuboids.add(new IndexedCuboid6(1, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + max)));
        }
        if (this.canConnectSide(EnumFacing.NORTH)) {
            cuboids.add(new IndexedCuboid6(2, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p(), this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + 0.5)));
        }
        if (this.canConnectSide(EnumFacing.SOUTH)) {
            cuboids.add(new IndexedCuboid6(3, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p() + 0.5, this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + 1)));
        }
        if (this.canConnectSide(EnumFacing.WEST)) {
            cuboids.add(new IndexedCuboid6(4, new Cuboid6(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + max)));
        }
        if (this.canConnectSide(EnumFacing.EAST)) {
            cuboids.add(new IndexedCuboid6(5, new Cuboid6(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + 1, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + max)));
        }
        cuboids.add(new IndexedCuboid6(6, new Cuboid6(this.field_174879_c.func_177958_n() + 0.25f, this.field_174879_c.func_177956_o() + 0.25f, this.field_174879_c.func_177952_p() + 0.25f, this.field_174879_c.func_177958_n() + 0.75f, this.field_174879_c.func_177956_o() + 0.75f, this.field_174879_c.func_177952_p() + 0.75f)));
    }
}
