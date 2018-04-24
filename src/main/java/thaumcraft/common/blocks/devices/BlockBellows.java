package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;

public class BlockBellows extends BlockTCDevice implements IBlockFacing, IBlockEnabled
{
    public BlockBellows() {
        super(Material.field_151575_d, TileBellows.class, "bellows");
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149711_c(1.0f);
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
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        if (this instanceof IBlockFacing) {
            bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing.func_176734_d());
        }
        if (this instanceof IBlockEnabled) {
            bs = bs.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true);
        }
        return bs;
    }
}
