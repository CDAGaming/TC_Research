package thaumcraft.common.container.slot;

import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class SlotLimitedByItemstack extends Slot
{
    ItemStack limitItem;
    
    public SlotLimitedByItemstack(final ItemStack item, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.limitItem = null;
        this.limitItem = item;
    }
    
    public boolean func_75214_a(final ItemStack stack1) {
        return stack1.func_77969_a(this.limitItem);
    }
}
