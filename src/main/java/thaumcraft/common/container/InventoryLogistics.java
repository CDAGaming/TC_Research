package thaumcraft.common.container;

import net.minecraft.inventory.*;

public class InventoryLogistics extends InventoryBasic
{
    public InventoryLogistics(final IInventoryChangedListener listener) {
        super("container.logistics", false, 81);
        this.func_110134_a(listener);
    }
    
    public int func_70297_j_() {
        return Integer.MAX_VALUE;
    }
}
