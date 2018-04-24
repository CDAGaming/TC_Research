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
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockPlantCinderpearl extends BlockBush
{
    public BlockPlantCinderpearl() {
        super(Material.field_151585_k);
        this.func_149663_c("cinderpearl");
        this.setRegistryName("thaumcraft", "cinderpearl");
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149672_a(SoundType.field_185850_c);
        this.func_149715_a(0.5f);
    }
    
    protected boolean func_185514_i(final IBlockState state) {
        return state.func_177230_c() == Blocks.field_150354_m || state.func_177230_c() == Blocks.field_150346_d || state.func_177230_c() == Blocks.field_150406_ce || state.func_177230_c() == Blocks.field_150405_ch;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
        return EnumPlantType.Desert;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        if (rand.nextBoolean()) {
            final float xr = pos.func_177958_n() + 0.5f + (rand.nextFloat() - rand.nextFloat()) * 0.1f;
            final float yr = pos.func_177956_o() + 0.6f + (rand.nextFloat() - rand.nextFloat()) * 0.1f;
            final float zr = pos.func_177952_p() + 0.5f + (rand.nextFloat() - rand.nextFloat()) * 0.1f;
            world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (double)xr, (double)yr, (double)zr, 0.0, 0.0, 0.0, new int[0]);
            world.func_175688_a(EnumParticleTypes.FLAME, (double)xr, (double)yr, (double)zr, 0.0, 0.0, 0.0, new int[0]);
        }
    }
}
