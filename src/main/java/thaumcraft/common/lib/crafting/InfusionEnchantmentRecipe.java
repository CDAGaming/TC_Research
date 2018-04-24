package thaumcraft.common.lib.crafting;

import thaumcraft.api.crafting.*;
import thaumcraft.common.lib.enchantment.*;
import net.minecraft.item.crafting.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraftforge.common.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.nbt.*;
import thaumcraft.api.aspects.*;
import baubles.api.*;

public class InfusionEnchantmentRecipe extends InfusionRecipe
{
    EnumInfusionEnchantment enchantment;
    
    public InfusionEnchantmentRecipe(final EnumInfusionEnchantment ench, final AspectList as, final Object... components) {
        super(ench.research, null, 4, as, Ingredient.field_193370_a, components);
        this.enchantment = ench;
    }
    
    public InfusionEnchantmentRecipe(final InfusionEnchantmentRecipe recipe, final ItemStack in) {
        super(recipe.enchantment.research, null, recipe.instability, recipe.aspects, in, recipe.components.toArray());
        this.enchantment = recipe.enchantment;
    }
    
    @Override
    public boolean matches(final List<ItemStack> input, final ItemStack central, final World world, final EntityPlayer player) {
        if (central == null || central.func_190926_b() || !ThaumcraftCapabilities.knowsResearch(player, this.research)) {
            return false;
        }
        if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(central, this.enchantment) >= this.enchantment.maxLevel) {
            return false;
        }
        if (!this.enchantment.toolClasses.contains("all")) {
            final Multimap<String, AttributeModifier> itemMods = (Multimap<String, AttributeModifier>)central.func_111283_C(EntityEquipmentSlot.MAINHAND);
            boolean cool = false;
            if (itemMods != null && itemMods.containsKey((Object)SharedMonsterAttributes.field_111264_e.func_111108_a()) && this.enchantment.toolClasses.contains("weapon")) {
                cool = true;
            }
            if (!cool && central.func_77973_b() instanceof ItemTool) {
                final Set<String> tcs = (Set<String>)((ItemTool)central.func_77973_b()).getToolClasses(central);
                for (final String tc : tcs) {
                    if (this.enchantment.toolClasses.contains(tc)) {
                        cool = true;
                        break;
                    }
                }
            }
            if (!cool && central.func_77973_b() instanceof ItemArmor) {
                String at = "none";
                switch (((ItemArmor)central.func_77973_b()).field_77881_a) {
                    case HEAD: {
                        at = "helm";
                        break;
                    }
                    case CHEST: {
                        at = "chest";
                        break;
                    }
                    case LEGS: {
                        at = "legs";
                        break;
                    }
                    case FEET: {
                        at = "boots";
                        break;
                    }
                }
                if (this.enchantment.toolClasses.contains("armor") || this.enchantment.toolClasses.contains(at)) {
                    cool = true;
                }
            }
            if (!cool && central.func_77973_b() instanceof IBauble) {
                String at = "none";
                switch (((IBauble)central.func_77973_b()).getBaubleType(central)) {
                    case AMULET: {
                        at = "amulet";
                        break;
                    }
                    case BELT: {
                        at = "belt";
                        break;
                    }
                    case RING: {
                        at = "ring";
                        break;
                    }
                }
                if (this.enchantment.toolClasses.contains("bauble") || this.enchantment.toolClasses.contains(at)) {
                    cool = true;
                }
            }
            if (!cool && central.func_77973_b() instanceof IRechargable && this.enchantment.toolClasses.contains("chargable")) {
                cool = true;
            }
            if (!cool) {
                return false;
            }
        }
        return (this.getRecipeInput() == Ingredient.field_193370_a || this.getRecipeInput().apply(central)) && RecipeMatcher.findMatches((List)input, (List)this.getComponents()) != null;
    }
    
    @Override
    public Object getRecipeOutput(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        if (input == null) {
            return null;
        }
        final ItemStack out = input.func_77946_l();
        final int cl = EnumInfusionEnchantment.getInfusionEnchantmentLevel(out, this.enchantment);
        if (cl >= this.enchantment.maxLevel) {
            return null;
        }
        final List<EnumInfusionEnchantment> el = EnumInfusionEnchantment.getInfusionEnchantments(input);
        final Random rand = new Random(System.nanoTime());
        if (rand.nextInt(10) < el.size()) {
            int base = 1;
            if (input.func_77942_o()) {
                base += input.func_77978_p().func_74771_c("TC.WARP");
            }
            out.func_77983_a("TC.WARP", (NBTBase)new NBTTagByte((byte)base));
        }
        EnumInfusionEnchantment.addInfusionEnchantment(out, this.enchantment, cl + 1);
        return out;
    }
    
    @Override
    public AspectList getAspects(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        final AspectList out = new AspectList();
        if (input == null || input.func_190926_b()) {
            return out;
        }
        final int cl = EnumInfusionEnchantment.getInfusionEnchantmentLevel(input, this.enchantment) + 1;
        if (cl > this.enchantment.maxLevel) {
            return out;
        }
        final List<EnumInfusionEnchantment> el = EnumInfusionEnchantment.getInfusionEnchantments(input);
        int otherEnchantments = el.size();
        if (el.contains(this.enchantment)) {
            --otherEnchantments;
        }
        final float modifier = cl + otherEnchantments * 0.33f;
        for (final Aspect a : this.getAspects().getAspects()) {
            out.add(a, (int)(this.getAspects().getAmount(a) * modifier));
        }
        return out;
    }
}
