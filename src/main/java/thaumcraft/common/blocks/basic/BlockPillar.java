package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import com.google.common.base.*;

public class BlockPillar extends BlockTC
{
    public static final PropertyDirection FACING;
    private final Random rand;
    
    public BlockPillar(final String name) {
        super(Material.field_151576_e, name);
        this.rand = new Random();
        this.func_149711_c(2.5f);
        this.func_149672_a(SoundType.field_185851_d);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)BlockPillar.FACING, (Comparable)EnumFacing.NORTH);
        this.func_180632_j(bs);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)BlockPillar.FACING, (Comparable)placer.func_174811_aO());
        return bs;
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final EnumFacing enumfacing = EnumFacing.func_176731_b(MathHelper.func_76128_c(placer.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3).func_176734_d();
        state = state.func_177226_a((IProperty)BlockPillar.FACING, (Comparable)enumfacing);
        worldIn.func_180501_a(pos, state, 3);
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (state.func_177230_c() == BlocksTC.pillarArcane) {
            func_180635_a(worldIn, pos, new ItemStack(BlocksTC.stoneArcane, 2, 0));
        }
        if (state.func_177230_c() == BlocksTC.pillarAncient) {
            func_180635_a(worldIn, pos, new ItemStack(BlocksTC.stoneAncient, 2, 2));
        }
        if (state.func_177230_c() == BlocksTC.pillarEldritch) {
            func_180635_a(worldIn, pos, new ItemStack(BlocksTC.stoneEldritchTile, 2, 4));
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    public IBlockState func_176203_a(final int meta) {
        final EnumFacing enumfacing = EnumFacing.func_176731_b(meta);
        return this.func_176194_O().func_177621_b().func_177226_a((IProperty)BlockPillar.FACING, (Comparable)enumfacing);
    }
    
    public static int calcMeta(EnumFacing enumfacing) {
        if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        final IBlockState state = BlocksTC.pillarArcane.func_176194_O().func_177621_b();
        return BlocksTC.pillarArcane.func_176201_c(state.func_177226_a((IProperty)BlockPillar.FACING, (Comparable)enumfacing));
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockPillar.FACING)).func_176736_b();
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockPillar.FACING });
    }
    
    static {
        FACING = PropertyDirection.func_177712_a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
}
