package thaumcraft.common.world.objects;

import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class WorldGenCustomFlowers extends WorldGenerator
{
    private Block plantBlock;
    private int plantBlockMeta;
    
    public WorldGenCustomFlowers(final Block bi, final int md) {
        this.plantBlock = bi;
        this.plantBlockMeta = md;
    }
    
    public boolean func_180709_b(final World world, final Random par2Random, final BlockPos pos) {
        for (int var6 = 0; var6 < 18; ++var6) {
            final int var7 = pos.func_177958_n() + par2Random.nextInt(8) - par2Random.nextInt(8);
            final int var8 = pos.func_177956_o() + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int var9 = pos.func_177952_p() + par2Random.nextInt(8) - par2Random.nextInt(8);
            final BlockPos bp = new BlockPos(var7, var8, var9);
            if (world.func_175623_d(bp) && (world.func_180495_p(bp.func_177977_b()).func_177230_c() == Blocks.field_150349_c || world.func_180495_p(bp.func_177977_b()).func_177230_c() == Blocks.field_150354_m)) {
                world.func_180501_a(bp, this.plantBlock.func_176203_a(this.plantBlockMeta), 3);
            }
        }
        return true;
    }
}
