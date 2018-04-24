package thaumcraft.common.world.objects;

import net.minecraft.world.gen.feature.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.storage.loot.*;

public class WorldGenGreatwoodTrees extends WorldGenAbstractTree
{
    static final byte[] otherCoordPairs;
    Random rand;
    World world;
    int[] basePos;
    int heightLimit;
    int height;
    double heightAttenuation;
    double branchDensity;
    double branchSlope;
    double scaleWidth;
    double leafDensity;
    int trunkSize;
    int heightLimitLimit;
    int leafDistanceLimit;
    int[][] leafNodes;
    boolean spiders;
    
    public WorldGenGreatwoodTrees(final boolean par1, final boolean spiders) {
        super(par1);
        this.rand = new Random();
        this.basePos = new int[] { 0, 0, 0 };
        this.heightLimit = 0;
        this.heightAttenuation = 0.618;
        this.branchDensity = 1.0;
        this.branchSlope = 0.38;
        this.scaleWidth = 1.2;
        this.leafDensity = 0.9;
        this.trunkSize = 2;
        this.heightLimitLimit = 11;
        this.leafDistanceLimit = 4;
        this.spiders = false;
        this.spiders = spiders;
    }
    
    void generateLeafNodeList() {
        this.height = (int)(this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }
        int var1 = (int)(1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        if (var1 < 1) {
            var1 = 1;
        }
        final int[][] var2 = new int[var1 * this.heightLimit][4];
        int var3 = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
        int var4 = 1;
        final int var5 = this.basePos[1] + this.height;
        int var6 = var3 - this.basePos[1];
        var2[0][0] = this.basePos[0];
        var2[0][1] = var3;
        var2[0][2] = this.basePos[2];
        var2[0][3] = var5;
        --var3;
        while (var6 >= 0) {
            int var7 = 0;
            final float var8 = this.layerSize(var6);
            if (var8 < 0.0f) {
                --var3;
                --var6;
            }
            else {
                final double var9 = 0.5;
                while (var7 < var1) {
                    final double var10 = this.scaleWidth * var8 * (this.rand.nextFloat() + 0.328);
                    final double var11 = this.rand.nextFloat() * 2.0 * 3.141592653589793;
                    final int var12 = MathHelper.func_76128_c(var10 * Math.sin(var11) + this.basePos[0] + var9);
                    final int var13 = MathHelper.func_76128_c(var10 * Math.cos(var11) + this.basePos[2] + var9);
                    final int[] var14 = { var12, var3, var13 };
                    final int[] var15 = { var12, var3 + this.leafDistanceLimit, var13 };
                    if (this.checkBlockLine(var14, var15) == -1) {
                        final int[] var16 = { this.basePos[0], this.basePos[1], this.basePos[2] };
                        final double var17 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var14[0]), 2.0) + Math.pow(Math.abs(this.basePos[2] - var14[2]), 2.0));
                        final double var18 = var17 * this.branchSlope;
                        if (var14[1] - var18 > var5) {
                            var16[1] = var5;
                        }
                        else {
                            var16[1] = (int)(var14[1] - var18);
                        }
                        if (this.checkBlockLine(var16, var14) == -1) {
                            var2[var4][0] = var12;
                            var2[var4][1] = var3;
                            var2[var4][2] = var13;
                            var2[var4][3] = var16[1];
                            ++var4;
                        }
                    }
                    ++var7;
                }
                --var3;
                --var6;
            }
        }
        System.arraycopy(var2, 0, this.leafNodes = new int[var4][4], 0, var4);
    }
    
    void genTreeLayer(final int par1, final int par2, final int par3, final float par4, final byte par5, final Block par6) {
        final int var7 = (int)(par4 + 0.618);
        final byte var8 = WorldGenGreatwoodTrees.otherCoordPairs[par5];
        final byte var9 = WorldGenGreatwoodTrees.otherCoordPairs[par5 + 3];
        final int[] var10 = { par1, par2, par3 };
        final int[] var11 = { 0, 0, 0 };
        int var12 = -var7;
        int var13 = -var7;
        var11[par5] = var10[par5];
        while (var12 <= var7) {
            var11[var8] = var10[var8] + var12;
            for (var13 = -var7; var13 <= var7; ++var13) {
                final double var14 = Math.pow(Math.abs(var12) + 0.5, 2.0) + Math.pow(Math.abs(var13) + 0.5, 2.0);
                if (var14 <= par4 * par4) {
                    try {
                        var11[var9] = var10[var9] + var13;
                        final IBlockState state = this.world.func_180495_p(new BlockPos(var11[0], var11[1], var11[2]));
                        final Block block = state.func_177230_c();
                        if (block == Blocks.field_150350_a || block == BlocksTC.leafGreatwood) {
                            if (block == null || block.canBeReplacedByLeaves(state, (IBlockAccess)this.world, new BlockPos(var11[0], var11[1], var11[2]))) {
                                this.func_175903_a(this.world, new BlockPos(var11[0], var11[1], var11[2]), par6.func_176223_P());
                            }
                        }
                    }
                    catch (Exception ex) {}
                }
            }
            ++var12;
        }
    }
    
    float layerSize(final int par1) {
        if (par1 < this.heightLimit * 0.3) {
            return -1.618f;
        }
        final float var2 = this.heightLimit / 2.0f;
        final float var3 = this.heightLimit / 2.0f - par1;
        float var4;
        if (var3 == 0.0f) {
            var4 = var2;
        }
        else if (Math.abs(var3) >= var2) {
            var4 = 0.0f;
        }
        else {
            var4 = (float)Math.sqrt(Math.pow(Math.abs(var2), 2.0) - Math.pow(Math.abs(var3), 2.0));
        }
        var4 *= 0.5f;
        return var4;
    }
    
    float leafSize(final int par1) {
        return (par1 >= 0 && par1 < this.leafDistanceLimit) ? ((par1 != 0 && par1 != this.leafDistanceLimit - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    void generateLeafNode(final int par1, final int par2, final int par3) {
        for (int var4 = par2, var5 = par2 + this.leafDistanceLimit; var4 < var5; ++var4) {
            final float var6 = this.leafSize(var4 - par2);
            this.genTreeLayer(par1, var4, par3, var6, (byte)1, BlocksTC.leafGreatwood);
        }
    }
    
    void placeBlockLine(final int[] par1ArrayOfInteger, final int[] par2ArrayOfInteger, final Block par3) {
        final int[] var4 = { 0, 0, 0 };
        byte var5 = 0;
        byte var6 = 0;
        while (var5 < 3) {
            var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];
            if (Math.abs(var4[var5]) > Math.abs(var4[var6])) {
                var6 = var5;
            }
            ++var5;
        }
        if (var4[var6] != 0) {
            final byte var7 = WorldGenGreatwoodTrees.otherCoordPairs[var6];
            final byte var8 = WorldGenGreatwoodTrees.otherCoordPairs[var6 + 3];
            byte var9;
            if (var4[var6] > 0) {
                var9 = 1;
            }
            else {
                var9 = -1;
            }
            final double var10 = var4[var7] / var4[var6];
            final double var11 = var4[var8] / var4[var6];
            final int[] var12 = { 0, 0, 0 };
            for (int var13 = 0, var14 = var4[var6] + var9; var13 != var14; var13 += var9) {
                var12[var6] = MathHelper.func_76128_c(par1ArrayOfInteger[var6] + var13 + 0.5);
                var12[var7] = MathHelper.func_76128_c(par1ArrayOfInteger[var7] + var13 * var10 + 0.5);
                var12[var8] = MathHelper.func_76128_c(par1ArrayOfInteger[var8] + var13 * var11 + 0.5);
                byte var15 = 1;
                final int var16 = Math.abs(var12[0] - par1ArrayOfInteger[0]);
                final int var17 = Math.abs(var12[2] - par1ArrayOfInteger[2]);
                final int var18 = Math.max(var16, var17);
                if (var18 > 0) {
                    if (var16 == var18) {
                        var15 = 0;
                    }
                    else if (var17 == var18) {
                        var15 = 2;
                    }
                }
                if (this.isReplaceable(this.world, new BlockPos(var12[0], var12[1], var12[2]))) {
                    this.func_175903_a(this.world, new BlockPos(var12[0], var12[1], var12[2]), par3.func_176203_a((int)var15));
                }
            }
        }
    }
    
    void generateLeaves() {
        for (int var1 = 0, var2 = this.leafNodes.length; var1 < var2; ++var1) {
            final int var3 = this.leafNodes[var1][0];
            final int var4 = this.leafNodes[var1][1];
            final int var5 = this.leafNodes[var1][2];
            this.generateLeafNode(var3, var4, var5);
        }
    }
    
    boolean leafNodeNeedsBase(final int par1) {
        return par1 >= this.heightLimit * 0.2;
    }
    
    void generateTrunk() {
        final int var1 = this.basePos[0];
        final int var2 = this.basePos[1];
        final int var3 = this.basePos[1] + this.height;
        final int var4 = this.basePos[2];
        final int[] var5 = { var1, var2, var4 };
        final int[] var6 = { var1, var3, var4 };
        this.placeBlockLine(var5, var6, BlocksTC.logGreatwood);
        if (this.trunkSize == 2) {
            final int[] array = var5;
            final int n = 0;
            ++array[n];
            final int[] array2 = var6;
            final int n2 = 0;
            ++array2[n2];
            this.placeBlockLine(var5, var6, BlocksTC.logGreatwood);
            final int[] array3 = var5;
            final int n3 = 2;
            ++array3[n3];
            final int[] array4 = var6;
            final int n4 = 2;
            ++array4[n4];
            this.placeBlockLine(var5, var6, BlocksTC.logGreatwood);
            final int[] array5 = var5;
            final int n5 = 0;
            --array5[n5];
            final int[] array6 = var6;
            final int n6 = 0;
            --array6[n6];
            this.placeBlockLine(var5, var6, BlocksTC.logGreatwood);
        }
    }
    
    void generateLeafNodeBases() {
        int var1 = 0;
        final int var2 = this.leafNodes.length;
        final int[] var3 = { this.basePos[0], this.basePos[1], this.basePos[2] };
        while (var1 < var2) {
            final int[] var4 = this.leafNodes[var1];
            final int[] var5 = { var4[0], var4[1], var4[2] };
            var3[1] = var4[3];
            final int var6 = var3[1] - this.basePos[1];
            if (this.leafNodeNeedsBase(var6)) {
                this.placeBlockLine(var3, var5, BlocksTC.logGreatwood);
            }
            ++var1;
        }
    }
    
    int checkBlockLine(final int[] par1ArrayOfInteger, final int[] par2ArrayOfInteger) {
        final int[] var3 = { 0, 0, 0 };
        byte var4 = 0;
        byte var5 = 0;
        while (var4 < 3) {
            var3[var4] = par2ArrayOfInteger[var4] - par1ArrayOfInteger[var4];
            if (Math.abs(var3[var4]) > Math.abs(var3[var5])) {
                var5 = var4;
            }
            ++var4;
        }
        if (var3[var5] == 0) {
            return -1;
        }
        final byte var6 = WorldGenGreatwoodTrees.otherCoordPairs[var5];
        final byte var7 = WorldGenGreatwoodTrees.otherCoordPairs[var5 + 3];
        byte var8;
        if (var3[var5] > 0) {
            var8 = 1;
        }
        else {
            var8 = -1;
        }
        final double var9 = var3[var6] / var3[var5];
        final double var10 = var3[var7] / var3[var5];
        final int[] var11 = { 0, 0, 0 };
        int var12;
        int var13;
        for (var12 = 0, var13 = var3[var5] + var8; var12 != var13; var12 += var8) {
            var11[var5] = par1ArrayOfInteger[var5] + var12;
            var11[var6] = MathHelper.func_76128_c(par1ArrayOfInteger[var6] + var12 * var9);
            var11[var7] = MathHelper.func_76128_c(par1ArrayOfInteger[var7] + var12 * var10);
            try {
                final Block var14 = this.world.func_180495_p(new BlockPos(var11[0], var11[1], var11[2])).func_177230_c();
                if (var14 != Blocks.field_150350_a && var14 != BlocksTC.leafGreatwood) {
                    break;
                }
            }
            catch (Exception ex) {}
        }
        return (var12 == var13) ? -1 : Math.abs(var12);
    }
    
    boolean validTreeLocation(final int x, final int z) {
        final int[] var1 = { this.basePos[0] + x, this.basePos[1], this.basePos[2] + z };
        final int[] var2 = { this.basePos[0] + x, this.basePos[1] + this.heightLimit - 1, this.basePos[2] + z };
        try {
            final IBlockState state = this.world.func_180495_p(new BlockPos(this.basePos[0] + x, this.basePos[1] - 1, this.basePos[2] + z));
            final Block var3 = state.func_177230_c();
            final boolean isSoil = var3.canSustainPlant(state, (IBlockAccess)this.world, new BlockPos(this.basePos[0] + x, this.basePos[1] - 1, this.basePos[2] + z), EnumFacing.UP, (IPlantable)Blocks.field_150345_g);
            if (!isSoil) {
                return false;
            }
            final int var4 = this.checkBlockLine(var1, var2);
            if (var4 == -1) {
                return true;
            }
            if (var4 < 6) {
                return false;
            }
            this.heightLimit = var4;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public void setScale(final double par1, final double par3, final double par5) {
    }
    
    public boolean func_180709_b(final World par1World, final Random par2Random, final BlockPos pos) {
        this.world = par1World;
        final long var6 = par2Random.nextLong();
        this.rand.setSeed(var6);
        this.basePos[0] = pos.func_177958_n();
        this.basePos[1] = pos.func_177956_o();
        this.basePos[2] = pos.func_177952_p();
        if (this.heightLimit == 0) {
            this.heightLimit = this.heightLimitLimit + this.rand.nextInt(this.heightLimitLimit);
        }
        int x = 0;
        int z = 0;
        boolean valid = false;
    Label_0208:
        for (int a = -1; a < 2; ++a) {
            int b = -1;
        Label_0098:
            while (b < 2) {
                for (x = 0; x < this.trunkSize; ++x) {
                    for (z = 0; z < this.trunkSize; ++z) {
                        if (!this.validTreeLocation(x + a, z + b)) {
                            ++b;
                            continue Label_0098;
                        }
                    }
                }
                valid = true;
                this.basePos[0] += a;
                this.basePos[2] += b;
                break Label_0208;
            }
        }
        if (!valid) {
            return false;
        }
        this.world.func_175698_g(pos);
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();
        this.generateTrunk();
        this.scaleWidth = 1.66;
        this.basePos[0] = pos.func_177958_n();
        this.basePos[1] = pos.func_177956_o() + this.height;
        this.basePos[2] = pos.func_177952_p();
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();
        this.generateTrunk();
        if (this.spiders) {
            this.world.func_175656_a(pos.func_177977_b(), Blocks.field_150474_ac.func_176223_P());
            final TileEntityMobSpawner var7 = (TileEntityMobSpawner)par1World.func_175625_s(pos.func_177977_b());
            if (var7 != null) {
                var7.func_145881_a().func_190894_a(EntityList.func_191306_a((Class)EntityCaveSpider.class));
                for (int a2 = 0; a2 < 50; ++a2) {
                    final int xx = pos.func_177958_n() - 7 + par2Random.nextInt(14);
                    final int yy = pos.func_177956_o() + par2Random.nextInt(10);
                    final int zz = pos.func_177952_p() - 7 + par2Random.nextInt(14);
                    if (par1World.func_175623_d(new BlockPos(xx, yy, zz)) && (BlockUtils.isBlockTouching((IBlockAccess)par1World, new BlockPos(xx, yy, zz), BlocksTC.leafGreatwood) || BlockUtils.isBlockTouching((IBlockAccess)par1World, new BlockPos(xx, yy, zz), BlocksTC.logGreatwood))) {
                        this.world.func_175656_a(new BlockPos(xx, yy, zz), Blocks.field_150321_G.func_176223_P());
                    }
                }
                par1World.func_175656_a(pos.func_177979_c(2), Blocks.field_150486_ae.func_176223_P());
                final TileEntityChest var8 = (TileEntityChest)par1World.func_175625_s(pos.func_177979_c(2));
                if (var8 != null) {
                    var8.func_189404_a(LootTableList.field_186422_d, this.rand.nextLong());
                }
            }
        }
        return true;
    }
    
    static {
        otherCoordPairs = new byte[] { 2, 0, 0, 1, 2, 1 };
    }
}
