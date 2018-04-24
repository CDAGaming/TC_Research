package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import java.util.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class BlockArcaneEar extends BlockTCDevice implements IBlockFacing, IBlockEnabled
{
    protected static final List<SoundEvent> INSTRUMENTS;
    
    public BlockArcaneEar(final String name) {
        super(Material.field_151575_d, TileArcaneEar.class, name);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149711_c(1.0f);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.UP);
        bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false);
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
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing);
        bs = bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false);
        return bs;
    }
    
    @Override
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileArcaneEar tile = (TileArcaneEar)worldIn.func_175625_s(pos);
        if (tile != null) {
            tile.updateTone();
        }
    }
    
    @Override
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        final TileArcaneEar tile = (TileArcaneEar)worldIn.func_175625_s(pos);
        if (tile != null) {
            tile.updateTone();
        }
        if (!worldIn.func_180495_p(pos.func_177972_a(BlockStateUtils.getFacing(state).func_176734_d())).isSideSolid((IBlockAccess)worldIn, pos.func_177972_a(BlockStateUtils.getFacing(state).func_176734_d()), BlockStateUtils.getFacing(state))) {
            this.func_176226_b(worldIn, pos, this.func_176223_P(), 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        final TileArcaneEar tile = (TileArcaneEar)world.func_175625_s(pos);
        if (tile != null) {
            tile.changePitch();
            tile.triggerNote(world, pos, true);
        }
        return true;
    }
    
    public boolean func_149744_f(final IBlockState state) {
        return true;
    }
    
    public int func_180656_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return BlockStateUtils.isEnabled(state.func_177230_c().func_176201_c(state)) ? 15 : 0;
    }
    
    public int func_176211_b(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return BlockStateUtils.isEnabled(state.func_177230_c().func_176201_c(state)) ? 15 : 0;
    }
    
    public boolean func_176198_a(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return worldIn.func_180495_p(pos.func_177972_a(side.func_176734_d())).isSideSolid((IBlockAccess)worldIn, pos.func_177972_a(side.func_176734_d()), side);
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
            default: {
                return new AxisAlignedBB(0.0, 0.125, 0.125, 0.375, 0.875, 0.875);
            }
        }
    }
    
    protected SoundEvent getSound(int p_185576_1_) {
        if (p_185576_1_ < 0 || p_185576_1_ >= BlockArcaneEar.INSTRUMENTS.size()) {
            p_185576_1_ = 0;
        }
        return BlockArcaneEar.INSTRUMENTS.get(p_185576_1_);
    }
    
    @Override
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int par5, final int par6) {
        super.func_189539_a(state, worldIn, pos, par5, par6);
        final float var7 = (float)Math.pow(2.0, (par6 - 12) / 12.0);
        worldIn.func_184133_a((EntityPlayer)null, pos, this.getSound(par5), SoundCategory.BLOCKS, 3.0f, var7);
        worldIn.func_175688_a(EnumParticleTypes.NOTE, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, par6 / 24.0, 0.0, 0.0, new int[0]);
        return true;
    }
    
    static {
        INSTRUMENTS = Lists.newArrayList((Object[])new SoundEvent[] { SoundEvents.field_187682_dG, SoundEvents.field_187676_dE, SoundEvents.field_187688_dI, SoundEvents.field_187685_dH, SoundEvents.field_187679_dF });
    }
}
