package thaumcraft.common.lib.utils;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.items.wrapper.*;
import net.minecraftforge.items.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.items.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraftforge.oredict.*;
import thaumcraft.api.*;
import thaumcraft.common.entities.*;
import net.minecraft.item.crafting.*;

public class InventoryUtils
{
    public static IItemHandler getItemHandlerAt(final World world, final BlockPos pos, final EnumFacing side) {
        final Pair<IItemHandler, Object> dest = (Pair<IItemHandler, Object>)VanillaInventoryCodeHooks.getItemHandler(world, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), side);
        if (dest == null) {
            return null;
        }
        if (dest.getLeft() != null) {
            return (IItemHandler)dest.getLeft();
        }
        final TileEntity tileentity = world.func_175625_s(pos);
        if (tileentity != null && tileentity instanceof IInventory) {
            return wrapInventory((IInventory)tileentity, side);
        }
        return null;
    }
    
    public static IItemHandler wrapInventory(final IInventory inventory, final EnumFacing side) {
        return (IItemHandler)((inventory instanceof ISidedInventory) ? new SidedInvWrapper((ISidedInventory)inventory, side) : new InvWrapper(inventory));
    }
    
    public static ItemStack insertStackAt(final World world, final BlockPos pos, final EnumFacing side, final ItemStack stack, final boolean simulate) {
        final IItemHandler inventory = getItemHandlerAt(world, pos, side);
        if (inventory != null) {
            return ItemHandlerHelper.insertItemStacked(inventory, stack, simulate);
        }
        return stack;
    }
    
    public static void ejectStackAt(final World world, BlockPos pos, final EnumFacing side, ItemStack out) {
        out = insertStackAt(world, pos, side, out, false);
        if (!out.func_190926_b()) {
            if (world.func_175665_u(pos.func_177972_a(side))) {
                pos = pos.func_177972_a(side.func_176734_d());
            }
            final EntityItem entityitem2 = new EntityItem(world, pos.func_177958_n() + 0.5 + 1 * side.func_82601_c(), (double)(pos.func_177956_o() + 1 * side.func_96559_d()), pos.func_177952_p() + 0.5 + 1 * side.func_82599_e(), out);
            entityitem2.field_70159_w = 0.3 * side.func_82601_c();
            entityitem2.field_70181_x = 0.3 * side.func_96559_d();
            entityitem2.field_70179_y = 0.3 * side.func_82599_e();
            world.func_72838_d((Entity)entityitem2);
        }
    }
    
    public static boolean hasRoomFor(final World world, final BlockPos pos, final EnumFacing side, final ItemStack stack) {
        final ItemStack testStack = insertStackAt(world, pos, side, stack.func_77946_l(), true);
        return stack.func_190916_E() == 0 || testStack.func_190916_E() != stack.func_190916_E();
    }
    
    public static ItemStack removeStackFrom(final World world, final BlockPos pos, final EnumFacing side, final ItemStack stack, final InvFilter filter, final boolean simulate) {
        return removeStackFrom(getItemHandlerAt(world, pos, side), stack, filter, simulate);
    }
    
    public static ItemStack removeStackFrom(final IItemHandler inventory, final ItemStack stack, final InvFilter filter, final boolean simulate) {
        int amount = stack.func_190916_E();
        if (inventory != null) {
            for (int a = 0; a < inventory.getSlots(); ++a) {
                if (areItemStacksEqual(stack, inventory.getStackInSlot(a), filter)) {
                    final int s = Math.min(amount, inventory.getStackInSlot(a).func_190916_E());
                    final ItemStack es = inventory.extractItem(a, s, simulate);
                    if (es != null && !es.func_190926_b()) {
                        amount -= es.func_190916_E();
                    }
                }
                if (amount <= 0) {
                    return ItemStack.field_190927_a;
                }
            }
        }
        stack.func_190920_e(amount);
        return stack;
    }
    
    public static int countStackInWorld(final World world, final BlockPos pos, final ItemStack stack, final double range, final InvFilter filter) {
        int count = 0;
        final List<EntityItem> l = EntityUtils.getEntitiesInRange(world, pos, (Entity)null, (Class<? extends EntityItem>)EntityItem.class, range);
        for (final EntityItem ei : l) {
            if (ei.func_92059_d() != null && areItemStacksEqual(stack, ei.func_92059_d(), filter)) {
                count += ei.func_92059_d().func_190916_E();
            }
        }
        return count;
    }
    
    public static int countStackIn(final World world, final BlockPos pos, final EnumFacing side, final ItemStack stack, final InvFilter filter) {
        return countStackIn(getItemHandlerAt(world, pos, side), stack, filter);
    }
    
    public static int countStackIn(final IItemHandler inventory, final ItemStack stack, final InvFilter filter) {
        int count = 0;
        if (inventory != null) {
            for (int a = 0; a < inventory.getSlots(); ++a) {
                if (areItemStacksEqual(stack, inventory.getStackInSlot(a), filter)) {
                    count += inventory.getStackInSlot(a).func_190916_E();
                }
            }
        }
        return count;
    }
    
    public static void dropItems(final World world, final BlockPos pos) {
        final TileEntity tileEntity = world.func_175625_s(pos);
        if (!(tileEntity instanceof IInventory)) {
            return;
        }
        final IInventory inventory = (IInventory)tileEntity;
        InventoryHelper.func_180175_a(world, pos, inventory);
    }
    
    public static boolean consumePlayerItem(final EntityPlayer player, final ItemStack item, final boolean nocheck, final boolean ore) {
        if (!nocheck && !isPlayerCarryingAmount(player, item, ore)) {
            return false;
        }
        int count = item.func_190916_E();
        for (int var2 = 0; var2 < player.field_71071_by.field_70462_a.size(); ++var2) {
            if (item.func_77973_b() == ItemsTC.enchantedPlaceholder) {
                final Map<Enchantment, Integer> en = (Map<Enchantment, Integer>)EnchantmentHelper.func_82781_a(item);
                boolean b = !en.isEmpty();
                for (final Enchantment e : en.keySet()) {
                    final int l = EnchantmentHelper.func_77506_a(e, (ItemStack)player.field_71071_by.field_70462_a.get(var2));
                    if (l < en.get(e)) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E() > count) {
                        ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190918_g(count);
                        count = 0;
                    }
                    else {
                        count -= ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E();
                        player.field_71071_by.field_70462_a.set(var2, (Object)ItemStack.field_190927_a);
                    }
                    if (count <= 0) {
                        return true;
                    }
                }
            }
            else if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_77969_a(item) && areItemStacksEqual((ItemStack)player.field_71071_by.field_70462_a.get(var2), item, new InvFilter(false, false, ore, false))) {
                if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E() > count) {
                    ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190918_g(count);
                    count = 0;
                }
                else {
                    count -= ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E();
                    player.field_71071_by.field_70462_a.set(var2, (Object)ItemStack.field_190927_a);
                }
                if (count <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean consumePlayerItem(final EntityPlayer player, final Item item, final int md, final int amt) {
        if (!isPlayerCarryingAmount(player, new ItemStack(item, amt, md), false)) {
            return false;
        }
        int count = amt;
        for (int var2 = 0; var2 < player.field_71071_by.field_70462_a.size(); ++var2) {
            if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_77973_b() == item && ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_77952_i() == md) {
                if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E() > count) {
                    ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190918_g(count);
                    count = 0;
                }
                else {
                    count -= ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E();
                    player.field_71071_by.field_70462_a.set(var2, (Object)ItemStack.field_190927_a);
                }
                if (count <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean consumePlayerItem(final EntityPlayer player, final Item item, final int md) {
        for (int var2 = 0; var2 < player.field_71071_by.field_70462_a.size(); ++var2) {
            if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_77973_b() == item && ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_77952_i() == md) {
                ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190918_g(1);
                if (((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E() <= 0) {
                    player.field_71071_by.field_70462_a.set(var2, (Object)ItemStack.field_190927_a);
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean isPlayerCarryingAmount(final EntityPlayer player, final ItemStack stack, final boolean ore) {
        if (stack == null) {
            return false;
        }
        int count = stack.func_190916_E();
        for (int var2 = 0; var2 < player.field_71071_by.field_70462_a.size(); ++var2) {
            if (stack.func_77973_b() == ItemsTC.enchantedPlaceholder) {
                final Map<Enchantment, Integer> en = (Map<Enchantment, Integer>)EnchantmentHelper.func_82781_a(stack);
                boolean b = !en.isEmpty();
                for (final Enchantment e : en.keySet()) {
                    final int l = EnchantmentHelper.func_77506_a(e, (ItemStack)player.field_71071_by.field_70462_a.get(var2));
                    if (l < en.get(e)) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    return true;
                }
            }
            else if (areItemStacksEqual((ItemStack)player.field_71071_by.field_70462_a.get(var2), stack, new InvFilter(false, false, ore, false))) {
                count -= ((ItemStack)player.field_71071_by.field_70462_a.get(var2)).func_190916_E();
                if (count <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static EntityEquipmentSlot isHoldingItem(final EntityPlayer player, final Item item) {
        if (player == null || item == null) {
            return null;
        }
        if (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() == item) {
            return EntityEquipmentSlot.MAINHAND;
        }
        if (player.func_184592_cb() != null && player.func_184592_cb().func_77973_b() == item) {
            return EntityEquipmentSlot.OFFHAND;
        }
        return null;
    }
    
    public static EntityEquipmentSlot isHoldingItem(final EntityPlayer player, final Class item) {
        if (player == null || item == null) {
            return null;
        }
        if (player.func_184614_ca() != null && item.isAssignableFrom(player.func_184614_ca().func_77973_b().getClass())) {
            return EntityEquipmentSlot.MAINHAND;
        }
        if (player.func_184592_cb() != null && item.isAssignableFrom(player.func_184592_cb().func_77973_b().getClass())) {
            return EntityEquipmentSlot.OFFHAND;
        }
        return null;
    }
    
    public static boolean areItemStacksEqualStrict(final ItemStack stack0, final ItemStack stack1) {
        return areItemStacksEqual(stack0, stack1, InvFilter.STRICT);
    }
    
    public static ItemStack findFirstMatchFromFilter(final NonNullList<ItemStack> filterStacks, final boolean blacklist, final IItemHandler inv, final EnumFacing face, final InvFilter filter) {
        return findFirstMatchFromFilter(filterStacks, blacklist, inv, face, filter, false);
    }
    
    public static ItemStack findFirstMatchFromFilter(final NonNullList<ItemStack> filterStacks, final boolean blacklist, final IItemHandler inv, final EnumFacing face, final InvFilter filter, final boolean leaveOne) {
    Label_0165:
        for (int a = 0; a < inv.getSlots(); ++a) {
            final ItemStack is = inv.getStackInSlot(a);
            if (is != null) {
                if (is.func_190916_E() > 0) {
                    if (!leaveOne || countStackIn(inv, is, filter) >= 2) {
                        boolean allow = false;
                        boolean allEmpty = true;
                        for (final ItemStack fs : filterStacks) {
                            if (fs == null) {
                                continue;
                            }
                            allEmpty = false;
                            final boolean r = areItemStacksEqual(fs.func_77946_l(), is.func_77946_l(), filter);
                            if (blacklist) {
                                if (r) {
                                    continue Label_0165;
                                }
                                allow = true;
                            }
                            else {
                                if (r) {
                                    return is;
                                }
                                continue;
                            }
                        }
                        if (blacklist && (allow || allEmpty)) {
                            return is;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static boolean matchesFilters(final NonNullList<ItemStack> nonNullList, final boolean blacklist, final ItemStack is, final InvFilter filter) {
        if (is == null || is.func_190916_E() <= 0) {
            return false;
        }
        boolean allow = false;
        boolean allEmpty = true;
        for (final ItemStack fs : nonNullList) {
            if (fs == null) {
                continue;
            }
            allEmpty = false;
            final boolean r = areItemStacksEqual(fs.func_77946_l(), is.func_77946_l(), filter);
            if (blacklist) {
                if (r) {
                    return false;
                }
                allow = true;
            }
            else {
                if (r) {
                    return true;
                }
                continue;
            }
        }
        return blacklist && (allow || allEmpty);
    }
    
    public static ItemStack findFirstMatchFromFilter(final NonNullList<ItemStack> filterStacks, final boolean blacklist, final NonNullList<ItemStack> itemStacks, final InvFilter filter) {
        return (ItemStack)findFirstMatchFromFilterTuple(filterStacks, blacklist, itemStacks, filter).func_76341_a();
    }
    
    public static Tuple<ItemStack, ItemStack> findFirstMatchFromFilterTuple(final NonNullList<ItemStack> filterStacks, final boolean blacklist, final NonNullList<ItemStack> stacks, final InvFilter filter) {
    Label_0006:
        for (final ItemStack is : stacks) {
            if (is != null) {
                if (is.func_190916_E() <= 0) {
                    continue;
                }
                boolean allow = false;
                boolean allEmpty = true;
                for (final ItemStack fs : filterStacks) {
                    if (fs == null) {
                        continue;
                    }
                    allEmpty = false;
                    final boolean r = areItemStacksEqual(fs.func_77946_l(), is.func_77946_l(), filter);
                    if (blacklist) {
                        if (r) {
                            continue Label_0006;
                        }
                        allow = true;
                    }
                    else {
                        if (r) {
                            return (Tuple<ItemStack, ItemStack>)new Tuple((Object)is, (Object)fs);
                        }
                        continue;
                    }
                }
                if (blacklist && (allow || allEmpty)) {
                    return (Tuple<ItemStack, ItemStack>)new Tuple((Object)is, (Object)null);
                }
                continue;
            }
        }
        return (Tuple<ItemStack, ItemStack>)new Tuple((Object)null, (Object)null);
    }
    
    public static boolean areItemStacksEqual(final ItemStack stack0, final ItemStack stack1, final InvFilter filter) {
        if (stack0 == null && stack1 != null) {
            return false;
        }
        if (stack0 != null && stack1 == null) {
            return false;
        }
        if (stack0 == null && stack1 == null) {
            return true;
        }
        if (filter.useMod) {
            String m1 = "A";
            String m2 = "B";
            final String a = stack0.func_77973_b().getRegistryName().func_110624_b();
            if (a != null) {
                m1 = a;
            }
            final String b = stack1.func_77973_b().getRegistryName().func_110624_b();
            if (b != null) {
                m2 = b;
            }
            return m1.equals(m2);
        }
        if (filter.useOre) {
            final int[] oreIDs;
            final int[] od = oreIDs = OreDictionary.getOreIDs(stack0);
            for (final int i : oreIDs) {
                if (ThaumcraftApiHelper.containsMatch(false, new ItemStack[] { stack1 }, (List<ItemStack>)OreDictionary.getOres(OreDictionary.getOreName(i), false))) {
                    return true;
                }
            }
        }
        boolean t1 = true;
        if (!filter.igNBT) {
            t1 = ItemStack.func_77970_a(stack0, stack1);
        }
        if (stack0.func_77952_i() == 32767 || stack1.func_77952_i() == 32767) {
            filter.igDmg = true;
        }
        final boolean t2 = !filter.igDmg && stack0.func_77952_i() != stack1.func_77952_i();
        return stack0.func_77973_b() == stack1.func_77973_b() && !t2 && t1;
    }
    
    public static void dropHarvestsAtPos(final World worldIn, final BlockPos pos, final List<ItemStack> list) {
        dropHarvestsAtPos(worldIn, pos, list, false, 0, null);
    }
    
    public static void dropHarvestsAtPos(final World worldIn, final BlockPos pos, final List<ItemStack> list, final boolean followItem, final int color, final Entity target) {
        for (final ItemStack item : list) {
            if (!worldIn.field_72995_K && worldIn.func_82736_K().func_82766_b("doTileDrops") && !worldIn.restoringBlockSnapshots) {
                final float f = 0.5f;
                final double d0 = worldIn.field_73012_v.nextFloat() * f + (1.0f - f) * 0.5;
                final double d2 = worldIn.field_73012_v.nextFloat() * f + (1.0f - f) * 0.5;
                final double d3 = worldIn.field_73012_v.nextFloat() * f + (1.0f - f) * 0.5;
                EntityItem entityitem = null;
                if (followItem) {
                    entityitem = new EntityFollowingItem(worldIn, pos.func_177958_n() + d0, pos.func_177956_o() + d2, pos.func_177952_p() + d3, item, target, color);
                }
                else {
                    entityitem = new EntityItem(worldIn, pos.func_177958_n() + d0, pos.func_177956_o() + d2, pos.func_177952_p() + d3, item);
                }
                entityitem.func_174869_p();
                worldIn.func_72838_d((Entity)entityitem);
            }
        }
    }
    
    public static void dropItemAtPos(final World world, final ItemStack item, final BlockPos pos) {
        if (!world.field_72995_K && item != null && item.func_190916_E() > 0) {
            final EntityItem entityItem = new EntityItem(world, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, item.func_77946_l());
            world.func_72838_d((Entity)entityItem);
        }
    }
    
    public static void dropItemAtEntity(final World world, final ItemStack item, final Entity entity) {
        if (!world.field_72995_K && item != null && item.func_190916_E() > 0) {
            final EntityItem entityItem = new EntityItem(world, entity.field_70165_t, entity.field_70163_u + entity.func_70047_e() / 2.0f, entity.field_70161_v, item.func_77946_l());
            world.func_72838_d((Entity)entityItem);
        }
    }
    
    public static void dropItemsAtEntity(final World world, final BlockPos pos, final Entity entity) {
        final TileEntity tileEntity = world.func_175625_s(pos);
        if (!(tileEntity instanceof IInventory) || world.field_72995_K) {
            return;
        }
        final IInventory inventory = (IInventory)tileEntity;
        for (int i = 0; i < inventory.func_70302_i_(); ++i) {
            final ItemStack item = inventory.func_70301_a(i);
            if (!item.func_190926_b() && item.func_190916_E() > 0) {
                final EntityItem entityItem = new EntityItem(world, entity.field_70165_t, entity.field_70163_u + entity.func_70047_e() / 2.0f, entity.field_70161_v, item.func_77946_l());
                world.func_72838_d((Entity)entityItem);
                inventory.func_70299_a(i, ItemStack.field_190927_a);
            }
        }
    }
    
    public static ItemStack cycleItemStack(final Object input) {
        return cycleItemStack(input, 0);
    }
    
    public static ItemStack cycleItemStack(Object input, int counter) {
        ItemStack it = ItemStack.field_190927_a;
        if (input instanceof Ingredient) {
            final boolean b = !((Ingredient)input).isSimple();
            input = ((Ingredient)input).func_193365_a();
            if (b) {
                final ItemStack[] q = (ItemStack[])input;
                final ItemStack[] r = new ItemStack[q.length];
                for (int a = 0; a < q.length; ++a) {
                    (r[a] = q[a].func_77946_l()).func_77964_b(32767);
                }
                input = r;
            }
        }
        if (input instanceof ItemStack[]) {
            final ItemStack[] q2 = (ItemStack[])input;
            if (q2 != null && q2.length > 0) {
                final int idx = (int)((counter + System.currentTimeMillis() / 1000L) % q2.length);
                it = cycleItemStack(q2[idx], counter++);
            }
        }
        else if (input instanceof ItemStack) {
            it = (ItemStack)input;
            if (it != null && !it.func_190926_b() && it.func_77973_b() != null && it.func_77984_f() && it.func_77952_i() == 32767) {
                final int q3 = 5000 / it.func_77958_k();
                final int md = (int)((counter + System.currentTimeMillis() / q3) % it.func_77958_k());
                final ItemStack it2 = new ItemStack(it.func_77973_b(), 1, md);
                it2.func_77982_d(it.func_77978_p());
                it = it2;
            }
        }
        else if (input instanceof List) {
            final List<ItemStack> q4 = (List<ItemStack>)input;
            if (q4 != null && q4.size() > 0) {
                final int idx = (int)((counter + System.currentTimeMillis() / 1000L) % q4.size());
                it = cycleItemStack(q4.get(idx), counter++);
            }
        }
        else if (input instanceof String) {
            final List<ItemStack> q4 = (List<ItemStack>)OreDictionary.getOres((String)input, false);
            if (q4 != null && q4.size() > 0) {
                final int idx = (int)((counter + System.currentTimeMillis() / 1000L) % q4.size());
                it = cycleItemStack(q4.get(idx), counter++);
            }
        }
        return it;
    }
    
    public static class InvFilter
    {
        boolean igDmg;
        boolean igNBT;
        boolean useOre;
        boolean useMod;
        public static final InvFilter STRICT;
        public static final InvFilter BASEORE;
        
        public InvFilter(final boolean ignoreDamage, final boolean ignoreNBT, final boolean useOre, final boolean useMod) {
            this.useMod = false;
            this.igDmg = ignoreDamage;
            this.igNBT = ignoreNBT;
            this.useOre = useOre;
            this.useMod = useMod;
        }
        
        static {
            STRICT = new InvFilter(false, false, false, false);
            BASEORE = new InvFilter(false, false, true, false);
        }
    }
}
