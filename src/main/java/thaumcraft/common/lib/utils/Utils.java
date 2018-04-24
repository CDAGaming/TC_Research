package thaumcraft.common.lib.utils;

import net.minecraft.world.chunk.*;
import java.nio.channels.*;
import java.util.*;
import net.minecraft.world.biome.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraftforge.oredict.*;
import thaumcraft.api.internal.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import io.netty.handler.codec.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import java.lang.reflect.*;

public class Utils
{
    public static HashMap<List<Object>, ItemStack> specialMiningResult;
    public static HashMap<List<Object>, Float> specialMiningChance;
    public static final String[] colorNames;
    public static final int[] colors;
    public static ArrayList<List> oreDictLogs;
    
    public static boolean isChunkLoaded(final World world, final int x, final int z) {
        final Chunk chunk = world.func_72863_F().func_186026_b(x >> 4, z >> 4);
        return chunk != null && !chunk.func_76621_g();
    }
    
    public static boolean useBonemealAtLoc(final World world, final EntityPlayer player, final BlockPos pos) {
        final ItemStack is = new ItemStack(Items.field_151100_aR, 1, 15);
        final ItemDye itemDye = (ItemDye)Items.field_151100_aR;
        return ItemDye.applyBonemeal(is, world, pos, player, EnumHand.MAIN_HAND);
    }
    
    public static boolean hasColor(final byte[] colors) {
        for (final byte col : colors) {
            if (col >= 0) {
                return true;
            }
        }
        return false;
    }
    
    public static void copyFile(final File sourceFile, final File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0L, source.size());
        }
        finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
    
    public static void addSpecialMiningResult(final ItemStack in, final ItemStack out, final float chance) {
        Utils.specialMiningResult.put(Arrays.asList(in.func_77973_b(), in.func_77952_i()), out);
        Utils.specialMiningChance.put(Arrays.asList(in.func_77973_b(), in.func_77952_i()), chance);
    }
    
    public static ItemStack findSpecialMiningResult(final ItemStack is, final float chance, final Random rand) {
        ItemStack dropped = is.func_77946_l();
        final float r = rand.nextFloat();
        final List ik = Arrays.asList(is.func_77973_b(), is.func_77952_i());
        if (Utils.specialMiningResult.containsKey(ik) && r <= chance * Utils.specialMiningChance.get(ik)) {
            dropped = Utils.specialMiningResult.get(ik).func_77946_l();
            dropped.func_190920_e(dropped.func_190916_E() * is.func_190916_E());
        }
        return dropped;
    }
    
    public static float clamp_float(final float par0, final float par1, final float par2) {
        return (par0 < par1) ? par1 : ((par0 > par2) ? par2 : par0);
    }
    
    public static void setBiomeAt(final World world, final BlockPos pos, final Biome biome) {
        setBiomeAt(world, pos, biome, true);
    }
    
    public static void setBiomeAt(final World world, final BlockPos pos, final Biome biome, final boolean sync) {
        if (biome == null) {
            return;
        }
        final Chunk chunk = world.func_175726_f(pos);
        final byte[] array = chunk.func_76605_m();
        array[(pos.func_177952_p() & 0xF) << 4 | (pos.func_177958_n() & 0xF)] = (byte)(Biome.func_185362_a(biome) & 0xFF);
        chunk.func_76616_a(array);
        if (sync && !world.field_72995_K) {
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketBiomeChange(pos.func_177958_n(), pos.func_177952_p(), (short)Biome.func_185362_a(biome)), new NetworkRegistry.TargetPoint(world.field_73011_w.getDimension(), (double)pos.func_177958_n(), (double)world.func_175645_m(pos).func_177956_o(), (double)pos.func_177952_p(), 32.0));
        }
    }
    
    public static boolean resetBiomeAt(final World world, final BlockPos pos) {
        return resetBiomeAt(world, pos, true);
    }
    
    public static boolean resetBiomeAt(final World world, final BlockPos pos, final boolean sync) {
        Biome[] biomesForGeneration = null;
        biomesForGeneration = world.func_72959_q().func_76937_a(biomesForGeneration, pos.func_177958_n(), pos.func_177952_p(), 1, 1);
        if (biomesForGeneration != null && biomesForGeneration[0] != null) {
            final Biome biome = biomesForGeneration[0];
            if (biome != world.func_180494_b(pos)) {
                setBiomeAt(world, pos, biome, sync);
                return true;
            }
        }
        return false;
    }
    
    public static boolean isWoodLog(final IBlockAccess world, final BlockPos pos) {
        final IBlockState bs = world.func_180495_p(pos);
        final Block bi = bs.func_177230_c();
        return bi.isWood(world, pos) || bi.canSustainLeaves(bs, world, pos) || Utils.oreDictLogs.contains(Arrays.asList(bi, bi.func_176201_c(bs)));
    }
    
    public static boolean isOreBlock(final World world, final BlockPos pos) {
        final IBlockState bi = world.func_180495_p(pos);
        if (bi.func_177230_c() != Blocks.field_150350_a && bi.func_177230_c() != Blocks.field_150357_h) {
            ItemStack is = BlockUtils.getSilkTouchDrop(bi);
            if (is == null || is.func_190926_b()) {
                final int md = bi.func_177230_c().func_176201_c(bi);
                is = new ItemStack(bi.func_177230_c(), 1, md);
            }
            if (is == null || is.func_190926_b() || is.func_77973_b() == null) {
                return false;
            }
            final int[] od = OreDictionary.getOreIDs(is);
            if (od != null && od.length > 0) {
                for (final int id : od) {
                    if (OreDictionary.getOreName(id) != null && OreDictionary.getOreName(id).toUpperCase().contains("ORE")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static int setNibble(final int data, final int nibble, final int nibbleIndex) {
        final int shift = nibbleIndex * 4;
        return (data & ~(15 << shift)) | nibble << shift;
    }
    
    public static int getNibble(final int data, final int nibbleIndex) {
        return data >> (nibbleIndex << 2) & 0xF;
    }
    
    public static boolean getBit(final int value, final int bit) {
        return (value & 1 << bit) != 0x0;
    }
    
    public static int setBit(final int value, final int bit) {
        return value | 1 << bit;
    }
    
    public static int clearBit(final int value, final int bit) {
        return value & ~(1 << bit);
    }
    
    public static int toggleBit(final int value, final int bit) {
        return value ^ 1 << bit;
    }
    
    public static byte pack(final boolean... vals) {
        byte result = 0;
        for (final boolean bit : vals) {
            result = (byte)(result << 1 | ((bit & true) ? 1 : 0));
        }
        return result;
    }
    
    public static boolean[] unpack(final byte val) {
        final boolean[] result = new boolean[8];
        for (int i = 0; i < 8; ++i) {
            result[i] = ((byte)(val >> 7 - i & 0x1) == 1);
        }
        return result;
    }
    
    public static final byte[] intToByteArray(final int value) {
        return new byte[] { (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value };
    }
    
    public static int byteArraytoInt(final byte[] bytes) {
        return bytes[0] << 24 | bytes[1] << 16 | bytes[2] << 8 | bytes[3];
    }
    
    public static final byte[] shortToByteArray(final short value) {
        return new byte[] { (byte)(value >>> 8), (byte)value };
    }
    
    public static short byteArraytoShort(final byte[] bytes) {
        return (short)(bytes[0] << 8 | bytes[1]);
    }
    
    public static boolean isLyingInCone(final double[] x, final double[] t, final double[] b, final float aperture) {
        final double halfAperture = aperture / 2.0f;
        final double[] apexToXVect = dif(t, x);
        final double[] axisVect = dif(t, b);
        final boolean isInInfiniteCone = dotProd(apexToXVect, axisVect) / magn(apexToXVect) / magn(axisVect) > Math.cos(halfAperture);
        if (!isInInfiniteCone) {
            return false;
        }
        final boolean isUnderRoundCap = dotProd(apexToXVect, axisVect) / magn(axisVect) < magn(axisVect);
        return isUnderRoundCap;
    }
    
    public static double dotProd(final double[] a, final double[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }
    
    public static double[] dif(final double[] a, final double[] b) {
        return new double[] { a[0] - b[0], a[1] - b[1], a[2] - b[2] };
    }
    
    public static double magn(final double[] a) {
        return Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    }
    
    public static Vec3d calculateVelocity(final Vec3d from, final Vec3d to, final double heightGain, final double gravity) {
        final double endGain = to.field_72448_b - from.field_72448_b;
        final double horizDist = Math.sqrt(distanceSquared2d(from, to));
        final double maxGain = (heightGain > endGain + heightGain) ? heightGain : (endGain + heightGain);
        final double a = -horizDist * horizDist / (4.0 * maxGain);
        final double b = horizDist;
        final double c = -endGain;
        final double slope = -b / (2.0 * a) - Math.sqrt(b * b - 4.0 * a * c) / (2.0 * a);
        final double vy = Math.sqrt(maxGain * gravity);
        final double vh = vy / slope;
        final double dx = to.field_72450_a - from.field_72450_a;
        final double dz = to.field_72449_c - from.field_72449_c;
        final double mag = Math.sqrt(dx * dx + dz * dz);
        final double dirx = dx / mag;
        final double dirz = dz / mag;
        final double vx = vh * dirx;
        final double vz = vh * dirz;
        return new Vec3d(vx, vy, vz);
    }
    
    public static double distanceSquared2d(final Vec3d from, final Vec3d to) {
        final double dx = to.field_72450_a - from.field_72450_a;
        final double dz = to.field_72449_c - from.field_72449_c;
        return dx * dx + dz * dz;
    }
    
    public static double distanceSquared3d(final Vec3d from, final Vec3d to) {
        final double dx = to.field_72450_a - from.field_72450_a;
        final double dy = to.field_72448_b - from.field_72448_b;
        final double dz = to.field_72449_c - from.field_72449_c;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public static ItemStack generateLoot(final int rarity, final Random rand) {
        ItemStack is = ItemStack.field_190927_a;
        if (rarity > 0 && rand.nextFloat() < 0.025f * rarity) {
            is = genGear(rarity, rand);
            if (is.func_190926_b()) {
                is = generateLoot(rarity, rand);
            }
        }
        else {
            switch (rarity) {
                default: {
                    is = ((WeightedRandomLoot)WeightedRandom.func_76271_a(rand, (List)WeightedRandomLoot.lootBagCommon)).item;
                    break;
                }
                case 1: {
                    is = ((WeightedRandomLoot)WeightedRandom.func_76271_a(rand, (List)WeightedRandomLoot.lootBagUncommon)).item;
                    break;
                }
                case 2: {
                    is = ((WeightedRandomLoot)WeightedRandom.func_76271_a(rand, (List)WeightedRandomLoot.lootBagRare)).item;
                    break;
                }
            }
        }
        if (is.func_77973_b() == Items.field_151122_aG) {
            EnchantmentHelper.func_77504_a(rand, is, (int)(5.0f + rarity * 0.75f * rand.nextInt(18)), false);
        }
        return is.func_77946_l();
    }
    
    private static ItemStack genGear(final int rarity, final Random rand) {
        ItemStack is = ItemStack.field_190927_a;
        int quality = rand.nextInt(2);
        if (rand.nextFloat() < 0.2f) {
            ++quality;
        }
        if (rand.nextFloat() < 0.15f) {
            ++quality;
        }
        if (rand.nextFloat() < 0.1f) {
            ++quality;
        }
        if (rand.nextFloat() < 0.095f) {
            ++quality;
        }
        if (rand.nextFloat() < 0.095f) {
            ++quality;
        }
        final Item item = getGearItemForSlot(rand.nextInt(5), quality);
        if (item != null) {
            is = new ItemStack(item, 1, rand.nextInt(1 + item.func_77612_l() / 6));
            if (rand.nextInt(4) < rarity) {
                EnchantmentHelper.func_77504_a(rand, is, (int)(5.0f + rarity * 0.75f * rand.nextInt(18)), false);
            }
            return is.func_77946_l();
        }
        return ItemStack.field_190927_a;
    }
    
    private static Item getGearItemForSlot(final int slot, final int quality) {
        switch (slot) {
            case 4: {
                if (quality == 0) {
                    return (Item)Items.field_151024_Q;
                }
                if (quality == 1) {
                    return (Item)Items.field_151169_ag;
                }
                if (quality == 2) {
                    return (Item)Items.field_151020_U;
                }
                if (quality == 3) {
                    return (Item)Items.field_151028_Y;
                }
                if (quality == 4) {
                    return ItemsTC.thaumiumHelm;
                }
                if (quality == 5) {
                    return (Item)Items.field_151161_ac;
                }
                if (quality == 6) {
                    return ItemsTC.voidHelm;
                }
            }
            case 3: {
                if (quality == 0) {
                    return (Item)Items.field_151027_R;
                }
                if (quality == 1) {
                    return (Item)Items.field_151171_ah;
                }
                if (quality == 2) {
                    return (Item)Items.field_151023_V;
                }
                if (quality == 3) {
                    return (Item)Items.field_151030_Z;
                }
                if (quality == 4) {
                    return ItemsTC.thaumiumChest;
                }
                if (quality == 5) {
                    return (Item)Items.field_151163_ad;
                }
                if (quality == 6) {
                    return ItemsTC.voidChest;
                }
            }
            case 2: {
                if (quality == 0) {
                    return (Item)Items.field_151026_S;
                }
                if (quality == 1) {
                    return (Item)Items.field_151149_ai;
                }
                if (quality == 2) {
                    return (Item)Items.field_151022_W;
                }
                if (quality == 3) {
                    return (Item)Items.field_151165_aa;
                }
                if (quality == 4) {
                    return ItemsTC.thaumiumLegs;
                }
                if (quality == 5) {
                    return (Item)Items.field_151173_ae;
                }
                if (quality == 6) {
                    return ItemsTC.voidLegs;
                }
            }
            case 1: {
                if (quality == 0) {
                    return (Item)Items.field_151021_T;
                }
                if (quality == 1) {
                    return (Item)Items.field_151151_aj;
                }
                if (quality == 2) {
                    return (Item)Items.field_151029_X;
                }
                if (quality == 3) {
                    return (Item)Items.field_151167_ab;
                }
                if (quality == 4) {
                    return ItemsTC.thaumiumBoots;
                }
                if (quality == 5) {
                    return (Item)Items.field_151175_af;
                }
                if (quality == 6) {
                    return ItemsTC.voidBoots;
                }
            }
            case 0: {
                if (quality == 0) {
                    return Items.field_151036_c;
                }
                if (quality == 1) {
                    return Items.field_151040_l;
                }
                if (quality == 2) {
                    return Items.field_151006_E;
                }
                if (quality == 3) {
                    return Items.field_151010_B;
                }
                if (quality == 4) {
                    return ItemsTC.thaumiumSword;
                }
                if (quality == 5) {
                    return Items.field_151048_u;
                }
                if (quality == 6) {
                    return ItemsTC.voidSword;
                }
                break;
            }
        }
        return null;
    }
    
    public static void writeItemStackToBuffer(final ByteBuf bb, final ItemStack stack) {
        if (stack == null || stack.func_190926_b()) {
            bb.writeShort(-1);
        }
        else {
            bb.writeShort(Item.func_150891_b(stack.func_77973_b()));
            bb.writeByte(stack.func_190916_E());
            bb.writeShort(stack.func_77960_j());
            NBTTagCompound nbttagcompound = null;
            if (stack.func_77973_b().func_77645_m() || stack.func_77973_b().func_77651_p()) {
                nbttagcompound = stack.func_77978_p();
            }
            writeNBTTagCompoundToBuffer(bb, nbttagcompound);
        }
    }
    
    public static ItemStack readItemStackFromBuffer(final ByteBuf bb) {
        ItemStack itemstack = ItemStack.field_190927_a;
        final short short1 = bb.readShort();
        if (short1 >= 0) {
            final byte b0 = bb.readByte();
            final short short2 = bb.readShort();
            itemstack = new ItemStack(Item.func_150899_d((int)short1), (int)b0, (int)short2);
            itemstack.func_77982_d(readNBTTagCompoundFromBuffer(bb));
        }
        return itemstack;
    }
    
    public static void writeNBTTagCompoundToBuffer(final ByteBuf bb, final NBTTagCompound nbt) {
        if (nbt == null) {
            bb.writeByte(0);
        }
        else {
            try {
                CompressedStreamTools.func_74800_a(nbt, (DataOutput)new ByteBufOutputStream(bb));
            }
            catch (IOException ioexception) {
                throw new EncoderException((Throwable)ioexception);
            }
        }
    }
    
    public static NBTTagCompound readNBTTagCompoundFromBuffer(final ByteBuf bb) {
        final int i = bb.readerIndex();
        final byte b0 = bb.readByte();
        if (b0 == 0) {
            return null;
        }
        bb.readerIndex(i);
        try {
            return CompressedStreamTools.func_152456_a((DataInput)new ByteBufInputStream(bb), new NBTSizeTracker(2097152L));
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public static Vec3d rotateAsBlock(final Vec3d vec, final EnumFacing side) {
        return rotate(vec.func_178786_a(0.5, 0.5, 0.5), side).func_72441_c(0.5, 0.5, 0.5);
    }
    
    public static Vec3d rotateAsBlockRev(final Vec3d vec, final EnumFacing side) {
        return revRotate(vec.func_178786_a(0.5, 0.5, 0.5), side).func_72441_c(0.5, 0.5, 0.5);
    }
    
    public static Vec3d rotate(final Vec3d vec, final EnumFacing side) {
        switch (side) {
            case DOWN: {
                return new Vec3d(vec.field_72450_a, -vec.field_72448_b, -vec.field_72449_c);
            }
            case UP: {
                return new Vec3d(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
            }
            case NORTH: {
                return new Vec3d(vec.field_72450_a, vec.field_72449_c, -vec.field_72448_b);
            }
            case SOUTH: {
                return new Vec3d(vec.field_72450_a, -vec.field_72449_c, vec.field_72448_b);
            }
            case WEST: {
                return new Vec3d(-vec.field_72448_b, vec.field_72450_a, vec.field_72449_c);
            }
            case EAST: {
                return new Vec3d(vec.field_72448_b, -vec.field_72450_a, vec.field_72449_c);
            }
            default: {
                return null;
            }
        }
    }
    
    public static Vec3d revRotate(final Vec3d vec, final EnumFacing side) {
        switch (side) {
            case DOWN: {
                return new Vec3d(vec.field_72450_a, -vec.field_72448_b, -vec.field_72449_c);
            }
            case UP: {
                return new Vec3d(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
            }
            case NORTH: {
                return new Vec3d(vec.field_72450_a, -vec.field_72449_c, vec.field_72448_b);
            }
            case SOUTH: {
                return new Vec3d(vec.field_72450_a, vec.field_72449_c, -vec.field_72448_b);
            }
            case WEST: {
                return new Vec3d(vec.field_72448_b, -vec.field_72450_a, vec.field_72449_c);
            }
            case EAST: {
                return new Vec3d(-vec.field_72448_b, vec.field_72450_a, vec.field_72449_c);
            }
            default: {
                return null;
            }
        }
    }
    
    public static Vec3d rotateAroundX(final Vec3d vec, final float angle) {
        final float var2 = MathHelper.func_76134_b(angle);
        final float var3 = MathHelper.func_76126_a(angle);
        final double var4 = vec.field_72450_a;
        final double var5 = vec.field_72448_b * var2 + vec.field_72449_c * var3;
        final double var6 = vec.field_72449_c * var2 - vec.field_72448_b * var3;
        return new Vec3d(var4, var5, var6);
    }
    
    public static Vec3d rotateAroundY(final Vec3d vec, final float angle) {
        final float var2 = MathHelper.func_76134_b(angle);
        final float var3 = MathHelper.func_76126_a(angle);
        final double var4 = vec.field_72450_a * var2 + vec.field_72449_c * var3;
        final double var5 = vec.field_72448_b;
        final double var6 = vec.field_72449_c * var2 - vec.field_72450_a * var3;
        return new Vec3d(var4, var5, var6);
    }
    
    public static Vec3d rotateAroundZ(final Vec3d vec, final float angle) {
        final float var2 = MathHelper.func_76134_b(angle);
        final float var3 = MathHelper.func_76126_a(angle);
        final double var4 = vec.field_72450_a * var2 + vec.field_72448_b * var3;
        final double var5 = vec.field_72448_b * var2 - vec.field_72450_a * var3;
        final double var6 = vec.field_72449_c;
        return new Vec3d(var4, var5, var6);
    }
    
    public static RayTraceResult rayTrace(final World worldIn, final Entity entityIn, final boolean useLiquids) {
        double d3 = 5.0;
        if (entityIn instanceof EntityPlayerMP) {
            d3 = ((EntityPlayerMP)entityIn).field_71134_c.getBlockReachDistance();
        }
        return rayTrace(worldIn, entityIn, useLiquids, d3);
    }
    
    public static RayTraceResult rayTrace(final World worldIn, final Entity entityIn, final boolean useLiquids, final double range) {
        final float f = entityIn.field_70125_A;
        final float f2 = entityIn.field_70177_z;
        final double d0 = entityIn.field_70165_t;
        final double d2 = entityIn.field_70163_u + entityIn.func_70047_e();
        final double d3 = entityIn.field_70161_v;
        final Vec3d vec3d = new Vec3d(d0, d2, d3);
        final float f3 = MathHelper.func_76134_b(-f2 * 0.017453292f - 3.1415927f);
        final float f4 = MathHelper.func_76126_a(-f2 * 0.017453292f - 3.1415927f);
        final float f5 = -MathHelper.func_76134_b(-f * 0.017453292f);
        final float f6 = MathHelper.func_76126_a(-f * 0.017453292f);
        final float f7 = f4 * f5;
        final float f8 = f3 * f5;
        final Vec3d vec3d2 = vec3d.func_72441_c(f7 * range, f6 * range, f8 * range);
        return worldIn.func_147447_a(vec3d, vec3d2, useLiquids, !useLiquids, false);
    }
    
    public static RayTraceResult rayTrace(final World worldIn, final Entity entityIn, final Vec3d lookvec, final boolean useLiquids, final double range) {
        final double d0 = entityIn.field_70165_t;
        final double d2 = entityIn.field_70163_u + entityIn.func_70047_e();
        final double d3 = entityIn.field_70161_v;
        final Vec3d vec3d = new Vec3d(d0, d2, d3);
        final Vec3d vec3d2 = vec3d.func_72441_c(lookvec.field_72450_a * range, lookvec.field_72448_b * range, lookvec.field_72449_c * range);
        return worldIn.func_147447_a(vec3d, vec3d2, useLiquids, !useLiquids, false);
    }
    
    public static Field getField(final Class clazz, final String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            final Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            }
            return getField(superClass, fieldName);
        }
    }
    
    static {
        Utils.specialMiningResult = new HashMap<List<Object>, ItemStack>();
        Utils.specialMiningChance = new HashMap<List<Object>, Float>();
        colorNames = new String[] { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
        colors = new int[] { 15790320, 15435844, 12801229, 6719955, 14602026, 4312372, 14188952, 4408131, 10526880, 2651799, 8073150, 2437522, 5320730, 3887386, 11743532, 1973019 };
        Utils.oreDictLogs = new ArrayList<List>();
    }
}
