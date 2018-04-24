package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.aura.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;

public class TileMirror extends TileThaumcraft implements IInventory, ITickable
{
    public boolean linked;
    public int linkX;
    public int linkY;
    public int linkZ;
    public int linkDim;
    public int instability;
    int count;
    int inc;
    private ArrayList<ItemStack> outputStacks;
    
    public TileMirror() {
        this.linked = false;
        this.count = 0;
        this.inc = 40;
        this.outputStacks = new ArrayList<ItemStack>();
    }
    
    public void restoreLink() {
        if (this.isDestinationValid()) {
            final World targetWorld = (World)FMLCommonHandler.instance().getMinecraftServerInstance().func_71218_a(this.linkDim);
            if (targetWorld == null) {
                return;
            }
            final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
            if (te != null && te instanceof TileMirror) {
                final TileMirror tm = (TileMirror)te;
                tm.linked = true;
                tm.linkX = this.func_174877_v().func_177958_n();
                tm.linkY = this.func_174877_v().func_177956_o();
                tm.linkZ = this.func_174877_v().func_177952_p();
                tm.linkDim = this.field_145850_b.field_73011_w.getDimension();
                tm.syncTile(false);
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
        if (te != null && te instanceof TileMirror) {
            final TileMirror tm = (TileMirror)te;
            tm.linked = false;
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
        if (te == null || !(te instanceof TileMirror)) {
            this.linked = false;
            this.func_70296_d();
            this.syncTile(false);
            return false;
        }
        final TileMirror tm = (TileMirror)te;
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
        if (te == null || !(te instanceof TileMirror)) {
            return false;
        }
        final TileMirror tm = (TileMirror)te;
        return tm.linked && tm.linkX == this.func_174877_v().func_177958_n() && tm.linkY == this.func_174877_v().func_177956_o() && tm.linkZ == this.func_174877_v().func_177952_p() && tm.linkDim == this.field_145850_b.field_73011_w.getDimension();
    }
    
    public boolean isDestinationValid() {
        final World targetWorld = (World)DimensionManager.getWorld(this.linkDim);
        if (targetWorld == null) {
            return false;
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (te == null || !(te instanceof TileMirror)) {
            this.linked = false;
            this.func_70296_d();
            this.syncTile(false);
            return false;
        }
        final TileMirror tm = (TileMirror)te;
        return !tm.isLinkValid();
    }
    
    public boolean transport(final EntityItem ie) {
        final ItemStack items = ie.func_92059_d();
        if (!this.linked || !this.isLinkValid()) {
            return false;
        }
        final World world = (World)FMLCommonHandler.instance().getMinecraftServerInstance().func_71218_a(this.linkDim);
        final TileEntity target = world.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (target != null && target instanceof TileMirror) {
            ((TileMirror)target).addStack(items);
            this.addInstability(null, items.func_190916_E());
            ie.func_70106_y();
            this.func_70296_d();
            target.func_70296_d();
            world.func_175641_c(this.func_174877_v(), this.field_145854_h, 1, 0);
            return true;
        }
        return false;
    }
    
    public boolean transportDirect(final ItemStack items) {
        if (items == null || items.func_190916_E() <= 0) {
            return false;
        }
        this.addStack(items.func_77946_l());
        this.func_70296_d();
        return true;
    }
    
    public void eject() {
        if (this.outputStacks.size() > 0 && this.count > 20) {
            final int i = this.field_145850_b.field_73012_v.nextInt(this.outputStacks.size());
            if (this.outputStacks.get(i) != null) {
                final ItemStack outItem = this.outputStacks.get(i).func_77946_l();
                outItem.func_190920_e(1);
                if (this.spawnItem(outItem)) {
                    this.outputStacks.get(i).func_190918_g(1);
                    this.addInstability(null, 1);
                    this.field_145850_b.func_175641_c(this.func_174877_v(), this.field_145854_h, 1, 0);
                    if (this.outputStacks.get(i).func_190916_E() <= 0) {
                        this.outputStacks.remove(i);
                    }
                    this.func_70296_d();
                }
            }
        }
    }
    
    public boolean spawnItem(final ItemStack stack) {
        try {
            final EnumFacing face = BlockStateUtils.getFacing(this.func_145832_p());
            final EntityItem ie2 = new EntityItem(this.field_145850_b, this.func_174877_v().func_177958_n() + 0.5, this.func_174877_v().func_177956_o() + 0.25, this.func_174877_v().func_177952_p() + 0.5, stack);
            ie2.field_70159_w = face.func_82601_c() * 0.15f;
            ie2.field_70181_x = face.func_96559_d() * 0.15f;
            ie2.field_70179_y = face.func_82599_e() * 0.15f;
            ie2.field_71088_bW = 20;
            this.field_145850_b.func_72838_d((Entity)ie2);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    protected void addInstability(final World targetWorld, final int amt) {
        this.instability += amt;
        this.func_70296_d();
        if (targetWorld != null) {
            final TileEntity te = targetWorld.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
            if (te != null && te instanceof TileMirror) {
                final TileMirror tileMirror = (TileMirror)te;
                tileMirror.instability += amt;
                if (((TileMirror)te).instability < 0) {
                    ((TileMirror)te).instability = 0;
                }
                te.func_70296_d();
            }
        }
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        super.readSyncNBT(nbttagcompound);
        this.linked = nbttagcompound.func_74767_n("linked");
        this.linkX = nbttagcompound.func_74762_e("linkX");
        this.linkY = nbttagcompound.func_74762_e("linkY");
        this.linkZ = nbttagcompound.func_74762_e("linkZ");
        this.linkDim = nbttagcompound.func_74762_e("linkDim");
        this.instability = nbttagcompound.func_74762_e("instability");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        super.writeSyncNBT(nbttagcompound);
        nbttagcompound.func_74757_a("linked", this.linked);
        nbttagcompound.func_74768_a("linkX", this.linkX);
        nbttagcompound.func_74768_a("linkY", this.linkY);
        nbttagcompound.func_74768_a("linkZ", this.linkZ);
        nbttagcompound.func_74768_a("linkDim", this.linkDim);
        nbttagcompound.func_74768_a("instability", this.instability);
        return nbttagcompound;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_145842_c(final int i, final int j) {
        if (i == 1) {
            if (this.field_145850_b.field_72995_K) {
                final EnumFacing face = BlockStateUtils.getFacing(this.func_145832_p());
                for (int q = 0; q < 2; ++q) {
                    final double xx = this.func_174877_v().func_177958_n() + 0.33 + this.field_145850_b.field_73012_v.nextFloat() * 0.33f - face.func_82601_c() / 2.0;
                    final double yy = this.func_174877_v().func_177956_o() + 0.33 + this.field_145850_b.field_73012_v.nextFloat() * 0.33f - face.func_96559_d() / 2.0;
                    final double zz = this.func_174877_v().func_177952_p() + 0.33 + this.field_145850_b.field_73012_v.nextFloat() * 0.33f - face.func_82599_e() / 2.0;
                    final ParticleSpell.AmbientMobFactory amf = new ParticleSpell.AmbientMobFactory();
                    final Particle var21 = amf.func_178902_a(0, this.field_145850_b, xx, yy, zz, face.func_82601_c() * 0.05, face.func_96559_d() * 0.05, face.func_82599_e() * 0.05, new int[0]);
                    Minecraft.func_71410_x().field_71452_i.func_78873_a(var21);
                }
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            this.eject();
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
        if (this.instability > 128) {
            AuraHelper.polluteAura(this.field_145850_b, this.field_174879_c, 1.0f, true);
            this.instability -= 128;
            this.func_70296_d();
        }
        if (this.instability > 0 && this.count % 100 == 0) {
            --this.instability;
        }
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbtCompound) {
        super.func_145839_a(nbtCompound);
        final NBTTagList nbttaglist = nbtCompound.func_150295_c("Items", 10);
        this.outputStacks = new ArrayList<ItemStack>();
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
            final byte b0 = nbttagcompound1.func_74771_c("Slot");
            this.outputStacks.add(new ItemStack(nbttagcompound1));
        }
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbtCompound) {
        super.func_189515_b(nbtCompound);
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.outputStacks.size(); ++i) {
            if (this.outputStacks.get(i) != null && this.outputStacks.get(i).func_190916_E() > 0) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.outputStacks.get(i).func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
            }
        }
        nbtCompound.func_74782_a("Items", (NBTBase)nbttaglist);
        return nbtCompound;
    }
    
    public int func_70302_i_() {
        return 1;
    }
    
    public ItemStack func_70301_a(final int par1) {
        return null;
    }
    
    public ItemStack func_70298_a(final int par1, final int par2) {
        return null;
    }
    
    public ItemStack func_70304_b(final int par1) {
        return null;
    }
    
    public void addStack(final ItemStack stack) {
        this.outputStacks.add(stack);
        this.func_70296_d();
    }
    
    public void func_70299_a(final int par1, final ItemStack stack2) {
        final World world = (World)FMLCommonHandler.instance().getMinecraftServerInstance().func_71218_a(this.linkDim);
        final TileEntity target = world.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        if (target != null && target instanceof TileMirror) {
            ((TileMirror)target).addStack(stack2.func_77946_l());
            this.addInstability(null, stack2.func_190916_E());
            world.func_175641_c(this.func_174877_v(), this.field_145854_h, 1, 0);
        }
        else {
            this.spawnItem(stack2.func_77946_l());
        }
    }
    
    public int func_70297_j_() {
        return 64;
    }
    
    public boolean func_70300_a(final EntityPlayer var1) {
        return false;
    }
    
    public boolean func_94041_b(final int var1, final ItemStack var2) {
        final World world = (World)FMLCommonHandler.instance().getMinecraftServerInstance().func_71218_a(this.linkDim);
        final TileEntity target = world.func_175625_s(new BlockPos(this.linkX, this.linkY, this.linkZ));
        return target != null && target instanceof TileMirror;
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
    }
    
    public boolean func_191420_l() {
        return false;
    }
}
