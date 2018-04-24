package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public class FlightNodeProcessor extends NodeProcessor
{
    public PathPoint func_186325_a(final double x, final double y, final double z) {
        return this.func_176159_a(MathHelper.func_76128_c(x - this.field_186326_b.field_70130_N / 2.0f), MathHelper.func_76128_c(y + 0.5), MathHelper.func_76128_c(z - this.field_186326_b.field_70130_N / 2.0f));
    }
    
    public PathPoint func_186318_b() {
        return this.func_176159_a(MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72340_a), MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5), MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72339_c));
    }
    
    public int func_186320_a(final PathPoint[] p_186320_1_, final PathPoint p_186320_2_, final PathPoint p_186320_3_, final float p_186320_4_) {
        int i = 0;
        for (final EnumFacing enumfacing : EnumFacing.values()) {
            final PathPoint pathpoint = this.func_186328_b(p_186320_2_.field_75839_a + enumfacing.func_82601_c(), p_186320_2_.field_75837_b + enumfacing.func_96559_d(), p_186320_2_.field_75838_c + enumfacing.func_82599_e());
            if (pathpoint != null && !pathpoint.field_75842_i && pathpoint.func_75829_a(p_186320_3_) < p_186320_4_) {
                p_186320_1_[i++] = pathpoint;
            }
        }
        return i;
    }
    
    public PathNodeType func_186319_a(final IBlockAccess p_186319_1_, final int p_186319_2_, final int p_186319_3_, final int p_186319_4_, final EntityLiving p_186319_5_, final int p_186319_6_, final int p_186319_7_, final int p_186319_8_, final boolean p_186319_9_, final boolean p_186319_10_) {
        return PathNodeType.WATER;
    }
    
    public PathNodeType func_186330_a(final IBlockAccess x, final int y, final int z, final int p_186330_4_) {
        return PathNodeType.WATER;
    }
    
    private PathPoint func_186328_b(final int p_186328_1_, final int p_186328_2_, final int p_186328_3_) {
        final PathNodeType pathnodetype = this.func_186327_c(p_186328_1_, p_186328_2_, p_186328_3_);
        return (pathnodetype == PathNodeType.WALKABLE) ? this.func_176159_a(p_186328_1_, p_186328_2_, p_186328_3_) : null;
    }
    
    private PathNodeType func_186327_c(final int p_186327_1_, final int p_186327_2_, final int p_186327_3_) {
        final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int i = p_186327_1_; i < p_186327_1_ + this.field_176168_c; ++i) {
            for (int j = p_186327_2_; j < p_186327_2_ + this.field_176165_d; ++j) {
                for (int k = p_186327_3_; k < p_186327_3_ + this.field_176166_e; ++k) {
                    final IBlockState iblockstate = this.field_176169_a.func_180495_p((BlockPos)blockpos$mutableblockpos.func_181079_c(i, j, k));
                    if (!this.field_176169_a.func_175623_d((BlockPos)blockpos$mutableblockpos.func_181079_c(i, j, k)) && !iblockstate.func_177230_c().func_176205_b(this.field_176169_a, (BlockPos)blockpos$mutableblockpos.func_181079_c(i, j, k))) {
                        return PathNodeType.BLOCKED;
                    }
                }
            }
        }
        return PathNodeType.WALKABLE;
    }
}
