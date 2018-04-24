package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.common.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.events.*;
import thaumcraft.api.aura.*;

public class TileMirrorEssentia extends TileThaumcraft implements IAspectSource, ITickable
{
    public boolean linked;
    public int linkX;
    public int linkY;
    public int linkZ;
    public int linkDim;
    public EnumFacing linkedFacing;
    public int instability;
    int count;
    int inc;
    
    public TileMirrorEssentia() {
        this.linked = false;
        this.linkedFacing = EnumFacing.DOWN;
        this.count = 0;
        this.inc = 40;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.linked = nbttagcompound.func_74767_n("linked");
        this.linkX = nbttagcompound.func_74762_e("linkX");
        this.linkY = nbttagcompound.func_74762_e("linkY");
        this.linkZ = nbttagcompound.func_74762_e("linkZ");
        this.linkDim = nbttagcompound.func_74762_e("linkDim");
        this.instability = nbttagcompound.func_74762_e("instability");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74757_a("linked", this.linked);
        nbttagcompound.func_74768_a("linkX", this.linkX);
        nbttagcompound.func_74768_a("linkY", this.linkY);
        nbttagcompound.func_74768_a("linkZ", this.linkZ);
        nbttagcompound.func_74768_a("linkDim", this.linkDim);
        nbttagcompound.func_74768_a("instability", this.instability);
        return nbttagcompound;
    }
    
    protected void addInstability(final World targetWorld, final int amt) {
        this.instability += amt;
        this.func_70296_d();
        if (targetWorld != null) {
            final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
            if (te != null && te instanceof TileMirrorEssentia) {
                final TileMirrorEssentia tileMirrorEssentia = (TileMirrorEssentia)te;
                tileMirrorEssentia.instability += amt;
                if (((TileMirrorEssentia)te).instability < 0) {
                    ((TileMirrorEssentia)te).instability = 0;
                }
                te.func_70296_d();
            }
        }
    }
    
    public void restoreLink() {
        if (this.isDestinationValid()) {
            final World targetWorld = (World)FMLCommonHandler.instance().getMinecraftServerInstance().func_71218_a(this.linkDim);
            if (targetWorld == null) {
                return;
            }
            final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
            if (te != null && te instanceof TileMirrorEssentia) {
                final TileMirrorEssentia tm = (TileMirrorEssentia)te;
                tm.linked = true;
                tm.linkX = this.func_174877_v().func_177958_n();
                tm.linkY = this.func_174877_v().func_177956_o();
                tm.linkZ = this.func_174877_v().func_177952_p();
                tm.linkDim = this.field_145850_b.field_73011_w.getDimension();
                tm.syncTile(false);
                this.linkedFacing = BlockStateUtils.getFacing(targetWorld.func_180495_p(new BlockPos(this.linkX, this.linkY, this.linkZ)));
                this.linked = true;
                this.func_70296_d();
                tm.func_70296_d();
                this.syncTile(false);
            }
        }
    }
    
    public void invalidateLink() {
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (targetWorld == null) {
            return;
        }
        if (!Utils.isChunkLoaded(targetWorld, this.linkX, this.linkZ)) {
            return;
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te != null && te instanceof TileMirrorEssentia) {
            final TileMirrorEssentia tm = (TileMirrorEssentia)te;
            tm.linked = false;
            tm.linkedFacing = EnumFacing.DOWN;
            this.func_70296_d();
            tm.func_70296_d();
            tm.syncTile(false);
        }
    }
    
    public boolean isLinkValid() {
        if (!this.linked) {
            return false;
        }
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (targetWorld == null) {
            return false;
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te == null || !(te instanceof TileMirrorEssentia)) {
            this.linked = false;
            this.func_70296_d();
            this.syncTile(false);
            return false;
        }
        final TileMirrorEssentia tm = (TileMirrorEssentia)te;
        if (!tm.linked) {
            this.linked = false;
            this.func_70296_d();
            this.syncTile(false);
            return false;
        }
        if (tm.linkX != this.func_174877_v().func_177958_n() || tm.linkY != this.func_174877_v().func_177956_o() || tm.linkZ != this.func_174877_v().func_177952_p() || tm.linkDim != this.field_145850_b.field_73011_w.getDimension()) {
            this.linked = false;
            this.func_70296_d();
            this.syncTile(false);
            return false;
        }
        return true;
    }
    
    public boolean isLinkValidSimple() {
        if (!this.linked) {
            return false;
        }
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (targetWorld == null) {
            return false;
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te == null || !(te instanceof TileMirrorEssentia)) {
            return false;
        }
        final TileMirrorEssentia tm = (TileMirrorEssentia)te;
        return tm.linked && tm.linkX == this.func_174877_v().func_177958_n() && tm.linkY == this.func_174877_v().func_177956_o() && tm.linkZ == this.func_174877_v().func_177952_p() && tm.linkDim == this.field_145850_b.field_73011_w.getDimension();
    }
    
    public boolean isDestinationValid() {
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (targetWorld == null) {
            return false;
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te == null || !(te instanceof TileMirrorEssentia)) {
            this.linked = false;
            this.func_70296_d();
            this.syncTile(false);
            return false;
        }
        final TileMirrorEssentia tm = (TileMirrorEssentia)te;
        return !tm.isLinkValid();
    }
    
    public AspectList getAspects() {
        return null;
    }
    
    public void setAspects(final AspectList aspects) {
    }
    
    public boolean doesContainerAccept(final Aspect tag) {
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (this.linkedFacing == EnumFacing.DOWN && targetWorld != null) {
            this.linkedFacing = BlockStateUtils.getFacing(targetWorld.func_180495_p(new BlockPos(this.linkX, this.linkY, this.linkZ)));
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        return te == null || !(te instanceof TileMirrorEssentia) || EssentiaHandler.canAcceptEssentia(te, tag, this.linkedFacing, 8, true);
    }
    
    public int addToContainer(final Aspect tag, final int amount) {
        if (!this.isLinkValid() || amount > 1) {
            return amount;
        }
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (this.linkedFacing == EnumFacing.DOWN && targetWorld != null) {
            this.linkedFacing = BlockStateUtils.getFacing(targetWorld.func_180495_p(new BlockPos(this.linkX, this.linkY, this.linkZ)));
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te != null && te instanceof TileMirrorEssentia) {
            final boolean b = EssentiaHandler.addEssentia(te, tag, this.linkedFacing, 8, true, 5);
            if (b) {
                this.addInstability(null, amount);
            }
            return b ? 0 : 1;
        }
        return amount;
    }
    
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        if (!this.isLinkValid() || amount > 1) {
            return false;
        }
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (this.linkedFacing == EnumFacing.DOWN && targetWorld != null) {
            this.linkedFacing = BlockStateUtils.getFacing(targetWorld.func_180495_p(new BlockPos(this.linkX, this.linkY, this.linkZ)));
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te != null && te instanceof TileMirrorEssentia) {
            final boolean b = EssentiaHandler.drainEssentia(te, tag, this.linkedFacing, 8, true, 5);
            if (b) {
                this.addInstability(null, amount);
            }
            return b;
        }
        return false;
    }
    
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        if (!this.isLinkValid() || amount > 1) {
            return false;
        }
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (this.linkedFacing == EnumFacing.DOWN && targetWorld != null) {
            this.linkedFacing = BlockStateUtils.getFacing(targetWorld.func_180495_p(new BlockPos(this.linkX, this.linkY, this.linkZ)));
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        return te != null && te instanceof TileMirrorEssentia && EssentiaHandler.findEssentia(te, tag, this.linkedFacing, 8, true);
    }
    
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    public int containerContains(final Aspect tag) {
        return 0;
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            this.checkInstability();
            if (this.count++ % this.inc == 0) {
                if (!this.isLinkValidSimple()) {
                    if (this.inc < 600) {
                        this.inc += 20;
                    }
                    this.restoreLink();
                }
                else {
                    this.inc = 40;
                }
            }
        }
    }
    
    public void checkInstability() {
        if (this.instability > 64) {
            AuraHelper.polluteAura(this.field_145850_b, this.field_174879_c, 1.0f, true);
            this.instability -= 64;
            this.func_70296_d();
        }
        if (this.instability > 0 && this.count % 100 == 0) {
            --this.instability;
        }
    }
    
    @Override
    public boolean isBlocked() {
        return false;
    }
}
