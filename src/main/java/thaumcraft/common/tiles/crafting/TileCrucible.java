package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fluids.*;
import net.minecraft.block.material.*;
import thaumcraft.api.blocks.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import thaumcraft.client.fx.*;
import java.awt.*;
import net.minecraft.item.*;
import thaumcraft.common.entities.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.crafting.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import thaumcraft.common.lib.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.crafting.*;
import thaumcraft.api.aspects.*;
import net.minecraft.entity.item.*;
import thaumcraft.api.aura.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.common.capabilities.*;
import javax.annotation.*;
import net.minecraftforge.fluids.capability.*;

public class TileCrucible extends TileThaumcraft implements ITickable, IFluidHandler, IAspectContainer
{
    public short heat;
    public AspectList aspects;
    public final int maxTags = 1000;
    int bellows;
    private int delay;
    private long counter;
    int prevcolor;
    int prevx;
    int prevy;
    public FluidTank tank;
    
    public TileCrucible() {
        this.aspects = new AspectList();
        this.bellows = -1;
        this.delay = 0;
        this.counter = -100L;
        this.prevcolor = 0;
        this.prevx = 0;
        this.prevy = 0;
        this.tank = new FluidTank(FluidRegistry.WATER, 0, 1000);
        this.heat = 0;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.heat = nbttagcompound.func_74765_d("Heat");
        this.tank.readFromNBT(nbttagcompound);
        if (nbttagcompound.func_74764_b("Empty")) {
            this.tank.setFluid((FluidStack)null);
        }
        this.aspects.readFromNBT(nbttagcompound);
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74777_a("Heat", this.heat);
        this.tank.writeToNBT(nbttagcompound);
        this.aspects.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }
    
    public void func_73660_a() {
        ++this.counter;
        final int prevheat = this.heat;
        if (!this.field_145850_b.field_72995_K) {
            if (this.tank.getFluidAmount() > 0) {
                final IBlockState block = this.field_145850_b.func_180495_p(this.func_174877_v().func_177977_b());
                if (block.func_185904_a() == Material.field_151587_i || block.func_185904_a() == Material.field_151581_o || BlocksTC.nitor.containsValue(block.func_177230_c()) || block.func_177230_c() == Blocks.field_189877_df) {
                    if (this.heat < 200) {
                        ++this.heat;
                        if (prevheat < 151 && this.heat >= 151) {
                            this.func_70296_d();
                            this.syncTile(false);
                        }
                    }
                }
                else if (this.heat > 0) {
                    --this.heat;
                    if (this.heat == 149) {
                        this.func_70296_d();
                        this.syncTile(false);
                    }
                }
            }
            else if (this.heat > 0) {
                --this.heat;
            }
            if (this.aspects.visSize() > 1000) {
                this.spillRandom();
            }
            if (this.counter >= 100L) {
                this.spillRandom();
                this.counter = 0L;
            }
        }
        else if (this.tank.getFluidAmount() > 0) {
            this.drawEffects();
        }
        if (this.field_145850_b.field_72995_K && prevheat < 151 && this.heat >= 151) {
            ++this.heat;
        }
    }
    
    private void drawEffects() {
        if (this.heat > 150) {
            FXDispatcher.INSTANCE.crucibleFroth(this.field_174879_c.func_177958_n() + 0.2f + this.field_145850_b.field_73012_v.nextFloat() * 0.6f, this.field_174879_c.func_177956_o() + this.getFluidHeight(), this.field_174879_c.func_177952_p() + 0.2f + this.field_145850_b.field_73012_v.nextFloat() * 0.6f);
            if (this.aspects.visSize() > 1000) {
                for (int a = 0; a < 2; ++a) {
                    FXDispatcher.INSTANCE.crucibleFrothDown(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + this.field_145850_b.field_73012_v.nextFloat());
                    FXDispatcher.INSTANCE.crucibleFrothDown(this.field_174879_c.func_177958_n() + 1, this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + this.field_145850_b.field_73012_v.nextFloat());
                    FXDispatcher.INSTANCE.crucibleFrothDown(this.field_174879_c.func_177958_n() + this.field_145850_b.field_73012_v.nextFloat(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p());
                    FXDispatcher.INSTANCE.crucibleFrothDown(this.field_174879_c.func_177958_n() + this.field_145850_b.field_73012_v.nextFloat(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + 1);
                }
            }
        }
        if (this.field_145850_b.field_73012_v.nextInt(6) == 0 && this.aspects.size() > 0) {
            final int color = this.aspects.getAspects()[this.field_145850_b.field_73012_v.nextInt(this.aspects.size())].getColor() - 16777216;
            final int x = 5 + this.field_145850_b.field_73012_v.nextInt(22);
            final int y = 5 + this.field_145850_b.field_73012_v.nextInt(22);
            this.delay = this.field_145850_b.field_73012_v.nextInt(10);
            this.prevcolor = color;
            this.prevx = x;
            this.prevy = y;
            final Color c = new Color(color);
            final float r = c.getRed() / 255.0f;
            final float g = c.getGreen() / 255.0f;
            final float b = c.getBlue() / 255.0f;
            FXDispatcher.INSTANCE.crucibleBubble(this.field_174879_c.func_177958_n() + x / 32.0f + 0.015625f, this.field_174879_c.func_177956_o() + 0.05f + this.getFluidHeight(), this.field_174879_c.func_177952_p() + y / 32.0f + 0.015625f, r, g, b);
        }
    }
    
    public void ejectItem(final ItemStack items) {
        boolean first = true;
        do {
            final ItemStack spitout = items.func_77946_l();
            if (spitout.func_190916_E() > spitout.func_77976_d()) {
                spitout.func_190920_e(spitout.func_77976_d());
            }
            items.func_190918_g(spitout.func_190916_E());
            final EntitySpecialItem entityitem = new EntitySpecialItem(this.field_145850_b, this.field_174879_c.func_177958_n() + 0.5f, this.field_174879_c.func_177956_o() + 0.71f, this.field_174879_c.func_177952_p() + 0.5f, spitout);
            entityitem.field_70181_x = 0.07500000298023224;
            entityitem.field_70159_w = (first ? 0.0 : ((this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.01f));
            entityitem.field_70179_y = (first ? 0.0 : ((this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.01f));
            this.field_145850_b.func_72838_d((Entity)entityitem);
            first = false;
        } while (items.func_190916_E() > 0);
    }
    
    public ItemStack attemptSmelt(final ItemStack item, final String username) {
        boolean bubble = false;
        boolean craftDone = false;
        int stacksize = item.func_190916_E();
        final EntityPlayer player = this.field_145850_b.func_72924_a(username);
        for (int a = 0; a < stacksize; ++a) {
            final CrucibleRecipe rc = ThaumcraftCraftingManager.findMatchingCrucibleRecipe(player, this.aspects, item);
            if (rc != null && this.tank.getFluidAmount() > 0) {
                final ItemStack out = rc.getRecipeOutput().func_77946_l();
                if (player != null) {
                    FMLCommonHandler.instance().firePlayerCraftingEvent(player, out, (IInventory)new InventoryFake(new ItemStack[] { item }));
                }
                this.aspects = rc.removeMatching(this.aspects);
                this.tank.drain(50, true);
                this.ejectItem(out);
                craftDone = true;
                --stacksize;
                this.counter = -250L;
            }
            else {
                final AspectList ot = ThaumcraftCraftingManager.getObjectTags(item);
                if (ot != null) {
                    if (ot.size() != 0) {
                        for (final Aspect tag : ot.getAspects()) {
                            this.aspects.add(tag, ot.getAmount(tag));
                        }
                        bubble = true;
                        --stacksize;
                        this.counter = -150L;
                    }
                }
            }
        }
        if (bubble) {
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.bubble, SoundCategory.BLOCKS, 0.2f, 1.0f + this.field_145850_b.field_73012_v.nextFloat() * 0.4f);
            this.syncTile(false);
            this.field_145850_b.func_175641_c(this.field_174879_c, BlocksTC.crucible, 2, 1);
        }
        if (craftDone) {
            this.syncTile(false);
            this.field_145850_b.func_175641_c(this.field_174879_c, BlocksTC.crucible, 99, 0);
        }
        this.func_70296_d();
        if (stacksize <= 0) {
            return null;
        }
        item.func_190920_e(stacksize);
        return item;
    }
    
    public void attemptSmelt(final EntityItem entity) {
        final ItemStack item = entity.func_92059_d();
        final NBTTagCompound itemData = entity.getEntityData();
        final String username = itemData.func_74779_i("thrower");
        final ItemStack res = this.attemptSmelt(item, username);
        if (res == null || res.func_190916_E() <= 0) {
            entity.func_70106_y();
        }
        else {
            item.func_190920_e(res.func_190916_E());
            entity.func_92058_a(item);
        }
    }
    
    public float getFluidHeight() {
        final float base = 0.3f + 0.5f * (this.tank.getFluidAmount() / this.tank.getCapacity());
        float out = base + this.aspects.visSize() / 1000.0f * (1.0f - base);
        if (out > 1.0f) {
            out = 1.001f;
        }
        if (out == 1.0f) {
            out = 0.9999f;
        }
        return out;
    }
    
    public void spillRandom() {
        if (this.aspects.size() > 0) {
            final Aspect tag = this.aspects.getAspects()[this.field_145850_b.field_73012_v.nextInt(this.aspects.getAspects().length)];
            this.aspects.remove(tag, 1);
            if (this.field_145850_b.field_73012_v.nextBoolean()) {
                AuraHelper.polluteAura(this.field_145850_b, this.func_174877_v(), 1.0f, true);
            }
        }
        this.func_70296_d();
        this.syncTile(false);
    }
    
    public void spillRemnants() {
        if (this.tank.getFluidAmount() > 0 || this.aspects.visSize() > 0) {
            this.tank.setFluid((FluidStack)null);
            if (this.aspects.visSize() > 1) {
                AuraHelper.polluteAura(this.field_145850_b, this.func_174877_v(), this.aspects.visSize() * 0.75f, true);
            }
            this.aspects = new AspectList();
            this.field_145850_b.func_175641_c(this.field_174879_c, BlocksTC.crucible, 2, 5);
            this.func_70296_d();
            this.syncTile(false);
        }
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 99) {
            if (this.field_145850_b.field_72995_K) {
                FXDispatcher.INSTANCE.drawBamf(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 1.25f, this.field_174879_c.func_177952_p() + 0.5, true, true, EnumFacing.UP);
                this.field_145850_b.func_184134_a((double)(this.field_174879_c.func_177958_n() + 0.5f), (double)(this.field_174879_c.func_177956_o() + 0.5f), (double)(this.field_174879_c.func_177952_p() + 0.5f), SoundsTC.spill, SoundCategory.BLOCKS, 0.2f, 1.0f, false);
            }
            return true;
        }
        if (i == 1) {
            if (this.field_145850_b.field_72995_K) {
                FXDispatcher.INSTANCE.drawBamf(this.field_174879_c.func_177984_a(), true, true, EnumFacing.UP);
            }
            return true;
        }
        if (i == 2) {
            this.field_145850_b.func_184134_a((double)(this.field_174879_c.func_177958_n() + 0.5f), (double)(this.field_174879_c.func_177956_o() + 0.5f), (double)(this.field_174879_c.func_177952_p() + 0.5f), SoundsTC.spill, SoundCategory.BLOCKS, 0.2f, 1.0f, false);
            if (this.field_145850_b.field_72995_K) {
                for (int q = 0; q < 10; ++q) {
                    FXDispatcher.INSTANCE.crucibleBoil(this.field_174879_c, this, j);
                }
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1));
    }
    
    public AspectList getAspects() {
        return this.aspects;
    }
    
    public void setAspects(final AspectList aspects) {
    }
    
    public int addToContainer(final Aspect tag, final int amount) {
        return 0;
    }
    
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        return false;
    }
    
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        return false;
    }
    
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    public int containerContains(final Aspect tag) {
        return 0;
    }
    
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability((Capability)capability, facing);
    }
    
    @Nullable
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)this.tank;
        }
        return (T)super.getCapability((Capability)capability, facing);
    }
    
    public IFluidTankProperties[] getTankProperties() {
        return this.tank.getTankProperties();
    }
    
    public int fill(final FluidStack resource, final boolean doFill) {
        this.func_70296_d();
        this.syncTile(false);
        return this.tank.fill(resource, doFill);
    }
    
    public FluidStack drain(final FluidStack resource, final boolean doDrain) {
        final FluidStack fs = this.tank.drain(resource, doDrain);
        this.func_70296_d();
        this.syncTile(false);
        return fs;
    }
    
    public FluidStack drain(final int maxDrain, final boolean doDrain) {
        final FluidStack fs = this.tank.drain(maxDrain, doDrain);
        this.func_70296_d();
        this.syncTile(false);
        return fs;
    }
}
