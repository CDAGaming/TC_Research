package thaumcraft.common.blocks.world.taint;

import thaumcraft.api.*;
import thaumcraft.common.blocks.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.entities.monster.tainted.*;
import thaumcraft.api.aura.*;
import thaumcraft.api.blocks.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.math.*;

public class BlockTaintFeature extends BlockTC implements ITaintBlock
{
    public BlockTaintFeature() {
        super(ThaumcraftMaterials.MATERIAL_TAINT, "taint_feature");
        this.func_149711_c(0.1f);
        this.func_149715_a(0.625f);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.UP);
        this.func_180632_j(bs);
        this.func_149675_a(true);
    }
    
    protected boolean func_149700_E() {
        return false;
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.field_72995_K) {
            if (worldIn.field_73012_v.nextFloat() < 0.333f) {
                final Entity e = (Entity)new EntityTaintCrawler(worldIn);
                e.func_70012_b((double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f), (float)worldIn.field_73012_v.nextInt(360), 0.0f);
                worldIn.func_72838_d(e);
            }
            else {
                AuraHelper.polluteAura(worldIn, pos, 1.0f, true);
            }
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    @Override
    public void die(final World world, final BlockPos pos, final IBlockState blockState) {
        world.func_175656_a(pos, BlocksTC.fluxGoo.func_176223_P());
    }
    
    public void func_180650_b(final World world, final BlockPos pos, final IBlockState state, final Random random) {
        if (!world.field_72995_K) {
            if (!TaintHelper.isNearTaintSeed(world, pos) && random.nextInt(10) == 0) {
                this.die(world, pos, state);
                return;
            }
            TaintHelper.spreadFibres(world, pos);
            if (world.func_180495_p(pos.func_177977_b()).func_177230_c() == BlocksTC.taintLog && world.func_180495_p(pos.func_177977_b()).func_177229_b((IProperty)BlockTaintLog.AXIS) == EnumFacing.Axis.Y && world.field_73012_v.nextInt(100) == 0) {
                world.func_175656_a(pos, BlocksTC.taintGeyser.func_176223_P());
            }
        }
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean canSilkHarvest(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player) {
        return true;
    }
    
    public int func_185484_c(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return 200;
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (!worldIn.field_72995_K && !worldIn.func_180495_p(pos.func_177972_a(BlockStateUtils.getFacing(state).func_176734_d())).isSideSolid((IBlockAccess)worldIn, pos.func_177972_a(BlockStateUtils.getFacing(state).func_176734_d()), BlockStateUtils.getFacing(state))) {
            worldIn.func_175698_g(pos);
        }
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing);
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
        final ArrayList<IProperty> ip = new ArrayList<IProperty>();
        ip.add((IProperty)IBlockFacing.FACING);
        return new BlockStateContainer((Block)this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_176201_c(state));
        switch (facing.ordinal()) {
            case 0: {
                return new AxisAlignedBB(0.125, 0.625, 0.125, 0.875, 1.0, 0.875);
            }
            case 1: {
                return new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.375, 0.875);
            }
            case 2: {
                return new AxisAlignedBB(0.125, 0.125, 0.625, 0.875, 0.875, 1.0);
            }
            case 3: {
                return new AxisAlignedBB(0.125, 0.125, 0.0, 0.875, 0.875, 0.375);
            }
            case 4: {
                return new AxisAlignedBB(0.625, 0.125, 0.125, 1.0, 0.875, 0.875);
            }
            case 5: {
                return new AxisAlignedBB(0.0, 0.125, 0.125, 0.375, 0.875, 0.875);
            }
            default: {
                return super.func_185496_a(state, source, pos);
            }
        }
    }
}
