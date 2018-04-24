package thaumcraft.common.blocks;

import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.state.*;
import java.util.*;

public class BlockTCDevice extends BlockTCTile
{
    public BlockTCDevice(final Material mat, final Class tc, final String name) {
        super(mat, tc, name);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        if (this instanceof IBlockFacingHorizontal) {
            bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)EnumFacing.NORTH);
        }
        else if (this instanceof IBlockFacing) {
            bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.UP);
        }
        if (this instanceof IBlockEnabled) {
            bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true);
        }
        this.func_180632_j(bs);
    }
    
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.func_176213_c(worldIn, pos, state);
        this.updateState(worldIn, pos, state);
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos frompos) {
        this.updateState(worldIn, pos, state);
        super.func_189540_a(state, worldIn, pos, blockIn, frompos);
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        if (this instanceof IBlockFacingHorizontal) {
            bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)(placer.func_70093_af() ? placer.func_174811_aO() : placer.func_174811_aO().func_176734_d()));
        }
        if (this instanceof IBlockFacing) {
            bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)(placer.func_70093_af() ? EnumFacing.func_190914_a(pos, placer).func_176734_d() : EnumFacing.func_190914_a(pos, placer)));
        }
        if (this instanceof IBlockEnabled) {
            bs = bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true);
        }
        return bs;
    }
    
    protected void updateState(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this instanceof IBlockEnabled) {
            final boolean flag = !worldIn.func_175640_z(pos);
            if (flag != (boolean)state.func_177229_b((IProperty)IBlockEnabled.ENABLED)) {
                worldIn.func_180501_a(pos, state.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)flag), 3);
            }
        }
    }
    
    public void updateFacing(final World world, final BlockPos pos, final EnumFacing face) {
        if (this instanceof IBlockFacing || this instanceof IBlockFacingHorizontal) {
            if (face == BlockStateUtils.getFacing(world.func_180495_p(pos))) {
                return;
            }
            if (this instanceof IBlockFacingHorizontal && face.func_176736_b() >= 0) {
                world.func_180501_a(pos, world.func_180495_p(pos).func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)face), 3);
            }
            if (this instanceof IBlockFacing) {
                world.func_180501_a(pos, world.func_180495_p(pos).func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)face), 3);
            }
        }
    }
    
    public IBlockState func_176203_a(final int meta) {
        IBlockState bs = this.func_176223_P();
        try {
            if (this instanceof IBlockFacingHorizontal) {
                bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)BlockStateUtils.getFacing(meta));
            }
            if (this instanceof IBlockFacing) {
                bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)BlockStateUtils.getFacing(meta));
            }
            if (this instanceof IBlockEnabled) {
                bs = bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)BlockStateUtils.isEnabled(meta));
            }
        }
        catch (Exception ex) {}
        return bs;
    }
    
    public int func_176201_c(final IBlockState state) {
        final byte b0 = 0;
        int i = (this instanceof IBlockFacingHorizontal) ? (b0 | ((EnumFacing)state.func_177229_b((IProperty)IBlockFacingHorizontal.FACING)).func_176745_a()) : ((this instanceof IBlockFacing) ? (b0 | ((EnumFacing)state.func_177229_b((IProperty)IBlockFacing.FACING)).func_176745_a()) : b0);
        if (this instanceof IBlockEnabled && !(boolean)state.func_177229_b((IProperty)IBlockEnabled.ENABLED)) {
            i |= 0x8;
        }
        return i;
    }
    
    protected BlockStateContainer func_180661_e() {
        final ArrayList<IProperty> ip = new ArrayList<IProperty>();
        if (this instanceof IBlockFacingHorizontal) {
            ip.add((IProperty)IBlockFacingHorizontal.FACING);
        }
        if (this instanceof IBlockFacing) {
            ip.add((IProperty)IBlockFacing.FACING);
        }
        if (this instanceof IBlockEnabled) {
            ip.add((IProperty)IBlockEnabled.ENABLED);
        }
        return (ip.size() == 0) ? super.func_180661_e() : new BlockStateContainer((Block)this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
    }
}
