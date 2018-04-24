package thaumcraft.common.blocks.world.plants;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import thaumcraft.api.blocks.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import java.util.*;

public class BlockLogsTC extends BlockTC
{
    public static final PropertyEnum AXIS;
    
    public BlockLogsTC(final String name) {
        super(Material.field_151575_d, name);
        this.setHarvestLevel("axe", 0);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockLogsTC.AXIS, (Comparable)EnumFacing.Axis.Y));
    }
    
    public IBlockState func_180642_a(final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final int metadata, final EntityLivingBase entity) {
        return super.func_180642_a(world, pos, side, hitX, hitY, hitZ, metadata, entity).func_177226_a((IProperty)BlockLogsTC.AXIS, (Comparable)side.func_176740_k());
    }
    
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return (state.func_177230_c() == BlocksTC.logSilverwood) ? 5 : super.getLightValue(state, world, pos);
    }
    
    protected ItemStack func_180643_i(final IBlockState state) {
        return new ItemStack(Item.func_150898_a((Block)this));
    }
    
    public IBlockState func_176203_a(final int meta) {
        final int axis = meta % 3;
        return this.func_176223_P().func_177226_a((IProperty)BlockLogsTC.AXIS, (Comparable)EnumFacing.Axis.values()[axis]);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing.Axis)state.func_177229_b((IProperty)BlockLogsTC.AXIS)).ordinal();
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockLogsTC.AXIS });
    }
    
    public boolean canSustainLeaves(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return true;
    }
    
    public boolean isWood(final IBlockAccess world, final BlockPos pos) {
        return true;
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final byte b0 = 4;
        final int i = b0 + 1;
        if (worldIn.func_175707_a(pos.func_177982_a(-i, -i, -i), pos.func_177982_a(i, i, i))) {
            for (final BlockPos blockpos1 : BlockPos.func_177980_a(pos.func_177982_a(-b0, -b0, -b0), pos.func_177982_a((int)b0, (int)b0, (int)b0))) {
                final IBlockState iblockstate1 = worldIn.func_180495_p(blockpos1);
                if (iblockstate1.func_177230_c().isLeaves(iblockstate1, (IBlockAccess)worldIn, blockpos1)) {
                    iblockstate1.func_177230_c().beginLeavesDecay(iblockstate1, worldIn, blockpos1);
                }
            }
        }
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 5;
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 5;
    }
    
    static {
        AXIS = PropertyEnum.func_177709_a("axis", (Class)EnumFacing.Axis.class);
    }
}
