package thaumcraft.common.lib.enchantment;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;

public enum EnumInfusionEnchantment
{
    COLLECTOR((Set<String>)ImmutableSet.of((Object)"axe", (Object)"pickaxe", (Object)"shovel", (Object)"weapon"), 1, "INFUSIONENCHANTMENT"), 
    DESTRUCTIVE((Set<String>)ImmutableSet.of((Object)"axe", (Object)"pickaxe", (Object)"shovel"), 1, "INFUSIONENCHANTMENT"), 
    BURROWING((Set<String>)ImmutableSet.of((Object)"axe", (Object)"pickaxe"), 1, "INFUSIONENCHANTMENT"), 
    SOUNDING((Set<String>)ImmutableSet.of((Object)"pickaxe"), 4, "INFUSIONENCHANTMENT"), 
    REFINING((Set<String>)ImmutableSet.of((Object)"pickaxe"), 4, "INFUSIONENCHANTMENT"), 
    ARCING((Set<String>)ImmutableSet.of((Object)"weapon"), 4, "INFUSIONENCHANTMENT"), 
    ESSENCE((Set<String>)ImmutableSet.of((Object)"weapon"), 5, "INFUSIONENCHANTMENT"), 
    VISBATTERY((Set<String>)ImmutableSet.of((Object)"chargable"), 3, "?"), 
    VISCHARGE((Set<String>)ImmutableSet.of((Object)"chargable"), 1, "?"), 
    SWIFT((Set<String>)ImmutableSet.of((Object)"boots"), 4, "IEARMOR"), 
    AGILE((Set<String>)ImmutableSet.of((Object)"legs"), 1, "IEARMOR"), 
    INFESTED((Set<String>)ImmutableSet.of((Object)"chest"), 1, "IETAINT");
    
    public Set<String> toolClasses;
    public int maxLevel;
    public String research;
    
    private EnumInfusionEnchantment(final Set<String> toolClasses, final int ml, final String research) {
        this.toolClasses = toolClasses;
        this.maxLevel = ml;
        this.research = research;
    }
    
    public static NBTTagList getInfusionEnchantmentTagList(final ItemStack stack) {
        return (stack == null || stack.func_190926_b() || stack.func_77978_p() == null) ? null : stack.func_77978_p().func_150295_c("infench", 10);
    }
    
    public static List<EnumInfusionEnchantment> getInfusionEnchantments(final ItemStack stack) {
        final NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
        final List<EnumInfusionEnchantment> list = new ArrayList<EnumInfusionEnchantment>();
        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.func_74745_c(); ++j) {
                final int k = nbttaglist.func_150305_b(j).func_74765_d("id");
                final int l = nbttaglist.func_150305_b(j).func_74765_d("lvl");
                if (k >= 0 && k < values().length) {
                    list.add(values()[k]);
                }
            }
        }
        return list;
    }
    
    public static int getInfusionEnchantmentLevel(final ItemStack stack, final EnumInfusionEnchantment enchantment) {
        final NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
        final List<EnumInfusionEnchantment> list = new ArrayList<EnumInfusionEnchantment>();
        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.func_74745_c(); ++j) {
                final int k = nbttaglist.func_150305_b(j).func_74765_d("id");
                final int l = nbttaglist.func_150305_b(j).func_74765_d("lvl");
                if (k >= 0 && k < values().length && values()[k] == enchantment) {
                    return l;
                }
            }
        }
        return 0;
    }
    
    public static void addInfusionEnchantment(final ItemStack stack, final EnumInfusionEnchantment ie, final int level) {
        if (stack == null || stack.func_190926_b() || level > ie.maxLevel) {
            return;
        }
        NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
        if (nbttaglist != null) {
            int j = 0;
            while (j < nbttaglist.func_74745_c()) {
                final int k = nbttaglist.func_150305_b(j).func_74765_d("id");
                final int l = nbttaglist.func_150305_b(j).func_74765_d("lvl");
                if (k == ie.ordinal()) {
                    if (level <= l) {
                        return;
                    }
                    nbttaglist.func_150305_b(j).func_74777_a("lvl", (short)level);
                    stack.func_77983_a("infench", (NBTBase)nbttaglist);
                    return;
                }
                else {
                    ++j;
                }
            }
        }
        else {
            nbttaglist = new NBTTagList();
        }
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.func_74777_a("id", (short)ie.ordinal());
        nbttagcompound.func_74777_a("lvl", (short)level);
        nbttaglist.func_74742_a((NBTBase)nbttagcompound);
        if (nbttaglist.func_74745_c() > 0) {
            stack.func_77983_a("infench", (NBTBase)nbttaglist);
        }
    }
}
