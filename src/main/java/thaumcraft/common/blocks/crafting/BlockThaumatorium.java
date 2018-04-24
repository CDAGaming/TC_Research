package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;
import java.util.*;
import net.minecraft.item.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;

public class BlockThaumatorium extends BlockTCDevice implements IBlockFacingHorizontal
{
    boolean top;
    
    public BlockThaumatorium(final boolean top) {
        super(Material.field_151573_f, null, top ? "thaumatorium_top" : "thaumatorium");
        this.func_149672_a(SoundType.field_185852_e);
        this.func_149647_a((CreativeTabs)null);
        this.top = top;
    }
    
    @Override
    public TileEntity func_149915_a(final World world, final int metadata) {
        if (!this.top) {
            return new TileThaumatorium();
        }
        if (this.top) {
            return new TileThaumatoriumTop();
        }
        return null;
    }
    
    @Override
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return this.top ? EnumBlockRenderType.INVISIBLE : EnumBlockRenderType.MODEL;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!world.field_72995_K && !player.func_70093_af()) {
            if (!this.top) {
                player.openGui((Object)Thaumcraft.instance, 3, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
            }
            else {
                player.openGui((Object)Thaumcraft.instance, 3, world, pos.func_177977_b().func_177958_n(), pos.func_177977_b().func_177956_o(), pos.func_177977_b().func_177952_p());
            }
        }
        return true;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150898_a(BlocksTC.metalAlchemical);
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 2;
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this.top && worldIn.func_180495_p(pos.func_177977_b()).func_177230_c() == BlocksTC.thaumatorium) {
            worldIn.func_175656_a(pos.func_177977_b(), BlocksTC.metalAlchemical.func_176223_P());
        }
        if (!this.top && worldIn.func_180495_p(pos.func_177984_a()).func_177230_c() == BlocksTC.thaumatoriumTop) {
            worldIn.func_175656_a(pos.func_177984_a(), BlocksTC.metalAlchemical.func_176223_P());
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    @Override
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (!this.top && worldIn.func_180495_p(pos.func_177977_b()).func_177230_c() != BlocksTC.crucible) {
            worldIn.func_175656_a(pos, BlocksTC.metalAlchemical.func_176223_P());
            if (worldIn.func_180495_p(pos.func_177984_a()).func_177230_c() == BlocksTC.thaumatoriumTop) {
                worldIn.func_175656_a(pos.func_177984_a(), BlocksTC.metalAlchemical.func_176223_P());
            }
        }
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return !this.top;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileThaumatorium) {
            return Container.func_94526_b((IInventory)tile);
        }
        return super.func_180641_l(state, world, pos);
    }
}
