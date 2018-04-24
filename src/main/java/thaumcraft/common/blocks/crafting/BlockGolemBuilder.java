package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;

public class BlockGolemBuilder extends BlockTCDevice implements IBlockFacingHorizontal
{
    public static boolean ignore;
    
    public BlockGolemBuilder() {
        super(Material.field_151576_e, TileGolemBuilder.class, "golem_builder");
        this.func_149672_a(SoundType.field_185851_d);
        this.func_149647_a((CreativeTabs)null);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150898_a((Block)Blocks.field_150331_J);
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        destroy(worldIn, pos, state, pos);
        super.func_180663_b(worldIn, pos, state);
    }
    
    public static void destroy(final World worldIn, final BlockPos pos, final IBlockState state, final BlockPos startpos) {
        if (BlockGolemBuilder.ignore || worldIn.field_72995_K) {
            return;
        }
        BlockGolemBuilder.ignore = true;
        for (int a = -1; a <= 1; ++a) {
            for (int b = 0; b <= 1; ++b) {
                for (int c = -1; c <= 1; ++c) {
                    if (pos.func_177982_a(a, b, c) != startpos) {
                        final IBlockState bs = worldIn.func_180495_p(pos.func_177982_a(a, b, c));
                        if (bs.func_177230_c() == BlocksTC.placeholderBars) {
                            worldIn.func_175656_a(pos.func_177982_a(a, b, c), Blocks.field_150411_aY.func_176223_P());
                        }
                        if (bs.func_177230_c() == BlocksTC.placeholderAnvil) {
                            worldIn.func_175656_a(pos.func_177982_a(a, b, c), Blocks.field_150467_bQ.func_176223_P());
                        }
                        if (bs.func_177230_c() == BlocksTC.placeholderCauldron) {
                            worldIn.func_175656_a(pos.func_177982_a(a, b, c), Blocks.field_150383_bp.func_176223_P());
                        }
                        if (bs.func_177230_c() == BlocksTC.placeholderTable) {
                            worldIn.func_175656_a(pos.func_177982_a(a, b, c), BlocksTC.tableStone.func_176223_P());
                        }
                    }
                }
            }
        }
        if (pos != startpos) {
            worldIn.func_175656_a(pos, Blocks.field_150331_J.func_176223_P().func_177226_a((IProperty)BlockPistonBase.field_176387_N, (Comparable)EnumFacing.UP));
        }
        BlockGolemBuilder.ignore = false;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        player.openGui((Object)Thaumcraft.instance, 19, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        return true;
    }
    
    static {
        BlockGolemBuilder.ignore = false;
    }
}
