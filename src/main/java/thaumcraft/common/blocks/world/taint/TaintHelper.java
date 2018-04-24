package thaumcraft.common.blocks.world.taint;

import java.util.concurrent.*;
import thaumcraft.common.config.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.monster.tainted.*;
import net.minecraft.entity.*;
import java.util.*;
import thaumcraft.common.world.aura.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.aura.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class TaintHelper
{
    private static ConcurrentHashMap<Integer, ArrayList<BlockPos>> taintSeeds;
    
    public static void addTaintSeed(final World world, final BlockPos pos) {
        ArrayList<BlockPos> locs = TaintHelper.taintSeeds.get(world.field_73011_w.getDimension());
        if (locs == null) {
            locs = new ArrayList<BlockPos>();
        }
        locs.add(pos);
        TaintHelper.taintSeeds.put(world.field_73011_w.getDimension(), locs);
    }
    
    public static void removeTaintSeed(final World world, final BlockPos pos) {
        final ArrayList<BlockPos> locs = TaintHelper.taintSeeds.get(world.field_73011_w.getDimension());
        if (locs != null && !locs.isEmpty()) {
            locs.remove(pos);
        }
    }
    
    public static boolean isNearTaintSeed(final World world, final BlockPos pos) {
        final double area = ModConfig.CONFIG_WORLD.taintSpreadArea * ModConfig.CONFIG_WORLD.taintSpreadArea;
        final ArrayList<BlockPos> locs = TaintHelper.taintSeeds.get(world.field_73011_w.getDimension());
        if (locs != null && !locs.isEmpty()) {
            for (final BlockPos p : locs) {
                if (p.func_177951_i((Vec3i)pos) <= area) {
                    if (EntityUtils.getEntitiesInRange(world, p, (Entity)null, (Class<? extends Entity>)EntityTaintSeed.class, 1.0).size() <= 0) {
                        removeTaintSeed(world, p);
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isAtTaintSeedEdge(final World world, final BlockPos pos) {
        final double area = ModConfig.CONFIG_WORLD.taintSpreadArea * ModConfig.CONFIG_WORLD.taintSpreadArea;
        final double fringe = ModConfig.CONFIG_WORLD.taintSpreadArea * 0.8 * (ModConfig.CONFIG_WORLD.taintSpreadArea * 0.8);
        final ArrayList<BlockPos> locs = TaintHelper.taintSeeds.get(world.field_73011_w.getDimension());
        if (locs != null && !locs.isEmpty()) {
            for (final BlockPos p : locs) {
                final double d = p.func_177951_i((Vec3i)pos);
                if (d < area && d > fringe) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void spreadFibres(final World world, final BlockPos pos) {
        spreadFibres(world, pos, false);
    }
    
    public static void spreadFibres(final World world, final BlockPos pos, final boolean ignore) {
        if (!ignore && ModConfig.CONFIG_MISC.wussMode) {
            return;
        }
        final float mod = 0.001f + AuraHandler.getFluxSaturation(world, pos) * 2.0f;
        if (!ignore && world.field_73012_v.nextFloat() > ModConfig.CONFIG_WORLD.taintSpreadRate / 100.0f * mod) {
            return;
        }
        if (isNearTaintSeed(world, pos)) {
            final int xx = pos.func_177958_n() + world.field_73012_v.nextInt(3) - 1;
            final int yy = pos.func_177956_o() + world.field_73012_v.nextInt(3) - 1;
            final int zz = pos.func_177952_p() + world.field_73012_v.nextInt(3) - 1;
            final BlockPos t = new BlockPos(xx, yy, zz);
            if (t.equals((Object)pos)) {
                return;
            }
            final IBlockState bs = world.func_180495_p(t);
            final Material bm = bs.func_177230_c().func_149688_o(bs);
            final float bh = bs.func_177230_c().func_176195_g(bs, world, t);
            if (bh < 0.0f || bh > 10.0f) {
                return;
            }
            if (!bs.func_177230_c().isLeaves(bs, (IBlockAccess)world, t) && !bm.func_76224_d() && (world.func_175623_d(t) || bs.func_177230_c().func_176200_f((IBlockAccess)world, t) || bs.func_177230_c() instanceof BlockFlower || bs.func_177230_c() instanceof IPlantable) && BlockUtils.isAdjacentToSolidBlock(world, t) && !BlockTaintFibre.isOnlyAdjacentToTaint(world, t)) {
                world.func_175656_a(t, BlocksTC.taintFibre.func_176223_P());
                world.func_175641_c(t, BlocksTC.taintFibre, 1, 0);
                AuraHelper.drainFlux(world, t, 0.01f, false);
                return;
            }
            if (bs.func_177230_c().isLeaves(bs, (IBlockAccess)world, t)) {
                EnumFacing face = null;
                if (world.field_73012_v.nextFloat() < 0.6 && (face = BlockUtils.getFaceBlockTouching((IBlockAccess)world, t, BlocksTC.taintLog)) != null) {
                    world.func_175656_a(t, BlocksTC.taintFeature.func_176223_P().func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)face.func_176734_d()));
                }
                else {
                    world.func_175656_a(t, BlocksTC.taintFibre.func_176223_P());
                    world.func_175641_c(t, BlocksTC.taintFibre, 1, 0);
                    AuraHelper.drainFlux(world, t, 0.01f, false);
                }
                return;
            }
            if (BlockTaintFibre.isHemmedByTaint(world, t) && bs.func_185887_b(world, t) < 5.0f) {
                if (Utils.isWoodLog((IBlockAccess)world, t) && bs.func_185904_a() != ThaumcraftMaterials.MATERIAL_TAINT) {
                    world.func_175656_a(t, BlocksTC.taintLog.func_176223_P().func_177226_a((IProperty)BlockTaintLog.AXIS, (Comparable)BlockUtils.getBlockAxis(world, t)));
                    return;
                }
                if (bs.func_177230_c() == Blocks.field_150419_aX || bs.func_177230_c() == Blocks.field_150420_aW || bm == Material.field_151572_C || bm == Material.field_151570_A || bm == Material.field_151589_v || bm == Material.field_151583_m || bm == Material.field_151575_d) {
                    world.func_175656_a(t, BlocksTC.taintCrust.func_176223_P());
                    world.func_175641_c(t, BlocksTC.taintCrust, 1, 0);
                    AuraHelper.drainFlux(world, t, 0.01f, false);
                    return;
                }
                if (bm == Material.field_151595_p || bm == Material.field_151578_c || bm == Material.field_151577_b || bm == Material.field_151571_B) {
                    world.func_175656_a(t, BlocksTC.taintSoil.func_176223_P());
                    world.func_175641_c(t, BlocksTC.taintSoil, 1, 0);
                    AuraHelper.drainFlux(world, t, 0.01f, false);
                    return;
                }
                if (bm == Material.field_151576_e) {
                    world.func_175656_a(t, BlocksTC.taintRock.func_176223_P());
                    world.func_175641_c(t, BlocksTC.taintRock, 1, 0);
                    AuraHelper.drainFlux(world, t, 0.01f, false);
                    return;
                }
            }
            if ((bs.func_177230_c() == BlocksTC.taintSoil || bs.func_177230_c() == BlocksTC.taintRock) && world.func_175623_d(t.func_177984_a()) && AuraHelper.getFlux(world, t) >= 5.0f && world.field_73012_v.nextFloat() < ModConfig.CONFIG_WORLD.taintSpreadRate / 100.0f * 0.33 && isAtTaintSeedEdge(world, t)) {
                final EntityTaintSeed e = new EntityTaintSeed(world);
                e.func_70012_b((double)(t.func_177958_n() + 0.5f), (double)t.func_177984_a().func_177956_o(), (double)(t.func_177952_p() + 0.5f), (float)world.field_73012_v.nextInt(360), 0.0f);
                if (e.func_70601_bi()) {
                    AuraHelper.drainFlux(world, t, 5.0f, false);
                    world.func_72838_d((Entity)e);
                }
            }
        }
    }
    
    static {
        TaintHelper.taintSeeds = new ConcurrentHashMap<Integer, ArrayList<BlockPos>>();
    }
}
