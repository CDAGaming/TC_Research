package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;
import net.minecraftforge.fluids.*;

public class BlockWaterJug extends BlockTCDevice
{
    public BlockWaterJug() {
        super(Material.field_151576_e, TileWaterJug.class, "everfull_urn");
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!world.field_72995_K) {
            final TileEntity te = world.func_175625_s(pos);
            if (te != null && te instanceof TileWaterJug) {
                final TileWaterJug tile = (TileWaterJug)te;
                final FluidTank tank = new FluidTank(new FluidStack(FluidRegistry.WATER, tile.charge), tile.charge);
                final ItemStack func_184586_b = player.func_184586_b(hand);
                final FluidTank fluidTank = tank;
                final IItemHandler itemHandler = null;
                final Fluid water = FluidRegistry.WATER;
                if (FluidUtil.tryFillContainerAndStow(func_184586_b, (IFluidHandler)fluidTank, itemHandler, 1000, player, true).isSuccess()) {
                    player.field_71069_bz.func_75142_b();
                    tile.charge = tank.getFluidAmount();
                    te.func_70296_d();
                    world.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.33f, 1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.3f);
                }
            }
        }
        return true;
    }
}
