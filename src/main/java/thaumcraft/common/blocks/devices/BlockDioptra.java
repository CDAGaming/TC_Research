package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class BlockDioptra extends BlockTCDevice implements IBlockEnabled
{
    public BlockDioptra() {
        super(Material.field_151576_e, TileDioptra.class, "dioptra");
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileDioptra) {
            final float r = ((TileDioptra)tile).grid_amt[84] / 64.0f;
            return MathHelper.func_76141_d(r * 14.0f) + ((r > 0.0f) ? 1 : 0);
        }
        return super.func_180641_l(state, world, pos);
    }
    
    @Override
    protected void updateState(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final boolean b = (boolean)state.func_177229_b((IProperty)IBlockEnabled.ENABLED);
        world.func_180501_a(pos, state.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)!b), 3);
        return true;
    }
}
