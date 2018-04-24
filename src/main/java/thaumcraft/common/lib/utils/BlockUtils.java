package thaumcraft.common.lib.utils;

import net.minecraftforge.event.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.stats.*;
import net.minecraftforge.event.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import thaumcraft.api.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.*;
import java.lang.reflect.*;
import java.util.*;

public class BlockUtils
{
    static BlockPos lastPos;
    static int lasty;
    static int lastz;
    static double lastdistance;
    public static ArrayList<String> portableHoleBlackList;
    
    public static boolean harvestBlockSkipCheck(final World world, final EntityPlayer player, final BlockPos pos) {
        return harvestBlock(world, player, pos, false, false, 0, true);
    }
    
    public static boolean harvestBlock(final World world, final EntityPlayer player, final BlockPos pos) {
        return harvestBlock(world, player, pos, false, false, 0, false);
    }
    
    public static boolean harvestBlock(final World world, final EntityPlayer player, final BlockPos pos, final boolean alwaysDrop, final boolean silkOverride, final int fortuneOverride, final boolean skipEvent) {
        final IBlockState bs = world.func_180495_p(pos);
        if (!skipEvent && !world.field_72995_K) {
            final BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, bs, player);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                return false;
            }
        }
        if (bs.func_185887_b(world, pos) < 0.0f) {
            return false;
        }
        world.func_180498_a((EntityPlayer)null, 2001, pos, Block.func_176210_f(bs));
        boolean flag = false;
        if (player.field_71075_bZ.field_75098_d) {
            flag = removeBlock(world, pos, player, false);
        }
        else {
            boolean flag2 = false;
            if (bs != null) {
                flag2 = (alwaysDrop || bs.func_177230_c().canHarvestBlock((IBlockAccess)world, pos, player));
            }
            flag = removeBlock(world, pos, player, true);
            if (flag && flag2) {
                harvestBlockVanilla(world, player, pos, bs, player.func_184614_ca(), silkOverride, fortuneOverride);
                try {
                    if (!bs.func_177230_c().canSilkHarvest(world, pos, bs, player) || (!silkOverride && EnchantmentHelper.func_185284_a(Enchantments.field_185306_r, (EntityLivingBase)player) <= 0)) {
                        int fortune = EnchantmentHelper.func_185284_a(Enchantments.field_185308_t, (EntityLivingBase)player);
                        if (fortuneOverride > fortune) {
                            fortune = fortuneOverride;
                        }
                        final int exp = bs.func_177230_c().getExpDrop(bs, (IBlockAccess)world, pos, fortune);
                        if (exp > 0) {
                            bs.func_177230_c().func_180637_b(world, pos, exp);
                        }
                    }
                }
                catch (Exception ex) {}
            }
        }
        return true;
    }
    
    public static void harvestBlockVanilla(final World worldIn, final EntityPlayer player, final BlockPos pos, final IBlockState state, @Nullable final ItemStack stack, final boolean silkOverride, final int fortuneOverride) {
        player.func_71029_a(StatList.func_188055_a(state.func_177230_c()));
        if (state.func_177230_c().canSilkHarvest(worldIn, pos, state, player) && (EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, stack) > 0 || silkOverride)) {
            final List<ItemStack> items = new ArrayList<ItemStack>();
            final ItemStack itemstack = getSilkTouchDrop(state);
            if (itemstack != null && !itemstack.func_190926_b()) {
                items.add(itemstack);
            }
            ForgeEventFactory.fireBlockHarvesting((List)items, worldIn, pos, state, 0, 1.0f, true, player);
            for (final ItemStack item : items) {
                state.func_177230_c();
                Block.func_180635_a(worldIn, pos, item);
            }
        }
        else {
            setHarvesters(state.func_177230_c(), player);
            int i = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, stack);
            if (fortuneOverride > i) {
                i = fortuneOverride;
            }
            state.func_177230_c().func_176226_b(worldIn, pos, state, i);
            clearHarvesters(state.func_177230_c());
        }
    }
    
    private static void setHarvesters(final Block block, final EntityPlayer player) {
        final Class myClass = block.getClass();
        try {
            final Field myField = Utils.getField(myClass, "harvesters");
            myField.setAccessible(true);
            ((ThreadLocal)myField.get(block)).set(player);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    private static void clearHarvesters(final Block block) {
        final Class myClass = block.getClass();
        try {
            final Field myField = Utils.getField(myClass, "harvesters");
            myField.setAccessible(true);
            ((ThreadLocal)myField.get(block)).set(null);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public static void destroyBlockPartially(final World world, final int par1, final BlockPos pos, final int par5) {
        for (final EntityPlayerMP entityplayermp : world.field_73010_i) {
            if (entityplayermp != null && entityplayermp.field_70170_p == FMLCommonHandler.instance().getMinecraftServerInstance().func_130014_f_() && entityplayermp.func_145782_y() != par1) {
                final double d0 = pos.func_177958_n() - entityplayermp.field_70165_t;
                final double d2 = pos.func_177956_o() - entityplayermp.field_70163_u;
                final double d3 = pos.func_177952_p() - entityplayermp.field_70161_v;
                if (d0 * d0 + d2 * d2 + d3 * d3 >= 1024.0) {
                    continue;
                }
                entityplayermp.field_71135_a.func_147359_a((Packet)new SPacketBlockBreakAnim(par1, pos, par5));
            }
        }
    }
    
    public static boolean removeBlock(final World world, final BlockPos pos, final EntityPlayer player, final boolean willHarvest) {
        final IBlockState block = world.func_180495_p(pos);
        final boolean flag = block != null && block.func_177230_c().removedByPlayer(block, world, pos, player, willHarvest);
        if (block != null && flag) {
            try {
                block.func_177230_c().func_176206_d(world, pos, world.func_180495_p(pos));
            }
            catch (Exception ex) {}
        }
        return flag;
    }
    
    public static void findBlocks(final World world, final BlockPos pos, final IBlockState block, final int reach) {
        for (int xx = -reach; xx <= reach; ++xx) {
            for (int yy = reach; yy >= -reach; --yy) {
                for (int zz = -reach; zz <= reach; ++zz) {
                    if (Math.abs(BlockUtils.lastPos.func_177958_n() + xx - pos.func_177958_n()) > 24) {
                        return;
                    }
                    if (Math.abs(BlockUtils.lastPos.func_177956_o() + yy - pos.func_177956_o()) > 48) {
                        return;
                    }
                    if (Math.abs(BlockUtils.lastPos.func_177952_p() + zz - pos.func_177952_p()) > 24) {
                        return;
                    }
                    final IBlockState bs = world.func_180495_p(BlockUtils.lastPos.func_177982_a(xx, yy, zz));
                    final boolean same = bs.func_177230_c() == block.func_177230_c() && bs.func_177230_c().func_180651_a(bs) == block.func_177230_c().func_180651_a(block);
                    if (same && bs.func_177230_c().func_176195_g(bs, world, BlockUtils.lastPos.func_177982_a(xx, yy, zz)) >= 0.0f) {
                        final double xd = BlockUtils.lastPos.func_177958_n() + xx - pos.func_177958_n();
                        final double yd = BlockUtils.lastPos.func_177956_o() + yy - pos.func_177956_o();
                        final double zd = BlockUtils.lastPos.func_177952_p() + zz - pos.func_177952_p();
                        final double d = xd * xd + yd * yd + zd * zd;
                        if (d > BlockUtils.lastdistance) {
                            BlockUtils.lastdistance = d;
                            BlockUtils.lastPos = BlockUtils.lastPos.func_177982_a(xx, yy, zz);
                            findBlocks(world, pos, block, reach);
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public static boolean breakFurthestBlock(final World world, final BlockPos pos, final IBlockState block, final EntityPlayer player) {
        BlockUtils.lastPos = new BlockPos((Vec3i)pos);
        BlockUtils.lastdistance = 0.0;
        final int reach = Utils.isWoodLog((IBlockAccess)world, pos) ? 2 : 1;
        findBlocks(world, pos, block, reach);
        final boolean worked = harvestBlockSkipCheck(world, player, BlockUtils.lastPos);
        world.markAndNotifyBlock(pos, world.func_175726_f(pos), block, block, 3);
        if (worked && Utils.isWoodLog((IBlockAccess)world, pos)) {
            world.markAndNotifyBlock(pos, world.func_175726_f(pos), block, block, 3);
            for (int xx = -3; xx <= 3; ++xx) {
                for (int yy = -3; yy <= 3; ++yy) {
                    for (int zz = -3; zz <= 3; ++zz) {
                        world.func_175684_a(BlockUtils.lastPos.func_177982_a(xx, yy, zz), world.func_180495_p(BlockUtils.lastPos.func_177982_a(xx, yy, zz)).func_177230_c(), 50 + world.field_73012_v.nextInt(75));
                    }
                }
            }
        }
        return worked;
    }
    
    public static RayTraceResult getTargetBlock(final World world, final Entity entity, final boolean par3) {
        return getTargetBlock(world, entity, par3, par3, 10.0);
    }
    
    public static RayTraceResult getTargetBlock(final World world, final Entity entity, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final double range) {
        final float var4 = 1.0f;
        final float var5 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * var4;
        final float var6 = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * var4;
        final double var7 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * var4;
        final double var8 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * var4 + entity.func_70047_e();
        final double var9 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * var4;
        final Vec3d var10 = new Vec3d(var7, var8, var9);
        final float var11 = MathHelper.func_76134_b(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.func_76126_a(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.func_76134_b(-var5 * 0.017453292f);
        final float var14 = MathHelper.func_76126_a(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        final Vec3d var17 = var10.func_72441_c(var15 * range, var14 * range, var16 * range);
        return world.func_147447_a(var10, var17, stopOnLiquid, !ignoreBlockWithoutBoundingBox, false);
    }
    
    public static int countExposedSides(final World world, final BlockPos pos) {
        int count = 0;
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            if (world.func_175623_d(pos.func_177972_a(dir))) {
                ++count;
            }
        }
        return count;
    }
    
    public static boolean isBlockExposed(final World world, final BlockPos pos) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (!world.func_180495_p(pos.func_177972_a(face)).func_185914_p()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAdjacentToSolidBlock(final World world, final BlockPos pos) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (world.isSideSolid(pos.func_177972_a(face), face.func_176734_d())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockTouching(final IBlockAccess world, final BlockPos pos, final IBlockState bs) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (world.func_180495_p(pos.func_177972_a(face)) == bs) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockTouching(final IBlockAccess world, final BlockPos pos, final Block bs) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (world.func_180495_p(pos.func_177972_a(face)).func_177230_c() == bs) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockTouching(final IBlockAccess world, final BlockPos pos, final Material mat, final boolean solid) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (world.func_180495_p(pos.func_177972_a(face)).func_185904_a() == mat && (!solid || world.func_180495_p(pos.func_177972_a(face)).isSideSolid(world, pos.func_177972_a(face), face.func_176734_d()))) {
                return true;
            }
        }
        return false;
    }
    
    public static EnumFacing getFaceBlockTouching(final IBlockAccess world, final BlockPos pos, final Block bs) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (world.func_180495_p(pos.func_177972_a(face)).func_177230_c() == bs) {
                return face;
            }
        }
        return null;
    }
    
    public static boolean isPortableHoleBlackListed(final IBlockState blockstate) {
        return isBlockListed(blockstate, BlockUtils.portableHoleBlackList);
    }
    
    public static boolean isBlockListed(final IBlockState blockstate, final List<String> list) {
        final String stateString = blockstate.toString();
        for (final String key : list) {
            final String[] splitString = key.split(";");
            if (splitString[0].contains(":")) {
                if (!((ResourceLocation)Block.field_149771_c.func_177774_c((Object)blockstate.func_177230_c())).toString().equals(splitString[0])) {
                    continue;
                }
                if (splitString.length <= 1) {
                    return true;
                }
                int matches = 0;
                for (int a = 1; a < splitString.length; ++a) {
                    if (stateString.contains(splitString[a])) {
                        ++matches;
                    }
                }
                if (matches == splitString.length - 1) {
                    return true;
                }
                continue;
            }
            else {
                final ItemStack bs = new ItemStack(Item.func_150898_a(blockstate.func_177230_c()), 1, blockstate.func_177230_c().func_176201_c(blockstate));
                for (final ItemStack stack : OreDictionary.getOres(splitString[0], false)) {
                    if (OreDictionary.itemMatches(stack, bs, false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static double distance(final BlockPos b1, final BlockPos b2) {
        final double d3 = b1.func_177958_n() - b2.func_177958_n();
        final double d4 = b1.func_177956_o() - b2.func_177956_o();
        final double d5 = b1.func_177952_p() - b2.func_177952_p();
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
    
    public static EnumFacing.Axis getBlockAxis(final World world, final BlockPos pos) {
        final IBlockState state = world.func_180495_p(pos);
        EnumFacing.Axis ax = EnumFacing.Axis.Y;
        for (final IProperty prop : state.func_177228_b().keySet()) {
            if (prop.func_177701_a().equals("axis")) {
                if (state.func_177229_b(prop) instanceof BlockLog.EnumAxis) {
                    ax = (((BlockLog.EnumAxis)state.func_177229_b(prop) == BlockLog.EnumAxis.X) ? EnumFacing.Axis.X : (((BlockLog.EnumAxis)state.func_177229_b(prop) == BlockLog.EnumAxis.Y) ? EnumFacing.Axis.Y : (((BlockLog.EnumAxis)state.func_177229_b(prop) == BlockLog.EnumAxis.Z) ? EnumFacing.Axis.Z : EnumFacing.Axis.Y)));
                    break;
                }
                if (state.func_177229_b(prop) instanceof EnumFacing.Axis) {
                    ax = (EnumFacing.Axis)state.func_177229_b(prop);
                    break;
                }
                continue;
            }
        }
        if (ax == null) {
            ax = EnumFacing.Axis.Y;
        }
        return ax;
    }
    
    public static boolean hasLOS(final World world, final BlockPos source, final BlockPos target) {
        final RayTraceResult mop = ThaumcraftApiHelper.rayTraceIgnoringSource(world, new Vec3d(source.func_177958_n() + 0.5, source.func_177956_o() + 0.5, source.func_177952_p() + 0.5), new Vec3d(target.func_177958_n() + 0.5, target.func_177956_o() + 0.5, target.func_177952_p() + 0.5), false, true, false);
        return mop == null || (mop.field_72313_a == RayTraceResult.Type.BLOCK && mop.func_178782_a().func_177958_n() == target.func_177958_n() && mop.func_178782_a().func_177956_o() == target.func_177956_o() && mop.func_178782_a().func_177952_p() == target.func_177952_p());
    }
    
    public static ItemStack getSilkTouchDrop(final IBlockState bs) {
        ItemStack dropped = ItemStack.field_190927_a;
        try {
            final Method m = ReflectionHelper.findMethod((Class)Block.class, "getSilkTouchDrop", "func_180643_i", new Class[] { IBlockState.class });
            dropped = (ItemStack)m.invoke(bs.func_177230_c(), bs);
        }
        catch (Exception e) {
            Thaumcraft.log.warn("Could not invoke net.minecraft.block.Block method getSilkTouchDrop");
        }
        return dropped;
    }
    
    static {
        BlockUtils.lastPos = BlockPos.field_177992_a;
        BlockUtils.lasty = 0;
        BlockUtils.lastz = 0;
        BlockUtils.lastdistance = 0.0;
        BlockUtils.portableHoleBlackList = new ArrayList<String>();
    }
    
    public static class BlockPosComparator implements Comparator<BlockPos>
    {
        private BlockPos source;
        
        public BlockPosComparator(final BlockPos source) {
            this.source = source;
        }
        
        @Override
        public int compare(final BlockPos a, final BlockPos b) {
            if (a.equals((Object)b)) {
                return 0;
            }
            final double da = this.source.func_177951_i((Vec3i)a);
            final double db = this.source.func_177951_i((Vec3i)b);
            return (da < db) ? -1 : ((da > db) ? 1 : 0);
        }
    }
}
