package thaumcraft.common.world.objects;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraftforge.common.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;

public class WorldGenSilverwoodTrees extends WorldGenAbstractTree
{
    private final int minTreeHeight;
    private final int randomTreeHeight;
    boolean worldgen;
    
    public WorldGenSilverwoodTrees(final boolean doBlockNotify, final int minTreeHeight, final int randomTreeHeight) {
        super(doBlockNotify);
        this.worldgen = false;
        this.worldgen = !doBlockNotify;
        this.minTreeHeight = minTreeHeight;
        this.randomTreeHeight = randomTreeHeight;
    }
    
    public boolean func_180709_b(final World world, final Random random, final BlockPos pos) {
        final int height = random.nextInt(this.randomTreeHeight) + this.minTreeHeight;
        boolean flag = true;
        final int x = pos.func_177958_n();
        final int y = pos.func_177956_o();
        final int z = pos.func_177952_p();
        if (y < 1 || y + height + 1 > 256) {
            return false;
        }
        for (int i1 = y; i1 <= y + 1 + height; ++i1) {
            byte spread = 1;
            if (i1 == y) {
                spread = 0;
            }
            if (i1 >= y + 1 + height - 2) {
                spread = 3;
            }
            for (int j1 = x - spread; j1 <= x + spread && flag; ++j1) {
                for (int k1 = z - spread; k1 <= z + spread && flag; ++k1) {
                    if (i1 >= 0 && i1 < 256) {
                        final IBlockState state = world.func_180495_p(new BlockPos(j1, i1, k1));
                        final Block block = state.func_177230_c();
                        if (!block.isAir(state, (IBlockAccess)world, new BlockPos(j1, i1, k1)) && !block.isLeaves(state, (IBlockAccess)world, new BlockPos(j1, i1, k1)) && !block.func_176200_f((IBlockAccess)world, new BlockPos(j1, i1, k1)) && i1 > y) {
                            flag = false;
                        }
                    }
                    else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        final IBlockState state2 = world.func_180495_p(new BlockPos(x, y - 1, z));
        final Block block2 = state2.func_177230_c();
        final boolean isSoil = block2.canSustainPlant(state2, (IBlockAccess)world, new BlockPos(x, y - 1, z), EnumFacing.UP, (IPlantable)Blocks.field_150345_g);
        if (isSoil && y < 256 - height - 1) {
            block2.onPlantGrow(state2, world, new BlockPos(x, y - 1, z), new BlockPos(x, y, z));
            final int start = y + height - 5;
            for (int end = y + height + 3 + random.nextInt(3), k2 = start; k2 <= end; ++k2) {
                final int cty = MathHelper.func_76125_a(k2, y + height - 3, y + height);
                for (int xx = x - 5; xx <= x + 5; ++xx) {
                    for (int zz = z - 5; zz <= z + 5; ++zz) {
                        final double d3 = xx - x;
                        final double d4 = k2 - cty;
                        final double d5 = zz - z;
                        final double dist = d3 * d3 + d4 * d4 + d5 * d5;
                        final IBlockState s2 = world.func_180495_p(new BlockPos(xx, k2, zz));
                        if (dist < 10 + random.nextInt(8) && s2.func_177230_c().canBeReplacedByLeaves(s2, (IBlockAccess)world, new BlockPos(xx, k2, zz))) {
                            this.func_175903_a(world, new BlockPos(xx, k2, zz), BlocksTC.leafSilverwood.func_176203_a(1));
                        }
                    }
                }
            }
            int k2;
            for (k2 = 0; k2 < height; ++k2) {
                final IBlockState s3 = world.func_180495_p(new BlockPos(x, y + k2, z));
                final Block block3 = s3.func_177230_c();
                if (block3.isAir(s3, (IBlockAccess)world, new BlockPos(x, y + k2, z)) || block3.isLeaves(s3, (IBlockAccess)world, new BlockPos(x, y + k2, z)) || block3.func_176200_f((IBlockAccess)world, new BlockPos(x, y + k2, z))) {
                    this.func_175903_a(world, new BlockPos(x, y + k2, z), BlocksTC.logSilverwood.func_176203_a(1));
                    this.func_175903_a(world, new BlockPos(x - 1, y + k2, z), BlocksTC.logSilverwood.func_176203_a(1));
                    this.func_175903_a(world, new BlockPos(x + 1, y + k2, z), BlocksTC.logSilverwood.func_176203_a(1));
                    this.func_175903_a(world, new BlockPos(x, y + k2, z - 1), BlocksTC.logSilverwood.func_176203_a(1));
                    this.func_175903_a(world, new BlockPos(x, y + k2, z + 1), BlocksTC.logSilverwood.func_176203_a(1));
                }
            }
            this.func_175903_a(world, new BlockPos(x, y + k2, z), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x - 1, y, z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x + 1, y, z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x - 1, y, z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x + 1, y, z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            if (random.nextInt(3) != 0) {
                this.func_175903_a(world, new BlockPos(x - 1, y + 1, z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            if (random.nextInt(3) != 0) {
                this.func_175903_a(world, new BlockPos(x + 1, y + 1, z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            if (random.nextInt(3) != 0) {
                this.func_175903_a(world, new BlockPos(x - 1, y + 1, z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            if (random.nextInt(3) != 0) {
                this.func_175903_a(world, new BlockPos(x + 1, y + 1, z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            this.func_175903_a(world, new BlockPos(x - 2, y, z), BlocksTC.logSilverwood.func_176203_a(0));
            this.func_175903_a(world, new BlockPos(x + 2, y, z), BlocksTC.logSilverwood.func_176203_a(0));
            this.func_175903_a(world, new BlockPos(x, y, z - 2), BlocksTC.logSilverwood.func_176203_a(2));
            this.func_175903_a(world, new BlockPos(x, y, z + 2), BlocksTC.logSilverwood.func_176203_a(2));
            this.func_175903_a(world, new BlockPos(x - 2, y - 1, z), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x + 2, y - 1, z), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x, y - 1, z - 2), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x, y - 1, z + 2), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x - 1, y + (height - 4), z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x + 1, y + (height - 4), z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x - 1, y + (height - 4), z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            this.func_175903_a(world, new BlockPos(x + 1, y + (height - 4), z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            if (random.nextInt(3) == 0) {
                this.func_175903_a(world, new BlockPos(x - 1, y + (height - 5), z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            if (random.nextInt(3) == 0) {
                this.func_175903_a(world, new BlockPos(x + 1, y + (height - 5), z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            if (random.nextInt(3) == 0) {
                this.func_175903_a(world, new BlockPos(x - 1, y + (height - 5), z + 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            if (random.nextInt(3) == 0) {
                this.func_175903_a(world, new BlockPos(x + 1, y + (height - 5), z - 1), BlocksTC.logSilverwood.func_176203_a(1));
            }
            this.func_175903_a(world, new BlockPos(x - 2, y + (height - 4), z), BlocksTC.logSilverwood.func_176203_a(0));
            this.func_175903_a(world, new BlockPos(x + 2, y + (height - 4), z), BlocksTC.logSilverwood.func_176203_a(0));
            this.func_175903_a(world, new BlockPos(x, y + (height - 4), z - 2), BlocksTC.logSilverwood.func_176203_a(2));
            this.func_175903_a(world, new BlockPos(x, y + (height - 4), z + 2), BlocksTC.logSilverwood.func_176203_a(2));
            if (this.worldgen) {
                final WorldGenerator flowers = new WorldGenCustomFlowers(BlocksTC.shimmerleaf, 0);
                flowers.func_180709_b(world, random, new BlockPos(x, y, z));
            }
            return true;
        }
        return false;
    }
}
