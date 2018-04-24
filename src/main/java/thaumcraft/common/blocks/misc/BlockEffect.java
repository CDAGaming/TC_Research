package thaumcraft.common.blocks.misc;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.common.lib.events.*;
import thaumcraft.api.entities.*;
import java.util.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.lib.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;

public class BlockEffect extends BlockTC
{
    public BlockEffect(final String name) {
        super(Material.field_151579_a, name);
        this.func_149675_a(true);
        this.field_149781_w = 999.0f;
        this.func_149715_a(0.5f);
    }
    
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        if (state.func_177230_c() != this) {
            return super.getLightValue(state, world, pos);
        }
        if (state.func_177230_c() == BlocksTC.effectGlimmer) {
            return 15;
        }
        return super.getLightValue(state, world, pos);
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        if (state.func_177230_c() == BlocksTC.effectShock) {
            if (entity instanceof EntityLivingBase) {
                ServerEvents.addRunnableServer(world, new Runnable() {
                    @Override
                    public void run() {
                        entity.func_70097_a(DamageSource.field_76376_m, 1.0f);
                        final PotionEffect pe = new PotionEffect(MobEffects.field_76421_d, 20, 0, true, true);
                        ((EntityLivingBase)entity).func_70690_d(pe);
                    }
                }, 0);
            }
            if (!world.field_72995_K && world.field_73012_v.nextInt(100) == 0) {
                world.func_175698_g(pos);
            }
        }
        else if (state.func_177230_c() == BlocksTC.effectSap && !(entity instanceof IEldritchMob) && entity instanceof EntityLivingBase && !((EntityLivingBase)entity).func_70644_a(MobEffects.field_82731_v)) {
            ServerEvents.addRunnableServer(world, new Runnable() {
                @Override
                public void run() {
                    final PotionEffect pe0 = new PotionEffect(MobEffects.field_82731_v, 40, 0, true, true);
                    ((EntityLivingBase)entity).func_70690_d(pe0);
                    final PotionEffect pe2 = new PotionEffect(MobEffects.field_76421_d, 40, 1, true, true);
                    ((EntityLivingBase)entity).func_70690_d(pe2);
                    final PotionEffect pe3 = new PotionEffect(MobEffects.field_76438_s, 40, 1, true, true);
                    ((EntityLivingBase)entity).func_70690_d(pe3);
                }
            }, 0);
        }
    }
    
    public void func_180650_b(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.func_180650_b(worldIn, pos, state, rand);
        if (!worldIn.field_72995_K && state.func_177230_c() != BlocksTC.effectGlimmer) {
            worldIn.func_175698_g(pos);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World w, final BlockPos pos, final Random r) {
        if (state.func_177230_c() != BlocksTC.effectGlimmer) {
            final float h = r.nextFloat() * 0.33f;
            if (state.func_177230_c() == BlocksTC.effectShock) {
                FXDispatcher.INSTANCE.spark(pos.func_177958_n() + w.field_73012_v.nextFloat(), pos.func_177956_o() + 0.1515f + h / 2.0f, pos.func_177952_p() + w.field_73012_v.nextFloat(), 3.0f + h * 6.0f, 0.65f + w.field_73012_v.nextFloat() * 0.1f, 1.0f, 1.0f, 0.8f);
            }
            else {
                FXDispatcher.INSTANCE.spark(pos.func_177958_n() + w.field_73012_v.nextFloat(), pos.func_177956_o() + 0.1515f + h / 2.0f, pos.func_177952_p() + w.field_73012_v.nextFloat(), 3.0f + h * 6.0f, 0.3f - w.field_73012_v.nextFloat() * 0.1f, 0.0f, 0.5f + w.field_73012_v.nextFloat() * 0.2f, 1.0f);
            }
            if (r.nextInt(50) == 0) {
                w.func_184134_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundsTC.jacobs, SoundCategory.AMBIENT, 0.25f, 1.0f + (r.nextFloat() - r.nextFloat()) * 0.2f, false);
            }
        }
    }
    
    public boolean isAir(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return true;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public boolean func_176200_f(final IBlockAccess worldIn, final BlockPos pos) {
        return true;
    }
    
    public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos, final EntityPlayer player) {
        return null;
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing o) {
        return false;
    }
    
    public boolean func_176205_b(final IBlockAccess worldIn, final BlockPos pos) {
        return true;
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return null;
    }
    
    public boolean func_176209_a(final IBlockState state, final boolean hitIfLiquid) {
        return false;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
}
