package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class GolemNodeProcessor extends NodeProcessor
{
    private float avoidsWater;
    
    public void func_186315_a(final IBlockAccess sourceIn, final EntityLiving mob) {
        super.func_186315_a(sourceIn, mob);
        this.avoidsWater = mob.func_184643_a(PathNodeType.WATER);
    }
    
    public void func_176163_a() {
        this.field_186326_b.func_184644_a(PathNodeType.WATER, this.avoidsWater);
        super.func_176163_a();
    }
    
    public PathPoint func_186318_b() {
        int i;
        if (this.func_186322_e() && this.field_186326_b.func_70090_H()) {
            i = (int)this.field_186326_b.func_174813_aQ().field_72338_b;
            final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(this.field_186326_b.field_70165_t), i, MathHelper.func_76128_c(this.field_186326_b.field_70161_v));
            for (Block block = this.field_176169_a.func_180495_p((BlockPos)blockpos$mutableblockpos).func_177230_c(); block == Blocks.field_150358_i || block == Blocks.field_150355_j; block = this.field_176169_a.func_180495_p((BlockPos)blockpos$mutableblockpos).func_177230_c()) {
                ++i;
                blockpos$mutableblockpos.func_181079_c(MathHelper.func_76128_c(this.field_186326_b.field_70165_t), i, MathHelper.func_76128_c(this.field_186326_b.field_70161_v));
            }
        }
        else if (this.field_186326_b.field_70122_E) {
            i = MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5);
        }
        else {
            BlockPos blockpos;
            for (blockpos = new BlockPos((Entity)this.field_186326_b); (this.field_176169_a.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a || this.field_176169_a.func_180495_p(blockpos).func_177230_c().func_176205_b(this.field_176169_a, blockpos)) && blockpos.func_177956_o() > 0; blockpos = blockpos.func_177977_b()) {}
            i = blockpos.func_177984_a().func_177956_o();
        }
        final BlockPos blockpos2 = new BlockPos((Entity)this.field_186326_b);
        final PathNodeType pathnodetype1 = this.getPathNodeType(this.field_186326_b, blockpos2.func_177958_n(), i, blockpos2.func_177952_p());
        if (this.field_186326_b.func_184643_a(pathnodetype1) < 0.0f) {
            final Set<BlockPos> set = (Set<BlockPos>)Sets.newHashSet();
            set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)i, this.field_186326_b.func_174813_aQ().field_72339_c));
            set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)i, this.field_186326_b.func_174813_aQ().field_72334_f));
            set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)i, this.field_186326_b.func_174813_aQ().field_72339_c));
            set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)i, this.field_186326_b.func_174813_aQ().field_72334_f));
            for (final BlockPos blockpos3 : set) {
                final PathNodeType pathnodetype2 = this.getPathNodeType(this.field_186326_b, blockpos3);
                if (this.field_186326_b.func_184643_a(pathnodetype2) >= 0.0f) {
                    return this.func_176159_a(blockpos3.func_177958_n(), blockpos3.func_177956_o(), blockpos3.func_177952_p());
                }
            }
        }
        return this.func_176159_a(blockpos2.func_177958_n(), i, blockpos2.func_177952_p());
    }
    
    public PathPoint func_186325_a(final double x, final double y, final double z) {
        return this.func_176159_a(MathHelper.func_76128_c(x - this.field_186326_b.field_70130_N / 2.0f), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z - this.field_186326_b.field_70130_N / 2.0f));
    }
    
    public int func_186320_a(final PathPoint[] pathOptions, final PathPoint currentPoint, final PathPoint targetPoint, final float maxDistance) {
        int i = 0;
        int j = 0;
        final PathNodeType pathnodetype = this.getPathNodeType(this.field_186326_b, currentPoint.field_75839_a, currentPoint.field_75837_b + 1, currentPoint.field_75838_c);
        if (this.field_186326_b.func_184643_a(pathnodetype) >= 0.0f) {
            j = MathHelper.func_76141_d(Math.max(1.0f, this.field_186326_b.field_70138_W));
        }
        final BlockPos blockpos = new BlockPos(currentPoint.field_75839_a, currentPoint.field_75837_b, currentPoint.field_75838_c).func_177977_b();
        final double d0 = currentPoint.field_75837_b - (1.0 - this.field_176169_a.func_180495_p(blockpos).func_185900_c(this.field_176169_a, blockpos).field_72337_e);
        final PathPoint pathpoint = this.getSafePoint(currentPoint.field_75839_a, currentPoint.field_75837_b, currentPoint.field_75838_c + 1, j, d0, EnumFacing.SOUTH);
        final PathPoint pathpoint2 = this.getSafePoint(currentPoint.field_75839_a - 1, currentPoint.field_75837_b, currentPoint.field_75838_c, j, d0, EnumFacing.WEST);
        final PathPoint pathpoint3 = this.getSafePoint(currentPoint.field_75839_a + 1, currentPoint.field_75837_b, currentPoint.field_75838_c, j, d0, EnumFacing.EAST);
        final PathPoint pathpoint4 = this.getSafePoint(currentPoint.field_75839_a, currentPoint.field_75837_b, currentPoint.field_75838_c - 1, j, d0, EnumFacing.NORTH);
        if (pathpoint != null && !pathpoint.field_75842_i && pathpoint.func_75829_a(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint;
        }
        if (pathpoint2 != null && !pathpoint2.field_75842_i && pathpoint2.func_75829_a(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint2;
        }
        if (pathpoint3 != null && !pathpoint3.field_75842_i && pathpoint3.func_75829_a(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint3;
        }
        if (pathpoint4 != null && !pathpoint4.field_75842_i && pathpoint4.func_75829_a(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint4;
        }
        final boolean flag = pathpoint4 == null || pathpoint4.field_186287_m == PathNodeType.OPEN || pathpoint4.field_186286_l != 0.0f;
        final boolean flag2 = pathpoint == null || pathpoint.field_186287_m == PathNodeType.OPEN || pathpoint.field_186286_l != 0.0f;
        final boolean flag3 = pathpoint3 == null || pathpoint3.field_186287_m == PathNodeType.OPEN || pathpoint3.field_186286_l != 0.0f;
        final boolean flag4 = pathpoint2 == null || pathpoint2.field_186287_m == PathNodeType.OPEN || pathpoint2.field_186286_l != 0.0f;
        if (flag && flag4) {
            final PathPoint pathpoint5 = this.getSafePoint(currentPoint.field_75839_a - 1, currentPoint.field_75837_b, currentPoint.field_75838_c - 1, j, d0, EnumFacing.NORTH);
            if (pathpoint5 != null && !pathpoint5.field_75842_i && pathpoint5.func_75829_a(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint5;
            }
        }
        if (flag && flag3) {
            final PathPoint pathpoint6 = this.getSafePoint(currentPoint.field_75839_a + 1, currentPoint.field_75837_b, currentPoint.field_75838_c - 1, j, d0, EnumFacing.NORTH);
            if (pathpoint6 != null && !pathpoint6.field_75842_i && pathpoint6.func_75829_a(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint6;
            }
        }
        if (flag2 && flag4) {
            final PathPoint pathpoint7 = this.getSafePoint(currentPoint.field_75839_a - 1, currentPoint.field_75837_b, currentPoint.field_75838_c + 1, j, d0, EnumFacing.SOUTH);
            if (pathpoint7 != null && !pathpoint7.field_75842_i && pathpoint7.func_75829_a(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint7;
            }
        }
        if (flag2 && flag3) {
            final PathPoint pathpoint8 = this.getSafePoint(currentPoint.field_75839_a + 1, currentPoint.field_75837_b, currentPoint.field_75838_c + 1, j, d0, EnumFacing.SOUTH);
            if (pathpoint8 != null && !pathpoint8.field_75842_i && pathpoint8.func_75829_a(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint8;
            }
        }
        return i;
    }
    
    private PathPoint getSafePoint(final int x, int y, final int z, final int p_186332_4_, final double p_186332_5_, final EnumFacing facing) {
        PathPoint pathpoint = null;
        final BlockPos blockpos = new BlockPos(x, y, z);
        final BlockPos blockpos2 = blockpos.func_177977_b();
        final double d0 = y - (1.0 - this.field_176169_a.func_180495_p(blockpos2).func_185900_c(this.field_176169_a, blockpos2).field_72337_e);
        if (d0 - p_186332_5_ > 1.125) {
            return null;
        }
        PathNodeType pathnodetype = this.getPathNodeType(this.field_186326_b, x, y, z);
        float f = this.field_186326_b.func_184643_a(pathnodetype);
        final double d2 = this.field_186326_b.field_70130_N / 2.0;
        if (f >= 0.0f) {
            pathpoint = this.func_176159_a(x, y, z);
            pathpoint.field_186287_m = pathnodetype;
            pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
        }
        if (pathnodetype == PathNodeType.WALKABLE) {
            return pathpoint;
        }
        if (pathpoint == null && p_186332_4_ > 0 && pathnodetype != PathNodeType.FENCE && pathnodetype != PathNodeType.TRAPDOOR) {
            pathpoint = this.getSafePoint(x, y + 1, z, p_186332_4_ - 1, p_186332_5_, facing);
            if (pathpoint != null && (pathpoint.field_186287_m == PathNodeType.OPEN || pathpoint.field_186287_m == PathNodeType.WALKABLE) && this.field_186326_b.field_70130_N < 1.0f) {
                final double d3 = x - facing.func_82601_c() + 0.5;
                final double d4 = z - facing.func_82599_e() + 0.5;
                final AxisAlignedBB axisalignedbb = new AxisAlignedBB(d3 - d2, y + 0.001, d4 - d2, d3 + d2, (double)(y + this.field_186326_b.field_70131_O), d4 + d2);
                final AxisAlignedBB axisalignedbb2 = this.field_176169_a.func_180495_p(blockpos).func_185900_c(this.field_176169_a, blockpos);
                final AxisAlignedBB axisalignedbb3 = axisalignedbb.func_72321_a(0.0, axisalignedbb2.field_72337_e - 0.002, 0.0);
                if (this.field_186326_b.field_70170_p.func_184143_b(axisalignedbb3)) {
                    pathpoint = null;
                }
            }
        }
        if (pathnodetype == PathNodeType.OPEN) {
            final AxisAlignedBB axisalignedbb4 = new AxisAlignedBB(x - d2 + 0.5, y + 0.001, z - d2 + 0.5, x + d2 + 0.5, (double)(y + this.field_186326_b.field_70131_O), z + d2 + 0.5);
            if (this.field_186326_b.field_70170_p.func_184143_b(axisalignedbb4)) {
                return null;
            }
            if (this.field_186326_b.field_70130_N >= 1.0f) {
                final PathNodeType pathnodetype2 = this.getPathNodeType(this.field_186326_b, x, y - 1, z);
                if (pathnodetype2 == PathNodeType.BLOCKED) {
                    pathpoint = this.func_176159_a(x, y, z);
                    pathpoint.field_186287_m = PathNodeType.WALKABLE;
                    pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
                    return pathpoint;
                }
            }
            int i = 0;
            while (y > 0 && pathnodetype == PathNodeType.OPEN) {
                --y;
                if (i++ >= this.field_186326_b.func_82143_as()) {
                    return null;
                }
                pathnodetype = this.getPathNodeType(this.field_186326_b, x, y, z);
                f = this.field_186326_b.func_184643_a(pathnodetype);
                if (pathnodetype != PathNodeType.OPEN && f >= 0.0f) {
                    pathpoint = this.func_176159_a(x, y, z);
                    pathpoint.field_186287_m = pathnodetype;
                    pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
                    break;
                }
                if (f < 0.0f) {
                    return null;
                }
            }
        }
        return pathpoint;
    }
    
    public PathNodeType func_186319_a(final IBlockAccess blockaccessIn, final int x, final int y, final int z, final EntityLiving entitylivingIn, final int xSize, final int ySize, final int zSize, final boolean canBreakDoorsIn, final boolean canEnterDoorsIn) {
        final EnumSet<PathNodeType> enumset = EnumSet.noneOf(PathNodeType.class);
        PathNodeType pathnodetype = PathNodeType.BLOCKED;
        final double d0 = entitylivingIn.field_70130_N / 2.0;
        final BlockPos blockpos = new BlockPos((Entity)entitylivingIn);
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                for (int k = 0; k < zSize; ++k) {
                    final int l = i + x;
                    final int i2 = j + y;
                    final int j2 = k + z;
                    PathNodeType pathnodetype2 = this.func_186330_a(blockaccessIn, l, i2, j2);
                    if (pathnodetype2 == PathNodeType.DOOR_WOOD_CLOSED && canBreakDoorsIn && canEnterDoorsIn) {
                        pathnodetype2 = PathNodeType.WALKABLE;
                    }
                    if (pathnodetype2 == PathNodeType.DOOR_OPEN && !canEnterDoorsIn) {
                        pathnodetype2 = PathNodeType.BLOCKED;
                    }
                    if (pathnodetype2 == PathNodeType.RAIL && !(blockaccessIn.func_180495_p(blockpos).func_177230_c() instanceof BlockRailBase) && !(blockaccessIn.func_180495_p(blockpos.func_177977_b()).func_177230_c() instanceof BlockRailBase)) {
                        pathnodetype2 = PathNodeType.FENCE;
                    }
                    if (i == 0 && j == 0 && k == 0) {
                        pathnodetype = pathnodetype2;
                    }
                    enumset.add(pathnodetype2);
                }
            }
        }
        if (enumset.contains(PathNodeType.FENCE)) {
            return PathNodeType.FENCE;
        }
        PathNodeType pathnodetype3 = PathNodeType.BLOCKED;
        for (final PathNodeType pathnodetype4 : enumset) {
            if (entitylivingIn.func_184643_a(pathnodetype4) < 0.0f) {
                return pathnodetype4;
            }
            if (entitylivingIn.func_184643_a(pathnodetype4) < entitylivingIn.func_184643_a(pathnodetype3)) {
                continue;
            }
            pathnodetype3 = pathnodetype4;
        }
        if (pathnodetype == PathNodeType.OPEN && entitylivingIn.func_184643_a(pathnodetype3) == 0.0f) {
            return PathNodeType.OPEN;
        }
        return pathnodetype3;
    }
    
    private PathNodeType getPathNodeType(final EntityLiving entitylivingIn, final BlockPos pos) {
        return this.getPathNodeType(entitylivingIn, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
    }
    
    private PathNodeType getPathNodeType(final EntityLiving entitylivingIn, final int x, final int y, final int z) {
        return this.func_186319_a(this.field_176169_a, x, y, z, entitylivingIn, this.field_176168_c, this.field_176165_d, this.field_176166_e, false, this.func_186323_c());
    }
    
    public PathNodeType func_186330_a(final IBlockAccess blockaccessIn, final int x, final int y, final int z) {
        PathNodeType pathnodetype = this.getPathNodeTypeRaw(blockaccessIn, x, y, z);
        if (pathnodetype == PathNodeType.OPEN && y >= 1) {
            final Block block = blockaccessIn.func_180495_p(new BlockPos(x, y - 1, z)).func_177230_c();
            final PathNodeType pathnodetype2 = this.getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
            pathnodetype = ((pathnodetype2 != PathNodeType.WALKABLE && pathnodetype2 != PathNodeType.OPEN && pathnodetype2 != PathNodeType.WATER && pathnodetype2 != PathNodeType.LAVA) ? PathNodeType.WALKABLE : PathNodeType.OPEN);
            if (pathnodetype2 == PathNodeType.DAMAGE_FIRE || block == Blocks.field_189877_df) {
                pathnodetype = PathNodeType.DAMAGE_FIRE;
            }
            if (pathnodetype2 == PathNodeType.DAMAGE_CACTUS) {
                pathnodetype = PathNodeType.DAMAGE_CACTUS;
            }
        }
        final BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
        if (pathnodetype == PathNodeType.WALKABLE) {
            for (int j = -1; j <= 1; ++j) {
                for (int i = -1; i <= 1; ++i) {
                    if (j != 0 || i != 0) {
                        final Block block2 = blockaccessIn.func_180495_p((BlockPos)blockpos$pooledmutableblockpos.func_181079_c(j + x, y, i + z)).func_177230_c();
                        if (block2 == Blocks.field_150434_aF) {
                            pathnodetype = PathNodeType.DANGER_CACTUS;
                        }
                        else if (block2 == Blocks.field_150480_ab) {
                            pathnodetype = PathNodeType.DANGER_FIRE;
                        }
                    }
                }
            }
        }
        blockpos$pooledmutableblockpos.func_185344_t();
        return pathnodetype;
    }
    
    private PathNodeType getPathNodeTypeRaw(final IBlockAccess p_189553_1_, final int p_189553_2_, final int p_189553_3_, final int p_189553_4_) {
        final BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
        final IBlockState iblockstate = p_189553_1_.func_180495_p(blockpos);
        final Block block = iblockstate.func_177230_c();
        final Material material = iblockstate.func_185904_a();
        return (material == Material.field_151579_a) ? PathNodeType.OPEN : ((block != Blocks.field_150415_aT && block != Blocks.field_180400_cw && block != Blocks.field_150392_bi) ? ((block == Blocks.field_150480_ab) ? PathNodeType.DAMAGE_FIRE : ((block == Blocks.field_150434_aF) ? PathNodeType.DAMAGE_CACTUS : ((block instanceof BlockDoor && material == Material.field_151575_d && !(boolean)iblockstate.func_177229_b((IProperty)BlockDoor.field_176519_b)) ? PathNodeType.DOOR_WOOD_CLOSED : ((block instanceof BlockDoor && material == Material.field_151573_f && !(boolean)iblockstate.func_177229_b((IProperty)BlockDoor.field_176519_b)) ? PathNodeType.DOOR_IRON_CLOSED : ((block instanceof BlockDoor && (boolean)iblockstate.func_177229_b((IProperty)BlockDoor.field_176519_b)) ? PathNodeType.DOOR_OPEN : ((block instanceof BlockRailBase) ? PathNodeType.RAIL : ((!(block instanceof BlockFence) && !(block instanceof BlockWall) && (!(block instanceof BlockFenceGate) || (boolean)iblockstate.func_177229_b((IProperty)BlockFenceGate.field_176466_a))) ? ((material == Material.field_151586_h) ? PathNodeType.WATER : ((material == Material.field_151587_i) ? PathNodeType.LAVA : (block.func_176205_b(p_189553_1_, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED))) : PathNodeType.FENCE))))))) : PathNodeType.TRAPDOOR);
    }
}
