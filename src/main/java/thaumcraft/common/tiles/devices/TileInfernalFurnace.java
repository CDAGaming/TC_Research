package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.item.crafting.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.aura.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import java.util.*;
import thaumcraft.api.internal.*;
import thaumcraft.api.*;
import net.minecraftforge.oredict.*;
import thaumcraft.client.fx.*;

public class TileInfernalFurnace extends TileThaumcraftInventory
{
    public int furnaceCookTime;
    public int furnaceMaxCookTime;
    public int speedyTime;
    public int facingX;
    public int facingZ;
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.func_174877_v().func_177958_n() - 1.3, this.func_174877_v().func_177956_o() - 1.3, this.func_174877_v().func_177952_p() - 1.3, this.func_174877_v().func_177958_n() + 2.3, this.func_174877_v().func_177956_o() + 2.3, this.func_174877_v().func_177952_p() + 2.3);
    }
    
    public TileInfernalFurnace() {
        super(32);
        this.facingX = -5;
        this.facingZ = -5;
        this.furnaceCookTime = 0;
        this.furnaceMaxCookTime = 0;
        this.speedyTime = 0;
    }
    
    @Override
    public int[] func_180463_a(final EnumFacing par1) {
        return (par1 == EnumFacing.UP) ? super.func_180463_a(par1) : new int[0];
    }
    
    @Override
    public boolean func_180461_b(final int par1, final ItemStack stack2, final EnumFacing par3) {
        return false;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.furnaceCookTime = nbttagcompound.func_74765_d("CookTime");
        this.speedyTime = nbttagcompound.func_74765_d("SpeedyTime");
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbttagcompound) {
        super.func_189515_b(nbttagcompound);
        nbttagcompound.func_74777_a("CookTime", (short)this.furnaceCookTime);
        nbttagcompound.func_74777_a("SpeedyTime", (short)this.speedyTime);
        return nbttagcompound;
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        if (this.facingX == -5) {
            this.setFacing();
        }
        if (!this.field_145850_b.field_72995_K) {
            boolean cookedflag = false;
            if (this.furnaceCookTime > 0) {
                --this.furnaceCookTime;
                cookedflag = true;
            }
            if (this.furnaceMaxCookTime == 0) {
                this.furnaceMaxCookTime = this.calcCookTime();
            }
            if (this.furnaceCookTime > this.furnaceMaxCookTime) {
                this.furnaceCookTime = this.furnaceMaxCookTime;
            }
            if (this.furnaceCookTime == 0 && cookedflag) {
                for (int a = 0; a < this.func_70302_i_(); ++a) {
                    if (this.func_70301_a(a) != null) {
                        final ItemStack itemstack = FurnaceRecipes.func_77602_a().func_151395_a(this.func_70301_a(a));
                        if (itemstack != null && !itemstack.func_190926_b()) {
                            if (this.speedyTime > 0) {
                                --this.speedyTime;
                            }
                            this.ejectItem(itemstack.func_77946_l(), this.func_70301_a(a));
                            this.field_145850_b.func_175641_c(this.func_174877_v(), BlocksTC.infernalFurnace, 3, 0);
                            if (this.func_145831_w().field_73012_v.nextInt(20) == 0) {
                                AuraHelper.polluteAura(this.func_145831_w(), this.func_174877_v().func_177972_a(this.getFacing().func_176734_d()), 1.0f, true);
                            }
                            this.func_70301_a(a).func_190918_g(1);
                            if (this.func_70301_a(a).func_190916_E() <= 0) {
                                this.func_70299_a(a, ItemStack.field_190927_a);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (this.speedyTime <= 0) {
                this.speedyTime = (int)AuraHelper.drainVis(this.func_145831_w(), this.func_174877_v(), 20.0f, false);
            }
            if (this.furnaceCookTime == 0 && !cookedflag) {
                for (int a = 0; a < this.func_70302_i_(); ++a) {
                    if (this.func_70301_a(a) != null && this.canSmelt(a)) {
                        this.furnaceMaxCookTime = this.calcCookTime();
                        this.furnaceCookTime = this.furnaceMaxCookTime;
                        break;
                    }
                    if (this.func_70301_a(a) != null && !this.canSmelt(a)) {
                        this.destroyItem(a);
                        this.func_70296_d();
                        break;
                    }
                }
            }
        }
    }
    
    private int getBellows() {
        int bellows = 0;
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            if (dir != EnumFacing.UP) {
                final BlockPos p2 = this.field_174879_c.func_177967_a(dir, 2);
                final TileEntity tile = this.field_145850_b.func_175625_s(p2);
                if (tile != null && tile instanceof TileBellows && BlockStateUtils.getFacing(this.field_145850_b.func_180495_p(p2)) == dir.func_176734_d() && this.field_145850_b.func_175687_A(p2) == 0) {
                    ++bellows;
                }
            }
        }
        return Math.min(3, bellows);
    }
    
    private int calcCookTime() {
        return ((this.speedyTime > 0) ? 80 : 140) - 20 * this.getBellows();
    }
    
    public boolean addItemsToInventory(final ItemStack items) {
        for (int a = 0; a < this.func_70302_i_(); ++a) {
            if (!this.func_70301_a(a).func_190926_b() && this.func_70301_a(a).func_77969_a(items) && this.func_70301_a(a).func_190916_E() + items.func_190916_E() <= items.func_77976_d()) {
                this.func_70301_a(a).func_190917_f(items.func_190916_E());
                if (!this.canSmelt(a)) {
                    this.destroyItem(a);
                }
                this.func_70296_d();
                return true;
            }
            if (this.func_70301_a(a).func_190926_b()) {
                this.func_70299_a(a, items);
                if (!this.canSmelt(a)) {
                    this.destroyItem(a);
                }
                this.func_70296_d();
                return true;
            }
        }
        return false;
    }
    
    private void destroyItem(final int slot) {
        this.func_70299_a(slot, ItemStack.field_190927_a);
        this.field_145850_b.func_184134_a((double)(this.field_174879_c.func_177958_n() + 0.5f), (double)(this.field_174879_c.func_177956_o() + 0.5f), (double)(this.field_174879_c.func_177952_p() + 0.5f), SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.3f, 2.6f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.8f, false);
        final double var21 = this.field_174879_c.func_177958_n() + this.field_145850_b.field_73012_v.nextFloat();
        final double var22 = this.field_174879_c.func_177956_o() + 1;
        final double var23 = this.field_174879_c.func_177952_p() + this.field_145850_b.field_73012_v.nextFloat();
        this.field_145850_b.func_175688_a(EnumParticleTypes.LAVA, var21, var22, var23, 0.0, 0.0, 0.0, new int[0]);
    }
    
    public void ejectItem(final ItemStack items, final ItemStack furnaceItemStack) {
        if (items == null || items.func_190926_b()) {
            return;
        }
        final ArrayList<ItemStack> ejecti = new ArrayList<ItemStack>();
        ejecti.add(items.func_77946_l());
        final int bellows = this.getBellows() + 1;
        float lx = 0.5f;
        lx += this.facingX * 1.2f;
        float lz = 0.5f;
        lz += this.facingZ * 1.2f;
        float mx = 0.0f;
        float mz = 0.0f;
        for (int a = 0; a < bellows; ++a) {
            final ItemStack[] boni = this.getSmeltingBonus(furnaceItemStack);
            if (boni != null) {
                for (final ItemStack bonus : boni) {
                    if (!bonus.func_190926_b() && bonus.func_190916_E() > 0) {
                        ejecti.add(bonus);
                    }
                }
            }
        }
        for (final ItemStack outItem : ejecti) {
            if (outItem.func_190926_b()) {
                continue;
            }
            final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d();
            InventoryUtils.ejectStackAt(this.func_145831_w(), this.func_174877_v(), facing, outItem);
        }
        int cnt = items.func_190916_E();
        final float xpf = FurnaceRecipes.func_77602_a().func_151398_b(items);
        if (xpf == 0.0f) {
            cnt = 0;
        }
        else if (xpf < 1.0f) {
            int var4 = MathHelper.func_76141_d(cnt * xpf);
            if (var4 < MathHelper.func_76123_f(cnt * xpf) && (float)Math.random() < cnt * xpf - var4) {
                ++var4;
            }
            cnt = var4;
        }
        while (cnt > 0) {
            final int var4 = EntityXPOrb.func_70527_a(cnt);
            cnt -= var4;
            final EntityXPOrb xp = new EntityXPOrb(this.field_145850_b, (double)(this.field_174879_c.func_177958_n() + lx), (double)(this.field_174879_c.func_177956_o() + 0.4f), (double)(this.field_174879_c.func_177952_p() + lz), var4);
            mx = ((this.facingX == 0) ? ((this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.025f) : (this.facingX * 0.13f));
            mz = ((this.facingZ == 0) ? ((this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.025f) : (this.facingZ * 0.13f));
            xp.field_70159_w = mx;
            xp.field_70179_y = mz;
            xp.field_70181_x = 0.0;
            this.field_145850_b.func_72838_d((Entity)xp);
        }
    }
    
    private ItemStack[] getSmeltingBonus(final ItemStack in) {
        final ArrayList<ItemStack> out = new ArrayList<ItemStack>();
        for (final ThaumcraftApi.SmeltBonus bonus : CommonInternals.smeltingBonus) {
            if (bonus.in instanceof ItemStack) {
                if (in.func_77973_b() != ((ItemStack)bonus.in).func_77973_b() || (in.func_77952_i() != ((ItemStack)bonus.in).func_77952_i() && ((ItemStack)bonus.in).func_77952_i() != 32767) || this.field_145850_b.field_73012_v.nextFloat() > bonus.chance) {
                    continue;
                }
                final ItemStack is = bonus.out.func_77946_l();
                if (is.func_190916_E() < 1) {
                    is.func_190920_e(1);
                }
                out.add(is);
            }
            else {
                final int[] oreIDs = OreDictionary.getOreIDs(in);
                final int length = oreIDs.length;
                int i = 0;
                while (i < length) {
                    final int id = oreIDs[i];
                    final String od = OreDictionary.getOreName(id);
                    if (((String)bonus.in).equals(od)) {
                        if (this.field_145850_b.field_73012_v.nextFloat() <= bonus.chance) {
                            final ItemStack is2 = bonus.out.func_77946_l();
                            if (is2.func_190916_E() < 1) {
                                is2.func_190920_e(1);
                            }
                            out.add(is2);
                            break;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
        return out.toArray(new ItemStack[0]);
    }
    
    private boolean canSmelt(final int slotIn) {
        if (this.func_70301_a(slotIn).func_190926_b()) {
            return false;
        }
        final ItemStack itemstack = FurnaceRecipes.func_77602_a().func_151395_a(this.func_70301_a(slotIn));
        return itemstack != null && !itemstack.func_190926_b();
    }
    
    private void setFacing() {
        this.facingX = 0;
        this.facingZ = 0;
        final EnumFacing face = this.getFacing().func_176734_d();
        this.facingX = face.func_82601_c();
        this.facingZ = face.func_82599_e();
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 3) {
            if (this.field_145850_b.field_72995_K) {
                for (int a = 0; a < 5; ++a) {
                    FXDispatcher.INSTANCE.furnaceLavaFx(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), this.facingX, this.facingZ);
                    this.field_145850_b.func_184134_a((double)(this.field_174879_c.func_177958_n() + 0.5f), (double)(this.field_174879_c.func_177956_o() + 0.5f), (double)(this.field_174879_c.func_177952_p() + 0.5f), SoundEvents.field_187662_cZ, SoundCategory.BLOCKS, 0.1f + this.field_145850_b.field_73012_v.nextFloat() * 0.1f, 0.9f + this.field_145850_b.field_73012_v.nextFloat() * 0.15f, false);
                }
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
}
