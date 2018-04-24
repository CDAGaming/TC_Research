package thaumcraft.common.lib.crafting;

import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;

public class RecipeTripleMeatTreat extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    public boolean func_77569_a(final InventoryCrafting inv, final World worldIn) {
        boolean sugar = false;
        final ArrayList<Integer> meats = new ArrayList<Integer>();
        for (int a = 0; a < 3; ++a) {
            for (int b = 0; b < 3; ++b) {
                if (inv.func_70463_b(a, b) != null) {
                    final ItemStack stack = inv.func_70463_b(a, b).func_77946_l();
                    if (stack.func_77973_b() == Items.field_151102_aT && sugar) {
                        return false;
                    }
                    if (stack.func_77973_b() == Items.field_151102_aT && !sugar) {
                        sugar = true;
                    }
                    else {
                        if (stack.func_77973_b() != ItemsTC.chunks) {
                            return false;
                        }
                        if (meats.contains(stack.func_77952_i()) || meats.size() >= 3) {
                            return false;
                        }
                        meats.add(stack.func_77952_i());
                    }
                }
            }
        }
        return sugar && meats.size() == 3;
    }
    
    public ItemStack func_77572_b(final InventoryCrafting inv) {
        return new ItemStack(ItemsTC.tripleMeatTreat);
    }
    
    public ItemStack func_77571_b() {
        return new ItemStack(ItemsTC.tripleMeatTreat);
    }
    
    public boolean func_194133_a(final int width, final int height) {
        return width * height >= 4;
    }
}
