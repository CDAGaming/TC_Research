package thaumcraft.common.blocks.devices;

import net.minecraft.block.material.*;
import thaumcraft.common.blocks.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.blocks.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;

public class BlockBrainBox extends BlockTC implements IBlockFacingHorizontal, IBlockEnabled
{
    public BlockBrainBox() {
        super(Material.field_151573_f, "brain_box");
        this.func_149672_a(SoundType.field_185852_e);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.UP);
        this.func_180632_j(bs);
        this.func_149711_c(1.0f);
        this.func_149752_b(10.0f);
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (worldIn.func_180495_p(pos.func_177972_a(BlockStateUtils.getFacing(state))).func_177230_c() != BlocksTC.thaumatorium) {
            this.func_176226_b(worldIn, pos, BlocksTC.brainBox.func_176223_P(), 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    public boolean func_176198_a(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return worldIn.func_180495_p(pos.func_177972_a(side.func_176734_d())).func_177230_c() == BlocksTC.thaumatorium && worldIn.func_180495_p(pos.func_177972_a(side.func_176734_d())).func_177229_b((IProperty)BlockBrainBox.FACING) != side && super.func_176198_a(worldIn, pos, side);
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing.func_176734_d());
        return bs;
    }
    
    public IBlockState func_176203_a(final int meta) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)BlockStateUtils.getFacing(meta));
        return bs;
    }
    
    public int func_176201_c(final IBlockState state) {
        final byte b0 = 0;
        final int i = b0 | ((EnumFacing)state.func_177229_b((IProperty)IBlockFacing.FACING)).func_176745_a();
        return i;
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { IBlockFacing.FACING });
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.1875, 0.1875, 0.1875, 0.8125, 0.8125, 0.8125);
    }
}
