package thaumcraft.common.lib.crafting;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import java.util.*;
import thaumcraft.common.items.armor.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;

public class RecipesRobeArmorDyes extends RecipesArmorDyes
{
    public boolean func_77569_a(final InventoryCrafting par1InventoryCrafting, final World par2World) {
        ItemStack itemstack = null;
        final ArrayList arraylist = new ArrayList();
        for (int i = 0; i < par1InventoryCrafting.func_70302_i_(); ++i) {
            final ItemStack itemstack2 = par1InventoryCrafting.func_70301_a(i);
            if (itemstack2 != null && !itemstack2.func_190926_b()) {
                if (itemstack2.func_77973_b() instanceof ItemArmor) {
                    final ItemArmor itemarmor = (ItemArmor)itemstack2.func_77973_b();
                    if (!(itemarmor instanceof ItemRobeArmor) || itemstack != null) {
                        return false;
                    }
                    itemstack = itemstack2;
                }
                else {
                    if (itemstack2.func_77973_b() != Items.field_151100_aR) {
                        return false;
                    }
                    arraylist.add(itemstack2);
                }
            }
        }
        return itemstack != null && !arraylist.isEmpty();
    }
    
    public ItemStack func_77572_b(final InventoryCrafting par1InventoryCrafting) {
        ItemStack itemstack = ItemStack.field_190927_a;
        final int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;
        for (int k = 0; k < par1InventoryCrafting.func_70302_i_(); ++k) {
            final ItemStack itemstack2 = par1InventoryCrafting.func_70301_a(k);
            if (itemstack2 != null && !itemstack2.func_190926_b()) {
                if (itemstack2.func_77973_b() instanceof ItemArmor) {
                    itemarmor = (ItemArmor)itemstack2.func_77973_b();
                    if (!(itemarmor instanceof ItemRobeArmor) || itemstack != null) {
                        return ItemStack.field_190927_a;
                    }
                    itemstack = itemstack2.func_77946_l();
                    itemstack.func_190920_e(1);
                    if (itemarmor.func_82816_b_(itemstack2)) {
                        final int l = itemarmor.func_82814_b(itemstack);
                        final float f = (l >> 16 & 0xFF) / 255.0f;
                        final float f2 = (l >> 8 & 0xFF) / 255.0f;
                        final float f3 = (l & 0xFF) / 255.0f;
                        i += (int)(Math.max(f, Math.max(f2, f3)) * 255.0f);
                        aint[0] += (int)(f * 255.0f);
                        aint[1] += (int)(f2 * 255.0f);
                        aint[2] += (int)(f3 * 255.0f);
                        ++j;
                    }
                }
                else {
                    if (itemstack2.func_77973_b() != Items.field_151100_aR) {
                        return ItemStack.field_190927_a;
                    }
                    final float[] afloat = EntitySheep.func_175513_a(EnumDyeColor.func_176766_a(itemstack2.func_77952_i()));
                    final int j2 = (int)(afloat[0] * 255.0f);
                    final int k2 = (int)(afloat[1] * 255.0f);
                    final int i2 = (int)(afloat[2] * 255.0f);
                    i += Math.max(j2, Math.max(k2, i2));
                    final int[] array = aint;
                    final int n = 0;
                    array[n] += j2;
                    final int[] array2 = aint;
                    final int n2 = 1;
                    array2[n2] += k2;
                    final int[] array3 = aint;
                    final int n3 = 2;
                    array3[n3] += i2;
                    ++j;
                }
            }
        }
        if (itemarmor == null) {
            return ItemStack.field_190927_a;
        }
        int k = aint[0] / j;
        int l2 = aint[1] / j;
        int l = aint[2] / j;
        final float f = i / j;
        final float f2 = Math.max(k, Math.max(l2, l));
        k = (int)(k * f / f2);
        l2 = (int)(l2 * f / f2);
        l = (int)(l * f / f2);
        int i2 = (k << 8) + l2;
        i2 = (i2 << 8) + l;
        itemarmor.func_82813_b(itemstack, i2);
        return itemstack;
    }
}
