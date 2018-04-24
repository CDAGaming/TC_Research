package thaumcraft.common.world.objects;

import net.minecraft.world.gen.feature.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;

public class WorldGenBigMagicTree extends WorldGenAbstractTree
{
    private Random field_175949_k;
    private World world;
    private BlockPos basePos;
    int heightLimit;
    int height;
    double heightAttenuation;
    double branchSlope;
    double scaleWidth;
    double leafDensity;
    int trunkSize;
    int heightLimitLimit;
    int leafDistanceLimit;
    List field_175948_j;
    private static final String __OBFID = "CL_00000400";
    
    public WorldGenBigMagicTree(final boolean p_i2008_1_) {
        super(p_i2008_1_);
        this.basePos = BlockPos.field_177992_a;
        this.heightAttenuation = 0.6618;
        this.branchSlope = 0.381;
        this.scaleWidth = 1.25;
        this.leafDensity = 0.9;
        this.trunkSize = 1;
        this.heightLimitLimit = 11;
        this.leafDistanceLimit = 4;
    }
    
    void generateLeafNodeList() {
        this.height = (int)(this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }
        int i = (int)(1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        if (i < 1) {
            i = 1;
        }
        final int j = this.basePos.func_177956_o() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        (this.field_175948_j = Lists.newArrayList()).add(new FoliageCoordinates(this.basePos.func_177981_b(k), j));
        while (k >= 0) {
            final float f = this.layerSize(k);
            if (f >= 0.0f) {
                for (int l = 0; l < i; ++l) {
                    final double d0 = this.scaleWidth * f * (this.field_175949_k.nextFloat() + 0.328);
                    final double d2 = this.field_175949_k.nextFloat() * 2.0f * 3.141592653589793;
                    final double d3 = d0 * Math.sin(d2) + 0.5;
                    final double d4 = d0 * Math.cos(d2) + 0.5;
                    final BlockPos blockpos = this.basePos.func_177963_a(d3, (double)(k - 1), d4);
                    final BlockPos blockpos2 = blockpos.func_177981_b(this.leafDistanceLimit);
                    if (this.func_175936_a(blockpos, blockpos2) == -1) {
                        final int i2 = this.basePos.func_177958_n() - blockpos.func_177958_n();
                        final int j2 = this.basePos.func_177952_p() - blockpos.func_177952_p();
                        final double d5 = blockpos.func_177956_o() - Math.sqrt(i2 * i2 + j2 * j2) * this.branchSlope;
                        final int k2 = (d5 > j) ? j : ((int)d5);
                        final BlockPos blockpos3 = new BlockPos(this.basePos.func_177958_n(), k2, this.basePos.func_177952_p());
                        if (this.func_175936_a(blockpos3, blockpos) == -1) {
                            this.field_175948_j.add(new FoliageCoordinates(blockpos, blockpos3.func_177956_o()));
                        }
                    }
                }
            }
            --k;
        }
    }
    
    void func_181631_a(final BlockPos p_181631_1_, final float p_181631_2_, final IBlockState p_181631_3_) {
        for (int i = (int)(p_181631_2_ + 0.618), j = -i; j <= i; ++j) {
            for (int k = -i; k <= i; ++k) {
                if (Math.pow(Math.abs(j) + 0.5, 2.0) + Math.pow(Math.abs(k) + 0.5, 2.0) <= p_181631_2_ * p_181631_2_) {
                    final BlockPos blockpos = p_181631_1_.func_177982_a(j, 0, k);
                    final IBlockState state = this.world.func_180495_p(blockpos);
                    if (state.func_177230_c().isAir(state, (IBlockAccess)this.world, blockpos) || state.func_177230_c().isLeaves(state, (IBlockAccess)this.world, blockpos)) {
                        this.func_175903_a(this.world, blockpos, p_181631_3_);
                    }
                }
            }
        }
    }
    
    float layerSize(final int p_76490_1_) {
        if (p_76490_1_ < this.heightLimit * 0.3f) {
            return -1.0f;
        }
        final float f = this.heightLimit / 2.0f;
        final float f2 = f - p_76490_1_;
        float f3 = MathHelper.func_76129_c(f * f - f2 * f2);
        if (f2 == 0.0f) {
            f3 = f;
        }
        else if (Math.abs(f2) >= f) {
            return 0.0f;
        }
        return f3 * 0.5f;
    }
    
    float leafSize(final int p_76495_1_) {
        return (p_76495_1_ >= 0 && p_76495_1_ < this.leafDistanceLimit) ? ((p_76495_1_ != 0 && p_76495_1_ != this.leafDistanceLimit - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    void generateLeafNode(final BlockPos pos) {
        for (int i = 0; i < this.leafDistanceLimit; ++i) {
            this.func_181631_a(pos.func_177981_b(i), this.leafSize(i), Blocks.field_150362_t.func_176223_P().func_177226_a((IProperty)BlockLeaves.field_176236_b, (Comparable)false));
        }
    }
    
    void func_175937_a(final BlockPos p_175937_1_, final BlockPos p_175937_2_, final Block p_175937_3_) {
        final BlockPos blockpos2 = p_175937_2_.func_177982_a(-p_175937_1_.func_177958_n(), -p_175937_1_.func_177956_o(), -p_175937_1_.func_177952_p());
        final int i = this.func_175935_b(blockpos2);
        final float f = blockpos2.func_177958_n() / i;
        final float f2 = blockpos2.func_177956_o() / i;
        final float f3 = blockpos2.func_177952_p() / i;
        for (int j = 0; j <= i; ++j) {
            final BlockPos blockpos3 = p_175937_1_.func_177963_a((double)(0.5f + j * f), (double)(0.5f + j * f2), (double)(0.5f + j * f3));
            final BlockLog.EnumAxis enumaxis = this.func_175938_b(p_175937_1_, blockpos3);
            this.func_175903_a(this.world, blockpos3, p_175937_3_.func_176223_P().func_177226_a((IProperty)BlockLog.field_176299_a, (Comparable)enumaxis));
        }
    }
    
    private int func_175935_b(final BlockPos p_175935_1_) {
        final int i = MathHelper.func_76130_a(p_175935_1_.func_177958_n());
        final int j = MathHelper.func_76130_a(p_175935_1_.func_177956_o());
        final int k = MathHelper.func_76130_a(p_175935_1_.func_177952_p());
        return (k > i && k > j) ? k : ((j > i) ? j : i);
    }
    
    private BlockLog.EnumAxis func_175938_b(final BlockPos p_175938_1_, final BlockPos p_175938_2_) {
        BlockLog.EnumAxis enumaxis = BlockLog.EnumAxis.Y;
        final int i = Math.abs(p_175938_2_.func_177958_n() - p_175938_1_.func_177958_n());
        final int j = Math.abs(p_175938_2_.func_177952_p() - p_175938_1_.func_177952_p());
        final int k = Math.max(i, j);
        if (k > 0) {
            if (i == k) {
                enumaxis = BlockLog.EnumAxis.X;
            }
            else if (j == k) {
                enumaxis = BlockLog.EnumAxis.Z;
            }
        }
        return enumaxis;
    }
    
    void func_175941_b() {
        for (final FoliageCoordinates foliagecoordinates : this.field_175948_j) {
            this.generateLeafNode(foliagecoordinates);
        }
    }
    
    boolean leafNodeNeedsBase(final int p_76493_1_) {
        return p_76493_1_ >= this.heightLimit * 0.2;
    }
    
    void func_175942_c() {
        final BlockPos blockpos = this.basePos;
        final BlockPos blockpos2 = this.basePos.func_177981_b(this.height);
        final Block block = Blocks.field_150364_r;
        this.func_175937_a(blockpos, blockpos2, block);
        if (this.trunkSize == 2) {
            this.func_175937_a(blockpos.func_177974_f(), blockpos2.func_177974_f(), block);
            this.func_175937_a(blockpos.func_177974_f().func_177968_d(), blockpos2.func_177974_f().func_177968_d(), block);
            this.func_175937_a(blockpos.func_177968_d(), blockpos2.func_177968_d(), block);
        }
    }
    
    void func_175939_d() {
        for (final FoliageCoordinates foliagecoordinates : this.field_175948_j) {
            final int i = foliagecoordinates.func_177999_q();
            final BlockPos blockpos = new BlockPos(this.basePos.func_177958_n(), i, this.basePos.func_177952_p());
            if (this.leafNodeNeedsBase(i - this.basePos.func_177956_o())) {
                this.func_175937_a(blockpos, foliagecoordinates, Blocks.field_150364_r);
            }
        }
    }
    
    int func_175936_a(final BlockPos p_175936_1_, final BlockPos p_175936_2_) {
        final BlockPos blockpos2 = p_175936_2_.func_177982_a(-p_175936_1_.func_177958_n(), -p_175936_1_.func_177956_o(), -p_175936_1_.func_177952_p());
        final int i = this.func_175935_b(blockpos2);
        final float f = blockpos2.func_177958_n() / i;
        final float f2 = blockpos2.func_177956_o() / i;
        final float f3 = blockpos2.func_177952_p() / i;
        if (i == 0) {
            return -1;
        }
        for (int j = 0; j <= i; ++j) {
            final BlockPos blockpos3 = p_175936_1_.func_177963_a((double)(0.5f + j * f), (double)(0.5f + j * f2), (double)(0.5f + j * f3));
            if (!this.isReplaceable(this.world, blockpos3)) {
                return j;
            }
        }
        return -1;
    }
    
    public void func_175904_e() {
        this.leafDistanceLimit = 4;
    }
    
    public boolean func_180709_b(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        this.world = worldIn;
        this.basePos = p_180709_3_;
        this.field_175949_k = new Random(p_180709_2_.nextLong());
        if (this.heightLimit == 0) {
            this.heightLimit = 11 + this.field_175949_k.nextInt(this.heightLimitLimit);
        }
        if (!this.validTreeLocation()) {
            this.world = null;
            return false;
        }
        this.generateLeafNodeList();
        this.func_175941_b();
        this.func_175942_c();
        this.func_175939_d();
        this.world = null;
        return true;
    }
    
    private boolean validTreeLocation() {
        final BlockPos down = this.basePos.func_177977_b();
        final IBlockState state = this.world.func_180495_p(down);
        final boolean isSoil = state.func_177230_c().canSustainPlant(state, (IBlockAccess)this.world, down, EnumFacing.UP, (IPlantable)Blocks.field_150345_g);
        if (!isSoil) {
            return false;
        }
        final int i = this.func_175936_a(this.basePos, this.basePos.func_177981_b(this.heightLimit - 1));
        if (i == -1) {
            return true;
        }
        if (i < 6) {
            return false;
        }
        this.heightLimit = i;
        return true;
    }
    
    static class FoliageCoordinates extends BlockPos
    {
        private final int field_178000_b;
        private static final String __OBFID = "CL_00002001";
        
        public FoliageCoordinates(final BlockPos p_i45635_1_, final int p_i45635_2_) {
            super(p_i45635_1_.func_177958_n(), p_i45635_1_.func_177956_o(), p_i45635_1_.func_177952_p());
            this.field_178000_b = p_i45635_2_;
        }
        
        public int func_177999_q() {
            return this.field_178000_b;
        }
    }
}
