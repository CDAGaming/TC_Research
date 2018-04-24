package thaumcraft.common.blocks.misc;

import net.minecraftforge.fluids.*;
import thaumcraft.common.config.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.potions.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.potion.*;
import java.util.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.material.*;

public class BlockFluidPure extends BlockFluidClassic
{
    public static final Material FLUID_PURE_MATERIAL;
    
    public BlockFluidPure() {
        super((Fluid)ConfigBlocks.FluidPure.instance, BlockFluidPure.FLUID_PURE_MATERIAL);
        this.setRegistryName("purifying_fluid");
        this.func_149663_c("purifying_fluid");
        this.func_149647_a(ConfigItems.TABTC);
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        if (!world.field_72995_K && this.isSourceBlock((IBlockAccess)world, pos) && entity instanceof EntityPlayer && !((EntityPlayer)entity).func_70644_a(PotionWarpWard.instance)) {
            final int warp = ThaumcraftCapabilities.getWarp((EntityPlayer)entity).get(IPlayerWarp.EnumWarpType.PERMANENT);
            int div = 1;
            if (warp > 0) {
                div = (int)Math.sqrt(warp);
                if (div < 1) {
                    div = 1;
                }
            }
            ((EntityPlayer)entity).func_70690_d(new PotionEffect(PotionWarpWard.instance, Math.min(32000, 200000 / div), 0, true, true));
            world.func_175698_g(pos);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        final int meta = this.func_176201_c(state);
        if (rand.nextInt(10) == 0) {
            final FXGeneric fb = new FXGeneric(world, pos.func_177958_n() + rand.nextFloat(), pos.func_177956_o() + 0.125f * (8 - meta), pos.func_177952_p() + rand.nextFloat(), 0.0, 0.0, 0.0);
            fb.func_187114_a(10 + world.field_73012_v.nextInt(10));
            fb.setScale(world.field_73012_v.nextFloat() * 0.3f + 0.3f);
            fb.func_70538_b(1.0f, 1.0f, 1.0f);
            fb.setRandomMovementScale(0.001f, 0.001f, 0.001f);
            fb.setGravity(-0.01f);
            fb.func_82338_g(0.25f);
            fb.setParticle(64);
            fb.setFinalFrames(65, 66);
            ParticleEngine.addEffect(world, fb);
        }
        if (rand.nextInt(50) == 0) {
            final double var21 = pos.func_177958_n() + rand.nextFloat();
            final double var22 = pos.func_177956_o() + 0.5;
            final double var23 = pos.func_177952_p() + rand.nextFloat();
            world.func_184134_a(var21, var22, var23, SoundEvents.field_187662_cZ, SoundCategory.BLOCKS, 0.1f + rand.nextFloat() * 0.1f, 0.9f + rand.nextFloat() * 0.15f, false);
        }
    }
    
    static {
        FLUID_PURE_MATERIAL = (Material)new MaterialLiquid(MapColor.field_151680_x);
    }
}
