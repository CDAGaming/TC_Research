package thaumcraft.common.blocks.world.taint;

import thaumcraft.api.*;
import net.minecraftforge.fluids.*;
import thaumcraft.common.config.*;
import thaumcraft.common.lib.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;
import java.util.*;
import thaumcraft.api.aura.*;
import thaumcraft.api.blocks.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockFluxGoo extends BlockFluidFinite
{
    public BlockFluxGoo() {
        super((Fluid)ConfigBlocks.FluidFluxGoo.instance, ThaumcraftMaterials.MATERIAL_TAINT);
        this.setRegistryName("flux_goo");
        this.func_149663_c("flux_goo");
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149672_a(SoundsTC.GORE);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockFluxGoo.LEVEL, (Comparable)7));
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.GORE;
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        final int md = (int)state.func_177229_b((IProperty)BlockFluxGoo.LEVEL);
        if (entity instanceof EntityThaumicSlime) {
            final EntityThaumicSlime slime = (EntityThaumicSlime)entity;
            if (slime.func_70809_q() < md && world.field_73012_v.nextBoolean()) {
                slime.func_70799_a(slime.func_70809_q() + 1, true);
                if (md > 1) {
                    world.func_180501_a(pos, state.func_177226_a((IProperty)BlockFluxGoo.LEVEL, (Comparable)(md - 1)), 2);
                }
                else {
                    world.func_175698_g(pos);
                }
            }
        }
        else {
            entity.field_70159_w *= 1.0f - this.getQuantaPercentage((IBlockAccess)world, pos);
            entity.field_70179_y *= 1.0f - this.getQuantaPercentage((IBlockAccess)world, pos);
            if (entity instanceof EntityLivingBase) {
                final PotionEffect pe = new PotionEffect(PotionVisExhaust.instance, 600, md / 3, true, true);
                pe.getCurativeItems().clear();
                ((EntityLivingBase)entity).func_70690_d(pe);
            }
        }
    }
    
    public void func_180650_b(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
        final int meta = (int)state.func_177229_b((IProperty)BlockFluxGoo.LEVEL);
        if (meta >= 2 && meta < 6 && world.func_175623_d(pos.func_177984_a()) && rand.nextInt(50) == 0) {
            world.func_175698_g(pos);
            final EntityThaumicSlime slime = new EntityThaumicSlime(world);
            slime.func_70012_b((double)(pos.func_177958_n() + 0.5f), (double)pos.func_177956_o(), (double)(pos.func_177952_p() + 0.5f), 0.0f, 0.0f);
            slime.func_70799_a(1, true);
            world.func_72838_d((Entity)slime);
            slime.func_184185_a(SoundsTC.gore, 1.0f, 1.0f);
            return;
        }
        if (meta >= 6 && world.func_175623_d(pos.func_177984_a()) && rand.nextInt(50) == 0) {
            world.func_175698_g(pos);
            final EntityThaumicSlime slime = new EntityThaumicSlime(world);
            slime.func_70012_b((double)(pos.func_177958_n() + 0.5f), (double)pos.func_177956_o(), (double)(pos.func_177952_p() + 0.5f), 0.0f, 0.0f);
            slime.func_70799_a(2, true);
            world.func_72838_d((Entity)slime);
            slime.func_184185_a(SoundsTC.gore, 1.0f, 1.0f);
            return;
        }
        if (rand.nextInt(4) == 0) {
            if (meta == 0) {
                if (rand.nextBoolean()) {
                    AuraHelper.polluteAura(world, pos, 1.0f, true);
                    world.func_175698_g(pos);
                }
                else {
                    world.func_175656_a(pos, BlocksTC.taintFibre.func_176223_P());
                }
            }
            else {
                world.func_180501_a(pos, state.func_177226_a((IProperty)BlockFluxGoo.LEVEL, (Comparable)(meta - 1)), 2);
                AuraHelper.polluteAura(world, pos, 1.0f, true);
            }
            return;
        }
        super.func_180650_b(world, pos, state, rand);
    }
    
    public boolean func_176200_f(final IBlockAccess world, final BlockPos pos) {
        return (int)world.func_180495_p(pos).func_177229_b((IProperty)BlockFluxGoo.LEVEL) < 4;
    }
    
    public boolean isSideSolid(final IBlockState base_state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        final int meta = this.func_176201_c(state);
        if (rand.nextInt(44) <= meta) {
            final FXGeneric fb = new FXGeneric(world, pos.func_177958_n() + rand.nextFloat(), pos.func_177956_o() + 0.125f * meta, pos.func_177952_p() + rand.nextFloat(), 0.0, 0.0, 0.0);
            fb.func_187114_a(2 + world.field_73012_v.nextInt(3));
            fb.setScale(world.field_73012_v.nextFloat() * 0.3f + 0.2f);
            fb.func_70538_b(1.0f, 0.0f, 0.5f);
            fb.setRandomMovementScale(0.001f, 0.001f, 0.001f);
            fb.setGravity(-0.01f);
            fb.func_82338_g(0.25f);
            fb.func_70536_a(64);
            fb.setFinalFrames(65, 66);
            ParticleEngine.addEffect(world, fb);
        }
    }
    
    static {
        BlockFluxGoo.defaultDisplacements.put(BlocksTC.taintFibre, true);
    }
}
