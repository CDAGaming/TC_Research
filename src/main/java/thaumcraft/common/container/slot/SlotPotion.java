package thaumcraft.common.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class SlotPotion extends Slot
{
    int limit;
    
    public SlotPotion(final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.limit = 64;
    }
    
    public SlotPotion(final int limit, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.limit = 64;
        this.limit = limit;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return stack != null && !stack.func_190926_b() && isValidPotion(stack);
    }
    
    public static boolean isValidPotion(final ItemStack stack) {
        if (stack.func_77973_b() != Items.field_151068_bn && stack.func_77973_b() != Items.field_185156_bI) {
            if (stack.func_77973_b() != Items.field_185155_bH) {
                return false;
            }
        }
        try {
            final PotionType potion = PotionUtils.func_185191_c(stack);
            return potion != null && potion != PotionTypes.field_185230_b && potion != PotionTypes.field_185233_e && potion != PotionTypes.field_185229_a && potion != PotionTypes.field_185231_c && potion != PotionTypes.field_185232_d;
        }
        catch (Exception ex) {}
        return false;
    }
    
    public int func_75219_a() {
        return this.limit;
    }
}
