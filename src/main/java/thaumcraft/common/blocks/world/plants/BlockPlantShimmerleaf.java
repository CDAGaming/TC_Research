package thaumcraft.common.blocks.world.plants;

import net.minecraft.block.material.*;
import thaumcraft.common.config.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.*;
import net.minecraft.world.*;
import java.util.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockPlantShimmerleaf extends BlockBush
{
    public BlockPlantShimmerleaf() {
        super(Material.field_151585_k);
        this.func_149663_c("shimmerleaf");
        this.setRegistryName("thaumcraft", "shimmerleaf");
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149672_a(SoundType.field_185850_c);
        this.func_149715_a(0.4f);
    }
    
    protected boolean func_185514_i(final IBlockState state) {
        return state.func_177230_c() == Blocks.field_150349_c || state.func_177230_c() == Blocks.field_150346_d;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
        return EnumPlantType.Plains;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        if (rand.nextInt(3) == 0) {
            final float xr = (float)(pos.func_177958_n() + 0.5f + rand.nextGaussian() * 0.1);
            final float yr = (float)(pos.func_177956_o() + 0.4f + rand.nextGaussian() * 0.1);
            final float zr = (float)(pos.func_177952_p() + 0.5f + rand.nextGaussian() * 0.1);
            FXDispatcher.INSTANCE.drawWispyMotes(xr, yr, zr, rand.nextGaussian() * 0.01, rand.nextGaussian() * 0.01, rand.nextGaussian() * 0.01, 10, 0.3f + world.field_73012_v.nextFloat() * 0.3f, 0.7f + world.field_73012_v.nextFloat() * 0.3f, 0.7f + world.field_73012_v.nextFloat() * 0.3f, 0.0f);
        }
    }
}
