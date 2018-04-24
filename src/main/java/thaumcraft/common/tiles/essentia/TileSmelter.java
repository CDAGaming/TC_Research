package thaumcraft.common.tiles.essentia;

import thaumcraft.common.tiles.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.blocks.essentia.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import net.minecraft.block.state.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.api.aura.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.*;

public class TileSmelter extends TileThaumcraftInventory
{
    private static final int[] slots_bottom;
    private static final int[] slots_top;
    private static final int[] slots_sides;
    public AspectList aspects;
    public int vis;
    private int maxVis;
    public int smeltTime;
    boolean speedBoost;
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;
    int count;
    int bellows;
    
    public TileSmelter() {
        super(2);
        this.aspects = new AspectList();
        this.maxVis = 256;
        this.smeltTime = 100;
        this.speedBoost = false;
        this.count = 0;
        this.bellows = -1;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.furnaceBurnTime = nbttagcompound.func_74765_d("BurnTime");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74777_a("BurnTime", (short)this.furnaceBurnTime);
        return nbttagcompound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbtCompound) {
        super.func_145839_a(nbtCompound);
        this.speedBoost = nbtCompound.func_74767_n("speedBoost");
        this.furnaceCookTime = nbtCompound.func_74765_d("CookTime");
        this.currentItemBurnTime = TileEntityFurnace.func_145952_a(this.func_70301_a(1));
        this.aspects.readFromNBT(nbtCompound);
        this.vis = this.aspects.visSize();
    }
    
    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbtCompound) {
        nbtCompound = super.func_189515_b(nbtCompound);
        nbtCompound.func_74757_a("speedBoost", this.speedBoost);
        nbtCompound.func_74777_a("CookTime", (short)this.furnaceCookTime);
        this.aspects.writeToNBT(nbtCompound);
        return nbtCompound;
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        final boolean flag = this.furnaceBurnTime > 0;
        boolean flag2 = false;
        ++this.count;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }
        if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
            if (this.bellows < 0) {
                this.checkNeighbours();
            }
            int speed = this.getSpeed();
            if (this.speedBoost) {
                speed *= (int)0.8;
            }
            if (this.count % speed == 0 && this.aspects.size() > 0) {
                for (final Aspect aspect : this.aspects.getAspects()) {
                    if (this.aspects.getAmount(aspect) > 0 && TileAlembic.processAlembics(this.func_145831_w(), this.func_174877_v(), aspect)) {
                        this.takeFromContainer(aspect, 1);
                        break;
                    }
                }
                for (final EnumFacing face : EnumFacing.field_176754_o) {
                    final IBlockState aux = this.field_145850_b.func_180495_p(this.func_174877_v().func_177972_a(face));
                    if (aux.func_177230_c() == BlocksTC.smelterAux && BlockStateUtils.getFacing(aux) == face.func_176734_d()) {
                        for (final Aspect aspect2 : this.aspects.getAspects()) {
                            if (this.aspects.getAmount(aspect2) > 0 && TileAlembic.processAlembics(this.func_145831_w(), this.func_174877_v().func_177972_a(face), aspect2)) {
                                this.takeFromContainer(aspect2, 1);
                                break;
                            }
                        }
                    }
                }
            }
            if (this.furnaceBurnTime == 0) {
                if (this.canSmelt()) {
                    final int func_145952_a = TileEntityFurnace.func_145952_a(this.func_70301_a(1));
                    this.furnaceBurnTime = func_145952_a;
                    this.currentItemBurnTime = func_145952_a;
                    if (this.furnaceBurnTime > 0) {
                        BlockSmelter.setFurnaceState(this.field_145850_b, this.func_174877_v(), true);
                        flag2 = true;
                        this.speedBoost = false;
                        if (this.func_70301_a(1) != null) {
                            if (this.func_70301_a(1).func_77969_a(new ItemStack(ItemsTC.alumentum))) {
                                this.speedBoost = true;
                            }
                            this.func_70301_a(1).func_190918_g(1);
                            if (this.func_70301_a(1).func_190916_E() == 0) {
                                this.func_70299_a(1, this.func_70301_a(1).func_77973_b().getContainerItem(this.func_70301_a(1)));
                            }
                        }
                    }
                    else {
                        BlockSmelter.setFurnaceState(this.field_145850_b, this.func_174877_v(), false);
                    }
                }
                else {
                    BlockSmelter.setFurnaceState(this.field_145850_b, this.func_174877_v(), false);
                }
            }
            if (BlockStateUtils.isEnabled(this.func_145832_p()) && this.canSmelt()) {
                ++this.furnaceCookTime;
                if (this.furnaceCookTime >= this.smeltTime) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    flag2 = true;
                }
            }
            else {
                this.furnaceCookTime = 0;
            }
            if (flag != this.furnaceBurnTime > 0) {
                flag2 = true;
            }
        }
        if (flag2) {
            this.func_70296_d();
        }
    }
    
    private boolean canSmelt() {
        if (this.func_70301_a(0).func_190926_b()) {
            return false;
        }
        final AspectList al = ThaumcraftCraftingManager.getObjectTags(this.func_70301_a(0));
        if (al == null || al.size() == 0) {
            return false;
        }
        final int vs = al.visSize();
        if (vs > this.maxVis - this.vis) {
            return false;
        }
        this.smeltTime = (int)(vs * 2 * (1.0f - 0.125f * this.bellows));
        return true;
    }
    
    public void checkNeighbours() {
        EnumFacing[] faces = EnumFacing.field_176754_o;
        try {
            if (BlockStateUtils.getFacing(this.func_145832_p()) == EnumFacing.NORTH) {
                faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST };
            }
            if (BlockStateUtils.getFacing(this.func_145832_p()) == EnumFacing.SOUTH) {
                faces = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
            }
            if (BlockStateUtils.getFacing(this.func_145832_p()) == EnumFacing.EAST) {
                faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.WEST };
            }
            if (BlockStateUtils.getFacing(this.func_145832_p()) == EnumFacing.WEST) {
                faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.NORTH };
            }
        }
        catch (Exception ex) {}
        this.bellows = TileBellows.getBellows(this.field_145850_b, this.field_174879_c, faces);
    }
    
    private int getType() {
        return (this.func_145838_q() == BlocksTC.smelterBasic) ? 0 : ((this.func_145838_q() == BlocksTC.smelterThaumium) ? 1 : 2);
    }
    
    private float getEfficiency() {
        float efficiency = 0.8f;
        if (this.getType() == 1) {
            efficiency = 0.85f;
        }
        if (this.getType() == 2) {
            efficiency = 0.95f;
        }
        return efficiency;
    }
    
    private int getSpeed() {
        final int speed = 20 - ((this.getType() == 1) ? 10 : 5);
        return speed;
    }
    
    public void smeltItem() {
        if (this.canSmelt()) {
            int flux = 0;
            final AspectList al = ThaumcraftCraftingManager.getObjectTags(this.func_70301_a(0));
            for (final Aspect a : al.getAspects()) {
                if (this.getEfficiency() < 1.0f) {
                    for (int qq = al.getAmount(a), q = 0; q < qq; ++q) {
                        if (this.field_145850_b.field_73012_v.nextFloat() > ((a == Aspect.FLUX) ? (this.getEfficiency() * 0.66f) : this.getEfficiency())) {
                            al.reduce(a, 1);
                            ++flux;
                        }
                    }
                }
                this.aspects.add(a, al.getAmount(a));
            }
            if (flux > 0) {
                int pp = 0;
                int c = 0;
            Label_0155:
                while (c < flux) {
                    while (true) {
                        for (final EnumFacing face : EnumFacing.field_176754_o) {
                            final IBlockState vent = this.field_145850_b.func_180495_p(this.func_174877_v().func_177972_a(face));
                            if (vent.func_177230_c() == BlocksTC.smelterVent && BlockStateUtils.getFacing(vent) == face.func_176734_d() && this.field_145850_b.field_73012_v.nextFloat() < 0.333) {
                                this.field_145850_b.func_175641_c(this.func_174877_v(), this.func_145838_q(), 1, face.func_176734_d().ordinal());
                                ++c;
                                continue Label_0155;
                            }
                        }
                        ++pp;
                        continue;
                    }
                }
                AuraHelper.polluteAura(this.func_145831_w(), this.func_174877_v(), pp, true);
            }
            this.vis = this.aspects.visSize();
            this.func_70301_a(0).func_190918_g(1);
            if (this.func_70301_a(0).func_190916_E() <= 0) {
                this.func_70299_a(0, ItemStack.field_190927_a);
            }
        }
    }
    
    public static boolean isItemFuel(final ItemStack par0ItemStack) {
        return TileEntityFurnace.func_145952_a(par0ItemStack) > 0;
    }
    
    @Override
    public boolean func_94041_b(final int par1, final ItemStack stack2) {
        if (par1 == 0) {
            final AspectList al = ThaumcraftCraftingManager.getObjectTags(stack2);
            if (al != null && al.size() > 0) {
                return true;
            }
        }
        return par1 == 1 && isItemFuel(stack2);
    }
    
    @Override
    public int[] func_180463_a(final EnumFacing par1) {
        return (par1 == EnumFacing.DOWN) ? TileSmelter.slots_bottom : ((par1 == EnumFacing.UP) ? TileSmelter.slots_top : TileSmelter.slots_sides);
    }
    
    @Override
    public boolean func_180462_a(final int par1, final ItemStack stack2, final EnumFacing par3) {
        return par3 != EnumFacing.UP && this.func_94041_b(par1, stack2);
    }
    
    @Override
    public boolean func_180461_b(final int par1, final ItemStack stack2, final EnumFacing par3) {
        return par3 != EnumFacing.UP || par1 != 1 || stack2.func_77973_b() == Items.field_151133_ar;
    }
    
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        if (this.aspects != null && this.aspects.getAmount(tag) >= amount) {
            this.aspects.remove(tag, amount);
            this.vis = this.aspects.visSize();
            this.func_70296_d();
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(final int par1) {
        if (this.smeltTime <= 0) {
            this.smeltTime = 1;
        }
        return this.furnaceCookTime * par1 / this.smeltTime;
    }
    
    @SideOnly(Side.CLIENT)
    public int getVisScaled(final int par1) {
        return this.vis * par1 / this.maxVis;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(final int par1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }
    
    @Override
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (this.field_145850_b != null) {
            this.field_145850_b.func_180500_c(EnumSkyBlock.BLOCK, this.field_174879_c);
        }
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 1) {
            if (this.field_145850_b.field_72995_K) {
                final EnumFacing d = EnumFacing.field_82609_l[j];
                this.field_145850_b.func_184134_a(this.func_174877_v().func_177958_n() + 0.5 + d.func_176734_d().func_82601_c(), this.func_174877_v().func_177956_o() + 0.5, this.func_174877_v().func_177952_p() + 0.5 + d.func_176734_d().func_82599_e(), SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.25f, 2.6f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.8f, true);
                for (int a = 0; a < 4; ++a) {
                    final float fx = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                    final float fz = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                    final float fy = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                    final float fx2 = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                    final float fz2 = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                    final float fy2 = 0.1f - this.field_145850_b.field_73012_v.nextFloat() * 0.2f;
                    final int color = 11184810;
                    FXDispatcher.INSTANCE.drawVentParticles(this.func_174877_v().func_177958_n() + 0.5f + fx + d.func_176734_d().func_82601_c(), this.func_174877_v().func_177956_o() + 0.5f + fy, this.func_174877_v().func_177952_p() + 0.5f + fz + d.func_176734_d().func_82599_e(), d.func_176734_d().func_82601_c() / 4.0f + fx2, d.func_176734_d().func_96559_d() / 4.0f + fy2, d.func_176734_d().func_82599_e() / 4.0f + fz2, color);
                }
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
    
    static {
        slots_bottom = new int[] { 1 };
        slots_top = new int[0];
        slots_sides = new int[] { 0 };
    }
}
