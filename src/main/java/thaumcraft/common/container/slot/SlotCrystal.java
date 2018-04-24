package thaumcraft.common.container.slot;

import thaumcraft.api.aspects.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import thaumcraft.common.items.resources.*;

public class SlotCrystal extends Slot
{
    private Aspect aspect;
    
    public SlotCrystal(final Aspect aspect, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.aspect = aspect;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return isValidCrystal(stack, this.aspect);
    }
    
    public static boolean isValidCrystal(final ItemStack stack, final Aspect aspect) {
        return stack != null && !stack.func_190926_b() && stack.func_77973_b() != null && stack.func_77973_b() instanceof ItemCrystalEssence && ((ItemCrystalEssence)stack.func_77973_b()).getAspects(stack).getAspects()[0] == aspect;
    }
}
