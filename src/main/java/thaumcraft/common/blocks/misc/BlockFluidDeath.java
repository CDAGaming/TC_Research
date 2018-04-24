package thaumcraft.common.blocks.misc;

import net.minecraftforge.fluids.*;
import thaumcraft.common.config.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import thaumcraft.api.damagesource.*;
import java.util.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.material.*;

public class BlockFluidDeath extends BlockFluidClassic
{
    public static final Material FLUID_DEATH_MATERIAL;
    
    public BlockFluidDeath() {
        super((Fluid)ConfigBlocks.FluidDeath.instance, BlockFluidDeath.FLUID_DEATH_MATERIAL);
        this.setRegistryName("liquid_death");
        this.func_149663_c("liquid_death");
        this.func_149647_a(ConfigItems.TABTC);
        this.setQuantaPerBlock(4);
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        if (!world.field_72995_K && entity instanceof EntityLivingBase) {
            entity.func_70097_a(DamageSourceThaumcraft.dissolve, (float)(4 - this.func_176201_c(state) + 1));
        }
    }
    
    public int getQuanta() {
        return this.quantaPerBlock;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        if (rand.nextInt(20) == 0) {
            final int meta = this.func_176201_c(state);
            final float h = rand.nextFloat() * 0.075f;
            final FXSlimyBubble ef = new FXSlimyBubble(world, pos.func_177958_n() + rand.nextFloat(), pos.func_177956_o() + 0.1f + 0.225f * (4 - meta), pos.func_177952_p() + rand.nextFloat(), 0.075f + h);
            ef.func_82338_g(0.8f);
            ef.func_70538_b(0.3f - rand.nextFloat() * 0.1f, 0.0f, 0.4f + rand.nextFloat() * 0.1f);
            ParticleEngine.addEffect(world, ef);
        }
        if (rand.nextInt(50) == 0) {
            final double var21 = pos.func_177958_n() + rand.nextFloat();
            final double var22 = pos.func_177956_o() + this.getMaxRenderHeightMeta() / 4.0f;
            final double var23 = pos.func_177952_p() + rand.nextFloat();
            world.func_184134_a(var21, var22, var23, SoundEvents.field_187662_cZ, SoundCategory.BLOCKS, 0.1f + rand.nextFloat() * 0.1f, 0.9f + rand.nextFloat() * 0.15f, false);
        }
    }
    
    static {
        FLUID_DEATH_MATERIAL = (Material)new MaterialLiquid(MapColor.field_151678_z);
    }
}
