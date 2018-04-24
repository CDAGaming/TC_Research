package thaumcraft.common.tiles.essentia;

import thaumcraft.common.lib.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import thaumcraft.codechicken.lib.raytracer.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import thaumcraft.api.aspects.*;

public class TileTubeValve extends TileTube
{
    public boolean allowFlow;
    boolean wasPoweredLastTick;
    public float rotation;
    
    public TileTubeValve() {
        this.allowFlow = true;
        this.wasPoweredLastTick = false;
        this.rotation = 0.0f;
    }
    
    @Override
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K && this.count % 5 == 0) {
            final boolean gettingPower = this.gettingPower();
            if (this.wasPoweredLastTick && !gettingPower && !this.allowFlow) {
                this.allowFlow = true;
                this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.squeek, SoundCategory.BLOCKS, 0.7f, 0.9f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f);
                this.syncTile(true);
                this.func_70296_d();
            }
            if (!this.wasPoweredLastTick && gettingPower && this.allowFlow) {
                this.allowFlow = false;
                this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.squeek, SoundCategory.BLOCKS, 0.7f, 0.9f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f);
                this.syncTile(true);
                this.func_70296_d();
            }
            this.wasPoweredLastTick = gettingPower;
        }
        if (this.field_145850_b.field_72995_K) {
            if (!this.allowFlow && this.rotation < 360.0f) {
                this.rotation += 20.0f;
            }
            else if (this.allowFlow && this.rotation > 0.0f) {
                this.rotation -= 20.0f;
            }
        }
        super.func_73660_a();
    }
    
    @Override
    public boolean onCasterRightClick(final World world, final ItemStack wandstack, final EntityPlayer player, final BlockPos bp, final EnumFacing side, final EnumHand hand) {
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, this.field_174879_c);
        if (hit == null) {
            return false;
        }
        if (hit.subHit >= 0 && hit.subHit < 6) {
            player.field_70170_p.func_184134_a((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), SoundsTC.tool, SoundCategory.BLOCKS, 0.5f, 0.9f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f, false);
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
            player.field_70170_p.func_184134_a((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), SoundsTC.tool, SoundCategory.BLOCKS, 0.5f, 0.9f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f, false);
            player.func_184609_a(hand);
            int a = this.facing.ordinal();
            this.func_70296_d();
            while (++a < 20) {
                if (!this.canConnectSide(EnumFacing.field_82609_l[a % 6])) {
                    a %= 6;
                    this.facing = EnumFacing.field_82609_l[a];
                    this.syncTile(true);
                    this.func_70296_d();
                    break;
                }
            }
        }
        return !world.field_72995_K;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        super.readSyncNBT(nbttagcompound);
        this.allowFlow = nbttagcompound.func_74767_n("flow");
        this.wasPoweredLastTick = nbttagcompound.func_74767_n("hadpower");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = super.writeSyncNBT(nbttagcompound);
        nbttagcompound.func_74757_a("flow", this.allowFlow);
        nbttagcompound.func_74757_a("hadpower", this.wasPoweredLastTick);
        return nbttagcompound;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face != this.facing && super.isConnectable(face);
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
        if (this.allowFlow) {
            super.setSuction(aspect, amount);
        }
    }
    
    @Override
    public boolean gettingPower() {
        return this.field_145850_b.func_175687_A(this.field_174879_c) > 0;
    }
}
