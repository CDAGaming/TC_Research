package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import thaumcraft.api.golems.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.items.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.entities.construct.golem.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.tileentity.*;

public class TileGolemBuilder extends TileThaumcraftInventory implements IEssentiaTransport
{
    public long golem;
    public int cost;
    public int maxCost;
    boolean bufferedEssentia;
    int ticks;
    public int press;
    IGolemProperties props;
    ItemStack[] components;
    
    public TileGolemBuilder() {
        super(1);
        this.golem = -1L;
        this.cost = 0;
        this.maxCost = 0;
        this.bufferedEssentia = false;
        this.ticks = 0;
        this.press = 0;
        this.props = null;
        this.components = null;
        this.syncedSlots = new int[] { 0 };
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.syncedSlots = new int[] { 0 };
        super.readSyncNBT(nbttagcompound);
        this.golem = nbttagcompound.func_74763_f("golem");
        this.cost = nbttagcompound.func_74762_e("cost");
        this.maxCost = nbttagcompound.func_74762_e("mcost");
        if (this.golem >= 0L) {
            try {
                this.props = GolemProperties.fromLong(this.golem);
                this.components = this.props.generateComponents();
            }
            catch (Exception e) {
                this.props = null;
                this.components = null;
                this.cost = 0;
                this.golem = -1L;
            }
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74772_a("golem", this.golem);
        nbttagcompound.func_74768_a("cost", this.cost);
        nbttagcompound.func_74768_a("mcost", this.maxCost);
        return super.writeSyncNBT(nbttagcompound);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)(this.field_174879_c.func_177958_n() - 1), (double)this.field_174879_c.func_177956_o(), (double)(this.field_174879_c.func_177952_p() - 1), (double)(this.field_174879_c.func_177958_n() + 2), (double)(this.field_174879_c.func_177956_o() + 2), (double)(this.field_174879_c.func_177952_p() + 2));
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        boolean complete = false;
        if (!this.field_145850_b.field_72995_K) {
            ++this.ticks;
            if (this.ticks % 5 == 0 && !complete && this.cost > 0 && this.golem >= 0L) {
                if (this.bufferedEssentia || this.drawEssentia()) {
                    this.bufferedEssentia = false;
                    --this.cost;
                    this.func_70296_d();
                }
                if (this.cost <= 0) {
                    final ItemStack placer = new ItemStack(ItemsTC.golemPlacer);
                    placer.func_77983_a("props", (NBTBase)new NBTTagLong(this.golem));
                    if (this.func_70301_a(0).func_190926_b() || (this.func_70301_a(0).func_190916_E() < this.func_70301_a(0).func_77976_d() && this.func_70301_a(0).func_77969_a(placer) && ItemStack.func_77970_a(this.func_70301_a(0), placer))) {
                        if (this.func_70301_a(0) == null) {
                            this.func_70299_a(0, placer.func_77946_l());
                        }
                        else {
                            this.func_70301_a(0).func_190917_f(1);
                        }
                        complete = true;
                        this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.wand, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    }
                }
            }
        }
        else {
            if (this.press < 90 && this.cost > 0 && this.golem > 0L) {
                this.press += 6;
                if (this.press >= 60) {
                    this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.66f, 1.0f + this.field_145850_b.field_73012_v.nextFloat() * 0.1f, false);
                    for (int a = 0; a < 16; ++a) {
                        FXDispatcher.INSTANCE.drawVentParticles(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + 0.5, this.field_145850_b.field_73012_v.nextGaussian() * 0.1, 0.0, this.field_145850_b.field_73012_v.nextGaussian() * 0.1, 11184810);
                    }
                }
            }
            if (this.press >= 90 && this.field_145850_b.field_73012_v.nextInt(8) == 0) {
                FXDispatcher.INSTANCE.drawVentParticles(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + 0.5, this.field_145850_b.field_73012_v.nextGaussian() * 0.1, 0.0, this.field_145850_b.field_73012_v.nextGaussian() * 0.1, 11184810);
                this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.1f, 1.0f + this.field_145850_b.field_73012_v.nextFloat() * 0.1f, false);
            }
            if (this.press > 0 && (this.cost <= 0 || this.golem == -1L)) {
                if (this.press >= 90) {
                    for (int a = 0; a < 10; ++a) {
                        FXDispatcher.INSTANCE.drawVentParticles(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + 0.5, this.field_145850_b.field_73012_v.nextGaussian() * 0.1, 0.0, this.field_145850_b.field_73012_v.nextGaussian() * 0.1, 11184810);
                    }
                }
                this.press -= 3;
            }
        }
        if (complete) {
            this.cost = 0;
            this.golem = -1L;
            this.syncTile(false);
            this.func_70296_d();
        }
    }
    
    public boolean startCraft(final long id, final EntityPlayer p) {
        final ItemStack placer = new ItemStack(ItemsTC.golemPlacer);
        placer.func_77983_a("props", (NBTBase)new NBTTagLong(id));
        if (this.func_70301_a(0) == null || (this.func_70301_a(0).func_190916_E() < this.func_70301_a(0).func_77976_d() && this.func_70301_a(0).func_77969_a(placer) && ItemStack.func_77970_a(this.func_70301_a(0), placer))) {
            this.golem = id;
            this.props = GolemProperties.fromLong(this.golem);
            this.components = this.props.generateComponents();
            for (final ItemStack stack : this.components) {
                if (!InventoryUtils.isPlayerCarryingAmount(p, stack, true)) {
                    this.cost = 0;
                    this.props = null;
                    this.components = null;
                    this.golem = -1L;
                    return false;
                }
            }
            this.cost = this.props.getTraits().size() * 2;
            for (final ItemStack stack : this.components) {
                this.cost += stack.func_190916_E();
                InventoryUtils.consumePlayerItem(p, stack, true, true);
            }
            this.maxCost = this.cost;
            this.func_70296_d();
            this.syncTile(false);
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.wand, SoundCategory.BLOCKS, 0.25f, 1.0f);
            return true;
        }
        this.cost = 0;
        this.props = null;
        this.components = null;
        this.golem = -1L;
        return false;
    }
    
    @Override
    public boolean func_94041_b(final int par1, final ItemStack stack2) {
        return stack2 != null && stack2.func_77973_b() instanceof ItemGolemPlacer;
    }
    
    boolean drawEssentia() {
        for (final EnumFacing face : EnumFacing.field_82609_l) {
            final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.func_174877_v(), face);
            if (te != null) {
                final IEssentiaTransport ic = (IEssentiaTransport)te;
                if (!ic.canOutputTo(face.func_176734_d())) {
                    return false;
                }
                if (ic.getSuctionAmount(face.func_176734_d()) < this.getSuctionAmount(face) && ic.takeEssentia(Aspect.MECHANISM, 1, face.func_176734_d()) == 1) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face.func_176736_b() >= 0 || face == EnumFacing.DOWN;
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return this.isConnectable(face);
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
        return Aspect.MECHANISM;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing face) {
        return (this.cost > 0 && this.golem >= 0L) ? 128 : 0;
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
        if (!this.bufferedEssentia && this.cost > 0 && this.golem >= 0L && aspect == Aspect.MECHANISM) {
            this.bufferedEssentia = true;
            return 1;
        }
        return 0;
    }
    
    public boolean canRenderBreaking() {
        return true;
    }
}
