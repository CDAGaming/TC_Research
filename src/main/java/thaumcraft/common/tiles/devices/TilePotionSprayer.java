package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import thaumcraft.common.container.slot.*;
import thaumcraft.common.config.*;
import thaumcraft.api.aspects.*;
import net.minecraft.potion.*;
import thaumcraft.api.*;
import net.minecraft.tileentity.*;

public class TilePotionSprayer extends TileThaumcraftInventory implements IAspectContainer, IEssentiaTransport
{
    public AspectList recipe;
    public AspectList recipeProgress;
    public int charges;
    public int color;
    int counter;
    boolean activated;
    int venting;
    Aspect currentSuction;
    
    public TilePotionSprayer() {
        super(1);
        this.recipe = new AspectList();
        this.recipeProgress = new AspectList();
        this.charges = 0;
        this.color = 0;
        this.counter = 0;
        this.activated = false;
        this.venting = 0;
        this.currentSuction = null;
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        ++this.counter;
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        if (!this.field_145850_b.field_72995_K) {
            if (this.counter % 5 == 0) {
                this.currentSuction = null;
                if (this.func_70301_a(0).func_190926_b() || this.charges >= 8) {
                    return;
                }
                boolean done = true;
                for (final Aspect aspect : this.recipe.getAspectsSortedByName()) {
                    if (this.recipeProgress.getAmount(aspect) < this.recipe.getAmount(aspect)) {
                        this.currentSuction = aspect;
                        done = false;
                        break;
                    }
                }
                if (done) {
                    this.recipeProgress = new AspectList();
                    ++this.charges;
                    this.syncTile(false);
                    this.func_70296_d();
                }
                else if (this.currentSuction != null) {
                    this.fill();
                }
            }
            if (!BlockStateUtils.isEnabled(this.func_145832_p())) {
                if (!this.activated && this.charges > 0) {
                    --this.charges;
                    final List<PotionEffect> effects = (List<PotionEffect>)PotionUtils.func_185189_a(this.func_70301_a(0));
                    if (effects != null && !effects.isEmpty()) {
                        final int area = 1;
                        final BlockPos p = this.field_174879_c.func_177967_a(facing, 2);
                        final List<EntityLivingBase> targets = (List<EntityLivingBase>)this.field_145850_b.func_72872_a((Class)EntityLivingBase.class, new AxisAlignedBB((double)(p.func_177958_n() - area), (double)(p.func_177956_o() - area), (double)(p.func_177952_p() - area), (double)(p.func_177958_n() + 1 + area), (double)(p.func_177956_o() + 1 + area), (double)(p.func_177952_p() + 1 + area)));
                        final boolean lifted = false;
                        if (targets.size() > 0) {
                            for (final EntityLivingBase e : targets) {
                                if (!e.field_70128_L) {
                                    if (!e.func_184603_cC()) {
                                        continue;
                                    }
                                    for (final PotionEffect potioneffect1 : effects) {
                                        final Potion potion = potioneffect1.func_188419_a();
                                        if (potion.func_76403_b()) {
                                            potion.func_180793_a((Entity)null, (Entity)null, e, potioneffect1.func_76458_c(), 1.0);
                                        }
                                        else {
                                            e.func_70690_d(new PotionEffect(potion, potioneffect1.func_76459_b(), potioneffect1.func_76458_c()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.25f, 2.6f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.8f);
                    this.field_145850_b.func_175641_c(this.func_174877_v(), this.func_145838_q(), 0, 0);
                    this.syncTile(false);
                    this.func_70296_d();
                }
                this.activated = true;
            }
            else if (this.activated) {
                this.activated = false;
            }
        }
        else if (this.venting > 0) {
            --this.venting;
            for (int a = 0; a < this.venting / 2; ++a) {
                final float fx = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                final float fz = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                final float fy = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                final float fx2 = (float)(this.field_145850_b.field_73012_v.nextGaussian() * 0.06);
                final float fz2 = (float)(this.field_145850_b.field_73012_v.nextGaussian() * 0.06);
                final float fy2 = (float)(this.field_145850_b.field_73012_v.nextGaussian() * 0.06);
                FXDispatcher.INSTANCE.drawVentParticles2(this.field_174879_c.func_177958_n() + 0.5f + fx + facing.func_82601_c() / 2.0f, this.field_174879_c.func_177956_o() + 0.5f + fy + facing.func_96559_d() / 2.0f, this.field_174879_c.func_177952_p() + 0.5f + fz + facing.func_82599_e() / 2.0f, fx2 + facing.func_82601_c() * 0.25, fy2 + facing.func_96559_d() * 0.25, fz2 + facing.func_82599_e() * 0.25, this.color, 4.0f);
            }
        }
    }
    
    private void drawFX(final EnumFacing facing, final double c) {
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i >= 0) {
            if (this.field_145850_b.field_72995_K) {
                this.venting = 15;
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbt) {
        (this.recipe = new AspectList()).readFromNBT(nbt, "recipe");
        (this.recipeProgress = new AspectList()).readFromNBT(nbt, "progress");
        this.charges = nbt.func_74762_e("charges");
        this.color = nbt.func_74762_e("color");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbt) {
        this.recipe.writeToNBT(nbt, "recipe");
        this.recipeProgress.writeToNBT(nbt, "progress");
        nbt.func_74768_a("charges", this.charges);
        nbt.func_74768_a("color", this.color);
        return nbt;
    }
    
    @Override
    public boolean func_94041_b(final int par1, final ItemStack stack) {
        return stack != null && !stack.func_190926_b() && SlotPotion.isValidPotion(stack);
    }
    
    @Override
    public void func_70299_a(final int par1, final ItemStack stack) {
        super.func_70299_a(par1, stack);
        this.recalcAspects();
    }
    
    @Override
    public ItemStack func_70298_a(final int par1, final int par2) {
        final ItemStack stack = super.func_70298_a(par1, par2);
        this.recalcAspects();
        return stack;
    }
    
    private void recalcAspects() {
        if (this.field_145850_b.field_72995_K) {
            return;
        }
        final ItemStack stack = this.func_70301_a(0);
        this.color = 3355443;
        if (!this.field_145850_b.field_72995_K) {
            if (stack == null || stack.func_190926_b()) {
                this.recipe = new AspectList();
            }
            else {
                this.recipe = ConfigAspects.getPotionAspects(stack);
                this.color = this.getPotionColor(stack);
            }
            this.charges = 0;
            this.recipe = AspectHelper.cullTags(this.recipe, 10);
            this.recipeProgress = new AspectList();
            this.syncTile(false);
            this.func_70296_d();
        }
    }
    
    public int getPotionColor(final ItemStack itemstack) {
        final PotionType potion = PotionUtils.func_185191_c(itemstack);
        if (potion != null) {
            return PotionUtils.func_185183_a(potion);
        }
        return 3355443;
    }
    
    void fill() {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        TileEntity te = null;
        IEssentiaTransport ic = null;
        for (int y = 0; y <= 1; ++y) {
            for (final EnumFacing dir : EnumFacing.field_82609_l) {
                if (dir != facing) {
                    te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.field_174879_c.func_177981_b(y), dir);
                    if (te != null) {
                        ic = (IEssentiaTransport)te;
                        if (ic.getEssentiaAmount(dir.func_176734_d()) > 0 && ic.getSuctionAmount(dir.func_176734_d()) < this.getSuctionAmount(null) && this.getSuctionAmount(null) >= ic.getMinimumSuction()) {
                            final int ess = ic.takeEssentia(this.currentSuction, 1, dir.func_176734_d());
                            if (ess > 0) {
                                this.addToContainer(this.currentSuction, ess);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public int addToContainer(final Aspect tt, final int am) {
        final int ce = this.recipe.getAmount(tt) - this.recipeProgress.getAmount(tt);
        if (ce <= 0) {
            return am;
        }
        final int add = Math.min(ce, am);
        this.recipeProgress.add(tt, add);
        this.syncTile(false);
        this.func_70296_d();
        return am - add;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tt, final int am) {
        return false;
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tt, final int am) {
        return this.recipeProgress.getAmount(tt) >= am;
    }
    
    @Override
    public int containerContains(final Aspect tt) {
        return this.recipeProgress.getAmount(tt);
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face != BlockStateUtils.getFacing(this.func_145832_p());
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return face != BlockStateUtils.getFacing(this.func_145832_p());
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return false;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
        this.currentSuction = aspect;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        return this.currentSuction;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        return (this.currentSuction != null) ? 128 : 0;
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
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return (this.canOutputTo(face) && this.takeFromContainer(aspect, amount)) ? amount : 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return this.canInputFrom(face) ? (amount - this.addToContainer(aspect, amount)) : 0;
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    @Override
    public AspectList getAspects() {
        return this.recipeProgress;
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
        this.recipeProgress = aspects;
    }
}
