package thaumcraft.common.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class SlotLimitedByClass extends Slot
{
    Class clazz;
    int limit;
    
    public SlotLimitedByClass(final Class clazz, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.clazz = Object.class;
        this.limit = 64;
        this.clazz = clazz;
    }
    
    public SlotLimitedByClass(final Class clazz, final int limit, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.clazz = Object.class;
        this.limit = 64;
        this.clazz = clazz;
        this.limit = limit;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return !stack.func_190926_b() && this.clazz.isAssignableFrom(stack.func_77973_b().getClass());
    }
    
    public int func_75219_a() {
        return this.limit;
    }
}
