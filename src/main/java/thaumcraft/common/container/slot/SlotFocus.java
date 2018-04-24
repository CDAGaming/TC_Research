package thaumcraft.common.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import thaumcraft.common.items.casters.*;

public class SlotFocus extends Slot
{
    int limit;
    
    public SlotFocus(final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.limit = 64;
    }
    
    public SlotFocus(final int limit, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.limit = 64;
        this.limit = limit;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return stack != null && !stack.func_190926_b() && stack.func_77973_b() != null && stack.func_77973_b() instanceof ItemFocus;
    }
    
    public int func_75219_a() {
        return this.limit;
    }
}
