package thaumcraft.common.container;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import thaumcraft.common.items.casters.*;

public class InventoryFocusPouch extends InventoryBasic
{
    public InventoryFocusPouch(final IInventoryChangedListener listener) {
        super("container.focuspouch", false, 18);
        this.func_110134_a(listener);
    }
    
    public int func_70297_j_() {
        return 1;
    }
    
    public boolean func_94041_b(final int i, final ItemStack itemstack) {
        return !itemstack.func_190926_b() && itemstack.func_77973_b() instanceof ItemFocus;
    }
}
