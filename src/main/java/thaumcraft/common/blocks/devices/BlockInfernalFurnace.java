package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.blocks.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;

public class BlockInfernalFurnace extends BlockTCDevice implements IBlockFacingHorizontal
{
    public static boolean ignore;
    
    public BlockInfernalFurnace() {
        super(Material.field_151576_e, TileInfernalFurnace.class, "infernal_furnace");
        this.func_149672_a(SoundType.field_185851_d);
        this.func_149715_a(0.9f);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)EnumFacing.NORTH);
        this.func_180632_j(bs);
        this.func_149647_a((CreativeTabs)null);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
    }
    
    @Override
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)placer.func_174811_aO().func_176734_d());
        return bs;
    }
    
    public static void destroyFurnace(final World worldIn, final BlockPos pos, final IBlockState state, final BlockPos startpos) {
        if (BlockInfernalFurnace.ignore || worldIn.field_72995_K) {
            return;
        }
        BlockInfernalFurnace.ignore = true;
        for (int a = -1; a <= 1; ++a) {
            for (int b = -1; b <= 1; ++b) {
                for (int c = -1; c <= 1; ++c) {
                    if (pos.func_177982_a(a, b, c) != startpos) {
                        final IBlockState bs = worldIn.func_180495_p(pos.func_177982_a(a, b, c));
                        if (bs.func_177230_c() == BlocksTC.placeholderNetherbrick) {
                            worldIn.func_175656_a(pos.func_177982_a(a, b, c), Blocks.field_150385_bj.func_176223_P());
                        }
                        if (bs.func_177230_c() == BlocksTC.placeholderObsidian) {
                            worldIn.func_175656_a(pos.func_177982_a(a, b, c), Blocks.field_150343_Z.func_176223_P());
                        }
                    }
                }
            }
        }
        if (worldIn.func_175623_d(pos.func_177972_a(BlockStateUtils.getFacing(state).func_176734_d()))) {
            worldIn.func_175656_a(pos.func_177972_a(BlockStateUtils.getFacing(state).func_176734_d()), Blocks.field_150411_aY.func_176223_P());
        }
        worldIn.func_175656_a(pos, Blocks.field_150353_l.func_176223_P());
        BlockInfernalFurnace.ignore = false;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        destroyFurnace(worldIn, pos, state, pos);
        super.func_180663_b(worldIn, pos, state);
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        if (entity.field_70165_t < pos.func_177958_n() + 0.3f) {
            entity.field_70159_w += 9.999999747378752E-5;
        }
        if (entity.field_70165_t > pos.func_177958_n() + 0.7f) {
            entity.field_70159_w -= 9.999999747378752E-5;
        }
        if (entity.field_70161_v < pos.func_177952_p() + 0.3f) {
            entity.field_70179_y += 9.999999747378752E-5;
        }
        if (entity.field_70161_v > pos.func_177952_p() + 0.7f) {
            entity.field_70179_y -= 9.999999747378752E-5;
        }
        if (entity instanceof EntityItem) {
            entity.field_70181_x = 0.02500000037252903;
            if (entity.field_70122_E) {
                final TileInfernalFurnace taf = (TileInfernalFurnace)world.func_175625_s(pos);
                if (taf.addItemsToInventory(((EntityItem)entity).func_92059_d())) {
                    entity.func_70106_y();
                }
            }
        }
        else if (entity instanceof EntityLivingBase && !entity.func_70045_F()) {
            entity.func_70097_a(DamageSource.field_76371_c, 3.0f);
            entity.func_70015_d(10);
        }
        super.func_180634_a(world, pos, state, entity);
    }
    
    static {
        BlockInfernalFurnace.ignore = false;
    }
}
