package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.util.math.*;

public class BlockLamp extends BlockTCDevice implements IBlockFacing, IBlockEnabled
{
    public BlockLamp(final Class tc, final String name) {
        super(Material.field_151573_f, tc, name);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_149711_c(1.0f);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.DOWN);
        bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true);
        this.func_180632_j(bs);
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
    
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return BlockStateUtils.isEnabled(world.func_180495_p(pos).func_177230_c().func_176201_c(world.func_180495_p(pos))) ? 15 : super.getLightValue(state, world, pos);
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing.func_176734_d());
        bs = bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false);
        return bs;
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te != null && te instanceof TileLampArcane) {
            ((TileLampArcane)te).removeLights();
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    @Override
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (worldIn.func_175623_d(pos.func_177972_a(BlockStateUtils.getFacing(state)))) {
            this.func_176226_b(worldIn, pos, this.func_176223_P(), 0);
            worldIn.func_175698_g(pos);
            return;
        }
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te != null && te instanceof TileLampArcane && BlockStateUtils.isEnabled(state) && worldIn.func_175640_z(pos)) {
            ((TileLampArcane)te).removeLights();
        }
        boolean checkUpdate = true;
        if (te != null && te instanceof TileLampGrowth && ((TileLampGrowth)te).charges <= 0) {
            checkUpdate = false;
        }
        if (te != null && te instanceof TileLampFertility && ((TileLampFertility)te).charges <= 0) {
            checkUpdate = false;
        }
        if (checkUpdate) {
            super.func_189540_a(state, worldIn, pos, blockIn, pos2);
        }
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.25, 0.125, 0.25, 0.75, 0.875, 0.75);
    }
}
