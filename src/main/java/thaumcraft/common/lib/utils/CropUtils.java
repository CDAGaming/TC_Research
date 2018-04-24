package thaumcraft.common.lib.utils;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class CropUtils
{
    public static ArrayList<String> clickableCrops;
    public static ArrayList<String> standardCrops;
    public static ArrayList<String> stackedCrops;
    public static ArrayList<String> lampBlacklist;
    
    public static void addStandardCrop(final ItemStack stack, final int grownMeta) {
        final Block block = Block.func_149634_a(stack.func_77973_b());
        if (block == null) {
            return;
        }
        addStandardCrop(block, grownMeta);
    }
    
    public static void addStandardCrop(final Block block, final int grownMeta) {
        if (grownMeta == 32767) {
            for (int a = 0; a < 16; ++a) {
                CropUtils.standardCrops.add(block.func_149739_a() + a);
            }
        }
        else {
            CropUtils.standardCrops.add(block.func_149739_a() + grownMeta);
        }
        if (block instanceof BlockCrops && grownMeta != 7) {
            CropUtils.standardCrops.add(block.func_149739_a() + "7");
        }
    }
    
    public static void addClickableCrop(final ItemStack stack, final int grownMeta) {
        if (Block.func_149634_a(stack.func_77973_b()) == null) {
            return;
        }
        if (grownMeta == 32767) {
            for (int a = 0; a < 16; ++a) {
                CropUtils.clickableCrops.add(Block.func_149634_a(stack.func_77973_b()).func_149739_a() + a);
            }
        }
        else {
            CropUtils.clickableCrops.add(Block.func_149634_a(stack.func_77973_b()).func_149739_a() + grownMeta);
        }
        if (Block.func_149634_a(stack.func_77973_b()) instanceof BlockCrops && grownMeta != 7) {
            CropUtils.clickableCrops.add(Block.func_149634_a(stack.func_77973_b()).func_149739_a() + "7");
        }
    }
    
    public static void addStackedCrop(final ItemStack stack, final int grownMeta) {
        if (Block.func_149634_a(stack.func_77973_b()) == null) {
            return;
        }
        addStackedCrop(Block.func_149634_a(stack.func_77973_b()), grownMeta);
    }
    
    public static void addStackedCrop(final Block block, final int grownMeta) {
        if (grownMeta == 32767) {
            for (int a = 0; a < 16; ++a) {
                CropUtils.stackedCrops.add(block.func_149739_a() + a);
            }
        }
        else {
            CropUtils.stackedCrops.add(block.func_149739_a() + grownMeta);
        }
        if (block instanceof BlockCrops && grownMeta != 7) {
            CropUtils.stackedCrops.add(block.func_149739_a() + "7");
        }
    }
    
    public static boolean isGrownCrop(final World world, final BlockPos pos) {
        if (world.func_175623_d(pos)) {
            return false;
        }
        boolean found = false;
        final IBlockState bs = world.func_180495_p(pos);
        final Block bi = bs.func_177230_c();
        final int md = bi.func_176201_c(bs);
        if (CropUtils.standardCrops.contains(bi.func_149739_a() + md) || CropUtils.clickableCrops.contains(bi.func_149739_a() + md) || CropUtils.stackedCrops.contains(bi.func_149739_a() + md)) {
            found = true;
        }
        final Block biB = world.func_180495_p(pos.func_177977_b()).func_177230_c();
        return (bi instanceof IGrowable && !((IGrowable)bi).func_176473_a(world, pos, world.func_180495_p(pos), world.field_72995_K) && !(bi instanceof BlockStem)) || (bi instanceof BlockCrops && md == 7 && !found) || CropUtils.standardCrops.contains(bi.func_149739_a() + md) || CropUtils.clickableCrops.contains(bi.func_149739_a() + md) || (CropUtils.stackedCrops.contains(bi.func_149739_a() + md) && biB == bi);
    }
    
    public static void blacklistLamp(final ItemStack stack, final int meta) {
        if (Block.func_149634_a(stack.func_77973_b()) == null) {
            return;
        }
        if (meta == 32767) {
            for (int a = 0; a < 16; ++a) {
                CropUtils.lampBlacklist.add(Block.func_149634_a(stack.func_77973_b()).func_149739_a() + a);
            }
        }
        else {
            CropUtils.lampBlacklist.add(Block.func_149634_a(stack.func_77973_b()).func_149739_a() + meta);
        }
    }
    
    public static boolean doesLampGrow(final World world, final BlockPos pos) {
        final Block bi = world.func_180495_p(pos).func_177230_c();
        final int md = bi.func_176201_c(world.func_180495_p(pos));
        return !CropUtils.lampBlacklist.contains(bi.func_149739_a() + md);
    }
    
    static {
        CropUtils.clickableCrops = new ArrayList<String>();
        CropUtils.standardCrops = new ArrayList<String>();
        CropUtils.stackedCrops = new ArrayList<String>();
        CropUtils.lampBlacklist = new ArrayList<String>();
    }
}
