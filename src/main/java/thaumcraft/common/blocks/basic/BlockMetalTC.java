package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class BlockMetalTC extends BlockTC
{
    public BlockMetalTC(final String name) {
        super(Material.field_151573_f, name);
        this.func_149711_c(4.0f);
        this.func_149752_b(10.0f);
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    public boolean isBeaconBase(final IBlockAccess world, final BlockPos pos, final BlockPos beacon) {
        return true;
    }
}
