package thaumcraft.common.tiles.misc;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import thaumcraft.client.fx.*;
import net.minecraft.world.*;

public class TileNitor extends TileEntity implements ITickable
{
    int count;
    
    public TileNitor() {
        this.count = 0;
    }
    
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState, final IBlockState newState) {
        return oldState.func_177230_c() != newState.func_177230_c();
    }
    
    public void func_73660_a() {
        if (this.field_145850_b.field_72995_K) {
            final IBlockState state = this.field_145850_b.func_180495_p(this.func_174877_v());
            FXDispatcher.INSTANCE.drawNitorFlames(this.field_174879_c.func_177958_n() + 0.5f + this.field_145850_b.field_73012_v.nextGaussian() * 0.025, this.field_174879_c.func_177956_o() + 0.45f + this.field_145850_b.field_73012_v.nextGaussian() * 0.025, this.field_174879_c.func_177952_p() + 0.5f + this.field_145850_b.field_73012_v.nextGaussian() * 0.025, this.field_145850_b.field_73012_v.nextGaussian() * 0.0025, this.field_145850_b.field_73012_v.nextFloat() * 0.06, this.field_145850_b.field_73012_v.nextGaussian() * 0.0025, state.func_177230_c().func_180659_g(state, (IBlockAccess)this.field_145850_b, this.func_174877_v()).field_76291_p, 0);
            if (this.count++ % 10 == 0) {
                FXDispatcher.INSTANCE.drawNitorCore(this.field_174879_c.func_177958_n() + 0.5f, this.field_174879_c.func_177956_o() + 0.49f, this.field_174879_c.func_177952_p() + 0.5f, 0.0, 0.0, 0.0);
            }
        }
    }
}
