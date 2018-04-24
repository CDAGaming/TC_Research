package thaumcraft.common.lib.crafting;

import net.minecraft.entity.player.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.inventory.*;
import thaumcraft.api.crafting.*;
import thaumcraft.api.internal.*;
import net.minecraftforge.oredict.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import thaumcraft.api.aspects.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.crafting.*;

public class ThaumcraftCraftingManager
{
    static final int ASPECTCAP = 500;
    
    public static CrucibleRecipe findMatchingCrucibleRecipe(final EntityPlayer player, final AspectList aspects, final ItemStack lastDrop) {
        int highest = 0;
        CrucibleRecipe out = null;
        for (final Object re : ThaumcraftApi.getCraftingRecipes().values()) {
            if (re != null && re instanceof CrucibleRecipe[]) {
                for (final CrucibleRecipe recipe : (CrucibleRecipe[])re) {
                    final ItemStack temp = lastDrop.func_77946_l();
                    temp.func_190920_e(1);
                    if (player != null && ThaumcraftCapabilities.knowsResearchStrict(player, recipe.getResearch()) && recipe.matches(aspects, temp)) {
                        final int result = recipe.getAspects().visSize();
                        if (result > highest) {
                            highest = result;
                            out = recipe;
                        }
                    }
                }
            }
        }
        return out;
    }
    
    public static IArcaneRecipe findMatchingArcaneRecipe(final InventoryCrafting matrix, final EntityPlayer player) {
        int var2 = 0;
        ItemStack var3 = null;
        ItemStack var4 = null;
        for (int var5 = 0; var5 < 15; ++var5) {
            final ItemStack var6 = matrix.func_70301_a(var5);
            if (!var6.func_190926_b()) {
                if (var2 == 0) {
                    var3 = var6;
                }
                if (var2 == 1) {
                    var4 = var6;
                }
                ++var2;
            }
        }
        for (final ResourceLocation key : CraftingManager.field_193380_a.func_148742_b()) {
            final IRecipe recipe = (IRecipe)CraftingManager.field_193380_a.func_82594_a((Object)key);
            if (recipe != null && recipe instanceof IArcaneRecipe && recipe.func_77569_a(matrix, player.field_70170_p)) {
                return (IArcaneRecipe)recipe;
            }
        }
        return null;
    }
    
    public static ItemStack findMatchingArcaneRecipeResult(final InventoryCrafting awb, final EntityPlayer player) {
        final IArcaneRecipe var13 = findMatchingArcaneRecipe(awb, player);
        return (var13 == null) ? null : var13.func_77572_b(awb);
    }
    
    public static AspectList findMatchingArcaneRecipeCrystals(final InventoryCrafting awb, final EntityPlayer player) {
        final IArcaneRecipe var13 = findMatchingArcaneRecipe(awb, player);
        return (var13 == null) ? null : var13.getCrystals();
    }
    
    public static int findMatchingArcaneRecipeVis(final InventoryCrafting awb, final EntityPlayer player) {
        final IArcaneRecipe var13 = findMatchingArcaneRecipe(awb, player);
        return (var13 == null) ? 0 : ((var13.getVis() > 0) ? var13.getVis() : var13.getVis());
    }
    
    public static InfusionRecipe findMatchingInfusionRecipe(final ArrayList<ItemStack> items, final ItemStack input, final EntityPlayer player) {
        for (final Object recipes : ThaumcraftApi.getCraftingRecipes().values()) {
            if (recipes != null && recipes instanceof InfusionRecipe[]) {
                for (final InfusionRecipe recipe : (InfusionRecipe[])recipes) {
                    if (recipe.matches(items, input, player.field_70170_p, player)) {
                        return recipe;
                    }
                }
            }
        }
        return null;
    }
    
    public static AspectList getObjectTags(final ItemStack itemstack) {
        return getObjectTags(itemstack, null);
    }
    
    public static AspectList getObjectTags(final ItemStack itemstack, final ArrayList<String> history) {
        String ss = null;
        ItemStack sc = null;
        try {
            sc = itemstack.func_77946_l();
            sc.func_190920_e(1);
            ss = sc.serializeNBT().toString();
        }
        catch (Exception e) {
            return null;
        }
        if (ss == null) {
            return null;
        }
        AspectList tmp = CommonInternals.objectTags.get(ss);
        if (tmp == null) {
            try {
                sc.func_77964_b(32767);
                ss = sc.serializeNBT().toString();
                tmp = CommonInternals.objectTags.get(ss);
                if (tmp == null) {
                    if (itemstack.func_77952_i() == 32767 && tmp == null) {
                        int index = 0;
                        do {
                            sc.func_77964_b(index);
                            ss = sc.serializeNBT().toString();
                            tmp = CommonInternals.objectTags.get(ss);
                        } while (++index < 16 && tmp == null);
                    }
                    if (tmp == null) {
                        tmp = generateTags(itemstack, history);
                    }
                }
            }
            catch (Exception ex) {}
        }
        return capAspects(getBonusTags(itemstack, tmp), 500);
    }
    
    private static AspectList capAspects(final AspectList sourcetags, final int amount) {
        if (sourcetags == null) {
            return sourcetags;
        }
        final AspectList out = new AspectList();
        for (final Aspect aspect : sourcetags.getAspects()) {
            if (aspect != null) {
                out.merge(aspect, Math.min(amount, sourcetags.getAmount(aspect)));
            }
        }
        return out;
    }
    
    private static AspectList getBonusTags(final ItemStack itemstack, final AspectList sourcetags) {
        AspectList tmp = new AspectList();
        if (itemstack.func_190926_b()) {
            return tmp;
        }
        final Item item = itemstack.func_77973_b();
        if (item != null && item instanceof IEssentiaContainerItem && !((IEssentiaContainerItem)item).ignoreContainedAspects()) {
            if (sourcetags != null) {
                sourcetags.aspects.clear();
            }
            tmp = ((IEssentiaContainerItem)item).getAspects(itemstack);
            if (tmp != null && tmp.size() > 0) {
                for (final Aspect tag : tmp.copy().getAspects()) {
                    if (tmp.getAmount(tag) <= 0) {
                        tmp.remove(tag);
                    }
                }
            }
        }
        if (tmp == null) {
            tmp = new AspectList();
        }
        if (sourcetags != null) {
            for (final Aspect tag : sourcetags.getAspects()) {
                if (tag != null) {
                    tmp.add(tag, sourcetags.getAmount(tag));
                }
            }
        }
        if (item != null && (tmp != null || item == Items.field_151068_bn)) {
            if (item instanceof ItemArmor) {
                tmp.merge(Aspect.PROTECT, ((ItemArmor)item).field_77879_b * 4);
            }
            else if (item instanceof ItemSword && ((ItemSword)item).func_150931_i() + 1.0f > 0.0f) {
                tmp.merge(Aspect.AVERSION, (int)(((ItemSword)item).func_150931_i() + 1.0f) * 4);
            }
            else if (item instanceof ItemBow) {
                tmp.merge(Aspect.AVERSION, 10).merge(Aspect.FLIGHT, 5);
            }
            else if (item instanceof ItemTool) {
                final String mat = ((ItemTool)item).func_77861_e();
                for (final Item.ToolMaterial tm : Item.ToolMaterial.values()) {
                    if (tm.toString().equals(mat)) {
                        tmp.merge(Aspect.TOOL, (tm.func_77996_d() + 1) * 4);
                    }
                }
            }
            else if (item instanceof ItemShears || item instanceof ItemHoe) {
                if (item.func_77612_l() <= Item.ToolMaterial.WOOD.func_77997_a()) {
                    tmp.merge(Aspect.TOOL, 4);
                }
                else if (item.func_77612_l() <= Item.ToolMaterial.STONE.func_77997_a() || item.func_77612_l() <= Item.ToolMaterial.GOLD.func_77997_a()) {
                    tmp.merge(Aspect.TOOL, 8);
                }
                else if (item.func_77612_l() <= Item.ToolMaterial.IRON.func_77997_a()) {
                    tmp.merge(Aspect.TOOL, 12);
                }
                else {
                    tmp.merge(Aspect.TOOL, 16);
                }
            }
            final String[] dyes = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
            final int[] ores = OreDictionary.getOreIDs(itemstack);
            if (ores != null && ores.length > 0) {
                Arrays.sort(dyes);
                for (final int od : ores) {
                    final String s = OreDictionary.getOreName(od);
                    if (s != null && Arrays.binarySearch(dyes, s) >= 0) {
                        tmp.merge(Aspect.SENSES, 5);
                        break;
                    }
                }
            }
            NBTTagList ench = itemstack.func_77986_q();
            if (item instanceof ItemEnchantedBook) {
                final ItemEnchantedBook itemEnchantedBook = (ItemEnchantedBook)item;
                ench = ItemEnchantedBook.func_92110_g(itemstack);
            }
            if (ench != null) {
                int var5 = 0;
                for (int var6 = 0; var6 < ench.func_74745_c(); ++var6) {
                    final short eid = ench.func_150305_b(var6).func_74765_d("id");
                    final short lvl = (short)(ench.func_150305_b(var6).func_74765_d("lvl") * 3);
                    final Enchantment e = Enchantment.func_185262_c((int)eid);
                    if (e != null) {
                        if (e == Enchantments.field_185299_g) {
                            tmp.merge(Aspect.WATER, lvl);
                        }
                        else if (e == Enchantments.field_180312_n) {
                            tmp.merge(Aspect.BEAST, lvl / 2).merge(Aspect.AVERSION, lvl / 2);
                        }
                        else if (e == Enchantments.field_185297_d) {
                            tmp.merge(Aspect.PROTECT, lvl / 2).merge(Aspect.ENTROPY, lvl / 2);
                        }
                        else if (e == Enchantments.field_185305_q) {
                            tmp.merge(Aspect.TOOL, lvl);
                        }
                        else if (e == Enchantments.field_180309_e) {
                            tmp.merge(Aspect.FLIGHT, lvl);
                        }
                        else if (e == Enchantments.field_77334_n) {
                            tmp.merge(Aspect.FIRE, lvl / 2).merge(Aspect.AVERSION, lvl / 2);
                        }
                        else if (e == Enchantments.field_77329_d) {
                            tmp.merge(Aspect.PROTECT, lvl / 2).merge(Aspect.FIRE, lvl / 2);
                        }
                        else if (e == Enchantments.field_185311_w) {
                            tmp.merge(Aspect.FIRE, lvl);
                        }
                        else if (e == Enchantments.field_185308_t) {
                            tmp.merge(Aspect.DESIRE, lvl);
                        }
                        else if (e == Enchantments.field_185312_x) {
                            tmp.merge(Aspect.CRAFT, lvl);
                        }
                        else if (e == Enchantments.field_180313_o) {
                            tmp.merge(Aspect.AIR, lvl);
                        }
                        else if (e == Enchantments.field_185304_p) {
                            tmp.merge(Aspect.DESIRE, lvl);
                        }
                        else if (e == Enchantments.field_185309_u) {
                            tmp.merge(Aspect.AVERSION, lvl);
                        }
                        else if (e == Enchantments.field_180308_g) {
                            tmp.merge(Aspect.PROTECT, lvl);
                        }
                        else if (e == Enchantments.field_180310_c) {
                            tmp.merge(Aspect.PROTECT, lvl);
                        }
                        else if (e == Enchantments.field_185310_v) {
                            tmp.merge(Aspect.AIR, lvl);
                        }
                        else if (e == Enchantments.field_185298_f) {
                            tmp.merge(Aspect.AIR, lvl);
                        }
                        else if (e == Enchantments.field_185302_k) {
                            tmp.merge(Aspect.AVERSION, lvl);
                        }
                        else if (e == Enchantments.field_185306_r) {
                            tmp.merge(Aspect.EXCHANGE, lvl);
                        }
                        else if (e == Enchantments.field_92091_k) {
                            tmp.merge(Aspect.AVERSION, lvl);
                        }
                        else if (e == Enchantments.field_185303_l) {
                            tmp.merge(Aspect.UNDEAD, lvl / 2).merge(Aspect.AVERSION, lvl / 2);
                        }
                        else if (e == Enchantments.field_185307_s) {
                            tmp.merge(Aspect.EARTH, lvl);
                        }
                        else if (e == Enchantments.field_185300_i) {
                            tmp.merge(Aspect.WATER, lvl);
                        }
                        else if (e == Enchantments.field_151370_z) {
                            tmp.merge(Aspect.DESIRE, lvl);
                        }
                        else if (e == Enchantments.field_151369_A) {
                            tmp.merge(Aspect.BEAST, lvl);
                        }
                        else if (e == Enchantments.field_185301_j) {
                            tmp.merge(Aspect.COLD, lvl);
                        }
                        else if (e == Enchantments.field_185296_A) {
                            tmp.merge(Aspect.CRAFT, lvl);
                        }
                        if (e.func_77324_c() == Enchantment.Rarity.UNCOMMON) {
                            var5 += 2;
                        }
                        if (e.func_77324_c() == Enchantment.Rarity.RARE) {
                            var5 += 4;
                        }
                        if (e.func_77324_c() == Enchantment.Rarity.VERY_RARE) {
                            var5 += 6;
                        }
                    }
                    var5 += lvl;
                }
                if (var5 > 0) {
                    tmp.merge(Aspect.MAGIC, var5);
                }
            }
        }
        return AspectHelper.cullTags(tmp);
    }
    
    public static void getPotionReagentsRecursive(final PotionType potion, final HashSet<ItemStack> hashSet) {
        for (int j = 0; j < PotionHelper.field_185213_a.size(); ++j) {
            final PotionHelper.MixPredicate mixpre = PotionHelper.field_185213_a.get(j);
            if (mixpre.field_185200_c instanceof PotionType && ((PotionType)mixpre.field_185200_c).getRegistryName() == potion.getRegistryName()) {
                try {
                    hashSet.add(mixpre.field_185199_b.func_193365_a()[0]);
                    if (mixpre.field_185198_a != PotionTypes.field_185230_b && mixpre.field_185198_a instanceof PotionType) {
                        getPotionReagentsRecursive((PotionType)mixpre.field_185198_a, hashSet);
                    }
                    break;
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static AspectList generateTags(final ItemStack is) {
        final AspectList temp = generateTags(is, new ArrayList<String>());
        return temp;
    }
    
    public static AspectList generateTags(final ItemStack is, ArrayList<String> history) {
        if (history == null) {
            history = new ArrayList<String>();
        }
        final ItemStack stack = is.func_77946_l();
        stack.func_190920_e(1);
        try {
            if (stack.func_77973_b().func_77645_m() || !stack.func_77973_b().func_77614_k()) {
                stack.func_77964_b(32767);
            }
        }
        catch (Exception ex) {}
        if (ThaumcraftApi.exists(stack)) {
            return getObjectTags(stack, history);
        }
        final String ss = stack.serializeNBT().toString();
        if (history.contains(ss)) {
            return null;
        }
        history.add(ss);
        if (history.size() < 100) {
            if (stack.func_77952_i() == 32767) {
                stack.func_77964_b(0);
            }
            AspectList ret = generateTagsFromRecipes(stack, history);
            ret = capAspects(ret, 500);
            ThaumcraftApi.registerObjectTag(is, ret);
            return ret;
        }
        return null;
    }
    
    private static AspectList generateTagsFromCrucibleRecipes(final ItemStack stack, final ArrayList<String> history) {
        final CrucibleRecipe cr = ThaumcraftApi.getCrucibleRecipe(stack);
        if (cr == null) {
            return null;
        }
        final AspectList ot = cr.getAspects().copy();
        final int ss = cr.getRecipeOutput().func_190916_E();
        final ItemStack cat = cr.getCatalyst().func_193365_a()[0];
        if (cat == null || cat.func_190926_b()) {
            return null;
        }
        final AspectList ot2 = getObjectTags(cat, history);
        final AspectList out = new AspectList();
        if (ot2 != null && ot2.size() > 0) {
            for (final Aspect tt : ot2.getAspects()) {
                out.add(tt, ot2.getAmount(tt));
            }
        }
        for (final Aspect tt : ot.getAspects()) {
            final int amt = (int)(Math.sqrt(ot.getAmount(tt)) / ss);
            out.add(tt, amt);
        }
        for (final Aspect as : out.getAspects()) {
            if (out.getAmount(as) <= 0) {
                out.remove(as);
            }
        }
        return out;
    }
    
    private static AspectList generateTagsFromInfusionRecipes(final ItemStack stack, final ArrayList<String> history) {
        final InfusionRecipe cr = ThaumcraftApi.getInfusionRecipe(stack);
        if (cr != null) {
            final AspectList ot = cr.getAspects().copy();
            final NonNullList<Ingredient> ingredients = (NonNullList<Ingredient>)NonNullList.func_191196_a();
            ingredients.add((Object)cr.getRecipeInput());
            ingredients.addAll((Collection)cr.getComponents());
            final AspectList out = new AspectList();
            final AspectList ot2 = getAspectsFromIngredients(ingredients, (ItemStack)cr.getRecipeOutput(), history);
            for (final Aspect tt : ot2.getAspects()) {
                out.add(tt, ot2.getAmount(tt));
            }
            for (final Aspect tt : ot.getAspects()) {
                final int amt = (int)(Math.sqrt(ot.getAmount(tt)) / ((ItemStack)cr.getRecipeOutput()).func_190916_E());
                out.add(tt, amt);
            }
            for (final Aspect as : out.getAspects()) {
                if (out.getAmount(as) <= 0) {
                    out.remove(as);
                }
            }
            return out;
        }
        return null;
    }
    
    private static AspectList generateTagsFromCraftingRecipes(final ItemStack stack, final ArrayList<String> history) {
        AspectList ret = null;
        int value = Integer.MAX_VALUE;
        for (final ResourceLocation key : CraftingManager.field_193380_a.func_148742_b()) {
            final IRecipe recipe = (IRecipe)CraftingManager.field_193380_a.func_82594_a((Object)key);
            if (recipe != null && recipe.func_77571_b() != null && Item.func_150891_b(recipe.func_77571_b().func_77973_b()) > 0) {
                if (recipe.func_77571_b().func_77973_b() == null) {
                    continue;
                }
                final int idR = (recipe.func_77571_b().func_77952_i() == 32767) ? 0 : recipe.func_77571_b().func_77952_i();
                final int idS = (stack.func_77952_i() == 32767) ? 0 : stack.func_77952_i();
                if (recipe.func_77571_b().func_77973_b() != stack.func_77973_b() || idR != idS) {
                    continue;
                }
                final ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>();
                AspectList ph = new AspectList();
                final int cval = 0;
                try {
                    ph = getAspectsFromIngredients((NonNullList<Ingredient>)recipe.func_192400_c(), recipe.func_77571_b(), history);
                    if (recipe instanceof IArcaneRecipe) {
                        final IArcaneRecipe ar = (IArcaneRecipe)recipe;
                        if (ar.getVis() > 0) {
                            ph.add(Aspect.MAGIC, (int)(Math.sqrt(1 + ar.getVis() / 2) / recipe.func_77571_b().func_190916_E()));
                        }
                    }
                    for (final Aspect as : ph.copy().getAspects()) {
                        if (ph.getAmount(as) <= 0) {
                            ph.remove(as);
                        }
                    }
                    if (ph.visSize() >= value || ph.visSize() <= 0) {
                        continue;
                    }
                    ret = ph;
                    value = ph.visSize();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
    
    private static AspectList getAspectsFromIngredients(final NonNullList<Ingredient> nonNullList, final ItemStack recipeOut, final ArrayList<String> history) {
        final AspectList out = new AspectList();
        final AspectList mid = new AspectList();
        for (final Ingredient is : nonNullList) {
            if (is.func_193365_a().length <= 0) {
                continue;
            }
            final AspectList obj = getObjectTags(is.func_193365_a()[0], history);
            if (is.func_193365_a()[0].func_77973_b().func_77668_q() != null) {
                if (is.func_193365_a()[0].func_77973_b().func_77668_q() == is.func_193365_a()[0].func_77973_b()) {
                    continue;
                }
                final AspectList objC = getObjectTags(new ItemStack(is.func_193365_a()[0].func_77973_b(), 1, 32767), history);
                for (final Aspect as : objC.getAspects()) {
                    out.reduce(as, objC.getAmount(as));
                }
            }
            if (obj == null) {
                continue;
            }
            for (final Aspect as2 : obj.getAspects()) {
                if (as2 != null) {
                    mid.add(as2, obj.getAmount(as2));
                }
            }
        }
        for (final Aspect as3 : mid.getAspects()) {
            if (as3 != null) {
                float v = mid.getAmount(as3) * 0.75f / recipeOut.func_190916_E();
                if (v < 1.0f && v > 0.75) {
                    v = 1.0f;
                }
                out.add(as3, (int)v);
            }
        }
        for (final Aspect as3 : out.getAspects()) {
            if (out.getAmount(as3) <= 0) {
                out.remove(as3);
            }
        }
        return out;
    }
    
    private static AspectList generateTagsFromRecipes(final ItemStack stack, final ArrayList<String> history) {
        AspectList ret = null;
        final int value = 0;
        ret = generateTagsFromCrucibleRecipes(stack, history);
        if (ret != null) {
            return ret;
        }
        ret = generateTagsFromInfusionRecipes(stack, history);
        if (ret != null) {
            return ret;
        }
        ret = generateTagsFromCraftingRecipes(stack, history);
        return ret;
    }
}
