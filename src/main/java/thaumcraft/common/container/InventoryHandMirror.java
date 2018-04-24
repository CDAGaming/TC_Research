package thaumcraft.common.container;

import net.minecraft.inventory.*;

public class InventoryHandMirror extends InventoryBasic
{
    public InventoryHandMirror(final IInventoryChangedListener listener) {
        super("container.handmirror", false, 1);
        this.func_110134_a(listener);
    }
}
