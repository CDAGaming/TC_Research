package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class BlockPlanksTC extends BlockTC
{
    public BlockPlanksTC(final String name) {
        super(Material.field_151575_d, name);
        this.setHarvestLevel("axe", 0);
        this.func_149711_c(2.0f);
        this.func_149672_a(SoundType.field_185848_a);
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 20;
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 5;
    }
}
