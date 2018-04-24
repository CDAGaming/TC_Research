package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;

public class BlockStoneTC extends BlockTC
{
    private boolean spawn;
    
    public BlockStoneTC(final String name, final boolean spawn) {
        super(Material.field_151576_e, name);
        this.spawn = spawn;
        this.func_149711_c(2.0f);
        this.func_149752_b(10.0f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public boolean isBeaconBase(final IBlockAccess world, final BlockPos pos, final BlockPos beacon) {
        return true;
    }
    
    public boolean canEntityDestroy(final IBlockState state, final IBlockAccess world, final BlockPos pos, final Entity entity) {
        return this.field_149782_v >= 0.0f;
    }
}
