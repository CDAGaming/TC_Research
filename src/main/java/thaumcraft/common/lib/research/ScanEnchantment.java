package thaumcraft.common.lib.research;

import net.minecraft.entity.player.*;
import thaumcraft.api.research.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import java.util.*;

public class ScanEnchantment implements IScanThing
{
    Enchantment enchantment;
    
    public ScanEnchantment(final Enchantment ench) {
        this.enchantment = ench;
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        return this.getEnchantment(player, obj) != null;
    }
    
    private Enchantment getEnchantment(final EntityPlayer player, final Object obj) {
        if (obj == null) {
            return null;
        }
        final ItemStack is = ScanningManager.getItemFromParms(player, obj);
        if (is != null && !is.func_190926_b()) {
            final Map<Enchantment, Integer> e = (Map<Enchantment, Integer>)EnchantmentHelper.func_82781_a(is);
            for (final Enchantment ench : e.keySet()) {
                if (ench == this.enchantment) {
                    return ench;
                }
            }
        }
        return null;
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object obj) {
        return "!" + this.enchantment.func_77320_a();
    }
}
