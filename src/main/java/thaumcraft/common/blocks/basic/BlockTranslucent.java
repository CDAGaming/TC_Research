package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;

public class BlockTranslucent extends BlockTC
{
    public BlockTranslucent(final String name) {
        super(Material.field_151592_s, name);
        this.func_149711_c(0.5f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public boolean isBeaconBase(final IBlockAccess world, final BlockPos pos, final BlockPos beacon) {
        return true;
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public EnumPushReaction func_149656_h(final IBlockState state) {
        return EnumPushReaction.NORMAL;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_176225_a(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side) {
        final IBlockState iblockstate = blockAccess.func_180495_p(pos.func_177972_a(side));
        final Block block = iblockstate.func_177230_c();
        return block != this && super.func_176225_a(blockState, blockAccess, pos, side);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    public boolean func_149662_c(final IBlockState iblockstate) {
        return false;
    }
}
