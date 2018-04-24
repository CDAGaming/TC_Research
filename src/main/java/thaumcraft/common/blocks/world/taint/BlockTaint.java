package thaumcraft.common.blocks.world.taint;

import thaumcraft.common.blocks.*;
import thaumcraft.api.internal.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.entities.monster.tainted.*;
import thaumcraft.api.aura.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fluids.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import thaumcraft.common.entities.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.item.*;
import thaumcraft.common.config.*;

public class BlockTaint extends BlockTC implements ITaintBlock
{
    static Random r;
    static ArrayList<WeightedRandomLoot> pdrops;
    
    public BlockTaint(final String name) {
        super(ThaumcraftMaterials.MATERIAL_TAINT, name);
        this.func_149711_c(10.0f);
        this.func_149752_b(100.0f);
        this.func_149672_a(SoundsTC.GORE);
        this.func_149675_a(true);
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.GORE;
    }
    
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return MapColor.field_151678_z;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public void die(final World world, final BlockPos pos, final IBlockState state) {
        if (state.func_177230_c() == BlocksTC.taintRock) {
            world.func_175656_a(pos, BlocksTC.stonePorous.func_176223_P());
        }
        else if (state.func_177230_c() == BlocksTC.taintSoil) {
            world.func_175656_a(pos, Blocks.field_150346_d.func_176223_P());
        }
        else if (state.func_177230_c() == BlocksTC.taintCrust) {
            world.func_175656_a(pos, BlocksTC.fluxGoo.func_176223_P());
        }
        else if (state.func_177230_c() == BlocksTC.taintGeyser) {
            world.func_175656_a(pos, BlocksTC.fluxGoo.func_176223_P());
        }
        else {
            world.func_175698_g(pos);
        }
    }
    
    public void func_180650_b(final World world, final BlockPos pos, final IBlockState state, final Random random) {
        if (!world.field_72995_K) {
            if (!TaintHelper.isNearTaintSeed(world, pos) && random.nextInt(10) == 0) {
                this.die(world, pos, state);
                return;
            }
            if (state.func_177230_c() == BlocksTC.taintRock) {
                TaintHelper.spreadFibres(world, pos);
            }
            if (state.func_177230_c() == BlocksTC.taintCrust) {
                final Random r = new Random(pos.func_177986_g());
                if (this.tryToFall(world, pos, pos)) {
                    return;
                }
                if (world.func_175623_d(pos.func_177984_a())) {
                    boolean doIt = true;
                    final EnumFacing dir = EnumFacing.field_176754_o[random.nextInt(4)];
                    for (int a = 1; a < 4; ++a) {
                        if (!world.func_175623_d(pos.func_177972_a(dir).func_177979_c(a))) {
                            doIt = false;
                            break;
                        }
                        if (world.func_180495_p(pos.func_177979_c(a)).func_177230_c() != this) {
                            doIt = false;
                            break;
                        }
                    }
                    if (doIt && this.tryToFall(world, pos, pos.func_177972_a(dir))) {
                        return;
                    }
                }
            }
            else if (state.func_177230_c() == BlocksTC.taintGeyser) {
                if (world.field_73012_v.nextFloat() < 0.2 && world.func_175636_b((double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f), 32.0)) {
                    final Entity e = (Entity)new EntityTaintSwarm(world);
                    e.func_70012_b((double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() + 1.25f), (double)(pos.func_177952_p() + 0.5f), (float)world.field_73012_v.nextInt(360), 0.0f);
                    world.func_72838_d(e);
                }
                else if (AuraHelper.getFlux(world, pos) < 2.0f) {
                    AuraHelper.polluteAura(world, pos, 0.25f, true);
                }
            }
        }
    }
    
    public boolean canSilkHarvest(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player) {
        return true;
    }
    
    public void func_176199_a(final World world, final BlockPos pos, final Entity entity) {
        if (!world.field_72995_K && entity instanceof EntityLivingBase && !((EntityLivingBase)entity).func_70662_br() && world.field_73012_v.nextInt(250) == 0) {
            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(PotionFluxTaint.instance, 200, 0, false, true));
        }
    }
    
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int eventID, final int eventParam) {
        if (eventID == 1) {
            if (worldIn.field_72995_K) {
                worldIn.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187540_ab, SoundCategory.BLOCKS, 0.1f, 0.9f + worldIn.field_73012_v.nextFloat() * 0.2f);
            }
            return true;
        }
        return super.func_189539_a(state, worldIn, pos, eventID, eventParam);
    }
    
    public static boolean canFallBelow(final World world, final BlockPos pos) {
        final IBlockState bs = world.func_180495_p(pos);
        final Block l = bs.func_177230_c();
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                for (int yy = -1; yy <= 1; ++yy) {
                    if (Utils.isWoodLog((IBlockAccess)world, pos.func_177982_a(xx, yy, zz))) {
                        return false;
                    }
                }
            }
        }
        return l.isAir(bs, (IBlockAccess)world, pos) || ((l != BlocksTC.fluxGoo || (int)bs.func_177229_b((IProperty)BlockFluidFinite.LEVEL) < 4) && (l == Blocks.field_150480_ab || l == BlocksTC.taintFibre || l.func_176200_f((IBlockAccess)world, pos) || bs.func_185904_a() == Material.field_151586_h || bs.func_185904_a() == Material.field_151587_i));
    }
    
    private boolean tryToFall(final World world, final BlockPos pos, final BlockPos pos2) {
        if (!BlockTaintFibre.isOnlyAdjacentToTaint(world, pos)) {
            return false;
        }
        if (canFallBelow(world, pos2.func_177977_b()) && pos2.func_177956_o() >= 0) {
            final byte b0 = 32;
            if (world.func_175707_a(pos2.func_177982_a(-b0, -b0, -b0), pos2.func_177982_a((int)b0, (int)b0, (int)b0))) {
                if (!world.field_72995_K) {
                    final EntityFallingTaint entityfalling = new EntityFallingTaint(world, pos2.func_177958_n() + 0.5f, pos2.func_177956_o() + 0.5f, pos2.func_177952_p() + 0.5f, world.func_180495_p(pos), pos);
                    world.func_72838_d((Entity)entityfalling);
                    return true;
                }
            }
            else {
                world.func_175698_g(pos);
                BlockPos p2;
                for (p2 = new BlockPos((Vec3i)pos2); canFallBelow(world, p2.func_177977_b()) && p2.func_177956_o() > 0; p2 = p2.func_177977_b()) {}
                if (p2.func_177956_o() > 0) {
                    world.func_175656_a(p2, BlocksTC.taintCrust.func_176223_P());
                }
            }
        }
        return false;
    }
    
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        if (state.func_177230_c() == this && state.func_177230_c() == BlocksTC.taintRock) {
            final int rr = BlockTaint.r.nextInt(15) + fortune;
            if (rr > 13) {
                final List<ItemStack> ret = new ArrayList<ItemStack>();
                ret.add(ConfigItems.FLUX_CRYSTAL.func_77946_l());
                return ret;
            }
        }
        return (List<ItemStack>)super.getDrops(world, pos, state, fortune);
    }
    
    protected boolean func_149700_E() {
        return false;
    }
    
    static {
        BlockTaint.r = new Random(System.currentTimeMillis());
        BlockTaint.pdrops = null;
    }
}
