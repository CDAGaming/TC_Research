package thaumcraft.common.tiles.essentia;

import thaumcraft.common.tiles.*;
import thaumcraft.api.casters.*;
import thaumcraft.api.aspects.*;
import net.minecraft.nbt.*;
import thaumcraft.client.fx.*;
import thaumcraft.api.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.config.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.lib.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.math.*;
import java.util.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;

public class TileTube extends TileThaumcraft implements IEssentiaTransport, IInteractWithCaster, ITickable
{
    public static final int freq = 5;
    public EnumFacing facing;
    public boolean[] openSides;
    Aspect essentiaType;
    int essentiaAmount;
    Aspect suctionType;
    int suction;
    int venting;
    int count;
    int ventColor;
    
    public TileTube() {
        this.facing = EnumFacing.NORTH;
        this.openSides = new boolean[] { true, true, true, true, true, true };
        this.essentiaType = null;
        this.essentiaAmount = 0;
        this.suctionType = null;
        this.suction = 0;
        this.venting = 0;
        this.count = 0;
        this.ventColor = 0;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.essentiaType = Aspect.getAspect(nbttagcompound.func_74779_i("type"));
        this.essentiaAmount = nbttagcompound.func_74762_e("amount");
        this.facing = EnumFacing.field_82609_l[nbttagcompound.func_74762_e("side")];
        final byte[] sides = nbttagcompound.func_74770_j("open");
        if (sides != null && sides.length == 6) {
            for (int a = 0; a < 6; ++a) {
                this.openSides[a] = (sides[a] == 1);
            }
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        if (this.essentiaType != null) {
            nbttagcompound.func_74778_a("type", this.essentiaType.getTag());
        }
        nbttagcompound.func_74768_a("amount", this.essentiaAmount);
        final byte[] sides = new byte[6];
        for (int a = 0; a < 6; ++a) {
            sides[a] = (byte)(this.openSides[a] ? 1 : 0);
        }
        nbttagcompound.func_74768_a("side", this.facing.ordinal());
        nbttagcompound.func_74773_a("open", sides);
        return nbttagcompound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.suctionType = Aspect.getAspect(nbttagcompound.func_74779_i("stype"));
        this.suction = nbttagcompound.func_74762_e("samount");
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbttagcompound) {
        super.func_189515_b(nbttagcompound);
        if (this.suctionType != null) {
            nbttagcompound.func_74778_a("stype", this.suctionType.getTag());
        }
        nbttagcompound.func_74768_a("samount", this.suction);
        return nbttagcompound;
    }
    
    public void func_73660_a() {
        if (this.venting > 0) {
            --this.venting;
        }
        if (this.count == 0) {
            this.count = this.field_145850_b.field_73012_v.nextInt(10);
        }
        if (!this.field_145850_b.field_72995_K) {
            if (this.venting <= 0) {
                if (++this.count % 2 == 0) {
                    this.calculateSuction(null, false, false);
                    this.checkVenting();
                    if (this.essentiaType != null && this.essentiaAmount == 0) {
                        this.essentiaType = null;
                    }
                }
                if (this.count % 5 == 0 && this.suction > 0) {
                    this.equalizeWithNeighbours(false);
                }
            }
        }
        else if (this.venting > 0) {
            final Random r = new Random(this.hashCode() * 4);
            final float rp = r.nextFloat() * 360.0f;
            final float ry = r.nextFloat() * 360.0f;
            final double fx = -MathHelper.func_76126_a(ry / 180.0f * 3.1415927f) * MathHelper.func_76134_b(rp / 180.0f * 3.1415927f);
            final double fz = MathHelper.func_76134_b(ry / 180.0f * 3.1415927f) * MathHelper.func_76134_b(rp / 180.0f * 3.1415927f);
            final double fy = -MathHelper.func_76126_a(rp / 180.0f * 3.1415927f);
            FXDispatcher.INSTANCE.drawVentParticles(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, fx / 5.0, fy / 5.0, fz / 5.0, this.ventColor);
        }
    }
    
    void calculateSuction(final Aspect filter, final boolean restrict, final boolean directional) {
        this.suction = 0;
        this.suctionType = null;
        EnumFacing loc = null;
        for (int dir = 0; dir < 6; ++dir) {
            try {
                loc = EnumFacing.field_82609_l[dir];
                if (!directional || this.facing == loc.func_176734_d()) {
                    if (this.isConnectable(loc)) {
                        final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, loc);
                        if (te != null) {
                            final IEssentiaTransport ic = (IEssentiaTransport)te;
                            if (filter == null || ic.getSuctionType(loc.func_176734_d()) == null || ic.getSuctionType(loc.func_176734_d()) == filter) {
                                if (filter != null || this.getEssentiaAmount(loc) <= 0 || ic.getSuctionType(loc.func_176734_d()) == null || this.getEssentiaType(loc) == ic.getSuctionType(loc.func_176734_d())) {
                                    if (filter == null || this.getEssentiaAmount(loc) <= 0 || this.getEssentiaType(loc) == null || ic.getSuctionType(loc.func_176734_d()) == null || this.getEssentiaType(loc) == ic.getSuctionType(loc.func_176734_d())) {
                                        final int suck = ic.getSuctionAmount(loc.func_176734_d());
                                        if (suck > 0 && suck > this.suction + 1) {
                                            Aspect st = ic.getSuctionType(loc.func_176734_d());
                                            if (st == null) {
                                                st = filter;
                                            }
                                            this.setSuction(st, restrict ? (suck / 2) : (suck - 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    void checkVenting() {
        EnumFacing loc = null;
        for (int dir = 0; dir < 6; ++dir) {
            try {
                loc = EnumFacing.field_82609_l[dir];
                if (this.isConnectable(loc)) {
                    final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, loc);
                    if (te != null) {
                        final IEssentiaTransport ic = (IEssentiaTransport)te;
                        final int suck = ic.getSuctionAmount(loc.func_176734_d());
                        if (this.suction > 0 && (suck == this.suction || suck == this.suction - 1) && this.suctionType != ic.getSuctionType(loc.func_176734_d()) && !(te instanceof TileTubeFilter)) {
                            int c = -1;
                            if (this.suctionType != null) {
                                c = ModConfig.aspectOrder.indexOf(this.suctionType);
                            }
                            this.field_145850_b.func_175641_c(this.field_174879_c, BlocksTC.tube, 1, c);
                            this.venting = 40;
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    void equalizeWithNeighbours(final boolean directional) {
        EnumFacing loc = null;
        if (this.essentiaAmount > 0) {
            return;
        }
        for (int dir = 0; dir < 6; ++dir) {
            try {
                loc = EnumFacing.field_82609_l[dir];
                if (!directional || this.facing != loc.func_176734_d()) {
                    if (this.isConnectable(loc)) {
                        final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c, loc);
                        if (te != null) {
                            final IEssentiaTransport ic = (IEssentiaTransport)te;
                            if (ic.canOutputTo(loc.func_176734_d())) {
                                if ((this.getSuctionType(null) == null || this.getSuctionType(null) == ic.getEssentiaType(loc.func_176734_d()) || ic.getEssentiaType(loc.func_176734_d()) == null) && this.getSuctionAmount(null) > ic.getSuctionAmount(loc.func_176734_d()) && this.getSuctionAmount(null) >= ic.getMinimumSuction()) {
                                    Aspect a = this.getSuctionType(null);
                                    if (a == null) {
                                        a = ic.getEssentiaType(loc.func_176734_d());
                                        if (a == null) {
                                            a = ic.getEssentiaType(null);
                                        }
                                    }
                                    final int am = this.addEssentia(a, ic.takeEssentia(a, 1, loc.func_176734_d()), loc);
                                    if (am > 0) {
                                        if (this.field_145850_b.field_73012_v.nextInt(100) == 0) {
                                            this.field_145850_b.func_175641_c(this.field_174879_c, BlocksTC.tube, 0, 0);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face != null && this.openSides[face.ordinal()];
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return face != null && this.openSides[face.ordinal()];
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return face != null && this.openSides[face.ordinal()];
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
        this.suctionType = aspect;
        this.suction = amount;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        return this.suctionType;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        return this.suction;
    }
    
    @Override
    public Aspect getEssentiaType(final EnumFacing loc) {
        return this.essentiaType;
    }
    
    @Override
    public int getEssentiaAmount(final EnumFacing loc) {
        return this.essentiaAmount;
    }
    
    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        if (this.canOutputTo(face) && this.essentiaType == aspect && this.essentiaAmount > 0 && amount > 0) {
            --this.essentiaAmount;
            if (this.essentiaAmount <= 0) {
                this.essentiaType = null;
            }
            this.func_70296_d();
            return 1;
        }
        return 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        if (this.canInputFrom(face) && this.essentiaAmount == 0 && amount > 0) {
            this.essentiaType = aspect;
            ++this.essentiaAmount;
            this.func_70296_d();
            return 1;
        }
        return 0;
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 0) {
            if (this.field_145850_b.field_72995_K) {
                this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, SoundsTC.creak, SoundCategory.AMBIENT, 1.0f, 1.3f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f, false);
            }
            return true;
        }
        if (i == 1) {
            if (this.field_145850_b.field_72995_K) {
                if (this.venting <= 0) {
                    this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.1f, 1.0f + this.field_145850_b.field_73012_v.nextFloat() * 0.1f, false);
                }
                this.venting = 50;
                if (j == -1 || j >= ModConfig.aspectOrder.size()) {
                    this.ventColor = 11184810;
                }
                else {
                    this.ventColor = ModConfig.aspectOrder.get(j).getColor();
                }
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
    
    @Override
    public boolean onCasterRightClick(final World world, final ItemStack wandstack, final EntityPlayer player, final BlockPos bp, final EnumFacing side, final EnumHand hand) {
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, this.field_174879_c);
        if (hit == null) {
            return false;
        }
        if (hit.subHit >= 0 && hit.subHit < 6) {
            player.field_70170_p.func_184134_a(bp.func_177958_n() + 0.5, bp.func_177956_o() + 0.5, bp.func_177952_p() + 0.5, SoundsTC.tool, SoundCategory.BLOCKS, 0.5f, 0.9f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f, false);
            player.func_184609_a(hand);
            this.func_70296_d();
            this.syncTile(true);
            this.openSides[hit.subHit] = !this.openSides[hit.subHit];
            final EnumFacing dir = EnumFacing.field_82609_l[hit.subHit];
            final TileEntity tile = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (tile != null && tile instanceof TileTube) {
                ((TileTube)tile).openSides[dir.func_176734_d().ordinal()] = this.openSides[hit.subHit];
                this.syncTile(true);
                tile.func_70296_d();
            }
        }
        if (hit.subHit == 6) {
            player.field_70170_p.func_184134_a(bp.func_177958_n() + 0.5, bp.func_177956_o() + 0.5, bp.func_177952_p() + 0.5, SoundsTC.tool, SoundCategory.BLOCKS, 0.5f, 0.9f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f, false);
            player.func_184609_a(hand);
            int a = this.facing.ordinal();
            this.func_70296_d();
            while (++a < 20) {
                if (this.canConnectSide(EnumFacing.field_82609_l[a % 6].func_176734_d()) && this.isConnectable(EnumFacing.field_82609_l[a % 6].func_176734_d())) {
                    a %= 6;
                    this.facing = EnumFacing.field_82609_l[a];
                    this.syncTile(true);
                    this.func_70296_d();
                    break;
                }
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1));
    }
    
    public RayTraceResult rayTrace(final World world, final Vec3d vec3d, final Vec3d vec3d1, final RayTraceResult fullblock) {
        return fullblock;
    }
    
    public boolean canConnectSide(final EnumFacing side) {
        final TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(side));
        return tile != null && tile instanceof IEssentiaTransport;
    }
    
    public void addTraceableCuboids(final List<IndexedCuboid6> cuboids) {
        final float min = 0.375f;
        final float max = 0.625f;
        if (this.canConnectSide(EnumFacing.DOWN)) {
            cuboids.add(new IndexedCuboid6(0, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + 0.375, this.field_174879_c.func_177952_p() + max)));
        }
        if (this.canConnectSide(EnumFacing.UP)) {
            cuboids.add(new IndexedCuboid6(1, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o() + 0.625, this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + max)));
        }
        if (this.canConnectSide(EnumFacing.NORTH)) {
            cuboids.add(new IndexedCuboid6(2, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p(), this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + 0.375)));
        }
        if (this.canConnectSide(EnumFacing.SOUTH)) {
            cuboids.add(new IndexedCuboid6(3, new Cuboid6(this.field_174879_c.func_177958_n() + min, this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p() + 0.625, this.field_174879_c.func_177958_n() + max, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + 1)));
        }
        if (this.canConnectSide(EnumFacing.WEST)) {
            cuboids.add(new IndexedCuboid6(4, new Cuboid6(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + 0.375, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + max)));
        }
        if (this.canConnectSide(EnumFacing.EAST)) {
            cuboids.add(new IndexedCuboid6(5, new Cuboid6(this.field_174879_c.func_177958_n() + 0.625, this.field_174879_c.func_177956_o() + min, this.field_174879_c.func_177952_p() + min, this.field_174879_c.func_177958_n() + 1, this.field_174879_c.func_177956_o() + max, this.field_174879_c.func_177952_p() + max)));
        }
        cuboids.add(new IndexedCuboid6(6, new Cuboid6(this.field_174879_c.func_177958_n() + 0.375, this.field_174879_c.func_177956_o() + 0.375, this.field_174879_c.func_177952_p() + 0.375, this.field_174879_c.func_177958_n() + 0.625, this.field_174879_c.func_177956_o() + 0.625, this.field_174879_c.func_177952_p() + 0.625)));
    }
}
