package thaumcraft.common.blocks.world.plants;

import net.minecraft.block.material.*;
import thaumcraft.common.config.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import java.util.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockPlantVishroom extends BlockBush
{
    public BlockPlantVishroom() {
        super(Material.field_151585_k);
        this.func_149663_c("vishroom");
        this.setRegistryName("thaumcraft", "vishroom");
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149672_a(SoundType.field_185850_c);
        this.func_149715_a(0.4f);
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return state.func_185913_b();
    }
    
    public void func_180634_a(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (!worldIn.field_72995_K && entityIn instanceof EntityLivingBase && worldIn.field_73012_v.nextInt(5) == 0) {
            ((EntityLivingBase)entityIn).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200, 0));
        }
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
        return EnumPlantType.Cave;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        if (rand.nextInt(3) == 0) {
            final float xr = pos.func_177958_n() + 0.5f + (rand.nextFloat() - rand.nextFloat()) * 0.4f;
            final float yr = pos.func_177956_o() + 0.3f;
            final float zr = pos.func_177952_p() + 0.5f + (rand.nextFloat() - rand.nextFloat()) * 0.4f;
            FXDispatcher.INSTANCE.drawWispyMotes(xr, yr, zr, 0.0, 0.0, 0.0, 10, 0.5f, 0.3f, 0.8f, 0.001f);
        }
    }
}
