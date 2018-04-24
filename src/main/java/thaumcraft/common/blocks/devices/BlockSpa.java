package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fluids.*;

public class BlockSpa extends BlockTCDevice
{
    public BlockSpa() {
        super(Material.field_151576_e, TileSpa.class, "spa");
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        final TileEntity tileEntity = world.func_175625_s(pos);
        if (tileEntity instanceof TileSpa && !player.func_70093_af()) {
            final FluidStack fs = FluidUtil.getFluidContained(player.func_184586_b(hand));
            if (fs != null) {
                final TileSpa tile = (TileSpa)tileEntity;
                if (tile.tank.getFluidAmount() < tile.tank.getCapacity() && (tile.tank.getFluid() == null || tile.tank.getFluid().isFluidEqual(fs)) && FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler)tile.tank)) {
                    player.field_71069_bz.func_75142_b();
                    tile.func_70296_d();
                    world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
                    world.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.33f, 1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.3f);
                }
            }
            else {
                player.openGui((Object)Thaumcraft.instance, 6, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
            }
        }
        return true;
    }
}
