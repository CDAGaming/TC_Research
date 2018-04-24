package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import thaumcraft.common.items.consumables.*;
import net.minecraft.util.text.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fluids.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.state.*;
import net.minecraftforge.common.capabilities.*;
import javax.annotation.*;
import net.minecraftforge.fluids.capability.*;

public class TileSpa extends TileThaumcraftInventory implements IFluidHandler
{
    private boolean mix;
    private int counter;
    public FluidTank tank;
    
    public TileSpa() {
        super(1);
        this.mix = true;
        this.counter = 0;
        this.tank = new FluidTank(5000);
    }
    
    public void toggleMix() {
        this.mix = !this.mix;
        this.syncTile(false);
        this.func_70296_d();
    }
    
    public boolean getMix() {
        return this.mix;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.mix = nbttagcompound.func_74767_n("mix");
        this.tank.readFromNBT(nbttagcompound);
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74757_a("mix", this.mix);
        this.tank.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }
    
    @Override
    public boolean func_94041_b(final int par1, final ItemStack stack) {
        return stack.func_77973_b() instanceof ItemBathSalts;
    }
    
    @Override
    public String func_70005_c_() {
        return "thaumcraft.spa";
    }
    
    @Override
    public boolean func_145818_k_() {
        return false;
    }
    
    @Override
    public ITextComponent func_145748_c_() {
        return null;
    }
    
    @Override
    public int[] func_180463_a(final EnumFacing side) {
        final int[] array;
        if (side != EnumFacing.UP) {
            array = new int[] { 0 };
        }
        else {
            final int[] array2 = new int[0];
        }
        return array;
    }
    
    @Override
    public boolean func_180462_a(final int index, final ItemStack itemStackIn, final EnumFacing side) {
        return side != EnumFacing.UP;
    }
    
    @Override
    public boolean func_180461_b(final int index, final ItemStack stack, final EnumFacing side) {
        return side != EnumFacing.UP;
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        Label_0267: {
            if (!this.field_145850_b.field_72995_K && this.counter++ % 40 == 0 && !this.field_145850_b.func_175640_z(this.field_174879_c) && this.hasIngredients()) {
                final Block b = this.field_145850_b.func_180495_p(this.field_174879_c.func_177984_a()).func_177230_c();
                final int m = b.func_176201_c(this.field_145850_b.func_180495_p(this.field_174879_c.func_177984_a()));
                Block tb = null;
                if (this.mix) {
                    tb = BlocksTC.purifyingFluid;
                }
                else {
                    tb = this.tank.getFluid().getFluid().getBlock();
                }
                if (b == tb && m == 0) {
                    for (int xx = -2; xx <= 2; ++xx) {
                        for (int zz = -2; zz <= 2; ++zz) {
                            final BlockPos p = this.func_174877_v().func_177982_a(xx, 1, zz);
                            if (this.isValidLocation(p, true, tb)) {
                                this.consumeIngredients();
                                this.field_145850_b.func_175656_a(p, tb.func_176223_P());
                                this.checkQuanta(p);
                                break Label_0267;
                            }
                        }
                    }
                }
                else if (this.isValidLocation(this.field_174879_c.func_177984_a(), false, tb)) {
                    this.consumeIngredients();
                    this.field_145850_b.func_175656_a(this.field_174879_c.func_177984_a(), tb.func_176223_P());
                    this.checkQuanta(this.field_174879_c.func_177984_a());
                }
            }
        }
    }
    
    private void checkQuanta(final BlockPos pos) {
        final Block b = this.field_145850_b.func_180495_p(pos).func_177230_c();
        if (b instanceof BlockFluidBase) {
            final float p = ((BlockFluidBase)b).getQuantaPercentage((IBlockAccess)this.field_145850_b, pos);
            if (p < 1.0f) {
                final int md = (int)(1.0f / p) - 1;
                if (md >= 0 && md < 16) {
                    this.field_145850_b.func_175656_a(pos, b.func_176203_a(md));
                }
            }
        }
    }
    
    private boolean hasIngredients() {
        if (this.mix) {
            if (this.tank.getInfo().fluid == null || !this.tank.getInfo().fluid.containsFluid(new FluidStack(FluidRegistry.WATER, 1000))) {
                return false;
            }
            if (!(this.func_70301_a(0).func_77973_b() instanceof ItemBathSalts)) {
                return false;
            }
        }
        else if (this.tank.getInfo().fluid == null || !this.tank.getFluid().getFluid().canBePlacedInWorld() || this.tank.getFluidAmount() < 1000) {
            return false;
        }
        return true;
    }
    
    private void consumeIngredients() {
        if (this.mix) {
            this.func_70298_a(0, 1);
        }
        this.drain(1000, true);
    }
    
    private boolean isValidLocation(final BlockPos pos, final boolean mustBeAdjacent, final Block target) {
        if ((target == Blocks.field_150355_j || target == Blocks.field_150358_i) && this.field_145850_b.field_73011_w.func_177500_n()) {
            return false;
        }
        final Block b = this.field_145850_b.func_180495_p(pos).func_177230_c();
        final IBlockState bb = this.field_145850_b.func_180495_p(pos.func_177977_b());
        final int m = b.func_176201_c(this.field_145850_b.func_180495_p(pos));
        return bb.isSideSolid((IBlockAccess)this.field_145850_b, pos.func_177977_b(), EnumFacing.UP) && b.func_176200_f((IBlockAccess)this.field_145850_b, pos) && (b != target || m != 0) && (!mustBeAdjacent || BlockUtils.isBlockTouching((IBlockAccess)this.field_145850_b, pos, target.func_176203_a(0)));
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
