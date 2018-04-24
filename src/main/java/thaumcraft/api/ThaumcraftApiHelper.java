package thaumcraft.api;

import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import java.nio.*;
import thaumcraft.api.items.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;

public class ThaumcraftApiHelper
{
    public static final IAttribute CHAMPION_MOD;
    
    public static boolean areItemsEqual(final ItemStack s1, final ItemStack s2) {
        if (s1.func_77984_f() && s2.func_77984_f()) {
            return s1.func_77973_b() == s2.func_77973_b();
        }
        return s1.func_77973_b() == s2.func_77973_b() && s1.func_77952_i() == s2.func_77952_i();
    }
    
    public static boolean containsMatch(final boolean strict, final ItemStack[] inputs, final List<ItemStack> targets) {
        for (final ItemStack input : inputs) {
            for (final ItemStack target : targets) {
                if (OreDictionary.itemMatches(target, input, strict) && ItemStack.func_77970_a(target, input)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean areItemStacksEqualForCrafting(final ItemStack stack0, final Object in) {
        if (stack0 == null && in != null) {
            return false;
        }
        if (stack0 != null && in == null) {
            return false;
        }
        if (stack0 == null && in == null) {
            return true;
        }
        if (in instanceof Object[]) {
            return true;
        }
        if (in instanceof String) {
            final List<ItemStack> l = (List<ItemStack>)OreDictionary.getOres((String)in, false);
            return containsMatch(false, new ItemStack[] { stack0 }, l);
        }
        if (in instanceof ItemStack) {
            final boolean t1 = !stack0.func_77942_o() || areItemStackTagsEqualForCrafting(stack0, (ItemStack)in);
            return t1 && OreDictionary.itemMatches((ItemStack)in, stack0, false);
        }
        return false;
    }
    
    public static boolean areItemStackTagsEqualForCrafting(final ItemStack slotItem, final ItemStack recipeItem) {
        if (recipeItem == null || slotItem == null) {
            return false;
        }
        if (recipeItem.func_77978_p() != null && slotItem.func_77978_p() == null) {
            return false;
        }
        if (recipeItem.func_77978_p() == null) {
            return true;
        }
        for (final String s : recipeItem.func_77978_p().func_150296_c()) {
            if (!slotItem.func_77978_p().func_74764_b(s)) {
                return false;
            }
            if (!slotItem.func_77978_p().func_74781_a(s).toString().equals(recipeItem.func_77978_p().func_74781_a(s).toString())) {
                return false;
            }
        }
        return true;
    }
    
    public static TileEntity getConnectableTile(final World world, final BlockPos pos, final EnumFacing face) {
        final TileEntity te = world.func_175625_s(pos.func_177972_a(face));
        if (te instanceof IEssentiaTransport && ((IEssentiaTransport)te).isConnectable(face.func_176734_d())) {
            return te;
        }
        return null;
    }
    
    public static TileEntity getConnectableTile(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        final TileEntity te = world.func_175625_s(pos.func_177972_a(face));
        if (te instanceof IEssentiaTransport && ((IEssentiaTransport)te).isConnectable(face.func_176734_d())) {
            return te;
        }
        return null;
    }
    
    public static RayTraceResult rayTraceIgnoringSource(final World world, Vec3d v1, final Vec3d v2, final boolean bool1, final boolean bool2, final boolean bool3) {
        if (Double.isNaN(v1.field_72450_a) || Double.isNaN(v1.field_72448_b) || Double.isNaN(v1.field_72449_c)) {
            return null;
        }
        if (!Double.isNaN(v2.field_72450_a) && !Double.isNaN(v2.field_72448_b) && !Double.isNaN(v2.field_72449_c)) {
            final int i = MathHelper.func_76128_c(v2.field_72450_a);
            final int j = MathHelper.func_76128_c(v2.field_72448_b);
            final int k = MathHelper.func_76128_c(v2.field_72449_c);
            int l = MathHelper.func_76128_c(v1.field_72450_a);
            int i2 = MathHelper.func_76128_c(v1.field_72448_b);
            int j2 = MathHelper.func_76128_c(v1.field_72449_c);
            final IBlockState block = world.func_180495_p(new BlockPos(l, i2, j2));
            RayTraceResult rayTraceResult2 = null;
            int k2 = 200;
            while (k2-- >= 0) {
                if (Double.isNaN(v1.field_72450_a) || Double.isNaN(v1.field_72448_b) || Double.isNaN(v1.field_72449_c)) {
                    return null;
                }
                if (l == i && i2 == j && j2 == k) {
                    continue;
                }
                boolean flag6 = true;
                boolean flag7 = true;
                boolean flag8 = true;
                double d0 = 999.0;
                double d2 = 999.0;
                double d3 = 999.0;
                if (i > l) {
                    d0 = l + 1.0;
                }
                else if (i < l) {
                    d0 = l + 0.0;
                }
                else {
                    flag6 = false;
                }
                if (j > i2) {
                    d2 = i2 + 1.0;
                }
                else if (j < i2) {
                    d2 = i2 + 0.0;
                }
                else {
                    flag7 = false;
                }
                if (k > j2) {
                    d3 = j2 + 1.0;
                }
                else if (k < j2) {
                    d3 = j2 + 0.0;
                }
                else {
                    flag8 = false;
                }
                double d4 = 999.0;
                double d5 = 999.0;
                double d6 = 999.0;
                final double d7 = v2.field_72450_a - v1.field_72450_a;
                final double d8 = v2.field_72448_b - v1.field_72448_b;
                final double d9 = v2.field_72449_c - v1.field_72449_c;
                if (flag6) {
                    d4 = (d0 - v1.field_72450_a) / d7;
                }
                if (flag7) {
                    d5 = (d2 - v1.field_72448_b) / d8;
                }
                if (flag8) {
                    d6 = (d3 - v1.field_72449_c) / d9;
                }
                if (d4 == -0.0) {
                    d4 = -1.0E-4;
                }
                if (d5 == -0.0) {
                    d5 = -1.0E-4;
                }
                if (d6 == -0.0) {
                    d6 = -1.0E-4;
                }
                EnumFacing enumfacing;
                if (d4 < d5 && d4 < d6) {
                    enumfacing = ((i > l) ? EnumFacing.WEST : EnumFacing.EAST);
                    v1 = new Vec3d(d0, v1.field_72448_b + d8 * d4, v1.field_72449_c + d9 * d4);
                }
                else if (d5 < d6) {
                    enumfacing = ((j > i2) ? EnumFacing.DOWN : EnumFacing.UP);
                    v1 = new Vec3d(v1.field_72450_a + d7 * d5, d2, v1.field_72449_c + d9 * d5);
                }
                else {
                    enumfacing = ((k > j2) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                    v1 = new Vec3d(v1.field_72450_a + d7 * d6, v1.field_72448_b + d8 * d6, d3);
                }
                l = MathHelper.func_76128_c(v1.field_72450_a) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
                i2 = MathHelper.func_76128_c(v1.field_72448_b) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
                j2 = MathHelper.func_76128_c(v1.field_72449_c) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
                final IBlockState block2 = world.func_180495_p(new BlockPos(l, i2, j2));
                if (bool2 && block2.func_185890_d((IBlockAccess)world, new BlockPos(l, i2, j2)) == null) {
                    continue;
                }
                if (block2.func_177230_c().func_176209_a(block2, bool1)) {
                    final RayTraceResult rayTraceResult3 = block2.func_185910_a(world, new BlockPos(l, i2, j2), v1, v2);
                    if (rayTraceResult3 != null) {
                        return rayTraceResult3;
                    }
                    continue;
                }
                else {
                    rayTraceResult2 = new RayTraceResult(RayTraceResult.Type.MISS, v1, enumfacing, new BlockPos(l, i2, j2));
                }
            }
            return bool3 ? rayTraceResult2 : null;
        }
        return null;
    }
    
    public static Object getNBTDataFromId(final NBTTagCompound nbt, final byte id, final String key) {
        switch (id) {
            case 1: {
                return nbt.func_74771_c(key);
            }
            case 2: {
                return nbt.func_74765_d(key);
            }
            case 3: {
                return nbt.func_74762_e(key);
            }
            case 4: {
                return nbt.func_74763_f(key);
            }
            case 5: {
                return nbt.func_74760_g(key);
            }
            case 6: {
                return nbt.func_74769_h(key);
            }
            case 7: {
                return nbt.func_74770_j(key);
            }
            case 8: {
                return nbt.func_74779_i(key);
            }
            case 9: {
                return nbt.func_150295_c(key, 10);
            }
            case 10: {
                return nbt.func_74781_a(key);
            }
            case 11: {
                return nbt.func_74759_k(key);
            }
            default: {
                return null;
            }
        }
    }
    
    public static int setByteInInt(final int data, final byte b, final int index) {
        final ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(0, data);
        bb.put(index, b);
        return bb.getInt(0);
    }
    
    public static byte getByteInInt(final int data, final int index) {
        final ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(0, data);
        return bb.get(index);
    }
    
    public static long setByteInLong(final long data, final byte b, final int index) {
        final ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(0, data);
        bb.put(index, b);
        return bb.getLong(0);
    }
    
    public static byte getByteInLong(final long data, final int index) {
        final ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(0, data);
        return bb.get(index);
    }
    
    public static int setNibbleInInt(final int data, final int nibble, final int nibbleIndex) {
        final int shift = nibbleIndex * 4;
        return (data & ~(15 << shift)) | nibble << shift;
    }
    
    public static int getNibbleInInt(final int data, final int nibbleIndex) {
        return data >> (nibbleIndex << 2) & 0xF;
    }
    
    public static ItemStack makeCrystal(final Aspect aspect, final int stackSize) {
        if (aspect == null) {
            return null;
        }
        final ItemStack is = new ItemStack(ItemsTC.crystalEssence, stackSize, 0);
        ((ItemGenericEssentiaContainer)ItemsTC.crystalEssence).setAspects(is, new AspectList().add(aspect, 1));
        return is;
    }
    
    public static ItemStack makeCrystal(final Aspect aspect) {
        return makeCrystal(aspect, 1);
    }
    
    public static List<ItemStack> getOresWithWildCards(final String oreDict) {
        if (oreDict.trim().endsWith("*")) {
            final ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
            final String[] names = OreDictionary.getOreNames();
            final String m = oreDict.trim().replaceAll("\\*", "");
            for (final String name : names) {
                if (name.startsWith(m)) {
                    ores.addAll((Collection<? extends ItemStack>)OreDictionary.getOres(name, false));
                }
            }
            return ores;
        }
        return (List<ItemStack>)OreDictionary.getOres(oreDict, false);
    }
    
    static {
        CHAMPION_MOD = (IAttribute)new RangedAttribute((IAttribute)null, "tc.mobmod", -2.0, -2.0, 100.0).func_111117_a("Champion modifier").func_111112_a(true);
    }
}
