package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aura.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import java.util.*;
import thaumcraft.client.fx.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;

public class TileLevitator extends TileThaumcraft implements ITickable
{
    private int[] ranges;
    private int range;
    private int rangeActual;
    private int counter;
    private int vis;
    
    public TileLevitator() {
        this.ranges = new int[] { 4, 8, 16, 32 };
        this.range = 1;
        this.rangeActual = 0;
        this.counter = 0;
        this.vis = 0;
    }
    
    public void func_73660_a() {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        if (this.rangeActual > this.ranges[this.range]) {
            this.rangeActual = 0;
        }
        final int p = this.counter % this.ranges[this.range];
        if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177967_a(facing, 1 + p)).func_185914_p()) {
            if (1 + p < this.rangeActual) {
                this.rangeActual = 1 + p;
            }
            this.counter = -1;
        }
        else if (1 + p > this.rangeActual) {
            this.rangeActual = 1 + p;
        }
        ++this.counter;
        if (!this.field_145850_b.field_72995_K && this.vis < 10) {
            this.vis += (int)(AuraHelper.drainVis(this.field_145850_b, this.func_174877_v(), 1.0f, false) * 1200.0f);
            this.func_70296_d();
            this.syncTile(false);
        }
        if (this.rangeActual > 0 && this.vis > 0 && BlockStateUtils.isEnabled(this.func_145832_p())) {
            final List<Entity> targets = (List<Entity>)this.field_145850_b.func_72872_a((Class)Entity.class, new AxisAlignedBB((double)(this.field_174879_c.func_177958_n() - ((facing.func_82601_c() < 0) ? this.rangeActual : 0)), (double)(this.field_174879_c.func_177956_o() - ((facing.func_96559_d() < 0) ? this.rangeActual : 0)), (double)(this.field_174879_c.func_177952_p() - ((facing.func_82599_e() < 0) ? this.rangeActual : 0)), (double)(this.field_174879_c.func_177958_n() + 1 + ((facing.func_82601_c() > 0) ? this.rangeActual : 0)), (double)(this.field_174879_c.func_177956_o() + 1 + ((facing.func_96559_d() > 0) ? this.rangeActual : 0)), (double)(this.field_174879_c.func_177952_p() + 1 + ((facing.func_82599_e() > 0) ? this.rangeActual : 0))));
            boolean lifted = false;
            if (targets.size() > 0) {
                for (final Entity e : targets) {
                    if (!(e instanceof EntityItem) && !e.func_70104_M() && !(e instanceof EntityHorse)) {
                        continue;
                    }
                    lifted = true;
                    this.drawFXAt(e);
                    this.drawFX(facing, 0.6);
                    if (e.func_70093_af() && facing == EnumFacing.UP) {
                        if (e.field_70181_x < 0.0) {
                            final Entity entity = e;
                            entity.field_70181_x *= 0.8999999761581421;
                        }
                    }
                    else {
                        final Entity entity2 = e;
                        entity2.field_70159_w += 0.1f * facing.func_82601_c();
                        final Entity entity3 = e;
                        entity3.field_70181_x += 0.1f * facing.func_96559_d();
                        final Entity entity4 = e;
                        entity4.field_70179_y += 0.1f * facing.func_82599_e();
                        if (facing.func_176740_k() != EnumFacing.Axis.Y && !e.field_70122_E) {
                            if (e.field_70181_x < 0.0) {
                                final Entity entity5 = e;
                                entity5.field_70181_x *= 0.8999999761581421;
                            }
                            final Entity entity6 = e;
                            entity6.field_70181_x += 0.07999999821186066;
                        }
                        if (e.field_70159_w > 0.3499999940395355) {
                            e.field_70159_w = 0.3499999940395355;
                        }
                        if (e.field_70181_x > 0.3499999940395355) {
                            e.field_70181_x = 0.3499999940395355;
                        }
                        if (e.field_70179_y > 0.3499999940395355) {
                            e.field_70179_y = 0.3499999940395355;
                        }
                        if (e.field_70159_w < -0.3499999940395355) {
                            e.field_70159_w = -0.3499999940395355;
                        }
                        if (e.field_70181_x < -0.3499999940395355) {
                            e.field_70181_x = -0.3499999940395355;
                        }
                        if (e.field_70179_y < -0.3499999940395355) {
                            e.field_70179_y = -0.3499999940395355;
                        }
                    }
                    e.field_70143_R = 0.0f;
                    this.vis -= this.getCost();
                    if (this.vis <= 0) {
                        break;
                    }
                }
            }
            this.drawFX(facing, 0.1);
            if (lifted && !this.field_145850_b.field_72995_K && this.counter % 20 == 0) {
                this.func_70296_d();
            }
        }
    }
    
    private void drawFX(final EnumFacing facing, final double c) {
        if (this.field_145850_b.field_72995_K && this.field_145850_b.field_73012_v.nextFloat() < c) {
            final float x = this.field_174879_c.func_177958_n() + 0.25f + this.field_145850_b.field_73012_v.nextFloat() * 0.5f;
            final float y = this.field_174879_c.func_177956_o() + 0.25f + this.field_145850_b.field_73012_v.nextFloat() * 0.5f;
            final float z = this.field_174879_c.func_177952_p() + 0.25f + this.field_145850_b.field_73012_v.nextFloat() * 0.5f;
            FXDispatcher.INSTANCE.drawLevitatorParticles(x, y, z, facing.func_82601_c() / 50.0, facing.func_96559_d() / 50.0, facing.func_82599_e() / 50.0);
        }
    }
    
    private void drawFXAt(final Entity e) {
        if (this.field_145850_b.field_72995_K && this.field_145850_b.field_73012_v.nextFloat() < 0.1f) {
            final float x = (float)(e.field_70165_t + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * e.field_70130_N);
            final float y = (float)(e.field_70163_u + this.field_145850_b.field_73012_v.nextFloat() * e.field_70131_O);
            final float z = (float)(e.field_70161_v + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * e.field_70130_N);
            FXDispatcher.INSTANCE.drawLevitatorParticles(x, y, z, (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.01, (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.01, (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.01);
        }
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbt) {
        this.range = nbt.func_74771_c("range");
        this.vis = nbt.func_74762_e("vis");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbt) {
        nbt.func_74774_a("range", (byte)this.range);
        nbt.func_74768_a("vis", this.vis);
        return nbt;
    }
    
    public int getCost() {
        return this.ranges[this.range] * 2;
    }
    
    public void increaseRange(final EntityPlayer playerIn) {
        this.rangeActual = 0;
        if (!this.field_145850_b.field_72995_K) {
            ++this.range;
            if (this.range >= this.ranges.length) {
                this.range = 0;
            }
            this.func_70296_d();
            this.syncTile(false);
            playerIn.func_145747_a((ITextComponent)new TextComponentString(String.format(I18n.func_74838_a("tc.levitator"), this.ranges[this.range], this.getCost())));
        }
    }
    
    public RayTraceResult rayTrace(final World world, final Vec3d vec3d, final Vec3d vec3d1, final RayTraceResult fullblock) {
        return fullblock;
    }
    
    public void addTraceableCuboids(final List<IndexedCuboid6> cuboids) {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        cuboids.add(new IndexedCuboid6(0, this.getCuboidByFacing(facing).add(new Vector3(this.func_174877_v().func_177958_n(), this.func_174877_v().func_177956_o(), this.func_174877_v().func_177952_p()))));
    }
    
    public Cuboid6 getCuboidByFacing(final EnumFacing facing) {
        switch (facing) {
            default: {
                return new Cuboid6(0.375, 0.0625, 0.375, 0.625, 0.125, 0.625);
            }
            case DOWN: {
                return new Cuboid6(0.375, 0.875, 0.375, 0.625, 0.9375, 0.625);
            }
            case EAST: {
                return new Cuboid6(0.0625, 0.375, 0.375, 0.125, 0.625, 0.625);
            }
            case WEST: {
                return new Cuboid6(0.875, 0.375, 0.375, 0.9375, 0.625, 0.625);
            }
            case SOUTH: {
                return new Cuboid6(0.375, 0.375, 0.0625, 0.625, 0.625, 0.125);
            }
            case NORTH: {
                return new Cuboid6(0.375, 0.375, 0.875, 0.625, 0.625, 0.9375);
            }
        }
    }
}
