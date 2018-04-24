package thaumcraft.common.lib.crafting;

import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import thaumcraft.api.items.*;
import thaumcraft.common.items.resources.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;

public class RecipeMagicDust extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    public boolean func_77569_a(final InventoryCrafting inv, final World worldIn) {
        boolean bowl = false;
        boolean flint = false;
        boolean redstone = false;
        final ArrayList<String> crystals = new ArrayList<String>();
        for (int a = 0; a < 3; ++a) {
            for (int b = 0; b < 3; ++b) {
                if (inv.func_70463_b(a, b) != null) {
                    if (!inv.func_70463_b(a, b).func_190926_b()) {
                        final ItemStack stack = inv.func_70463_b(a, b).func_77946_l();
                        if (stack.func_77973_b() == Items.field_151054_z && bowl) {
                            return false;
                        }
                        if (stack.func_77973_b() == Items.field_151054_z && !bowl) {
                            bowl = true;
                        }
                        else {
                            if (stack.func_77973_b() == Items.field_151145_ak && flint) {
                                return false;
                            }
                            if (stack.func_77973_b() == Items.field_151145_ak && !flint) {
                                flint = true;
                            }
                            else {
                                if (stack.func_77973_b() == Items.field_151137_ax && redstone) {
                                    return false;
                                }
                                if (stack.func_77973_b() == Items.field_151137_ax && !redstone) {
                                    redstone = true;
                                }
                                else {
                                    if (stack.func_77973_b() != ItemsTC.crystalEssence) {
                                        return false;
                                    }
                                    final ItemCrystalEssence ice = (ItemCrystalEssence)stack.func_77973_b();
                                    if (crystals.contains(ice.getAspects(stack).getAspects()[0].getTag()) || crystals.size() >= 3) {
                                        return false;
                                    }
                                    crystals.add(ice.getAspects(stack).getAspects()[0].getTag());
                                }
                            }
                        }
                    }
                }
            }
        }
        return bowl && redstone && flint && crystals.size() == 3;
    }
    
    public ItemStack func_77572_b(final InventoryCrafting inv) {
        return new ItemStack(ItemsTC.salisMundus);
    }
    
    public boolean func_194133_a(final int width, final int height) {
        return width * height >= 6;
    }
    
    public ItemStack func_77571_b() {
        return new ItemStack(ItemsTC.salisMundus);
    }
    
    public NonNullList<ItemStack> func_179532_b(final InventoryCrafting inv) {
        final NonNullList<ItemStack> ret = (NonNullList<ItemStack>)NonNullList.func_191197_a(inv.func_70302_i_(), (Object)ItemStack.field_190927_a);
        for (int i = 0; i < ret.size(); ++i) {
            final ItemStack itemstack = inv.func_70301_a(i);
            ItemStack itemstack2 = ForgeHooks.getContainerItem(itemstack);
            if (itemstack != null && !itemstack.func_190926_b() && (itemstack.func_77973_b() == Items.field_151145_ak || itemstack.func_77973_b() == Items.field_151054_z)) {
                final ItemStack is = itemstack.func_77946_l();
                is.func_190920_e(1);
                itemstack2 = is;
            }
            ret.set(i, (Object)itemstack2);
        }
        return ret;
    }
}
