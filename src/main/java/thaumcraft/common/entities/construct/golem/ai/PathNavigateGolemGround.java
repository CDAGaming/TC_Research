package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import java.util.*;

public class PathNavigateGolemGround extends PathNavigateGround
{
    public PathNavigateGolemGround(final EntityLiving entitylivingIn, final World worldIn) {
        super(entitylivingIn, worldIn);
    }
    
    protected PathFinder func_179679_a() {
        (this.field_179695_a = new GolemNodeProcessor()).func_186317_a(true);
        return new PathFinder(this.field_179695_a);
    }
    
    protected boolean func_75485_k() {
        return this.field_75515_a.field_70122_E || (this.func_179684_h() && this.func_75506_l()) || this.field_75515_a.func_184218_aH();
    }
    
    protected Vec3d func_75502_i() {
        return new Vec3d(this.field_75515_a.field_70165_t, (double)this.getPathablePosY(), this.field_75515_a.field_70161_v);
    }
    
    public Path func_179680_a(BlockPos pos) {
        if (this.field_75513_b.func_180495_p(pos).func_185904_a() == Material.field_151579_a) {
            BlockPos blockpos;
            for (blockpos = pos.func_177977_b(); blockpos.func_177956_o() > 0 && this.field_75513_b.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a; blockpos = blockpos.func_177977_b()) {}
            if (blockpos.func_177956_o() > 0) {
                return super.func_179680_a(blockpos.func_177984_a());
            }
            while (blockpos.func_177956_o() < this.field_75513_b.func_72800_K() && this.field_75513_b.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a) {
                blockpos = blockpos.func_177984_a();
            }
            pos = blockpos;
        }
        if (!this.field_75513_b.func_180495_p(pos).func_185904_a().func_76220_a()) {
            return super.func_179680_a(pos);
        }
        BlockPos blockpos2;
        for (blockpos2 = pos.func_177984_a(); blockpos2.func_177956_o() < this.field_75513_b.func_72800_K() && this.field_75513_b.func_180495_p(blockpos2).func_185904_a().func_76220_a(); blockpos2 = blockpos2.func_177984_a()) {}
        return super.func_179680_a(blockpos2);
    }
    
    public Path func_75494_a(final Entity entityIn) {
        final BlockPos blockpos = new BlockPos(entityIn);
        return this.func_179680_a(blockpos);
    }
    
    private int getPathablePosY() {
        if (this.field_75515_a.func_70090_H() && this.func_179684_h()) {
            int i = (int)this.field_75515_a.func_174813_aQ().field_72338_b;
            Block block = this.field_75513_b.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), i, MathHelper.func_76128_c(this.field_75515_a.field_70161_v))).func_177230_c();
            int j = 0;
            while (block == Blocks.field_150358_i || block == Blocks.field_150355_j) {
                ++i;
                block = this.field_75513_b.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), i, MathHelper.func_76128_c(this.field_75515_a.field_70161_v))).func_177230_c();
                if (++j > 16) {
                    return (int)this.field_75515_a.func_174813_aQ().field_72338_b;
                }
            }
            return i;
        }
        return (int)(this.field_75515_a.func_174813_aQ().field_72338_b + 0.5);
    }
    
    protected void func_75487_m() {
        super.func_75487_m();
        for (int i = 0; i < this.field_75514_c.func_75874_d(); ++i) {
            final PathPoint pathpoint = this.field_75514_c.func_75877_a(i);
            final PathPoint pathpoint2 = (i + 1 < this.field_75514_c.func_75874_d()) ? this.field_75514_c.func_75877_a(i + 1) : null;
            final IBlockState iblockstate = this.field_75513_b.func_180495_p(new BlockPos(pathpoint.field_75839_a, pathpoint.field_75837_b, pathpoint.field_75838_c));
            final Block block = iblockstate.func_177230_c();
            if (block == Blocks.field_150383_bp) {
                this.field_75514_c.func_186309_a(i, pathpoint.func_186283_a(pathpoint.field_75839_a, pathpoint.field_75837_b + 1, pathpoint.field_75838_c));
                if (pathpoint2 != null && pathpoint.field_75837_b >= pathpoint2.field_75837_b) {
                    this.field_75514_c.func_186309_a(i + 1, pathpoint2.func_186283_a(pathpoint2.field_75839_a, pathpoint.field_75837_b + 1, pathpoint2.field_75838_c));
                }
            }
        }
    }
    
    protected boolean func_75493_a(final Vec3d posVec31, final Vec3d posVec32, int sizeX, final int sizeY, int sizeZ) {
        int i = MathHelper.func_76128_c(posVec31.field_72450_a);
        int j = MathHelper.func_76128_c(posVec31.field_72449_c);
        double d0 = posVec32.field_72450_a - posVec31.field_72450_a;
        double d2 = posVec32.field_72449_c - posVec31.field_72449_c;
        final double d3 = d0 * d0 + d2 * d2;
        if (d3 < 1.0E-8) {
            return false;
        }
        final double d4 = 1.0 / Math.sqrt(d3);
        d0 *= d4;
        d2 *= d4;
        sizeX += 2;
        sizeZ += 2;
        if (!this.isSafeToStandAt(i, (int)posVec31.field_72448_b, j, sizeX, sizeY, sizeZ, posVec31, d0, d2)) {
            return false;
        }
        sizeX -= 2;
        sizeZ -= 2;
        final double d5 = 1.0 / Math.abs(d0);
        final double d6 = 1.0 / Math.abs(d2);
        double d7 = i - posVec31.field_72450_a;
        double d8 = j - posVec31.field_72449_c;
        if (d0 >= 0.0) {
            ++d7;
        }
        if (d2 >= 0.0) {
            ++d8;
        }
        d7 /= d0;
        d8 /= d2;
        final int k = (d0 < 0.0) ? -1 : 1;
        final int l = (d2 < 0.0) ? -1 : 1;
        final int i2 = MathHelper.func_76128_c(posVec32.field_72450_a);
        final int j2 = MathHelper.func_76128_c(posVec32.field_72449_c);
        int k2 = i2 - i;
        int l2 = j2 - j;
        while (k2 * k > 0 || l2 * l > 0) {
            if (d7 < d8) {
                d7 += d5;
                i += k;
                k2 = i2 - i;
            }
            else {
                d8 += d6;
                j += l;
                l2 = j2 - j;
            }
            if (!this.isSafeToStandAt(i, (int)posVec31.field_72448_b, j, sizeX, sizeY, sizeZ, posVec31, d0, d2)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSafeToStandAt(final int x, final int y, final int z, final int sizeX, final int sizeY, final int sizeZ, final Vec3d vec31, final double p_179683_8_, final double p_179683_10_) {
        final int i = x - sizeX / 2;
        final int j = z - sizeZ / 2;
        if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
            return false;
        }
        for (int k = i; k < i + sizeX; ++k) {
            for (int l = j; l < j + sizeZ; ++l) {
                final double d0 = k + 0.5 - vec31.field_72450_a;
                final double d2 = l + 0.5 - vec31.field_72449_c;
                if (d0 * p_179683_8_ + d2 * p_179683_10_ >= 0.0) {
                    PathNodeType pathnodetype = this.field_179695_a.func_186319_a((IBlockAccess)this.field_75513_b, k, y - 1, l, this.field_75515_a, sizeX, sizeY, sizeZ, true, true);
                    if (pathnodetype == PathNodeType.WATER) {
                        return false;
                    }
                    if (pathnodetype == PathNodeType.LAVA) {
                        return false;
                    }
                    if (pathnodetype == PathNodeType.OPEN) {
                        return false;
                    }
                    pathnodetype = this.field_179695_a.func_186319_a((IBlockAccess)this.field_75513_b, k, y, l, this.field_75515_a, sizeX, sizeY, sizeZ, true, true);
                    final float f = this.field_75515_a.func_184643_a(pathnodetype);
                    if (f < 0.0f || f >= 8.0f) {
                        return false;
                    }
                    if (pathnodetype == PathNodeType.DAMAGE_FIRE || pathnodetype == PathNodeType.DANGER_FIRE || pathnodetype == PathNodeType.DAMAGE_OTHER) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean isPositionClear(final int p_179692_1_, final int p_179692_2_, final int p_179692_3_, final int p_179692_4_, final int p_179692_5_, final int p_179692_6_, final Vec3d p_179692_7_, final double p_179692_8_, final double p_179692_10_) {
        for (final BlockPos blockpos : BlockPos.func_177980_a(new BlockPos(p_179692_1_, p_179692_2_, p_179692_3_), new BlockPos(p_179692_1_ + p_179692_4_ - 1, p_179692_2_ + p_179692_5_ - 1, p_179692_3_ + p_179692_6_ - 1))) {
            final double d0 = blockpos.func_177958_n() + 0.5 - p_179692_7_.field_72450_a;
            final double d2 = blockpos.func_177952_p() + 0.5 - p_179692_7_.field_72449_c;
            if (d0 * p_179692_8_ + d2 * p_179692_10_ >= 0.0) {
                final Block block = this.field_75513_b.func_180495_p(blockpos).func_177230_c();
                if (!block.func_176205_b((IBlockAccess)this.field_75513_b, blockpos)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    public void func_179691_c(final boolean enterDoors) {
        this.field_179695_a.func_186317_a(enterDoors);
    }
    
    public boolean func_179686_g() {
        return this.field_179695_a.func_186323_c();
    }
    
    public void func_179693_d(final boolean canSwim) {
        this.field_179695_a.func_186316_c(canSwim);
    }
    
    public boolean func_179684_h() {
        return this.field_179695_a.func_186322_e();
    }
    
    public void func_179685_e(final boolean avoidSun) {
    }
}
