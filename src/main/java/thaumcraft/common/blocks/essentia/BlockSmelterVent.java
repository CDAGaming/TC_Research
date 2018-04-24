package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class BlockSmelterVent extends BlockTC implements IBlockFacingHorizontal
{
    public BlockSmelterVent() {
        super(Material.field_151573_f, "smelter_vent");
        this.func_149672_a(SoundType.field_185852_e);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)EnumFacing.NORTH));
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
    
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public boolean func_176198_a(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return super.func_176198_a(worldIn, pos, side) && side.func_176740_k().func_176722_c() && worldIn.func_180495_p(pos.func_177972_a(side.func_176734_d())).func_177230_c() instanceof BlockSmelter && BlockStateUtils.getFacing(worldIn.func_180495_p(pos.func_177972_a(side.func_176734_d()))) != side;
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        if (!facing.func_176740_k().func_176722_c()) {
            facing = EnumFacing.NORTH;
        }
        bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)facing.func_176734_d());
        return bs;
    }
    
    public IBlockState func_176203_a(final int meta) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)EnumFacing.func_176731_b(BlockStateUtils.getFacing(meta).func_176736_b()));
        return bs;
    }
    
    public int func_176201_c(final IBlockState state) {
        return 0x0 | ((EnumFacing)state.func_177229_b((IProperty)IBlockFacingHorizontal.FACING)).func_176745_a();
    }
    
    protected BlockStateContainer func_180661_e() {
        final ArrayList<IProperty> ip = new ArrayList<IProperty>();
        ip.add((IProperty)IBlockFacingHorizontal.FACING);
        return (ip.size() == 0) ? super.func_180661_e() : new BlockStateContainer((Block)this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumFacing facing = BlockStateUtils.getFacing(state);
        switch (facing.ordinal()) {
            default: {
                return new AxisAlignedBB(0.125, 0.125, 0.0, 0.875, 0.875, 0.5);
            }
            case 3: {
                return new AxisAlignedBB(0.125, 0.125, 0.5, 0.875, 0.875, 1.0);
            }
            case 4: {
                return new AxisAlignedBB(0.0, 0.125, 0.125, 0.5, 0.875, 0.875);
            }
            case 5: {
                return new AxisAlignedBB(0.5, 0.125, 0.125, 1.0, 0.875, 0.875);
            }
        }
    }
}
