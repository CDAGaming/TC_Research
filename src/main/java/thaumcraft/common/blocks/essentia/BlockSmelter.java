package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import thaumcraft.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.inventory.*;
import thaumcraft.api.aura.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockSmelter extends BlockTCDevice implements IBlockEnabled, IBlockFacingHorizontal
{
    public BlockSmelter(final String name) {
        super(Material.field_151573_f, TileSmelter.class, name);
        this.func_149672_a(SoundType.field_185852_e);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)EnumFacing.NORTH);
        bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false);
        this.func_180632_j(bs);
    }
    
    @Override
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)placer.func_174811_aO().func_176734_d());
        bs = bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false);
        return bs;
    }
    
    @Override
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te != null && te instanceof TileSmelter) {
            ((TileSmelter)te).checkNeighbours();
        }
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!world.field_72995_K && !player.func_70093_af()) {
            player.openGui((Object)Thaumcraft.instance, 9, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        }
        return true;
    }
    
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return BlockStateUtils.isEnabled(world.func_180495_p(pos).func_177230_c().func_176201_c(world.func_180495_p(pos))) ? 13 : super.getLightValue(state, world, pos);
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof IInventory) {
            return Container.func_94526_b((IInventory)te);
        }
        return 0;
    }
    
    public static void setFurnaceState(final World world, final BlockPos pos, final boolean state) {
        if (state == BlockStateUtils.isEnabled(world.func_180495_p(pos).func_177230_c().func_176201_c(world.func_180495_p(pos)))) {
            return;
        }
        final TileEntity tileentity = world.func_175625_s(pos);
        BlockSmelter.keepInventory = true;
        world.func_180501_a(pos, world.func_180495_p(pos).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)state), 3);
        world.func_180501_a(pos, world.func_180495_p(pos).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)state), 3);
        if (tileentity != null) {
            tileentity.func_145829_t();
            world.func_175690_a(pos, tileentity);
        }
        BlockSmelter.keepInventory = false;
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        if (tileentity instanceof TileSmelter && !worldIn.field_72995_K && ((TileSmelter)tileentity).vis > 0) {
            final int ess = ((TileSmelter)tileentity).vis;
            AuraHelper.polluteAura(worldIn, pos, ess, true);
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World w, final BlockPos pos, final Random r) {
        if (BlockStateUtils.isEnabled(state)) {
            final float f = pos.func_177958_n() + 0.5f;
            final float f2 = pos.func_177956_o() + 0.2f + r.nextFloat() * 5.0f / 16.0f;
            final float f3 = pos.func_177952_p() + 0.5f;
            final float f4 = 0.52f;
            final float f5 = r.nextFloat() * 0.5f - 0.25f;
            if (BlockStateUtils.getFacing(state) == EnumFacing.WEST) {
                w.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (double)(f - f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0, new int[0]);
                w.func_175688_a(EnumParticleTypes.FLAME, (double)(f - f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0, new int[0]);
            }
            if (BlockStateUtils.getFacing(state) == EnumFacing.EAST) {
                w.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (double)(f + f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0, new int[0]);
                w.func_175688_a(EnumParticleTypes.FLAME, (double)(f + f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0, new int[0]);
            }
            if (BlockStateUtils.getFacing(state) == EnumFacing.NORTH) {
                w.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (double)(f + f5), (double)f2, (double)(f3 - f4), 0.0, 0.0, 0.0, new int[0]);
                w.func_175688_a(EnumParticleTypes.FLAME, (double)(f + f5), (double)f2, (double)(f3 - f4), 0.0, 0.0, 0.0, new int[0]);
            }
            if (BlockStateUtils.getFacing(state) == EnumFacing.SOUTH) {
                w.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (double)(f + f5), (double)f2, (double)(f3 + f4), 0.0, 0.0, 0.0, new int[0]);
                w.func_175688_a(EnumParticleTypes.FLAME, (double)(f + f5), (double)f2, (double)(f3 + f4), 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
}
