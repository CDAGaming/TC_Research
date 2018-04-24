package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.crafting.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.text.*;

public class TileThaumatorium extends TileThaumcraftInventory implements IAspectContainer, IEssentiaTransport, ITickable
{
    public AspectList essentia;
    public ArrayList<Integer> recipeHash;
    public ArrayList<AspectList> recipeEssentia;
    public ArrayList<String> recipePlayer;
    public int currentCraft;
    public int maxRecipes;
    public Aspect currentSuction;
    int venting;
    int counter;
    boolean heated;
    CrucibleRecipe currentRecipe;
    public Container eventHandler;
    
    public TileThaumatorium() {
        super(1);
        this.essentia = new AspectList();
        this.recipeHash = new ArrayList<Integer>();
        this.recipeEssentia = new ArrayList<AspectList>();
        this.recipePlayer = new ArrayList<String>();
        this.currentCraft = -1;
        this.maxRecipes = 1;
        this.currentSuction = null;
        this.venting = 0;
        this.counter = 0;
        this.heated = false;
        this.currentRecipe = null;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.func_174877_v().func_177958_n() - 0.1, this.func_174877_v().func_177956_o() - 0.1, this.func_174877_v().func_177952_p() - 0.1, this.func_174877_v().func_177958_n() + 1.1, this.func_174877_v().func_177956_o() + 2.1, this.func_174877_v().func_177952_p() + 1.1);
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.essentia.readFromNBT(nbttagcompound);
        this.maxRecipes = nbttagcompound.func_74771_c("maxrec");
        this.recipeEssentia = new ArrayList<AspectList>();
        this.recipeHash = new ArrayList<Integer>();
        this.recipePlayer = new ArrayList<String>();
        final int[] hashes = nbttagcompound.func_74759_k("recipes");
        if (hashes != null) {
            for (final int hash : hashes) {
                final CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(hash);
                if (recipe != null) {
                    this.recipeEssentia.add(recipe.getAspects().copy());
                    this.recipePlayer.add("");
                    this.recipeHash.add(hash);
                }
            }
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74774_a("maxrec", (byte)this.maxRecipes);
        this.essentia.writeToNBT(nbttagcompound);
        final int[] hashes = new int[this.recipeHash.size()];
        int a = 0;
        for (final Integer i : this.recipeHash) {
            hashes[a] = i;
            ++a;
        }
        nbttagcompound.func_74783_a("recipes", hashes);
        return nbttagcompound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbtCompound) {
        super.func_145839_a(nbtCompound);
        final NBTTagList nbttaglist2 = nbtCompound.func_150295_c("OutputPlayer", 8);
        for (int a = 0; a < nbttaglist2.func_74745_c(); ++a) {
            if (this.recipePlayer.size() > a) {
                this.recipePlayer.set(a, nbttaglist2.func_150307_f(a));
            }
        }
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbtCompound) {
        super.func_189515_b(nbtCompound);
        final NBTTagList nbttaglist2 = new NBTTagList();
        if (this.recipePlayer.size() > 0) {
            for (int a = 0; a < this.recipePlayer.size(); ++a) {
                if (this.recipePlayer.get(a) != null) {
                    final NBTTagString nbttagcompound1 = new NBTTagString((String)this.recipePlayer.get(a));
                    nbttaglist2.func_74742_a((NBTBase)nbttagcompound1);
                }
            }
        }
        nbtCompound.func_74782_a("OutputPlayer", (NBTBase)nbttaglist2);
        return nbtCompound;
    }
    
    boolean checkHeat() {
        final Material mat = this.field_145850_b.func_180495_p(this.field_174879_c.func_177979_c(2)).func_185904_a();
        final Block bi = this.field_145850_b.func_180495_p(this.field_174879_c.func_177979_c(2)).func_177230_c();
        return mat == Material.field_151587_i || mat == Material.field_151581_o || BlocksTC.nitor.containsValue(bi) || bi == Blocks.field_189877_df;
    }
    
    public ItemStack getCurrentOutputRecipe() {
        ItemStack out = ItemStack.field_190927_a;
        if (this.currentCraft >= 0 && this.recipeHash != null && this.recipeHash.size() > 0) {
            final CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(this.recipeHash.get(this.currentCraft));
            if (recipe != null) {
                out = recipe.getRecipeOutput().func_77946_l();
            }
        }
        return out;
    }
    
    @Override
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            if (this.counter == 0 || this.counter % 40 == 0) {
                this.heated = this.checkHeat();
                this.getUpgrades();
            }
            ++this.counter;
            if (this.heated && !this.gettingPower() && this.counter % 5 == 0 && this.recipeHash != null && this.recipeHash.size() > 0) {
                if (this.func_70301_a(0).func_190926_b()) {
                    this.currentSuction = null;
                    return;
                }
                if (this.currentCraft < 0 || this.currentCraft >= this.recipeHash.size() || this.currentRecipe == null || !this.currentRecipe.catalystMatches(this.func_70301_a(0))) {
                    for (int a = 0; a < this.recipeHash.size(); ++a) {
                        final CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(this.recipeHash.get(a));
                        if (recipe.catalystMatches(this.func_70301_a(0))) {
                            this.currentCraft = a;
                            this.currentRecipe = recipe;
                            break;
                        }
                    }
                }
                if (this.currentCraft < 0 || this.currentCraft >= this.recipeHash.size()) {
                    return;
                }
                boolean done = true;
                this.currentSuction = null;
                for (final Aspect aspect : this.recipeEssentia.get(this.currentCraft).getAspectsSortedByName()) {
                    if (this.essentia.getAmount(aspect) < this.recipeEssentia.get(this.currentCraft).getAmount(aspect)) {
                        this.currentSuction = aspect;
                        done = false;
                        break;
                    }
                }
                if (done) {
                    this.completeRecipe();
                }
                else if (this.currentSuction != null) {
                    this.fill();
                }
            }
        }
        else if (this.venting > 0) {
            --this.venting;
            final float fx = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
            final float fz = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
            final float fy = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
            final float fx2 = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
            final float fz2 = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
            final float fy2 = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
            final int color = 16777215;
            final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
            FXDispatcher.INSTANCE.drawVentParticles(this.field_174879_c.func_177958_n() + 0.5f + fx + facing.func_82601_c() / 2.0f, this.field_174879_c.func_177956_o() + 0.5f + fy, this.field_174879_c.func_177952_p() + 0.5f + fz + facing.func_82599_e() / 2.0f, facing.func_82601_c() / 4.0f + fx2, fy2, facing.func_82599_e() / 4.0f + fz2, color);
        }
    }
    
    private void completeRecipe() {
        if (this.currentRecipe != null && this.currentCraft < this.recipeHash.size() && this.currentRecipe.matches(this.essentia, this.func_70301_a(0)) && this.func_70298_a(0, 1) != null) {
            this.essentia = new AspectList();
            final ItemStack dropped = this.getCurrentOutputRecipe();
            final EntityPlayer p = this.field_145850_b.func_72924_a((String)this.recipePlayer.get(this.currentCraft));
            if (p != null) {
                FMLCommonHandler.instance().firePlayerCraftingEvent(p, dropped, (IInventory)new InventoryFake(new ItemStack[] { this.func_70301_a(0) }));
            }
            final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
            InventoryUtils.ejectStackAt(this.func_145831_w(), this.func_174877_v(), facing, dropped);
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.25f, 2.6f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.8f);
            this.currentCraft = -1;
            this.syncTile(false);
            this.func_70296_d();
        }
    }
    
    void fill() {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        TileEntity te = null;
        IEssentiaTransport ic = null;
        for (int y = 0; y <= 1; ++y) {
            for (final EnumFacing dir : EnumFacing.field_82609_l) {
                if (dir != facing && dir != EnumFacing.DOWN) {
                    if (y != 0 || dir != EnumFacing.UP) {
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
    }
    
    @Override
    public int addToContainer(final Aspect tt, final int am) {
        final int ce = this.currentRecipe.getAspects().getAmount(tt) - this.essentia.getAmount(tt);
        if (this.currentRecipe == null || ce <= 0) {
            return am;
        }
        final int add = Math.min(ce, am);
        this.essentia.add(tt, add);
        this.syncTile(false);
        this.func_70296_d();
        return am - add;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tt, final int am) {
        if (this.essentia.getAmount(tt) >= am) {
            this.essentia.remove(tt, am);
            this.syncTile(false);
            this.func_70296_d();
            return true;
        }
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
        return this.essentia.getAmount(tt) >= am;
    }
    
    @Override
    public int containerContains(final Aspect tt) {
        return this.essentia.getAmount(tt);
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i >= 0) {
            if (this.field_145850_b.field_72995_K) {
                this.venting = 7;
            }
            return true;
        }
        return super.func_145842_c(i, j);
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
        return this.essentia;
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
        this.essentia = aspects;
    }
    
    public void func_70296_d() {
        super.func_70296_d();
        if (this.eventHandler != null) {
            this.eventHandler.func_75130_a((IInventory)this);
        }
    }
    
    @Override
    public int[] func_180463_a(final EnumFacing side) {
        return new int[] { 0 };
    }
    
    public boolean gettingPower() {
        return this.field_145850_b.func_175687_A(this.field_174879_c) > 0 || this.field_145850_b.func_175687_A(this.field_174879_c.func_177977_b()) > 0 || this.field_145850_b.func_175687_A(this.field_174879_c.func_177984_a()) > 0;
    }
    
    public void getUpgrades() {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        int mr = 1;
        for (int yy = 0; yy <= 1; ++yy) {
            for (final EnumFacing dir : EnumFacing.field_82609_l) {
                if (dir != EnumFacing.DOWN) {
                    if (dir != facing) {
                        final int xx = this.field_174879_c.func_177958_n() + dir.func_82601_c();
                        final int zz = this.field_174879_c.func_177952_p() + dir.func_82599_e();
                        final BlockPos bp = new BlockPos(xx, this.field_174879_c.func_177956_o() + yy + dir.func_96559_d(), zz);
                        final IBlockState bs = this.field_145850_b.func_180495_p(bp);
                        if (bs == BlocksTC.brainBox.func_176223_P().func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)dir.func_176734_d())) {
                            mr += 2;
                        }
                    }
                }
            }
        }
        if (mr != this.maxRecipes) {
            this.maxRecipes = mr;
            while (this.recipeHash.size() > this.maxRecipes) {
                this.recipeHash.remove(this.recipeHash.size() - 1);
            }
            this.syncTile(false);
            this.func_70296_d();
        }
    }
    
    @Override
    public String func_70005_c_() {
        return "container.alchemyfurnace";
    }
    
    @Override
    public boolean func_145818_k_() {
        return false;
    }
    
    @Override
    public ITextComponent func_145748_c_() {
        return null;
    }
}
