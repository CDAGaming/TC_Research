package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.utils.*;

public class BlockEssentiaTransport extends BlockTCDevice implements IBlockFacing
{
    public BlockEssentiaTransport(final Class te, final String name) {
        super(Material.field_151573_f, te, name);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_149711_c(1.0f);
        this.func_149752_b(10.0f);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.UP);
        this.func_180632_j(bs);
    }
    
    @Override
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing);
        return bs;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumFacing facing = BlockStateUtils.getFacing(state);
        switch (facing.ordinal()) {
            default: {
                return new AxisAlignedBB(0.25, 0.5, 0.25, 0.75, 1.0, 0.75);
            }
            case 1: {
                return new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
            }
            case 2: {
                return new AxisAlignedBB(0.25, 0.25, 0.5, 0.75, 0.75, 1.0);
            }
            case 3: {
                return new AxisAlignedBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.5);
            }
            case 4: {
                return new AxisAlignedBB(0.5, 0.25, 0.25, 1.0, 0.75, 0.75);
            }
            case 5: {
                return new AxisAlignedBB(0.0, 0.25, 0.25, 0.5, 0.75, 0.75);
            }
        }
    }
}
