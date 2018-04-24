package thaumcraft.common.blocks.basic;

import net.minecraft.block.*;
import net.minecraft.block.state.*;
import thaumcraft.common.config.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class BlockStairsTC extends BlockStairs
{
    public BlockStairsTC(final String name, final IBlockState modelState) {
        super(modelState);
        this.func_149663_c(name);
        this.setRegistryName("thaumcraft", name);
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149713_g(0);
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        if (this.func_149688_o(this.func_176223_P()) == Material.field_151575_d) {
            return 20;
        }
        return super.getFlammability(world, pos, face);
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        if (this.func_149688_o(this.func_176223_P()) == Material.field_151575_d) {
            return 5;
        }
        return super.getFireSpreadSpeed(world, pos, face);
    }
}
