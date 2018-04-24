package thaumcraft.common.blocks.misc;

import thaumcraft.common.blocks.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.misc.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class BlockNitor extends BlockTC implements ITileEntityProvider
{
    public final EnumDyeColor dye;
    
    public BlockNitor(final String name, final EnumDyeColor dye) {
        super(Material.field_151594_q, name);
        this.func_149711_c(0.1f);
        this.func_149672_a(SoundType.field_185854_g);
        this.func_149715_a(1.0f);
        this.dye = dye;
    }
    
    public TileEntity func_149915_a(final World worldIn, final int meta) {
        return new TileNitor();
    }
    
    public boolean hasTileEntity(final IBlockState state) {
        return true;
    }
    
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return MapColor.func_193558_a(this.dye);
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.33000001311302185, 0.33000001311302185, 0.33000001311302185, 0.6600000262260437, 0.6600000262260437, 0.6600000262260437);
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return null;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
}
